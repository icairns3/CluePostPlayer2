package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public String getCardName() {
		return cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	public boolean equals(){
		return false;
	}

	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
}
