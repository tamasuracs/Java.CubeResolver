package com.tauracs.cubepuzzle.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import com.tauracs.cubepuzzle.model.enums.CoordAxis;
import com.tauracs.cubepuzzle.model.enums.Direction;
import com.tauracs.cubepuzzle.model.enums.Side;
import com.tauracs.cubepuzzle.model.interfaces.IBrick;

/**
 * Abstract shape class that is built from Bricks
 *
 */
public abstract class Shape implements Iterable<Brick>  {
	
	
	/**
	 * Set containing all the brick instances  
	 */
	private Set<Brick> _brickSet = new LinkedHashSet<Brick>();
	
	/**
	 * Array based 'index' for the bricks of current shape
	 */
	private Brick[][][] _bricks = new Brick[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];

	/**
	 * Creates brick on the give coordinates.
	 * 
	 * @param x_ The X coordinate of the current item - 0 is Top-Front edge of the cube
	 * @param y_ The Y coordinate of the current item - 0 is Left-Front edge of the cube
	 * @param z_ The Z coordinate of the current item - 0 is Top-Left edge of the cube
	 * @return the newly created brick
	 */
	protected Brick createBrick(int x_, int y_, int z_) {
		
		Brick result = new Brick(x_, y_, z_);
		Brick brickInArray = _bricks[x_][y_][z_];
		
		if (brickInArray != null) {
			_brickSet.remove(brickInArray);
		}
		
		_bricks[x_][y_][z_] =  result;		
		_brickSet.add(result);
		
		return result;
	}
	
	/**
	 * Eliminates a brick from the shape
	 * @param brick_ - the item to eliminate
	 * @return true if the operation was successful
	 */
	public boolean remove(IBrick brick_) {
		boolean result = _brickSet.remove(brick_);
		
		if (result) {
			_bricks[brick_.getX()][brick_.getY()][brick_.getZ()] =  null;
		}
		
		return result;
	}
	
	/**
	 * Removes all the building blocks from the current shape
	 */
	public void clear() {
		_bricks = new Brick[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];
		_brickSet.clear();
	}
	
	/**
	 * Returns the brick on the given coordinates - or null
	 * @param x_ The X coordinate 
	 * @param y_ The Y coordinate 
	 * @param z_ The Z coordinate 
	 * @return
	 */
	public Brick getBrick(final int x_,final int y_,final int z_) {
		Dimensions.validateCoords(x_, y_, z_);
		return _bricks[x_][y_][z_];		
	}
	
	public Iterator<Brick> iterator() {
		return _brickSet.iterator();	
	}
	
	public int size() {
		return _brickSet.size();
	}
	
	public abstract Shape clone();
	
	/**
	 * Rotates the current shape around the given axis
	 * 
	 * @param axis_ Axis of the rotation
	 * @param direction_ Direction of the roation. How many times 90°s are rotated, negative value means clockwise rotation
	 * 
	 */
	protected void rotateAroundAxis(final CoordAxis axis_,final int direction_) {
		
		_bricks = new Brick[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];
		for (Brick brick : this) {
			brick.rotateAroundShapeAxis(axis_, direction_);
			_bricks[brick.getX()][brick.getY()][brick.getZ()] = brick;
		}
	}
	
	/**
	 * Rotates the current shape around the given axis
	 * 
	 * @param axis_ - Axis of the rotation
	 * @param direction_ - Direction of the rotation.
	 */
	protected void rotateAroundAxis(final CoordAxis axis_,final Direction direction_) {
		if (direction_ == Direction.ROTATED_0) {					
			return;
		}
		
		_bricks = new Brick[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];
		for (Brick brick : this) {
			brick.rotateAroundShapeAxis(axis_, direction_);			
			//Updating the state 
			_bricks[brick.getX()][brick.getY()][brick.getZ()] = brick;
		}
	}
	
