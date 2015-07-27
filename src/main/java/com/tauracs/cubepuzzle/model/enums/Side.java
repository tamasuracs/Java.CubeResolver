package com.tauracs.cubepuzzle.model.enums;

/**
 * Enumeration representing the 6 sides of a cube
 *
 */
public enum Side{
	FRONT,
	BACK,
	LEFT,
	RIGHT,
	TOP,
	BOTTOM;
	
	/**
	 * Helper method for crawling through the various sides of the cube
	 */
	public Side next() {
		switch (this) {
		case FRONT: return LEFT;
		case LEFT: return BACK;
		case BACK: return TOP;			
		case TOP: return RIGHT;
		case RIGHT: return BOTTOM;
		case BOTTOM: return null;
		default: return null;
		}		
	}
}
