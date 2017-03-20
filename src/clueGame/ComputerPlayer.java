package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	BoardCell lastDoorWay;

	public void setLastDoorWay(BoardCell lastDoorWay) {
		this.lastDoorWay = lastDoorWay;
	}

	public ComputerPlayer(String name, int row, int column, Color color) {
		super(name, row, column, color);
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets){
		for(BoardCell e:targets){
			if(e != lastDoorWay && e.isDoorway()){
				lastDoorWay = e;
				return e;
			}
		}
		List<BoardCell> asList = new ArrayList(targets);
		Collections.shuffle(asList);
		return asList.get(0);
	}
	
	
	public void makeAccusation(){
		
	}
	

	public void addSeen(Card card) {
		// TODO Auto-generated method stub
		cardSeen.add(card);
		switch(card.getCardType()){
		case PERSON:
			unSeenPeople.remove(card);
			break;
		case WEAPON:
			unSeenWeapon.remove(card);
			break;
		case ROOM:
			unSeenRoom.remove(card);
			break;
		}
	}

	public void createSuggestion(Card room) {
		// TODO Auto-generated method stub
		List<Card> aList = new ArrayList(unSeenPeople);
		List<Card> bList = new ArrayList(unSeenWeapon);
		Collections.shuffle(aList);
		Collections.shuffle(bList);
		suggestion = new Solution(aList.get(0), room, bList.get(0));
	}

	
}
