package com.tauracs.cubepuzzle.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tauracs.cubepuzzle.model.PuzzlePiece;
import com.tauracs.cubepuzzle.model.Brick;
import com.tauracs.cubepuzzle.model.Cube;
import com.tauracs.cubepuzzle.model.interfaces.IBrick;

/**
 *Test class checking various shape operations 
 */
public class ShapeTest extends TestBase {

	@Test
	public void UseCase01_Shape_creation_and_single_brick_removed() {
		
		Cube cube = new Cube();
		
		int initBrickSize = cube.size();
		Brick first = cube.iterator().next();
		cube.remove(first);
		
		assertEquals("Brick size after removal is wrong", initBrickSize - 1, cube.size());
		
		for (Brick b : cube) {
			if (b.equals(first)) {
				fail("Removed brick was found");
			}
		}			
	}
	
	@Test
	public void UseCase02_Shape_creation_and_all_bricks_removed() {
		
		Cube cube = new Cube();
		cube.clear();
		assertEquals("Cube brick count is non zero", 0, cube.size());
		
		for (Brick brick : cube) {
			fail("There is a cube existing.");
		}
					
	}
	
	@Test
	public void UseCase3_getBricks_by_Side() {
		
		
		PuzzlePiece b = PuzzlePieceTests.COMPLETE;
		int brickSizeInPuzzlePiece = b.size();
		b.nextState();
		b.nextState();
		b.nextState();
		b.nextState();
		b.nextState();
		
		int i=0;
		
		for (IBrick brick : b.getBricks(b.getSide())) {
			i++;
		}
		
		assertEquals("Side base brick retrieval failed",brickSizeInPuzzlePiece, i);
		
	}
	
	@Test
	public void UseCase4_getBricksNormalized() {
		
		
		PuzzlePiece b = PuzzlePieceTests.COMPLETE;
		int brickSizeInPuzzlePiece = b.size();
		b.nextState();
		b.nextState();
		b.nextState();
		b.nextState();
		b.nextState();
		
		int i=0;
		
		IBrick[][] bricks =b.getBricksNormalized(b.getSide());
		
		for (int y=0; y < bricks.length; y++) {
			for (int x=0; x < bricks[0].length; x++) {
				IBrick brick = bricks[x][y];
				assertNotNull("Normalization was wrong", brick);
				i++;
			}
		}
		
		assertEquals("Side base brick retrieval failed",brickSizeInPuzzlePiece, i);
		
	}

}
