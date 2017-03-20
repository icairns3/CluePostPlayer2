package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class gameActionTests {

private static Board board;
	
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		board.initialize();
	}
	
	
	
	//Select a target. 
	@Test
	public void testTargetRandomSelection() {
	    ComputerPlayer player = new ComputerPlayer("joe", 32, 5, Color.BLUE);
	    // Pick a location with no rooms in target, just three targets
	    board.calcTargets(32,6, 1);
	    boolean loc_31_6 = false;
	    boolean loc_32_5 = false;
	    boolean loc_32_7 = false;
	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {
	        BoardCell selected = player.pickLocation(board.getTargets());
	        if(selected == board.getCellAt(31, 6))
	            loc_31_6 = true;
	        else if (selected == board.getCellAt(32, 5))
	            loc_32_5 = true;
	        else if (selected == board.getCellAt(32, 7))
	            loc_32_7 = true;
	        else
	        	assertTrue(false);
	        
	        }
	        // Ensure each target was selected at least once
	        assertTrue(loc_31_6);
	        assertTrue(loc_32_5);
	        assertTrue(loc_32_7);				
	}
	@Test
	public void testWithRoomThatWasntVisited() {
		ComputerPlayer player = new ComputerPlayer("joe", 11, 13, Color.BLUE);
	    // Pick a location with no rooms in target, just three targets
	    board.calcTargets(11,13, 1);
	    BoardCell selected = player.pickLocation(board.getTargets());
	    assertEquals(selected, board.getCellAt(10, 13));
	}

	
}
