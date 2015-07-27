package com.tauracs.cubepuzzle.model.interfaces;

/**
 * Interface representing the abstract building element of shapes *
 */
public interface IBrick {
	/**
	 * @return The X coordinate of the current item - 0 is Top-Front edge of the cube
	 */
	int getX();	
	
	/**
	 * @return The Y coordinate of the current item - 0 is Top-Front edge of the cube
	 */
	int getY();	
	
	/**
	 * @return The Y coordinate of the current item - 0 is Top-Front edge of the cube
	 */
	int getZ();

}
