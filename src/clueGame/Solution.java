package clueGame;

public class Solution {
	private String person;
	private String room;
	private String weapon;
	private Card personCard;
	private Card roomCard;
	private Card weaponCard;
	
	public String getPerson() {
		return person;
	}

	public String getRoom() {
		return room;
	}

	public String getWeapon() {
		return weapon;
	}

	public Solution(Card person, Card room, Card weapon){
		this.personCard = person;
		this.weaponCard = weapon;
		this.roomCard = room;
		this.person = this.personCard.getCardName();
		this.room = this.roomCard.getCardName();
		this.weapon = this.weaponCard.getCardName();
	}
	
}