	/**
	 * Returns the (non-modifiable) bricks of the requested side 'shifted back' to the FRONT side (Z coord is 0)
	 * @param side_ - The side of the shape whose building blocks are request.
	 * @return
	 */
	public IBrick[][] getBricksNormalized(final Side side_) {
		
		IBrick[][] result = new IBrick[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];
		
		for (IBrick brick : getBricks(side_)) {
			Brick clone = ((Brick)brick).clone();
			switch (side_) {
				case FRONT: 					
					break;
				
				case BACK:
					clone.rotateAroundShapeAxis(CoordAxis.Y, 2);
					break;
					
				case LEFT:
					clone.rotateAroundShapeAxis(CoordAxis.Y, -1);
					break;
				
				case RIGHT:
					clone.rotateAroundShapeAxis(CoordAxis.Y, 1);
					break;
					
				case TOP:
					clone.rotateAroundShapeAxis(CoordAxis.X, -1);
					break;
					
				case BOTTOM:
					clone.rotateAroundShapeAxis(CoordAxis.X, 1);
					break;
	
				default:
					break;
			}			
			result[clone.getX()][clone.getY()] = (IBrick)clone;
		}
		
		return result;
	}
	
	/**
	 * Returns the (non-modifiable) bricks of the requested side 
	 * @param side_  - The side of the shape whose building blocks are request.
	 * @return
	 */
	public Iterable<IBrick> getBricks(Side side_) {
		Iterable<IBrick> result = null;
		switch (side_) {
		case FRONT:
			result = getBricks(CoordAxis.Z, 0);
			break;
		
		case BACK:
			result = getBricks(CoordAxis.Z, Dimensions.MAX_COORD);
			break;
			
		case LEFT:
			result = getBricks(CoordAxis.X, 0);
			break;
		
		case RIGHT:
			result = getBricks(CoordAxis.X, Dimensions.MAX_COORD);
			break;
			
		case TOP:
			result = getBricks(CoordAxis.Y, 0);
			break;
			
		case BOTTOM:
			result = getBricks(CoordAxis.Y, Dimensions.MAX_COORD);
			break;

		default:
			break;
		}
		
		return result;
	}
	
	/**
	 * Returns the (non-modifiable) bricks of the requested "layer"
	 * 
	 *  e.g.: getBricks(Z,0) returns all the bricks of the FRONT side of the cube
	 * 
	 * @param axis_  Coordinate that should be fixed
	 * @param amount_ Amount of the coordinate value 
	 * @return
	 */	
	public Iterable<IBrick> getBricks(final CoordAxis axis_,final int amount_) {
		
		TreeMap<String, IBrick> result =  new TreeMap<String, IBrick>();
		
		for (Brick brickInCube : this) {
			if (
					(axis_ == CoordAxis.X && brickInCube.getX() == amount_)
					||
					(axis_ == CoordAxis.Y && brickInCube.getY() == amount_)
					||
					(axis_ == CoordAxis.Z && brickInCube.getZ() == amount_)
			  ) {
				result.put(brickInCube.toString(), brickInCube);
			}
		}
		
		return result.values();
	}
	
	/**
	 * Creates string representation of the requested side of the shape
	 * 
	 * @param side_ The side of the shape to be printed (as if it were shifted to the FRONT)
	 * @return
	 */
	public String toString(final Side side_) {
		return toString(side_, true);		
	}
	
	/**
	 * Creates string representation of the requested side of the shape
	 * 
	 * @param side_ The side of the shape to be printed (as if it were shifted to the FRONT)
	 * @return
	 */
	public String toString(final Side side_, final boolean printSideLabel_ ) {
		
		String result = "";
		
		if (printSideLabel_) {
			result = String.format("Side: %s\n\n", side_);
		}
		
		IBrick[][] var =  getBricksNormalized(side_);
				
		for (int y=0; y< Dimensions.CUBE_EDGE_SIZE; y++) {
			for (int x=0; x< Dimensions.CUBE_EDGE_SIZE; x++) {
				
				char currentNode = Brick.EMPTY_BLOCK_MARKER;
				if (var[x][y] != null) {
					currentNode=Brick.BRICK_MARKER;
				}
				result = String.format("%s%c", result, currentNode);
			}
			result = String.format("%s\n", result);
		}
		return result;
	}
}
