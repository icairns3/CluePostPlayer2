package clueGame;
import java.awt.Color;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row, column;
	private Color color;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	protected Set<Card> unSeenPeople = new HashSet<Card>();
	protected Set<Card> unSeenWeapon= new HashSet<Card>();
	protected Set<Card> unSeenRoom=new HashSet<Card>();
	protected Set<Card> cardSeen=new HashSet<Card>();
	protected Solution suggestion;
	
	public Player(String name, int row, int column, Color color)
	  {
	    this.playerName = name;
	    this.row = row;
	    this.column = column;
	    this.color = color;
	  }
	
	
	
	public ArrayList<Card> getMyCards() {
		return myCards;
	}



	public void addCard(Card card){
	    this.myCards.add(card);  
	  }
	
	public Card disproveSuggestion(Solution suggestion){
		return null;
	}
	
	public Color getColor() {
		return color;
	}


	//Getters and Setters for testing
	public String getPlayerName(){
		return playerName;
	}
	
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}
	
	public void makeUnseen(Set<Card> deck) {
		// TODO Auto-generated method stub
		for(Card e: deck){
			switch(e.getCardType()){
			case PERSON:
				unSeenPeople.add(e);
				break;
			case WEAPON:
				unSeenWeapon.add(e);
				break;
			case ROOM:
				unSeenRoom.add(e);
				break;
			}
		}
	}
	public Solution getSuggestion(){
		return suggestion;
	}
}
