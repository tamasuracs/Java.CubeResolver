package com.tauracs.cubepuzzle.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.logging.Logger;

import org.junit.Test;
import com.tauracs.cubepuzzle.model.PuzzlePiece;
import com.tauracs.cubepuzzle.model.Brick;
import com.tauracs.cubepuzzle.model.Dimensions;
import com.tauracs.cubepuzzle.model.enums.Side;

/**
 *Test class checking various operations on the PuzzlePiece
 */
public class PuzzlePieceTests extends TestBase{
	
	/**
	 * An empty PuzzlePiece that has no bricks
	 */
	public static PuzzlePiece EMPTY = new PuzzlePiece(
													new char[][]{
															{' ',' ',' ',' ',' '},
															{' ',' ',' ',' ',' '},
															{' ',' ',' ',' ',' '},
															{' ',' ',' ',' ',' '},
															{' ',' ',' ',' ',' '}
													}
												);
	
	/**
	 * A complete PuzzlePiece that is fully covered by bricks
	 */
	public static PuzzlePiece COMPLETE = new PuzzlePiece(new char[][]{
															{'*','*','*','*','*'},
															{'*','*','*','*','*'},
															{'*','*','*','*','*'},
															{'*','*','*','*','*'},
															{'*','*','*','*','*'}
													});
	
	/**
	 * Helper method that checks the coordinates of the passed brick_
	 * if they are matching the requested Side.
	 * 
	 * @param side_ Side of thats requested
	 * @param brick_ The brick_ whoose coordinates are checked
 	 * @return true if the coordinates are correctly set on the brick
	 */
	private static boolean checkCoords(final Side side_, final Brick brick_) {
		switch (side_) {
		case FRONT: return (brick_.getZ()==0);
		case LEFT: return (brick_.getX()==0);
		case BACK: return (brick_.getZ()== Dimensions.MAX_COORD);			
		case TOP: return (brick_.getY()==0);
		case RIGHT: return (brick_.getX()==Dimensions.MAX_COORD);
		case BOTTOM: return (brick_.getY()==Dimensions.MAX_COORD);
		default: return false;
		}	
		
	}

	@Test
	public void UseCase00_PuzzlePiece_creation() {
		assertNotNull(EMPTY);
	}
	
	@Test
	public void UseCase01_PuzzlePiece_creation_brick_coord_are_fine() {
		
		char[][] layout = new char[][]{
				{' ',' ','*',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '}
		};
		PuzzlePiece piece = new PuzzlePiece(layout);
		
		for (Brick brick : piece) {
			assertEquals(2, brick.getX());
			assertEquals(0, brick.getY());
			assertEquals(0, brick.getZ());
		}
		
		assertEquals("Brick count mismatch",1, piece.size());
	}
	
	@Test
	public void UseCase02_PuzzlePiece_creation_failure_if_argument_is_NULL() {
		try{
			new PuzzlePiece((String)null);
			fail("There was no exception thrown");
		}
		catch(Exception e) {
			;
		}
	}
	
	@Test
	public void UseCase03_PuzzlePiece_creation_failure_if_argument_layout_is_incorrect() {
		try{
			char[][] layout = new char[][]{
					{' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' '}
			};
			
			new PuzzlePiece(layout);
			fail("There was no exception thrown");
		}
		catch(Exception e) {
			;
		}
	}
	
	@Test
	public void UseCase04_PuzzlePiece_creation_failure_if_argument_layout_is_incorrect() {
		try{
			char[][] layout = new char[][]{
					{' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' ',' '},
					{' ',' ',' ',' ',' '}
			};
			
			new PuzzlePiece(layout);
			fail("There was no exception thrown");
		}
		catch(Exception e) {
			;
		}
	}
	
	
	@Test
	public void UseCase06_PuzzlePiece_nextState() {
		char[][] layout = new char[][]{
				{' ',' ',' ',' ',' '},
				{' ','*',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '}
		};
		PuzzlePiece b = new PuzzlePiece(layout);
		
		//Default state FRONT, Non rotated
		int statesVisited = 1;
		
		HashSet<String> coords = new HashSet<String>();
		HashSet<Side>   sides = new HashSet<Side>();		
		
		while(b.nextState()) {
			statesVisited++;
			
			if (statesVisited % 4 == 1) {
				Side currentSide = b.getSide();
				assertTrue(String.format("'%s' side was already visited", currentSide), sides.add(currentSide));				
			}
			
			for (Brick brick : b) {
				checkCoords(b.getSide(), brick);
				String coordsEntry = brick.toString();
				
				if (coords.contains(coords)) {
					fail("Duplicate coord entry found");
				}
				else{
					coords.add(coordsEntry);
				}
			}				
		}
		
		assertEquals(6*4, statesVisited++);
	
	}
	
	@Test
	public void UseCase07_PuzzlePiece_clone() {
		char[][] layout = new char[][]{
				{' ',' ',' ',' ',' '},
				{' ','*',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '}
		};
		
		PuzzlePiece piece = new PuzzlePiece(layout);
		
		piece.nextState();
		piece.nextState();
		
		PuzzlePiece clone = (PuzzlePiece)piece.clone();

		assertEquals(piece.size(), clone.size());
		assertEquals(piece.getSide(), clone.getSide());
		
		for (Brick brick : piece) {
			Brick brick_in_clone = clone.getBrick(brick.getX(), brick.getY(), brick.getZ());
			assertEquals("Brick in the clone does not equals original", brick, brick_in_clone);
		}	
	}
	
	@Test
	public void UseCase08_PuzzlePiece_mirror() {
		char[][] layout = new char[][]{
				{' ',' ',' ',' ',' '},
				{' ','*',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '}
		};
		PuzzlePiece b = new PuzzlePiece(layout);
		b.flip();
		
		assertNotNull("Mirroring failed",  b.getBrick(3, 1, 0));
	}
	
	@Test
	public void UseCase09_PuzzlePiece_BACK_toString() {
		char[][] layout = new char[][]{
				{' ',' ',' ',' ',' '},
				{' ','*',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' '}
		};
		PuzzlePiece b = new PuzzlePiece(layout);
		while(b.getSide() != Side.BACK) {
			b.nextState();
		}
		
		Logger.getGlobal().info(b.toString());
		Logger.getGlobal().info(String.format("\n\n%s",b.to360TString()));
		
	}



}
