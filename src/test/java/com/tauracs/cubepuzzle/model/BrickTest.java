package com.tauracs.cubepuzzle.model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.tauracs.cubepuzzle.model.enums.*;
import com.tauracs.cubepuzzle.model.Brick;
import com.tauracs.cubepuzzle.model.Dimensions;

/**
 *Test class that checks the various use cases / method invocations of the Brick class
 */
public class BrickTest extends TestBase {

	@Test
	public void UseCase_01_Brick_creation() {
		Brick b = new Brick(0, 0, 0);
		assertNotNull(b);
	}
	
	@Test
	public void UseCase_02_Brick_creation_fails_when_wrong_coords_passed() {
		
		try{
			new Brick(-1, 0, 0);
			fail("No exception was thrown");		
		}
		catch(Exception e) {
			;
		}
	}
	
	@Test
	public void UseCase_03_Brick_equality() {
		
		Brick brick = new Brick(1, 0, 0);
		Brick b2 = new Brick(1,0,0);
		
		assertEquals(brick, b2);	
	}
	
	@Test
	public void UseCase_03_Brick_rotateArondAxisZ_90() {	
		assertEquals("Side size should be 5",5, Dimensions.CUBE_EDGE_SIZE);
		Brick brick = new Brick(1, 1, 0);
		brick.rotateAroundShapeAxis(CoordAxis.Z, -1);		
		Brick expected = new Brick(3, 1, 0);		
		assertEquals(expected, brick);			
	}
	
	@Test
	public void UseCase_03_Brick_rotateArondShapeAxis_Z_180() {
		assertEquals("Side size should be 5",5, Dimensions.CUBE_EDGE_SIZE);
		Brick brick = new Brick(1, 1, 0);
		brick.rotateAroundShapeAxis(CoordAxis.Z, -2);		
		Brick expected = new Brick(3, 3, 0);		
		assertEquals(expected, brick);			
	}
	
	@Test
	public void UseCase_03_Brick_rotateArondShapeAxis_Z_270() {
		assertEquals("Side size should be 5",5, Dimensions.CUBE_EDGE_SIZE);
		Brick brick = new Brick(1, 1, 0);
		brick.rotateAroundShapeAxis(CoordAxis.Z, -3);		
		Brick expected = new Brick(1, 3, 0);		
		assertEquals(expected, brick);			
	}
	
	@Test
	public void UseCase_03_Brick_rotateArondAxisX() {
		Brick brick = new Brick(1, 1, 0);
		Brick clone = brick.clone();
		brick.rotateAroundShapeAxis(CoordAxis.X, 4);		
		assertEquals(clone, brick);		
		brick.rotateAroundShapeAxis(CoordAxis.X, -4);					
	}
	
	@Test
	public void UseCase_03_Brick_rotateArondAxisY() {
		Brick brick = new Brick(1, 1, 0);
		Brick clone = brick.clone();
		brick.rotateAroundShapeAxis(CoordAxis.Y, 4);		
		assertEquals(clone, brick);		
		brick.rotateAroundShapeAxis(CoordAxis.Y, -4);					
	}
	
	@Test
	public void UseCase_03_Brick_rotateArondAxisZ() {
		Brick brick = new Brick(1, 1, 0);
		Brick clone = brick.clone();
		brick.rotateAroundShapeAxis(CoordAxis.Z, 4);		
		assertEquals(clone, brick);		
		brick.rotateAroundShapeAxis(CoordAxis.Z, -4);					
	}
}
