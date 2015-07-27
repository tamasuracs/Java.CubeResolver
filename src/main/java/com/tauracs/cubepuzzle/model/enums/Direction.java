package com.tauracs.cubepuzzle.model.enums;

/**
 * Direction of the FRONT side of the cube
 * 
 * ROTATED_XYZ - means rotated XYZ degrees
 *  
 *
 */
public enum Direction{
	ROTATED_0 ,
	ROTATED_90,
	ROTATED_180,
	ROTATED_270;
	
	/**
	 * Helper method for crawling through all the directions of one side of the cube
	 */
	public Direction next() {
		switch (this) {
		case ROTATED_0: return ROTATED_90;
		case ROTATED_90: return ROTATED_180;
		case ROTATED_180: return ROTATED_270;			
		case ROTATED_270: return null;
		default: return null;
		}		
	}
}
