/**
 * 
 */
package com.atomicsware;

import java.awt.geom.Ellipse2D;

/**
 * @author Jack Lisenko
 *
 */
@SuppressWarnings("serial")
public class Bullet extends Ellipse2D.Double {
	private double angle;
	private int time = 5000;
	private double xtran;
	private double ytran;
	
	/**
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 * @param angle
	 * @param time
	 */
	public Bullet(double x, double y, double width, double height, double angle, int time, double xtran, double ytran) {
		super(x, y, width, height);
		this.angle = angle;
		this.time = time;
		this.xtran = xtran;
		this.ytran = ytran;
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
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the xtran
	 */
	public double getXtran() {
		return xtran;
	}

	/**
	 * @param xtran the xtran to set
	 */
	public void setXtran(double xtran) {
		this.xtran = xtran;
	}

	/**
	 * @return the ytran
	 */
	public double getYtran() {
		return ytran;
	}

	/**
	 * @param ytran the ytran to set
	 */
	public void setYtran(double ytran) {
		this.ytran = ytran;
	}
	
	
}
