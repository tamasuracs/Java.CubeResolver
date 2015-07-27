package com.tauracs.cubepuzzle.model;

/**
 * Class that Represents the base metrics
 *
 */
public final class Dimensions{
	
	/**
	 * Size of one edge of the CUBE
	 */
	final static public int CUBE_EDGE_SIZE = 5;
	
	/**
	 * The maximal coord amount that can be assigned to a building block of the cube
	 */
	final static public int MAX_COORD = CUBE_EDGE_SIZE - 1;
	
	/**
	 * Validates the passed coordinates 
	 * @param x_ - X coordinate
	 * @param y_ - Y coordinate
	 * @param z_ - Z coordinate
	 * 
	 * 	
	 * The X coordinate of the current item - 0 is Top-Front edge of the cube	
	 * The Y coordinate of the current item - 0 is Left-Front edge of the cube
	 * The Z coordinate of the current item - 0 is Top-Left edge of the cube
	 */
	public static void validateCoords(final int x_,final  int y_ ,final  int z_) {
	
		if ( x_ > Dimensions.MAX_COORD   || 
				x_ < 0                   ||
				y_ > Dimensions.MAX_COORD   || 
				y_ < 0					|| 
				z_ > Dimensions.MAX_COORD   || 
				z_ < 0
				) {
				throw new IllegalArgumentException(String.format("x_,y_ and z_ args should be btw 0 and %s", Dimensions.MAX_COORD));
	    }
	}
}	
