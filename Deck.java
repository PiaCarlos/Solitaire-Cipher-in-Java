/*
 Your name here: Carlos Alvarado
 Your McGill ID here: 260551435
*/

package assignment2;

import java.util.Random;

public class Deck {
	public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
	public static Random gen = new Random();

	public int numOfCards; // contains the total number of cards in the deck
	public Card head; // contains a pointer to the card on the top of the deck

	/* 
	 * TODO: Initializes a Deck object using the inputs provided
	 */
	public Deck(int numOfCardsPerSuit, int numOfSuits) {
		
		// Raise an error in the inputs are not valid
		
		if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13 || numOfSuits < 1 || 
				numOfSuits > suitsInOrder.length ) {
			
			throw new IllegalArgumentException("Some or all of the inputs are not valid");

		}	
		
		this.head = null;
		this.numOfCards = 0;
		 	
	
		// Creating the deck with the specified cards except the jokers
		 
		int i = 0;
		
		while(i < numOfSuits) {
			int j = 1;
			while(j < numOfCardsPerSuit + 1) {
				String s = suitsInOrder[i]; // the suit of the card
				int positionSuit = j; // position of the card in the suit
				PlayingCard card = new PlayingCard(s,positionSuit);
				addCard(card);
				
				j++;	
			}			
				i++;
		}
		
		// adding the 2 jokers
		
