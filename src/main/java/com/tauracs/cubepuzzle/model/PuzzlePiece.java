/**
 * 
 */
package com.tauracs.cubepuzzle.model;
import java.util.logging.Logger;
import com.tauracs.cubepuzzle.model.enums.*;

/**
 * Class representing a single puzzle piece 
 */
public class PuzzlePiece extends Shape {
	
	/**
	 * Side of the cube that is matched 
	 * in the current state of the PuzzlePiece
	 */
	Side _side = Side.FRONT;
	
	
	Direction _direction = Direction.ROTATED_0;
	
	/**
	 * Char array representation of the puzzle
	 */
	char[][] _layout = null;
	
	/**
	 * Constructor taking a string representation of the Puzzle
	 * @param layoutString_ - String representation of the PuzzlePeace layout
	 * 
	 */
	public PuzzlePiece(final String layoutString_) {
		this(convert(layoutString_));		
	}

	/**
	 * Constructor taking a character array representation of the Puzzle
	 * @param layout_ The char array representing the layout of the puzzle piece
	 */
	public PuzzlePiece(final char[][] layout_) {
		validate(layout_);		
		_layout = layout_;
		reset();
	}
	
	
	private static char[][] convert(final String string_) {
		if (string_ == null) {
			throw new IllegalArgumentException(String.format("Passed string should have %d rows.",Dimensions.CUBE_EDGE_SIZE));
		}
		
		char[][] result = new char[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];
		String[] rows = string_.split("\n");
		
		if (rows.length != Dimensions.CUBE_EDGE_SIZE) {
			throw new IllegalArgumentException(String.format("Passed string should have %d rows.",Dimensions.CUBE_EDGE_SIZE));
		}
		
		for (int y=0; y<Dimensions.CUBE_EDGE_SIZE;y++) {
			for (int x=0; x<Dimensions.CUBE_EDGE_SIZE;x++) {
				if (rows[y].length() != Dimensions.CUBE_EDGE_SIZE) {
					throw new IllegalArgumentException(String.format("Passed string should have %d characters in a row.",Dimensions.CUBE_EDGE_SIZE));					
				}
					
				result[y][x] = rows[y].charAt(x);
			}			
		}
		
		return result;
	}

	/**
	 * Resets the state of the PuzzlePice
	 * 
	 */
	private void reset() {		
		_direction = Direction.ROTATED_0;
		_side = Side.FRONT;
		createBricksFromLayout();
	}

	/**
	 * Creates the bricks based on the layout
	 */
	private void createBricksFromLayout() {
		clear();
		int z=0;
		
		for (int y=0; y< Dimensions.CUBE_EDGE_SIZE; y++) {
			for (int x=0; x< Dimensions.CUBE_EDGE_SIZE; x++) {				
				if (!Character.isSpaceChar(_layout[y][x])) {
					createBrick(x, y, z);				
				}
			}
		}
	}
	
	/**
	 * Validates the passed character array
	 * @param layout_ - 
	 * @return
	 */
	private boolean validate(final char[][] layout_) {
		boolean result = true;
		
		if (layout_ == null) {
			throw new IllegalArgumentException("layout_ argument is null");
		}
		
		if (layout_.length != Dimensions.CUBE_EDGE_SIZE) {
			result = false;
		}
		else{
			for (int i=0; i<layout_.length; i++) {
				if (layout_[i].length != Dimensions.CUBE_EDGE_SIZE) {
					result = false;
					break;					
				}
			}
		}
		
		if (!result) {
			throw new IllegalArgumentException(String.format("layout_ should be an %s * %s matrix", Dimensions.CUBE_EDGE_SIZE, Dimensions.CUBE_EDGE_SIZE ));
		}
		
		return result;
	}	
	
	/**
	 * @return Returns the side of the Cube which can be matched in the current state of the PuzzlePiece 
	 */
	public Side getSide() {
		return _side;
	}

	public void moveToSideFromFront(final Side side_) {
		try{
			Logger.getGlobal().info(String.format("Moveing Puzzle Piece from '%s' side to '%s' side\n", getSide().toString(), side_.toString()));
			
			for (Brick brick : this) {			
				brick.moveToSideFromFront(side_);
			}
			_side = side_;
		}
		catch(UnsupportedOperationException ex) {
			throw new UnsupportedOperationException("Only Panes of the Front side (Z=0) can be moved.", ex);
		}
	}
	
	/**
	 * Flipping the current PuzzlePiece (there is no dedicated side) - the state details of the PuzzlePiece gets reset 
	 */
	public void flip() {
		reset();
		char[][] layout = new char[Dimensions.CUBE_EDGE_SIZE][Dimensions.CUBE_EDGE_SIZE];
		for (int y=0;y<Dimensions.CUBE_EDGE_SIZE;y++) {
			for (int x=0;x<Dimensions.CUBE_EDGE_SIZE;x++) {
				layout[y][Dimensions.MAX_COORD-x] = _layout[y][x];
			}			
		}
		_layout = layout;
		createBricksFromLayout();
	}
	
	
	
	@Override
	protected void rotateAroundAxis(final CoordAxis axis_,final Direction direction_) {
		
		Logger.getGlobal().info(String.format("Rotating  Puzzle Piece from '%s' to '%s' side\n", _direction.toString(), direction_.toString()));
		
		Side side = getSide();
		_direction = direction_;
		
		//Step 1 - Re creation of the original brick set-up - Front side
		createBricksFromLayout();
	
		//Step 2 - Rotation - Front side
		super.rotateAroundAxis(axis_, direction_);
		
		//Step 3 - Shiftin  Puzzle Piece to the prev side
		if (side != Side.FRONT) {
			moveToSideFromFront(side);			
		}
	}
	
	
	@Override
	public String toString() {
		
		String result= "\n";
		result = String.format("%sHashCode: %d\n", result, hashCode());		
		result = String.format("%sDircetion: %s\n", result, _direction);		
		result = String.format("%s%s\n", result, toString(_side));	
		return result;
	}
	
	public String to360TString() {
		
		if (this.getSide() == Side.BACK) {
			PuzzlePiece clone = (PuzzlePiece)this.clone();
			clone.rotateAroundAxis(CoordAxis.Z, 2);
			return clone.toString(clone.getSide(), false);
		}else{
			return toString(_side, false);
			
		}
		
		
	}
	
	public boolean nextState() {
		boolean result = false;
		
		String prevBlock = this.toString();
		Side side = getSide();
		
		Direction newDirection = _direction.next();
		if (newDirection != null) {
			rotateAroundAxis(CoordAxis.Z, newDirection);
			result = true;
		}
		else{
			if (side != null) {
				Side newSide = side.next();
				
				if (newSide != null)
				{
					//	Reseting the whole  Puzzle Piece
					reset();
					
					//Moving to the side
					moveToSideFromFront(newSide);
					result = true;
				}
			}
		}
		
		if (result) {
			Logger.getGlobal().info(String.format(" Puzzle Piece '%s' state changed: '%s'", prevBlock, this.toString()));
		}
		
		return result;
	}
	
	@Override
	public Shape clone() {
		PuzzlePiece result = new PuzzlePiece(_layout);		
		result._direction = this._direction;
		result._side = this._side;		
		result.clear();
		
		for (Brick brick : this) {				
			result.createBrick(brick.getX(), brick.getY(), brick.getZ());
		}
		
		Logger.getGlobal().info(String.format(" Puzzle Piece '%s' cloned: '%s'", this.toString(), result.toString()));		
		return result;
	}
}
