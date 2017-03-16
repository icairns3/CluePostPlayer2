package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and
	// then do all the tests.
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are PURPLE on the spreadsheet
	@Test
	public void testAdjacenciesInsideRooms() {
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(6, 9);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(13, 16);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(26, 14);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(24, 4);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(17, 7);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door
	// direction test.
	// These tests are ORANGE on the spreadsheet
	@Test
	public void testAdjacencyRoomExit() {
		// TEST DOORWAY RIGHT
		Set<BoardCell> testList = board.getAdjList(12, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 8)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(23, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(23, 8)));
		// TEST DOORWAY DOWN
		testList = board.getAdjList(28, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(29, 5)));
		// TEST DOORWAY UP
		testList = board.getAdjList(20, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 15)));
		// TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY ABOVE AND LEFT
		testList = board.getAdjList(12, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 11)));

	}

	// Test adjacency at entrance to rooms
	// These tests are PINK on the spreadsheet
	@Test
	public void testAdjacencyDoorways() {
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(13, 8);
		assertTrue(testList.contains(board.getCellAt(14, 8)));
		assertTrue(testList.contains(board.getCellAt(12, 8)));
		assertTrue(testList.contains(board.getCellAt(13, 9)));
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(29, 5);
		assertTrue(testList.contains(board.getCellAt(29, 6)));
		assertTrue(testList.contains(board.getCellAt(28, 5)));
		assertTrue(testList.contains(board.getCellAt(30, 5)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(15, 13);
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(16, 13)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(19, 15);
		assertTrue(testList.contains(board.getCellAt(19, 14)));
		assertTrue(testList.contains(board.getCellAt(19, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(18, 15)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are DARK BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test on left edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertEquals(1, testList.size());

		// Test on bottom edge of board, three walkway pieces
		testList = board.getAdjList(32, 7);
		assertTrue(testList.contains(board.getCellAt(32, 8)));
		assertTrue(testList.contains(board.getCellAt(32, 6)));
		assertTrue(testList.contains(board.getCellAt(31, 7)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways above and below
		testList = board.getAdjList(13, 4);
		assertTrue(testList.contains(board.getCellAt(12, 4)));
		assertTrue(testList.contains(board.getCellAt(14, 4)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(19, 7);
		assertTrue(testList.contains(board.getCellAt(19, 8)));
		assertTrue(testList.contains(board.getCellAt(19, 6)));
		assertTrue(testList.contains(board.getCellAt(18, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		assertEquals(4, testList.size());

		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(32, 11);
		assertTrue(testList.contains(board.getCellAt(31, 11)));
		assertTrue(testList.contains(board.getCellAt(32, 10)));
		assertEquals(2, testList.size());

		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(18, 17);
		assertTrue(testList.contains(board.getCellAt(18, 16)));
		assertTrue(testList.contains(board.getCellAt(19, 17)));
		assertEquals(2, testList.size());

		// Test on walkway next to door that is not in the needed
		// direction to enter
		testList = board.getAdjList(6, 3);
		assertTrue(testList.contains(board.getCellAt(7, 3)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		assertEquals(3, testList.size());
	}

	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(29, 11, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(29, 10)));
		assertTrue(targets.contains(board.getCellAt(30, 11)));

		board.calcTargets(1, 11, 1);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 10)));
		assertTrue(targets.contains(board.getCellAt(0, 11)));
		assertTrue(targets.contains(board.getCellAt(2, 11)));
	}

	// Tests of just walkways, 2 steps
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(29, 11, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(29, 9)));
		assertTrue(targets.contains(board.getCellAt(30, 10)));
		assertTrue(targets.contains(board.getCellAt(31, 11)));

		board.calcTargets(1, 11, 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 10)));
		assertTrue(targets.contains(board.getCellAt(2, 10)));
		assertTrue(targets.contains(board.getCellAt(3, 11)));
	}

	// Tests of just walkways, 4 steps
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(29, 11, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(30, 10)));
		assertTrue(targets.contains(board.getCellAt(28, 8)));
		assertTrue(targets.contains(board.getCellAt(29, 7)));
		assertTrue(targets.contains(board.getCellAt(30, 8)));
		assertTrue(targets.contains(board.getCellAt(31, 9)));
		assertTrue(targets.contains(board.getCellAt(29, 9)));
		assertTrue(targets.contains(board.getCellAt(31, 11)));
		assertTrue(targets.contains(board.getCellAt(32, 10)));

		board.calcTargets(1, 11, 4);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 10)));
		assertTrue(targets.contains(board.getCellAt(2, 10)));
		assertTrue(targets.contains(board.getCellAt(3, 11)));
		assertTrue(targets.contains(board.getCellAt(4, 10)));
		assertTrue(targets.contains(board.getCellAt(5, 11)));
	}

	// Tests of just walkways plus one door, 6 steps
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(29, 11, 6);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCellAt(31, 11)));
		assertTrue(targets.contains(board.getCellAt(30, 10)));
		assertTrue(targets.contains(board.getCellAt(29, 9)));
		assertTrue(targets.contains(board.getCellAt(32, 10)));
		assertTrue(targets.contains(board.getCellAt(31, 9)));
		assertTrue(targets.contains(board.getCellAt(30, 8)));
		assertTrue(targets.contains(board.getCellAt(29, 7)));
		assertTrue(targets.contains(board.getCellAt(28, 8)));
		assertTrue(targets.contains(board.getCellAt(26, 8)));
		assertTrue(targets.contains(board.getCellAt(27, 7)));
		assertTrue(targets.contains(board.getCellAt(28, 6)));
		assertTrue(targets.contains(board.getCellAt(29, 5)));
		assertTrue(targets.contains(board.getCellAt(30, 6)));
		assertTrue(targets.contains(board.getCellAt(31, 7)));
		assertTrue(targets.contains(board.getCellAt(32, 8)));
	}

	// Test getting into a room
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsIntoRoom() {
		// One room is exactly 2 away
		board.calcTargets(19, 0, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 1)));
		//Door
		assertTrue(targets.contains(board.getCellAt(20, 1)));
	}

	// Test getting into room, doesn't require all steps
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() {
		board.calcTargets(19, 0, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 3)));
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 0)));
		assertTrue(targets.contains(board.getCellAt(19, 1)));
		//Door 3 spaces away
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		//Door 2 spaces away
		assertTrue(targets.contains(board.getCellAt(20, 1)));
		
	}

	// TODO
	// Test getting out of a room
	// These are BROWN on the planning spreadsheet
	@Test
	public void testRoomExit() {
		// Take one step, essentially just the adj list
		board.calcTargets(25, 9, 1);
		Set<BoardCell> targets = board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(25, 8)));
		// Take two steps
		board.calcTargets(25, 9, 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(25, 7)));
		assertTrue(targets.contains(board.getCellAt(24, 8)));
		assertTrue(targets.contains(board.getCellAt(26, 8)));
	}

}
