package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	private ArrayList<Player> playerList;
	private Map<String, CardType> cardDeck;

	// Header for data file location
	private static final String dataLocation = "data/";

	// ctor is private to ensure only one can be created
	private Board() {

	}
	// this method returns the only Board

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		legend = new HashMap<>();
		adjMatrix = new HashMap<>();
		cardDeck = new HashMap<String, CardType>();
		//load Legend file
		try {
			loadRoomConfig();
		} catch (FileNotFoundException e) {
			System.out.println("Legend file was not found at: " + roomConfigFile);
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//load Map file
		try {
			loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.out.println("Board file was not found at: " + boardConfigFile);
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		calcAdjacencies();
		
		//Load Players into Game
		try {
			loadPeople();
		} catch (FileNotFoundException e) {
			System.out.println("People file not found");
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		//Load Players into Game
				try {
					loadWeapons();
				} catch (FileNotFoundException e) {
					System.out.println("Weapons file not found");
					e.printStackTrace();
				} catch (BadConfigFormatException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
		
		
	}
	
	public void loadWeapons() throws FileNotFoundException, BadConfigFormatException {
		Scanner scan = new Scanner(new File(dataLocation + "Weapons.txt"));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			cardDeck.put(line, CardType.WEAPON);
		}
		scan.close();
		
	}

	public void loadPeople() throws FileNotFoundException, BadConfigFormatException {
		playerList = new ArrayList<Player>();
		Scanner scan = new Scanner(new File(dataLocation + "People.txt"));
		int playerNum=0;
		while (scan.hasNextLine()) {
			String line1, line2, line3, line4;
			if(scan.hasNextLine()) line1 = scan.nextLine();
			else throw new BadConfigFormatException();
			if(scan.hasNextLine()) line2 = scan.nextLine();
			else throw new BadConfigFormatException();
			if(scan.hasNextLine()) line3 = scan.nextLine();
			else throw new BadConfigFormatException();
			if(scan.hasNextLine()) line4 = scan.nextLine();
			else throw new BadConfigFormatException();
			Color color = convertColor(line2);
			if(color == null)throw new BadConfigFormatException("Bad Color");
			int row, column;
			try{
				row = Integer.parseInt(line4);
				column = Integer.parseInt(line3);
			}catch(NumberFormatException e){
				throw new BadConfigFormatException();
			}
			if(row != -1){
				if(playerNum == 0)playerList.add(new HumanPlayer(line1, row, column, color));
				else playerList.add(new ComputerPlayer(line1, row, column, color));
			}
			cardDeck.put(line1, CardType.PERSON);
			
			playerNum++;
		}
		scan.close();
		
	}
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		Scanner scan = new Scanner(new File(dataLocation + roomConfigFile));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			// Splitting by ", " to consume the space after the comma
			String[] entries = line.split(", ");
			if (entries[2].equals("Card") || entries[2].equals("Other")) {
				legend.put(entries[0].charAt(0), entries[1]);
				if(entries[2].equals("Card"))cardDeck.put(entries[1], CardType.ROOM);

			} else {
				scan.close();
				throw new BadConfigFormatException("Room is not a Card or Other");
			}
		}
		scan.close();
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		Scanner scan = new Scanner(new File(dataLocation + boardConfigFile));
		ArrayList<ArrayList<BoardCell>> tempBoard = new ArrayList<ArrayList<BoardCell>>();
		int i = 0;
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] entries = line.split(",");
			tempBoard.add(new ArrayList<BoardCell>());
			for (int j = 0; j < entries.length; j++) {
				if (!legend.containsKey(entries[j].charAt(0))) {
					scan.close();
					throw new BadConfigFormatException("Room " + entries[j].charAt(0) + " not in the Legend");
				}
				if (entries[j].length() == 1) {
					tempBoard.get(tempBoard.size() - 1).add(new BoardCell(i, j, entries[j].charAt(0)));
				} else {
					DoorDirection dir;
					switch (entries[j].charAt(1)) {
					case 'U':
						dir = DoorDirection.UP;
						break;
					case 'D':
						dir = DoorDirection.DOWN;
						break;
					case 'L':
						dir = DoorDirection.LEFT;
						break;
					case 'R':
						dir = DoorDirection.RIGHT;
						break;
					case 'N':
						dir = DoorDirection.NONE;
						break;
					default:
						scan.close();
						throw new BadConfigFormatException(
								"Tile had invalid second character \"" + entries[j].charAt(j) + "\".");

					}
					tempBoard.get(tempBoard.size() - 1).add(new BoardCell(i, j, entries[j].charAt(0), dir));
				}

			}
			i++;

		}

		numRows = tempBoard.size();
		numColumns = tempBoard.get(0).size();
		for (ArrayList<BoardCell> row : tempBoard) {
			if (row.size() != numColumns) {
				scan.close();
				throw new BadConfigFormatException("Not all rows are the same length.");
			}
		}
		board = new BoardCell[numRows][numColumns];

		for (i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = tempBoard.get(i).get(j);

			}
		}

		scan.close();
	}

	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				adjMatrix.put(board[i][j], calcAdjacent(i, j));
			}
		}
	}

	private Set<BoardCell> calcAdjacent(int row, int col) {
		Set<BoardCell> adj = new HashSet<BoardCell>();
		if (board[row][col].isDoorway()) {
			switch (board[row][col].getDoorDirection()) {
			case DOWN:
				if (board[row + 1][col].isWalkway()) {
					adj.add(board[row + 1][col]);
				}
				break;
			case UP:
				if (board[row - 1][col].isWalkway()) {
					adj.add(board[row - 1][col]);
				}
				break;
			case RIGHT:
				if (board[row][col + 1].isWalkway()) {
					adj.add(board[row][col + 1]);
				}
				break;
			case LEFT:
				if (board[row][col - 1].isWalkway()) {
					adj.add(board[row][col - 1]);
				}
				break;
			default:
				System.out.println("Error: a doorway was found that was not pointing Down, Up, Right, or Left.");
				break;
			}
		} else if (board[row][col].isWalkway()) {
			// Sets up two arrays of all adjacencies
			int[] rows = { row + 1, row - 1, row, row };
			int[] cols = { col, col, col + 1, col - 1 };
			for (int i = 0; i < rows.length; i++) {
				if (rows[i] >= 0 && rows[i] < numRows && cols[i] >= 0 && cols[i] < numColumns) {
					if(board[rows[i]][cols[i]].isWalkway()){
						adj.add(board[rows[i]][cols[i]]);
					} else {
						switch(board[rows[i]][cols[i]].getDoorDirection()){
						case UP:
							if(i == 0){
								adj.add(board[rows[i]][cols[i]]);
							}
							break;
						case DOWN:
							if(i == 1){
								adj.add(board[rows[i]][cols[i]]);
							}
							break;
						case LEFT:
							if(i == 2){
								adj.add(board[rows[i]][cols[i]]);
							}
							break;
						case RIGHT:
							if(i == 3){
								adj.add(board[rows[i]][cols[i]]);
							}
							break;
						default:
							break;
						}
					}
				}
			}
		}

		return adj;
	}

	public void calcTargets(int row, int column, int pathLength) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		calcTargetsHelper(board[row][column], pathLength);
	}
	
	private void calcTargetsHelper(BoardCell startCell, int pathLength){
		Set<BoardCell> adjCells = adjMatrix.get(startCell);
		visited.add(startCell);
		
		for (BoardCell adjCell : adjCells) {
			if (!visited.contains(adjCell)) {
				if (pathLength == 1 || adjCell.isDoorway()) {
					targets.add(adjCell);
				} else {
					calcTargetsHelper(adjCell, pathLength - 1);
				}
			}
		}
		
		visited.remove(startCell);
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(board[row][col]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return playerList;
	}

	public Map<String, CardType> getCardDeck() {
		return cardDeck;
	}
}
