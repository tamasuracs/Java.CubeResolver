package com.tauracs.cubepuzzle.model;

import static org.junit.Assert.*;

import org.junit.Test;
import com.tauracs.cubepuzzle.model.Brick;
import com.tauracs.cubepuzzle.model.Dimensions;
import com.tauracs.cubepuzzle.model.enums.Side;

/**
 * Test class that checks various Brick movements
 * 
 */
public class BrickMovementTest extends TestBase {
	
	/**
	 * Constant for the Z coordinate used in the moved bricks
	 */
	private final int FRONT_SIDE_Z_POSITION = 0;
	
	/**
	 * The base Brick that is moved around
	 */
	private final Brick _brick = new Brick(1, 1, FRONT_SIDE_Z_POSITION);
	
	
	private void moveToSideAndCheckResult(Brick brick, Brick expected,
			Side newSide) {
		assertEquals("Side size should be 5",5, Dimensions.CUBE_EDGE_SIZE);
		brick.moveToSideFromFront(newSide);		
		assertEquals(expected, brick);
	}
	
	@Test
	public void UseCase0_Move_Brick_To_FRONT_side() {
		moveToSideAndCheckResult(_brick, new Brick(1, 1, 0), Side.FRONT);
	}
	
	@Test
	public void UseCase1_Move_Brick_To_BACK_side() {
		moveToSideAndCheckResult(_brick, new Brick(3, 1, 4), Side.BACK);
	}

	@Test
	public void UseCase2_Move_Brick_To_LEFT_side() {
		moveToSideAndCheckResult(_brick, new Brick(0, 1, 3), Side.LEFT);
	}
	
	@Test
	public void UseCase3_Move_Brick_To_RIGHT_side() {
		moveToSideAndCheckResult(_brick, new Brick(4, 1, 1), Side.RIGHT);
	}
	
	@Test
	public void UseCase4_Move_Brick_To_TOP_side() {
		moveToSideAndCheckResult(_brick, new Brick(1, 0, 3), Side.TOP);
	}
	
	@Test
	public void UseCase5_Move_Brick_To_BOTTOM_side() {
		moveToSideAndCheckResult(_brick, new Brick(1, 4, 1), Side.BOTTOM);
	}
}
