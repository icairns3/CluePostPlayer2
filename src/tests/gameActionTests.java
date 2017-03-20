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
	
	
	
	//Select a target._______________________________________________________ 
	
	//if no rooms in list, select randomly
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
	
	// if room in list that was not just visited, must select it
	@Test
	public void testWithRoomThatWasntVisited() {
		ComputerPlayer player = new ComputerPlayer("joe", 11, 13, Color.BLUE);
	    // Pick a location with no rooms in target, just three targets
	    board.calcTargets(11,13, 1);
	    for (int i=0; i<100; i++) {
	    BoardCell selected = player.pickLocation(board.getTargets());
	    assertEquals(selected, board.getCellAt(10, 13));
	    player.setLastDoorWay(null);
	    }
	}
	
	// If room just visited is in list, each target (including room) selected randomly
		@Test
		public void testWithRoomThatWasVisited() {
			ComputerPlayer player = new ComputerPlayer("joe", 11, 13, Color.BLUE);
		    // Pick a location with no rooms in target, just three targets
		    board.calcTargets(11,13, 1);
		    
		    //This will set the last door way as 10,13
		    BoardCell selected = player.pickLocation(board.getTargets());
		    assertEquals(selected, board.getCellAt(10, 13));//makes sure it selected the door
		    
		    //Now the last doorway should be the 10,13 position and the player should pick a random spot
		    boolean loc_11_12 = false;
		    boolean loc_12_13 = false;
		    boolean loc_11_14 = false;
		    boolean loc_10_13 = false;
		    // Run the test a large number of times
		    for (int i=0; i<100; i++) {
		        selected = player.pickLocation(board.getTargets());
		        if(selected == board.getCellAt(11, 12))
		            loc_11_12 = true;
		        else if (selected == board.getCellAt(12, 13))
		            loc_12_13 = true;
		        else if (selected == board.getCellAt(11, 14))
		            loc_11_14 = true;
		        else if (selected == board.getCellAt(10, 13))
		            loc_10_13 = true;
		        else
		        	assertTrue(false);
		        
		        }
		        // Ensure each target was selected at least once
		        assertTrue(loc_11_12);
		        assertTrue(loc_12_13);
		        assertTrue(loc_11_14);
		        assertTrue(loc_10_13);
		    
		}
		
		
	//Make an accusation. ____________________________________________________________
	@Test
	public void testAccusation(){
		//Check real answer
		assertTrue(board.checkAccusation(new Solution(board.getSolution().getPersonCard(), board.getSolution().getRoomCard(), board.getSolution().getWeaponCard())));
		//solution with wrong person
		assertFalse(board.checkAccusation(new Solution(new Card("JoeJoe", CardType.PERSON), board.getSolution().getRoomCard(), board.getSolution().getWeaponCard())));
		//solution with wrong room
			
		//solution with wrong weapon
	
	
	}
}
