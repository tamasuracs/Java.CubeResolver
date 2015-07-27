
package com.tauracs.cubepuzzle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.tauracs.cubepuzzle.model.enums.Side;

/**
 * Class for the Cube Puzzle resolution
 *
 */
public final class PuzzleResolver {
	/**
	 * Member variable storing the result Cube
	 */
	Cube _resultCube = null;
	
	/**
	 * HashMap storing the PuzzlePieces of the solution
	 */
	HashMap<Side,PuzzlePiece> _solution = new HashMap<Side, PuzzlePiece>();
	
	/**
	 * Getter of the result cube
	 * @return
	 */
	public Cube getResultCube() {
		return _resultCube;
	}
	
	/**
	 * Getter for the solution - PuzzlePieces
	 * @return
	 */
	public Iterable<PuzzlePiece> getSolution() {
		return _solution.values();
	}
	
	/**
	 * Tries to resolve the Cube puzzle with the passed PuzzlePices
	 * @param puzzlePieces_ the pieces 
	 * @return
	 */
	public boolean resolve( final List<PuzzlePiece> puzzlePieces_) {
		_resultCube = null;
		_solution = new HashMap<Side, PuzzlePiece>();
		
		HashSet<PuzzlePiece> tempSolution = new HashSet<PuzzlePiece>();
		
		boolean result = resolve(new Cube(), puzzlePieces_, tempSolution);
		
		if (!result) {
			_resultCube = null;
			_solution = new HashMap<Side, PuzzlePiece>();
		}
		else{
			for (PuzzlePiece piece : tempSolution) {
				_solution.put(piece.getSide(), piece);
			}
		}
		
		return result;
	}
	
	/**
	 * Generates a 360T string formatted (unfolded) string representation
	 * @return
	 */
	public String to360TString() {
		return toString().replace(Brick.EMPTY_BLOCK_MARKER, ' ').replace(Brick.BRICK_MARKER, 'o');
	}
	
	/**
	 * Standard string representation of an instance
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String string = null;
		
		for (int i=0; i<Dimensions.CUBE_EDGE_SIZE; i++) {
			string ="";			
			string = generateRow(_solution.get(Side.LEFT), i, string);
			string = generateRow(_solution.get(Side.FRONT), i, string);
			string = generateRow(_solution.get(Side.RIGHT), i, string);
			sb.append(string);
			sb.append("\n");
		}
		
		for (int i=Dimensions.CUBE_EDGE_SIZE; i<2*Dimensions.CUBE_EDGE_SIZE; i++) {
			string = getIndentRow();
			string = generateRow(_solution.get(Side.BOTTOM), i, string);
			sb.append(string);
			sb.append("\n");
		}
		
		for (int i=2*Dimensions.CUBE_EDGE_SIZE; i<3*Dimensions.CUBE_EDGE_SIZE; i++) {
			string = getIndentRow();
			string = generateRow(_solution.get(Side.BACK), i, string);
			sb.append(string);
			sb.append("\n");
		}
		
		for (int i=3*Dimensions.CUBE_EDGE_SIZE; i<4*Dimensions.CUBE_EDGE_SIZE; i++) {
			string = getIndentRow();
			string = generateRow(_solution.get(Side.TOP), i, string);
			sb.append(string);
			sb.append("\n");
		}
				
		return sb.toString();
	}
	
	
	/**
	 * Generates one string row for the passed puzzle piece
	 * 
	 * @param piece_ - puzzle piece 
	 * @param lineIdx_ - line number of 
	 * @param prefix_ - previously generated content for the row
	 * @return
	 */
	private static String generateRow(final PuzzlePiece piece_, final int lineIdx_, final String prefix_) {
		String result = null;
		if (piece_ != null) {
			result = String.format("%s%s", prefix_, piece_.to360TString().split("\n")[lineIdx_ % Dimensions.CUBE_EDGE_SIZE]);
		}
		else{
			result = String.format("%s%s", prefix_, getIndentRow());
		}
		return result;
	}

	/**
	 * Generates indentation string
	 */
	private static String getIndentRow() {
		String indentBlock = "";
		
			for (int i=0;i<Dimensions.CUBE_EDGE_SIZE;i++) {
				indentBlock=String.format(" %s", indentBlock);
			}
			indentBlock=String.format("%s", indentBlock);
			
		return indentBlock;
	}

	
	/**
	 * Puzzle Resolver recursive function
	 * 
	 * @param cube_ - The helper Cube instance whose (and its clones) building blocks are eliminated
	 * @param puzzlePieces_ - The set of puzzlePieces_ that should be used for the resolution
	 * @param solution_ - Set containing the list of the various puzzle pieces 
	 * @return true if the solution was successful - solution_ contains the puzzlePieces_
	 */
	private boolean resolve(final Cube cube_, final List<PuzzlePiece> puzzlePieces_,final HashSet<PuzzlePiece> solution_)
	{
		boolean result = false;
		
		//Recursion ends when cube is empty or there are no puzzle elements
		if (cube_.size() == 0
				||
			puzzlePieces_.isEmpty()) {
			_resultCube = cube_;
			return true;
		}
		
		for (PuzzlePiece piece : puzzlePieces_) {
			// Checking puzzle pieces from the list			 
			boolean extraction=cube_.extract(piece);
			boolean mirrored = false;
			
			// Checking until extraction was successful or all the states of the PuzzlePiece were visited
			 
			while(!extraction)
			{
				if (piece.nextState()) {
					extraction = cube_.extract(piece);
				}
				else{
					if (mirrored) {
						break;
					}else{
						piece.flip();
						mirrored = true;
					}
				}
			}
			
			/**
			 * If there was Puzzle state found that was extracted 
			 */
			if (extraction) {
				//Adding the Piece to the solution
				solution_.add(piece);
				
				//Composing a clone list from the remaining puzzle pieces
				List<PuzzlePiece> list = new ArrayList<PuzzlePiece>();				
				for (PuzzlePiece b : puzzlePieces_) {
					if (b == piece)
						continue;
					
					PuzzlePiece clonePuzzlePiece = (PuzzlePiece)b.clone();
					list.add(clonePuzzlePiece);
				}
				
				//Creating a clone cube
				Cube cloneCube = (Cube)cube_.clone();
				
				//Trying the resolution with the clones recursively
				extraction = resolve(cloneCube, list, solution_);
				if (!extraction) {					
					//if there was an issue with the sub extract - the current piece should be removed					
					solution_.remove(piece);
				}
				
				return extraction;
			}
		}
		
		return result;
	}

}
