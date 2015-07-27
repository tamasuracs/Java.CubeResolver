package com.tauracs.cubepuzzle.model;

import java.util.logging.Logger;
import com.tauracs.cubepuzzle.model.Dimensions;
import com.tauracs.cubepuzzle.model.enums.CoordAxis;
import com.tauracs.cubepuzzle.model.enums.Direction;
import com.tauracs.cubepuzzle.model.enums.Side;
import com.tauracs.cubepuzzle.model.interfaces.IBrick;

/**
 * A building block of various shapes
 *
 */
public class Brick implements IBrick {
	/**
	 * UTF-8 marker of an empty block 
	 */
	public final static char EMPTY_BLOCK_MARKER = '0';
	
	/**
	 * UTF-8 marker of an brick block 
	 */
	public final static char BRICK_MARKER = '1';
	
	/**
	 * The X coordinate of the current item - 0 is Top-Front edge of the cube
	 */
	protected int _x;
	
	/**
	 * The Y coordinate of the current item - 0 is Left-Front edge of the cube
	 */
	protected int _y;
	
	/**
	 * The Z coordinate of the current item - 0 is Top-Left edge of the cube
	 */
	protected int _z;
	
	/**
	 * Creates a building block
	 * 
	 * @param x_ The X coordinate of the current item - 0 is Top-Front edge of the cube
	 * @param y_ The Y coordinate of the current item - 0 is Left-Front edge of the cube
	 * @param z_ The Z coordinate of the current item - 0 is Top-Left edge of the cube
	 */
	public Brick(final int x_, final  int y_,final  int z_) {
		Dimensions.validateCoords(x_, y_, z_);			
		_x = x_;
		_y = y_;
		_z = z_;		
	}
	
	/**
	 * Getter of the X coordinate
	 */
	public int getX() {
		return _x;
	}
	
	/**
	 * Getter of the Y coordinate
	 */
	public int getY() {
		return _y;
	}
	
	/**
	 * Getter of the Z coordinate
	 */
	public int getZ() {
		return _z;
	}
	
	/**
	 * String representation of the current entity
	 */
	@Override
	public String toString() {
		return String.format("X:%s, Y:%s, Z:%s, ",_x,_y,_z);
	}
	

	/**
	 * equality check
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean result;		
		try{
			Brick other = (Brick)obj;
			
			if (this._x != other._x
					||
					this._y != other._y
					||
					this._z != other._z
					) {
				result = false;
			}
			else{
				result = true;
			}
		}
		catch(Exception e) {
			result = false;
		}
		return result; 
	}
	
	/**
	 * Rotates the current prick around the passed axis_ of the parent shape
	 * 
	 * @param axis_ The axis of the rotation
	 * @param amount_ How many times 90°s are rotated, negative value means clockwise rotation
	 */
	public void rotateAroundShapeAxis(final CoordAxis axis_,final int amount_) {
		
		String original = this.toString();
		
		boolean clockwise = amount_<0;
		int i_ = Math.abs(amount_);
		
		for (int idx=0; idx<i_;idx++) {
			int newX=_x;
			int newY=_y;
			int newZ=_z;
			
			switch (axis_) {
			case Z:
				if (clockwise) {
					newX = Dimensions.MAX_COORD - _y;
					newY = _x;
				}else{
					newX = _y;
					newY = Dimensions.MAX_COORD - _x;
				}		
				break;
				
			case X:
				if (clockwise) {
					newZ = _y;
					newY = Dimensions.MAX_COORD - _z;
				}else{
					newZ = Dimensions.MAX_COORD - _y;
					newY = _z;
				}		
				break;
				
			case Y:
				if (clockwise) {
					newX = Dimensions.MAX_COORD - _z;
					newZ = _x;
				}else{
					newX = _z;
					newZ = Dimensions.MAX_COORD - _x;
				}
				break;
	
			default:
				break;
			}
			
			_x = newX;
			_y = newY;
			_z = newZ;
		}
		
		Logger.getGlobal().info(String.format("Brick '%s' Rotated %s times --> '%s' side\n", original, i_, this.toString()  ));
		
	}
	
		
	/**
	 * Helper method that 
	 * 
	 * @param direction_
	 */
	public void rotateAroundShapeAxis(final CoordAxis axis_,final Direction direction_) {
		
		switch (direction_) {
		case ROTATED_90:
			this.rotateAroundShapeAxis(axis_, -1);			
			break;
			
		case ROTATED_180:
			this.rotateAroundShapeAxis(axis_, -2);
			break;
			
		case ROTATED_270:
			this.rotateAroundShapeAxis(axis_, -3);
			break;

		default:
			break;
		}
	}
	
	public void moveToSideFromFront(Side side_) {
		if (this.getZ() != 0) {
			throw new UnsupportedOperationException("The current operation  only items on the Front side of the shape(Z > 0)");
		}
		switch (side_) {
		case FRONT:
			break;
			
		case BACK:
			rotateAroundShapeAxis(CoordAxis.Y, -2);
			break;
			
		case LEFT:
			rotateAroundShapeAxis(CoordAxis.Y, 1);
			break;

		case RIGHT:
			rotateAroundShapeAxis(CoordAxis.Y, -1);
			break;
			
		case TOP:
			rotateAroundShapeAxis(CoordAxis.X, 1);
			break;
			
		case BOTTOM:
			rotateAroundShapeAxis(CoordAxis.X, -1);
			break;
			
		default:
			break;
		}
		
	}

	
	public Brick clone() {
		return new Brick(getX(), getY(), getZ());
	}
}
