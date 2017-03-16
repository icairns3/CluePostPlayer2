package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 33;
	public static final int NUM_COLUMNS = 18;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();
	}
	
	@Test
	public void testRoomLegend() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Observatory", legend.get('O'));
		assertEquals("Conservatory", legend.get('C'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Closet", legend.get('X'));
		// First in file
		assertEquals("Walkway", legend.get('W'));
		// Last in file
		assertEquals("Manufactory", legend.get('M'));
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	@Test
	public void fourDoorDirections() {
		// Observatory
		BoardCell room = board.getCellAt(6, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		// Ballroom
		room = board.getCellAt(26, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		// Conservatory
		room = board.getCellAt(6, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		// Kitchen
		room = board.getCellAt(9, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		// Test that room pieces that aren't doors know it.
		// Manufactory
		room = board.getCellAt(24, 12);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(13, 8);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++) {
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		}
		Assert.assertEquals(23, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('O', board.getCellAt(0, 0).getInitial());
		assertEquals('C', board.getCellAt(0, 12).getInitial());
		assertEquals('M', board.getCellAt(20, 9).getInitial());
		// Test last cell in room
		assertEquals('L', board.getCellAt(6, 9).getInitial());
		assertEquals('A', board.getCellAt(15, 11).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(7, 0).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(18, 10).getInitial());
	}
	

}
