package clueGame;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;

	/**
	 * CONSTRUCTOR
	 * 
	 * Creates a new board cell at a specific location.
	 * 
	 * @param row
	 * @param col
	 */
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		doorDirection = DoorDirection.NONE;
	}

	public BoardCell(int row, int col, char initial, DoorDirection dir) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		doorDirection = dir;
	}

	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isRoom() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWalkway() {
		if (Board.getInstance().getLegend().get(initial).equals("Walkway")) {
			return true;
		} else {
			return false;
		}
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}
}