		Joker rj = new Joker("red");
		Joker bj = new Joker("black");
		addCard(rj);
		addCard(bj);
		
		}

	/* 
	 * TODO: Implements a copy constructor for Deck using Card.getCopy().
	 * This method runs in O(n), where n is the number of cards in d.
	 */
	public Deck(Deck d) {
		
		this.head = null;
		this.numOfCards = 0;
		
		// only copy if d is not empty
		if (d.numOfCards > 0) {
			int i = 0;
			Card card = d.head;
	
		    while(i < d.numOfCards) {
			
		    	Card copyCard = card.getCopy();
		    	this.addCard(copyCard);
		    	card = card.next;
		    	i++;
		    }
		}	
	}

	/*
	 * For testing purposes we need a default constructor.
	 */
	public Deck() {
		
		this.head = null;
		this.numOfCards = 0;
		

	}

	/* 
	 * TODO: Adds the specified card at the bottom of the deck. This 
	 * method runs in $O(1)$. 
	 */
	public void addCard(Card c) {
	
		if (this.head == null) {
			this.head = c;
			c.prev = head;
		}
		
		else {
		
		c.prev = head.prev;
		head.prev.next = c;
		
		}
		
		c.next = head;
		head.prev = c;
		
		numOfCards++;
	}

	/*
	 * TODO: Shuffles the deck using the algorithm described in the pdf. 
	 * This method runs in O(n) and uses O(n) space, where n is the total 
	 * number of cards in the deck.
	 */
	public void shuffle() {
	
		Card[] cards = new Card[numOfCards];
		
		// Creating an array cards with the cards on the deck.
		if (numOfCards > 0) {
			int n = 0;
			Card copyCard = head;
	
		    while(n < numOfCards) {
		    	
		    	cards[n] = copyCard;
		    	copyCard = copyCard.next;
		    	n++;
		    }
		    
		    // shuffle the array
		    for (int i = numOfCards - 1; i > 0; i--) {	

		    	int j = gen.nextInt(i + 1);
		    	Card tempCard = cards[j];
		    	cards[j] = cards [i];
		    	cards[i] = tempCard; 
		 
		    }
		    
		   head = null; // I do this to remove every card in the deck. So simple
		  
		   n = 0;
		   while(n < numOfCards) {
			   
			   this.addCard(cards[n]);
			   numOfCards--; // I didn't change the number of cards
			   n++;
		   }
		
		}
		
		
	}

	/*
	 * TODO: Returns a reference to the joker with the specified color in 
	 * the deck. This method runs in O(n), where n is the total number of 
	 * cards in the deck. 
	 */
	public Joker locateJoker(String color) {

		Card card = head;
		int i = 0;
		
		// If the deck is empty, it will simply return null
		while(i < numOfCards) {
			
			if(card instanceof Joker) {
			Joker joker = (Joker) card;
			
				if(joker.getColor().equalsIgnoreCase(color)) {
					return joker;
				}
			}
			card = card.next;
			i++;
		}
		
		return null;
	}

	/*
	 * TODO: Moved the specified Card, p positions down the deck. You can 
	 * assume that the input Card does belong to the deck (hence the deck is
	 * not empty). This method runs in O(p).
	 */
	public void moveCard(Card c, int p) {
		
		
		int i = 0;
		int j = 0;
		
		if (c != head) {
			
			// Get final position of c
			Card finalPositionNext = c.next;
	        while (i < p) { 
	        	finalPositionNext = finalPositionNext.next;
	        	i++;
	        	
	        	if(finalPositionNext == c) {
	        		finalPositionNext = finalPositionNext.next;
	        	}
	        }
	        
	        
	        // move c to that final position
	        
	        c.prev.next = c.next;
	        c.next.prev = c.prev;
	        c.next = finalPositionNext;
	        c.prev = finalPositionNext.prev;
	        finalPositionNext.prev.next = c;
	        finalPositionNext.prev = c;
	    }

		else {
	
			while(j < p){
				 	
				Card bottomCard = c.next;
				c.next = c.next.next;
				this.addCard(bottomCard);
				numOfCards--; // since the size didn't change
					j++;
				}
			head.next.prev = head;
			
		}
		
	}

	/*
	 * TODO: Performs a triple cut on the deck using the two input cards. You 
	 * can assume that the input cards belong to the deck and the first one is 
	 * nearest to the top of the deck. This method runs in O(1)
	 */
	public void tripleCut(Card firstCard, Card secondCard) {
		
		// Cut off points
		Card lastTop = firstCard.prev; // the last of those in top to be moved to bottom
		Card firstBottom = secondCard.next; // the first of those in bottom to be moved up.
		
		// Do the triple cut
		
		if(firstCard == head && secondCard.next == head) {
			return;
		} 
		
		if(firstCard == head) {
			head.prev.next = firstCard;
	        head = firstBottom;
	        head.prev = secondCard;
	      
		} 
		
		else if (secondCard.next == head)  {
			// if secondCard is at the bottom
		    head.prev.next = head;
	        head = firstCard;
	        head.prev = lastTop;	
	        
		}
		
		else { // If secondCard is not at the bottom and first is not at the top. 
		
			Card tail = head.prev;
			Card beforeFirst = head.prev;
			Card afterSecond = head;
			
			secondCard.next = head; //
			tail.next = firstCard; //
			head = firstBottom; // the new head. 
			head.prev = lastTop; // the new tail
			lastTop.next = head; //
			
			afterSecond.prev = secondCard;
		
			firstCard.prev = beforeFirst;
			

			
		}
	}

	/*
	 * TODO: Performs a count cut on the deck. Note that if the value of the 
	 * bottom card is equal to a multiple of the number of cards in the deck, 
	 * then the method should not do anything. This method runs in O(n).
	 */
	public void countCut() {

		int lastVal = head.prev.getValue();
		int index = lastVal % this.numOfCards;// # of cards at the top should be transported

		if( index == 0) {
			return;
		}
		
		Card firstCard = head;
		Card lastCard = firstCard;
		int i = 1;
		
		while(i < index) {
			lastCard = lastCard.next;
			i++;
		}
		
		Card bottomCard = head.prev;
		Card newHead = lastCard.next;
		
		
		lastCard.next = bottomCard;
		bottomCard.prev.next = firstCard;
		firstCard.prev = bottomCard.prev;
		bottomCard.prev = lastCard;
		head = newHead; // the new head.  
		head.prev = bottomCard;
		head.prev.next = head;
		
		
		
		
	}

	/*
	 * TODO: Returns the card that can be found by looking at the value of the 
	 * card on the top of the deck, and counting down that many cards. If the 
	 * card found is a Joker, then the method returns null, otherwise it returns
	 * the Card found. This method runs in O(n).
	 */
	public Card lookUpCard() {
		
		if (head == null) {
	        return null; 
	    }

		int topVal = head.getValue();
		int i = 0;
		
		Card cardPosition = head;
		
		while(i < topVal) {
			cardPosition = cardPosition.next;
			i++;
		}
		
		if (cardPosition instanceof Joker ) {
			return null;
		}
		
		return cardPosition;
		
		
	}

	/*
	 * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
	 * using this deck. This method runs in O(n).
	 */
	public int generateNextKeystreamValue() {
	
		// 1) Locate red joker and move it one card below.
		
		 Joker redJoker = locateJoker("red");
		 moveCard(redJoker, 1);
		 
		 // 2) Locate black joker and move it 2 cards below.
		  
		 Joker blackJoker = locateJoker("black");
		 moveCard(blackJoker, 2);
		 
		// 3) perform triple cut 
		 
		 Card cardPosition = head;
		 int i = 0;
		 
		 while(i < this.numOfCards) {
			 
			 if(cardPosition instanceof Joker ) {
				 if(cardPosition == redJoker) {
					 this.tripleCut(redJoker, blackJoker);
					 break;
				 }
				 else {
					 this.tripleCut(blackJoker,redJoker);
					 break;
				 }
			 }
			 cardPosition = cardPosition.next;
			 i++;
		 }
		 // 4) perform a count cut
		 
		 this.countCut();
		 
		 // 5) 
		 
		 Card nextCard = this.lookUpCard();
		 
		 if (nextCard == null) {
			 return generateNextKeystreamValue();
		 }
		 else {
			 int cardVal = nextCard.getValue();
			 return cardVal;
		 }
		
	}


	public abstract class Card { 
		public Card next;
		public Card prev;

		public abstract Card getCopy();
		public abstract int getValue();

	}

	public class PlayingCard extends Card {
		public String suit;
		public int rank;

		public PlayingCard(String s, int r) {
			this.suit = s.toLowerCase();
			this.rank = r;
		}

		public String toString() {
			String info = "";
			if (this.rank == 1) {
				//info += "Ace";
				info += "A";
			} else if (this.rank > 10) {
				String[] cards = {"Jack", "Queen", "King"};
				//info += cards[this.rank - 11];
				info += cards[this.rank - 11].charAt(0);
			} else {
				info += this.rank;
			}
			//info += " of " + this.suit;
			info = (info + this.suit.charAt(0)).toUpperCase();
			return info;
		}

		public PlayingCard getCopy() {
			return new PlayingCard(this.suit, this.rank);   
		}

		public int getValue() {
			int i;
			for (i = 0; i < suitsInOrder.length; i++) {
				if (this.suit.equals(suitsInOrder[i]))
					break;
			}

			return this.rank + 13*i;
		}

	}

	public class Joker extends Card{
		public String redOrBlack;

		public Joker(String c) {
			if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
				throw new IllegalArgumentException("Jokers can only be red or black"); 

			this.redOrBlack = c.toLowerCase();
		}

		public String toString() {
			//return this.redOrBlack + " Joker";
			return (this.redOrBlack.charAt(0) + "J").toUpperCase();
		}

		public Joker getCopy() {
			return new Joker(this.redOrBlack);
		}

		public int getValue() {
			return numOfCards - 1;
		}

		public String getColor() {
			return this.redOrBlack;
		}
	}

}
