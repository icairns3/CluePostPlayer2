package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	private final int ROW_COUNT = 4;
	private final int COL_COUNT = 4;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * Initializes the game board.
	 */
	public IntBoard() {
		grid = new BoardCell[ROW_COUNT][COL_COUNT];
		adjMtx = new HashMap<>();
		visited = new HashSet<>();
		targets = new HashSet<>();
		populateBoard();
		calcAdjacencies();
	}

	/**
	 * Fills the grid with cells.
	 */
	private void populateBoard() {
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				grid[i][j] = new BoardCell(i, j);
			}
		}
	}

	/**
	 * Calculates the adjacent cells.
	 * NOTE: This method may be unfinished and is untested.
	 */
	private void calcAdjacencies() {
		for (int r = 0; r < ROW_COUNT; r++) {
			for (int c = 0; c < COL_COUNT; c++) {
				Set<BoardCell> adjCells = new HashSet<BoardCell>();
				BoardCell cell = grid[r][c];
				
				// If we aren't in the top row...
				if (r > 0) {
					// Get cell above
					adjCells.add(grid[r-1][c]);
				}
				
				// If we aren't in the bottom row...
				if (r < ROW_COUNT-1) {
					// Get cell below
					adjCells.add(grid[r+1][c]);
				}
				
				// If we aren't in the left column...
				if (c > 0) {
					adjCells.add(grid[r][c-1]);
				}
				
				// If we aren't in the right column...
				if (c < COL_COUNT-1) {
					adjCells.add(grid[r][c+1]);
				}
				
				adjMtx.put(cell, adjCells);
			}
		}
	}
	
	/**
	 * Calculates the viable targets.
	 * NOTE: This method may be unfinished and is untested.
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		Set<BoardCell> adjCells = adjMtx.get(startCell);
		visited.add(startCell);
		
		for (BoardCell adjCell : adjCells) {
			if (!visited.contains(adjCell)) {
				if (pathLength == 1) {
					targets.add(adjCell);
				} else {
					calcTargets(adjCell, (pathLength - 1));
				}
			}
		}
		
		visited.remove(startCell);
	}
	
	/**
	 * Gets the cell at a specific location.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public BoardCell getCellAt(int row, int col){
		return grid[row][col];
	}
	
	/**
	 * Gets the viable targets.
	 * 
	 * @return
	 */
	public Set<BoardCell> getTargets() {
		return this.targets;
	}
	
	/**
	 * Gets the cells adjacent to the desired cell.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		BoardCell cell = grid[row][col];
		return adjMtx.get(cell);
	}
}
