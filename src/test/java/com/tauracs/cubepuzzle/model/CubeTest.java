package com.tauracs.cubepuzzle.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tauracs.cubepuzzle.model.PuzzlePiece;
import com.tauracs.cubepuzzle.model.Brick;
import com.tauracs.cubepuzzle.model.Cube;
import com.tauracs.cubepuzzle.model.Shape;
import com.tauracs.cubepuzzle.model.Dimensions;

/**
 * Class test various cube based operations
 */
public class CubeTest extends TestBase {
	
	PuzzlePiece _testPuzzliePiece = new PuzzlePiece(new char[][]{
													{' ',' ',' ',' ',' '},
													{' ',' ','*',' ',' '},
													{' ',' ',' ',' ',' '},
													{' ',' ',' ',' ',' '},
													{' ',' ',' ',' ',' '}
											});
	
	/**
	 * Creates a cube, eliminates brick(s) based on the test puzzle piece and the rest is returned
	 * @return the result cube after the extract operation
	 */
	private Cube createCubeAndExtractPuzzlePiece() {
		Cube cube = new Cube();
		assertTrue("Block extraction from new queue failed",cube.extract(_testPuzzliePiece));		
		return cube;
	}
	
	/**
	 * Calculates the brick count of a cube that is empty inside
	 * @return brick count of an empty cube
	 */
	private static int getEmptyCubeSize() {
		int side = Dimensions.CUBE_EDGE_SIZE;
		
		int fullCubeBrickCount = side * side * side;
		int innerCubeBrickCount = (side-2)*(side-2)*(side-2);
		int emptyBrickCount = fullCubeBrickCount - innerCubeBrickCount;
		return emptyBrickCount;
	}

	@Test
	public void UseCase01_Cube_creation() {
		
		Cube cube = new Cube();
		
		int brickCount = 0;
		
		for (Brick brick : cube) {
			brickCount++;
		}
				
		assertEquals( getEmptyCubeSize(), brickCount);
			
	}
	
	@Test
	public void UseCase02_PuzzlePiece_removed_from_Cube() {
		Cube cube = createCubeAndExtractPuzzlePiece();				
		assertEquals("Brick count in result cube is wrong ",getEmptyCubeSize() - 1, cube.size());		
		assertNull("Brick was not removed.",cube.getBrick(2, 1, 0));
	}
	
	@Test
	public void UseCase03_clone() {
		Cube cube = createCubeAndExtractPuzzlePiece();		
		Shape clone = cube.clone();
		
		assertEquals(cube.size(), clone.size());
		for (Brick brick : cube) {
			Brick brick_in_clone = clone.getBrick(brick.getX(), brick.getY(), brick.getZ());
			assertEquals("Brick in the clone does not equals original", brick, brick_in_clone);
		}
	}

}
