package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class gameActionTests {

private static Board board;

private static Card blueman, redman, greenman, orangeman;
private static Card study, kitchen, diningroom, ballroom;
private static Card watergun, rope, masonJar, breadloaf;
private static Set<Card> deck;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		board.initialize();
		
		deck = new HashSet<Card>();
		
		blueman = new Card("Blueman", CardType.PERSON);
		deck.add(blueman);
	    redman = new Card("redman", CardType.PERSON);
	    deck.add(redman);
	    greenman = new Card("greenman", CardType.PERSON);
	    deck.add(greenman);
	    orangeman = new Card("Orangeman", CardType.PERSON);
	    deck.add(orangeman);
	    
	    study = new Card("Study", CardType.ROOM);
	    deck.add(study);
	    kitchen = new Card("Kitchen", CardType.ROOM);
	    deck.add(kitchen);
	    diningroom = new Card("Dining Room", CardType.ROOM);
	    deck.add(diningroom);
	    ballroom = new Card("Ballroom", CardType.ROOM);
	    deck.add(ballroom);
	    
	    watergun = new Card("Watergun", CardType.WEAPON);
	    deck.add(watergun);
	    breadloaf = new Card("BreadLoaf", CardType.WEAPON);
	    deck.add(breadloaf);
	    masonJar = new Card("Mason Jar", CardType.WEAPON);
	    deck.add(masonJar);
	    rope = new Card("Rope", CardType.WEAPON);
	    deck.add(rope);
	    
	    
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
		assertFalse(board.checkAccusation(new Solution(board.getSolution().getPersonCard(), new Card("JoeJoe", CardType.ROOM), board.getSolution().getWeaponCard())));
		//solution with wrong weapon
		assertFalse(board.checkAccusation(new Solution(board.getSolution().getPersonCard(), board.getSolution().getRoomCard(), new Card("JoeJoe", CardType.WEAPON))));
	
	}
	
	//Create suggestion___________________________________________________________________
	@Test
	public void CreateSuggestion(){
		ComputerPlayer player = new ComputerPlayer("joe", 11, 13, Color.BLUE);
		player.makeUnseen(deck);
		player.addSeen(blueman);
		player.addSeen(redman);
		player.addSeen(greenman);
		
		player.addSeen(watergun);
		player.addSeen(rope);
		player.addSeen(masonJar);
		
		player.createSuggestion(kitchen);
		assertEquals(kitchen, player.getSuggestion().room);
	    assertEquals(orangeman, player.getSuggestion().person);
	    assertEquals(breadloaf, player.getSuggestion().weapon);
		
	}
	
	
}
