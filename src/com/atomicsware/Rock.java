package com.atomicsware;

import java.awt.Polygon;

/**
 * @author Jack Lisenko
 *
 */
public class Rock extends Polygon {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double xPos;
	private double yPos;
	private double angle;
	private int vel;
	private double angPath;
	private double angRate;
	private boolean unhit;

	public Rock(int[] xPoints, int[] yPoints, int nPoints, double xPos, double yPos, double angle, int vel, double angPath, double angRate) {
		super(xPoints, yPoints, nPoints);
		this.xPos = xPos;
		this.yPos = yPos;
		this.angle = angle;
		this.vel = vel;
		this.angPath = angPath;
		this.angRate = angRate;
		this.unhit = true;
	}	

	/**
	 * @return the unhit
	 */
	public boolean isUnhit() {
		return unhit;
	}

	/**
	 * @param unhit the unhit to set
	 */
	public void setUnhit(boolean unhit) {
		this.unhit = unhit;
	}

	/**
	 * @return the angRate
	 */
	public double getAngRate() {
		return angRate;
	}

	/**
	 * @param angRate the angRate to set
	 */
	public void setAngRate(double angRate) {
		this.angRate = angRate;
	}

	/**
	 * @return the angPath
	 */
	public double getAngPath() {
		return angPath;
	}

	/**
	 * @param angPath the angPath to set
	 */
	public void setAngPath(double angPath) {
		this.angPath = angPath;
	}

	/**
	 * @return the xPos
	 */
	public double getxPos() {
		return xPos;
	}

	/**
	 * @param xPos the xPos to set
	 */
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	/**
	 * @return the yPos
	 */
	public double getyPos() {
		return yPos;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * @return the vel
	 */
	public int getVel() {
		return vel;
	}

	/**
	 * @param vel the vel to set
	 */
	public void setVel(int vel) {
		this.vel = vel;
	}	

}
