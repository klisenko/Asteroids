package com.atomicsware;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Asteroids extends JPanel implements KeyListener, ActionListener {
	private Timer timer;
	private double angle = 0;
	private double angleBullet = 0;
	private double positionX = 0;
	private double positionY = 0;
	private double velocityX = 0;
	private double velocityY = 0;
	private boolean thrust = false;
	private boolean ccw = false;
	private boolean cw = false;
	private boolean fire = false;
	private double delta = 0;
	private int h = getHeight();
	private int w = getWidth();
	private int[] xPoints = {0, 10, -10};
	private int[] yPoints = {-15, 15, 15};
	private int[] xPointsWingR = {7, 10, 12, 10};
	private int[] yPointsWingR = {5, 5, 7, 14};
	private int[] xPointsWingL = {-7, -10, -12, -10};
	private int[] yPointsWingL = {5, 5, 7, 14};
	private int[] xPointsThrust = {6, 4, 3, 2, 1, 0, -1, -2, -3, -4, -6};
	private int[] yPointsThrust = {15, 23, 21, 29, 27, 31, 27, 29, 21, 23, 15};
	private float hBullet = 10;
	private float wBullet = 10;
	private float xBullet = -wBullet / 2;
	private float yBullet = -15 - hBullet / 2;
	private double xAbsBullet;
	private double yAbsBullet;
	private int bulletSpeed = 8;
	private Polygon t =new Polygon(xPoints, yPoints, 3);
	private Polygon t2 = new Polygon(xPointsWingR, yPointsWingR, 4);
	private Polygon t3 = new Polygon(xPointsWingL, yPointsWingL, 4);
	private Polygon t4 = new Polygon(xPointsThrust, yPointsThrust, 11);	
	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private static final int BULLET_LIMIT = 80;
	private int numBullets = 0;
	
	private ArrayList<Rock> rocks = new ArrayList<Rock>();
	private int[] xPointsRock = {-1, 1, 4, 5, 2, -1, -4, -5};
	private int[] yPointsRock = {-3, -2, -2, 1, 3, 1, 3, -1};
	private Rock r1 = new Rock(xPointsRock, yPointsRock, 8, 100, 100, 0.0, 2, 0.349, .002);
	private Rock r2 = new Rock(xPointsRock, yPointsRock, 8, 150, 200, 0.3, 1, -.7854, -.007);
	private Rock r3 = new Rock(xPointsRock, yPointsRock, 8, 300, 350, 0.6, 3, .7854, .01);
	
	
	//Rock(int[] xPoints, int[] yPoints, int nPoints, int xPos, int yPos, double angle, int vel)
	
	//private double angRock = 0;
	private double rockSpeed = 2;
	  
	public Asteroids(JFrame j) {
		timer = new Timer(0, this);
	    timer.start();
	    j.addKeyListener(this);
	    rocks.add(r1);
	    rocks.add(r2);
	    rocks.add(r3);
	}
	
	public static void main(String[] args) {
	    JFrame frame = new JFrame("Asteroids Ship");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1000, 1000);
	    frame.setLocationRelativeTo(null);
	    frame.add(new Asteroids(frame));
	    frame.setVisible(true);
	  }
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
	    h = getHeight();
	    w = getWidth();	    
	    
	    //draw ship
	    drawItem(g, 2.0f, Color.RED, 2, w / 2 - positionX, h / 2 - positionY, angle, t, false);
	    drawItem(g, 2.0f, Color.RED, 2, w / 2 - positionX, h / 2 - positionY, angle, t2, false);
	    drawItem(g, 2.0f, Color.RED, 2, w / 2 - positionX, h / 2 - positionY, angle, t3, false);
	    
	    //draw ship thrust if it is activated
	    if(thrust) {
	    	drawItem(g, 2.0f, Color.RED, 2, w / 2 - positionX, h / 2 - positionY, angle, t4, true);
	    }
	    
	    drawRocks(g); //draw the asteroids
	    drawBullets(g); //draw the bullets if there are any
	    
	    Area areaShip = shipArea(); //get the area of the ship
	    
	    if(!rocks.isEmpty()) {
	    	boolean shipRock = false;
	    	//ArrayList<Rock> removeRocks = new ArrayList<Rock>();
	    	
	    	for (Rock rock : rocks) {
	    		Area areaRock = rockArea(rock);
	    		shipRock = checkRockShipHit(areaRock, areaShip);
	    		if(!shipRock) {
	    			Graphics2D g5d = (Graphics2D) g.create();
		    		g5d.setColor(Color.PINK);
		    		g5d.fill(areaRock);
		    		g5d.fill(areaShip);
		    		g5d.dispose();
	    		}
	    		if(!bullets.isEmpty()) {
	    			for (Bullet shot : bullets) {
	    				boolean b = areaRock.contains(shot.getXtran(), shot.getYtran());
	    				if(b) {
	    					rock.setUnhit(false);
	    					shot.setUnhit(false);
//	    	    			Graphics2D g5d = (Graphics2D) g.create();
//	    		    		g5d.setColor(Color.PINK);
//	    		    		g5d.fill(areaRock);
//	    		    		g5d.dispose();
	    	    		}	    				
	    			}
	    		}	    		
	    	}
	    	//Remove rocks that have been hit
	    	for (Iterator<Rock> iterator = rocks.iterator(); iterator.hasNext();) {
	    	    Rock rock = iterator.next();
	    	    if (!rock.isUnhit()) {
	    	        iterator.remove();
	    	    }
	    	}
	    	
	    	//Remove bullets that have hit rocks
	    	for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
	    	    Bullet bullet = iterator.next();
	    	    if (!bullet.isUnhit()) {
	    	        iterator.remove();
	    	    }
	    	}
	    	
	    }
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		wrapShip();
		checkThrust();
		checkRotation();
		checkBullets();
		checkRocks();
		//angRock = angRock + .002;
	    repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)	
	 * up=38, down=40, left=37, right=39
	 */
	@Override
	public void keyPressed(KeyEvent e) {		
		int i = e.getKeyCode();
		
		if(i == 32 && fire == false) {
			
			xAbsBullet = w / 2 - positionX;
			yAbsBullet = h / 2 - positionY;	
			angleBullet = angle;
			fire = true;
			if(numBullets < BULLET_LIMIT) {
				bullets.add(new Bullet(xBullet, yBullet, wBullet, hBullet, angleBullet, 1500, xAbsBullet, yAbsBullet, Color.BLUE));
				numBullets = numBullets + 1;
			}			
		}
		
		if(i == 37) {
			ccw = true;
			cw = false;
		}
		if(i == 39) {
			cw = true;
			ccw = false;
		}
		if(i == 38) {
			thrust = true;
		}
	    repaint();		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//char c = e.getKeyChar();
		int i = e.getKeyCode();
		
		if(i == 32) {
			fire = false;
		}
		
		if(i == 38) {
			thrust = false;
		}
		if(i == 37) {
			ccw = false;
		}
		if(i == 39) {
			cw = false;
		}		
	    repaint();		
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}
	
	private void wrapShip() {

		double posXabs = w / 2 - positionX;
		double posYabs = h / 2 - positionY;
		
		if(posXabs <= 0) {
			positionX = -(w / 2);
		}
		else if(posXabs >= w) {
			positionX = w / 2;
		}
		
		if(posYabs <= 0) {
			positionY = -(h / 2);
		}
		else if(posYabs >= h) {
			positionY = h / 2;
		}
	}
	
	private void checkThrust() {
		if(thrust) {
			velocityX = velocityX + .02 * Math.sin(angle);
			velocityY = velocityY + .02 * Math.cos(angle);
		}
		positionX = positionX - velocityX;
		positionY = positionY + velocityY;
	}
	
	private void checkRotation() {
		if(ccw && cw) {
			delta = 0;
		}
		else if(!ccw && !cw) {
			delta = 0;
		}
		else if(ccw) {
			delta = -.02;
		}
		else {
			delta = .02;
		}
		angle = angle + delta;
	}
	
	private void checkBullets() {
		if(!bullets.isEmpty()) {
			if (bullets.get(0).getTime() <= 0) {
				bullets.remove(0);
				numBullets = numBullets - 1;
			}
		}
		
		if(!bullets.isEmpty()) {			
			for (Bullet shot : bullets) {
				double xAbsB = shot.getXtran();
				if(xAbsB <= 0) {
					shot.setXtran(w + bulletSpeed * Math.sin(shot.getAngle()));
				}
				else if(xAbsB >= w) {
					shot.setXtran(bulletSpeed * Math.sin(shot.getAngle()));;
				}
				
				double yAbsB = shot.getYtran();
				if(yAbsB <= 0) {
					shot.setYtran(h - bulletSpeed * Math.cos(shot.getAngle()));
				}
				else if(yAbsB >= h) {
					shot.setYtran(-bulletSpeed * Math.cos(shot.getAngle()));
				}				
				shot.setXtran(shot.getXtran() + bulletSpeed * Math.sin(shot.getAngle()));
				shot.setYtran(shot.getYtran() - bulletSpeed * Math.cos(shot.getAngle()));
				shot.setTime(shot.getTime() - 1);
			}
		}
	}
	
	private void checkRocks() {		
		if(!rocks.isEmpty()) {			
			for (Rock rock : rocks) {
				double xAbsR = rock.getxPos();
				if(xAbsR <= 0) {
					rock.setxPos(w + rockSpeed * Math.sin(rock.getAngPath()));
				}
				else if(xAbsR >= w) {
					rock.setxPos(rockSpeed * Math.sin(rock.getAngPath()));
				}
				
				double yAbsB = rock.getyPos();
				if(yAbsB <= 0) {
					rock.setyPos(h - rockSpeed * Math.cos(rock.getAngPath()));
				}
				else if(yAbsB >= h) {
					rock.setyPos(-rockSpeed * Math.cos(rock.getAngPath()));
				}				
				rock.setxPos(rock.getxPos() + rockSpeed * Math.sin(rock.getAngPath()));
				
				//System.out.println(rock.getxPos());
				rock.setyPos(rock.getyPos() - rockSpeed * Math.cos(rock.getAngPath()));
			}
		}
	}
	
	private void drawRocks(Graphics g) {
		if(!rocks.isEmpty()) {
	    	for (Rock rock : rocks) {
	    		rock.setAngle(rock.getAngle() + rock.getAngRate());
	    		drawItem(g, 0.2f, Color.GREEN, 25, rock.getxPos(), rock.getyPos(), rock.getAngle(), rock, false);	    		
	    	}
	    }
	}
	
	private void drawItem(Graphics g, float stroke, Color c, int scale, double x, double y, double angle, Shape s, boolean fill) {
		BasicStroke str = new BasicStroke(stroke);
		Graphics2D gd = (Graphics2D) g.create();
		gd.translate(x, y);
		gd.rotate(angle);
		gd.setStroke(str);
		gd.setColor(c);
		gd.scale(scale, scale);
		if(fill) {
			gd.fill(s);
		}
		else {
			gd.draw(s);
		}
		gd.dispose();
	}
	
	private boolean checkRockShipHit(Area areaRock, Area ship) {
		Area areaIntersect = (Area)ship.clone();
		areaIntersect.intersect(areaRock);
		boolean shipRock = areaIntersect.isEmpty();
		return shipRock;		
	}
	
	private Area rockArea(Rock rock) {
		Area areaRock = new Area(rock);
		AffineTransform atArea = new AffineTransform();
		atArea.translate((int)rock.getxPos(), (int)rock.getyPos());
		atArea.rotate(rock.getAngle());
		atArea.scale(25, 25);
		areaRock.transform(atArea);
		return areaRock;
	}
	
	private void drawBullets(Graphics g) {
		if(!bullets.isEmpty()) {
	    	for (Bullet shot : bullets) {	    		
	    		drawItem(g, 2.0f, shot.getC(), 2, shot.getXtran(), shot.getYtran(), shot.getAngle(), shot, false);	    		
	    	}
	    }
	}
	
	private Area shipArea() {
		Area areaShip = new Area(t);
    	Area areaWingR = new Area(t2);
    	Area areaWingL = new Area(t3);
    	areaShip.add(areaWingR);
    	areaShip.add(areaWingL);
    	AffineTransform shipArea = new AffineTransform();
    	shipArea.translate(w / 2 - positionX, h / 2 - positionY);
    	shipArea.rotate(angle);
    	shipArea.scale(2, 2);
    	areaShip.transform(shipArea);
    	return areaShip;
	}
	
	
	
	
	
	
}
