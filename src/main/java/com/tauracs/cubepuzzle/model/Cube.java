/**
 * 
 */
package com.tauracs.cubepuzzle.model;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import com.tauracs.cubepuzzle.model.enums.Side;

/**
 * Cube whose building blocks are eliminated during the Puzzle resolution
 * 
 */
public class Cube extends Shape {
	
	/**
	 * Constructor, creates a cube which is empty inside
	 */
	public Cube() {
		for (int z=0; z< Dimensions.CUBE_EDGE_SIZE; z++) {
			for (int y=0; y< Dimensions.CUBE_EDGE_SIZE; y++) {
				for (int x=0; x< Dimensions.CUBE_EDGE_SIZE; x++) {
					
					if ( (x == 0 || x == Dimensions.MAX_COORD)
							||
						(y == 0 || y == Dimensions.MAX_COORD)
						    ||
						 (z == 0 || z == Dimensions.MAX_COORD)
							) {
						createBrick(x, y, z);
					}															
				}
			}
		}
	}
	
	/**
	 * Extracting the puzzle piece from the current Cube instance.
	 * 
	 * @param puzzlePiece_ the item that is matched
	 * @return true if all the building blocks of the puzzle are matching the blocks of cube
	 */
	public Boolean extract(PuzzlePiece puzzlePiece_) {
		
		if (puzzlePiece_ == null) {
			return true;
		}
		Logger.getGlobal().info(String.format("Extracting '%s' puzzle piece.", puzzlePiece_.toString()));
		
		Set<Brick> bricksToRemove = new HashSet<Brick>();
		
		for (Brick brickOfPuzzlePiece : puzzlePiece_) {
			Brick brickInCube = this.getBrick(brickOfPuzzlePiece.getX(), brickOfPuzzlePiece.getY(), brickOfPuzzlePiece.getZ());
			
			if (brickInCube == null) {
				return false;
			}			
			bricksToRemove.add(brickInCube);
		}
		
		for (Brick brickInCube : bricksToRemove) {
			remove(brickInCube);
			Logger.getGlobal().info(String.format("Brick '%s' of cube destroyed.", brickInCube.toString()));
		}
		
		Logger.getGlobal().info(String.format("Cube: %s ", this.toString()));
		
		return true;
	}
	
	/**
	 * Creates a Clone from the current cube
	 */
	public Shape clone() {
		Cube result = new Cube();
		result.clear();
		
		for (Brick brick : this) {
			result.createBrick(brick.getX(), brick.getY(), brick.getZ());
		}
		
		return result;
	}
	
	/**
	 * String representation of the cube
	 */
	@Override
	public String toString() {
		String result= "\n";
		result = String.format("%sHashCode: %d\n", result, hashCode());		
		result = String.format("%s%s", result, toString(Side.FRONT)) ;
		result = String.format("%s%s", result, toString(Side.LEFT)) ;
		result = String.format("%s%s", result, toString(Side.RIGHT)) ;
		result = String.format("%s%s", result, toString(Side.BACK)) ;
		result = String.format("%s%s", result, toString(Side.TOP)) ;
		result = String.format("%s%s", result, toString(Side.BOTTOM)) ;
		
		return result;
	}

	
}
