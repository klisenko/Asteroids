package com.atomicsware;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Asteroids extends JPanel implements KeyListener, ActionListener {
	Timer timer;
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
	private int bulletSpeed = 2;
	private Polygon t =new Polygon(xPoints, yPoints, 3);
	private Polygon t2 = new Polygon(xPointsWingR, yPointsWingR, 4);
	private Polygon t3 = new Polygon(xPointsWingL, yPointsWingL, 4);
	private Polygon t4 = new Polygon(xPointsThrust, yPointsThrust, 11);	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	  
	public Asteroids(JFrame j) {
		timer = new Timer(0, this);
	    timer.start();
	    j.addKeyListener(this);
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
	    Graphics2D g2d = (Graphics2D) g.create();
	    BasicStroke s = new BasicStroke((float) 2.0);
	    g2d.setStroke(s);
	    //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    //g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	    g2d.translate(w / 2 - positionX, h / 2 - positionY);
	    g2d.rotate(angle);
	    g2d.setColor(Color.red);
	    g2d.scale(2, 2);
	    g2d.draw(t);
	    g2d.draw(t2);
	    g2d.draw(t3);
	    
	    if(thrust) {
	    	g2d.fill(t4);
	    }
	    
	    g2d.dispose();

	    if(!bullets.isEmpty()) {
	    	for (Bullet shot : bullets) {
	    		Graphics2D g3d = (Graphics2D) g.create();
	    		g3d.translate(shot.getXtran(), shot.getYtran());
	    		g3d.rotate(shot.getAngle());
	    		g3d.setStroke(s);
	    		g3d.setColor(Color.blue);
	    		g2d.scale(2, 2);
	    		g3d.draw(shot);
	    		g3d.dispose();
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
			bullets.add(new Bullet(xBullet, yBullet, wBullet, hBullet, angleBullet, 1500, xAbsBullet, yAbsBullet));			
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
}
