package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class gameSetupTests {
	private static Board board;
	
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		board.initialize();
	}
	@Test
	public void testLoadingPeople() {
		ArrayList<Player> testList = board.getPlayers();
		assertEquals(testList.size(), 6);
		//Human Player
		assertEquals("Professor Steeze", testList.get(0).getPlayerName());
		assertEquals(Color.RED, testList.get(0).getColor());
		assertEquals(3, testList.get(0).getColumn());
		assertEquals(0, testList.get(0).getRow());
		
		// computer 1
		assertEquals("Dr Bean", testList.get(1).getPlayerName());
		assertEquals(Color.BLUE, testList.get(1).getColor());
		assertEquals(0, testList.get(1).getColumn());
		assertEquals(9, testList.get(1).getRow());
		
		// computer 2
		assertEquals("Mrs $Bills", testList.get(2).getPlayerName());
		assertEquals(Color.GREEN, testList.get(2).getColor());
		assertEquals(6, testList.get(2).getColumn());
		assertEquals(32, testList.get(2).getRow());
	}
	
	@Test
	public void testLoadingDeck(){
		ArrayList<Card> cardDeck = board.getCardDeck();
		int personCount = 0;
		int weaponCount = 0;
		int roomCount=0;
		
		//check for correct size of deck
		assertEquals(cardDeck.size(), 21);
		Set names = new HashSet<String>();
		for(Card e:cardDeck){
			switch(e.getCardType()){
			case PERSON:
				personCount++;
				break;
			case WEAPON:
				weaponCount++;
				break;
			case ROOM:
				roomCount++;
				break;
			}
			names.add(e.getCardName());
		}
		// check for correct number of each card
		assertEquals(personCount, 6);
		assertEquals(weaponCount, 6);
		assertEquals(roomCount, 9);
		
		//Make sure we have the certain cards deck
		assertTrue(names.contains("Dr Bean"));
		assertTrue(names.contains("Lady Luck"));
		assertTrue(names.contains("Knife"));
		assertTrue(names.contains("Plunger"));
		assertTrue(names.contains("Kitchen"));
	}
	@Test
	public void testDealingDeck(){
		ArrayList<Player> testList = board.getPlayers();
		int cardCount=0;
		for(Player e: testList){
			cardCount+=e.getMyCards().size();
		}
		assertEquals(21-3, cardCount);// assert correct number of cards
		
		//check players have ~same number of cards
		assertTrue(Math.abs(testList.get(0).getMyCards().size()-testList.get(1).getMyCards().size())<2);
		assertTrue(Math.abs(testList.get(1).getMyCards().size()-testList.get(2).getMyCards().size())<2);
		
		
		//MAKE sure all players have unique cards
		//make sure player 1's cards are unique
		for(Card e: testList.get(0).getMyCards()){
			assertTrue(!testList.get(1).getMyCards().contains(e));
			assertTrue(!testList.get(2).getMyCards().contains(e));
			
		}
		//make sure player 2's cards are unique;
		for(Card e: testList.get(1).getMyCards()){
			assertTrue(!testList.get(2).getMyCards().contains(e));
			
		}
		
	}
	
}
