package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() 
	{
		CardNode prev = deckRear;
		for(CardNode place = deckRear.next; place != deckRear; place = place.next)
		{
			if(place.cardValue == 27)
			{
				CardNode a = place;
				CardNode b = place.next.next;
				prev.next = place.next;
				prev.next.next = a;
				a.next = b;
				return;
			}
			else if(place.next == deckRear && place.cardValue == 27)
			{
				CardNode a = place;
				CardNode head = deckRear.next;
				CardNode tail = deckRear;
				prev.next = place.next;
				tail.next = a;
				deckRear = a;
				a.next = head;
				return;
			}
			else if(place.next == deckRear && place.next.cardValue == 27)
			{
				CardNode a = deckRear;
				place.next = deckRear.next;
				deckRear = place.next;
				CardNode b = deckRear.next;
				deckRear.next = a;
				a.next = b;
				return;
			}
			prev = prev.next;
		}
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() 
	{
		CardNode prev = deckRear;
		for(CardNode place = deckRear.next; place != deckRear; place = place.next)
		{
			if(place.next == deckRear && place.cardValue == 28)
			{
				CardNode a = place;
				CardNode b = deckRear.next;
				prev.next = place.next;
				deckRear = a;
				deckRear.next = b;
				prev.next.next = deckRear.next;
				deckRear = prev.next.next;
				b = deckRear.next;
				deckRear.next = a;
				a.next = b;
				return;
			}
			if(place.next == deckRear && place.next.cardValue == 28)
			{
				CardNode a = deckRear;
				place.next = deckRear.next;
				deckRear = place.next;
				CardNode b = deckRear.next;
				a.next = b;
				CardNode c = b.next;
				b.next = a;
				a.next = c;
				return;
			}
			if ( place.next.next == deckRear && place.cardValue == 28)
			{
				CardNode tail = deckRear;
				CardNode head = deckRear.next;
				tail.next = place;
				deckRear = place;
				prev.next = place.next;
				place.next = head;
				return;
			}
			if (place.cardValue == 28)
			{
				CardNode a = place;
				CardNode b = place.next.next.next;
				prev.next = place.next;
				prev.next.next.next = a;
				a.next = b;
				return;
			}
			else
				prev = prev.next;
		}
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() 
	{
		CardNode prev = deckRear.next;
		CardNode place = deckRear.next.next;
		if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
		{
			for(place = deckRear.next.next; place != deckRear; place = place.next)
			{
				if(deckRear.cardValue == 27 || deckRear.cardValue == 28)
					return;
				else if(place.cardValue == 27 || place.cardValue == 28)
				{
					deckRear = place;
					deckRear.next = place.next;
					return;
				}
				else
					prev = prev.next;
			}
		}
		else if (deckRear.cardValue == 27 || deckRear.cardValue == 28)
		{
			prev = deckRear;
			place = deckRear.next;
			for(place = deckRear.next; place != deckRear; place = place.next)
			{
				if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28)
					return;
				else if(place.cardValue == 27 || place.cardValue == 28)
				{
					deckRear = prev;
					deckRear.next = place;
					return;
				}
				else
					prev = prev.next;
			}
		}
		else
		{
			prev = deckRear;
			place = deckRear.next;
			while(place != deckRear)
			{
				if(place.cardValue == 27 || place.cardValue == 28)
				{
					CardNode nextplace = place.next;
					while(nextplace != deckRear.next)
					{
						if(nextplace.cardValue == 27 || nextplace.cardValue == 28)
						{
							CardNode joker = nextplace;
							CardNode folplace = nextplace.next;
							CardNode head = deckRear.next;
							deckRear.next = place;
							joker.next = head;
							deckRear = prev;
							deckRear.next = folplace;
							return;
						}
						else
							nextplace = nextplace.next;
					}
				}
				else
				{
					prev = prev.next;
					place = place.next;
				}
			}
		}
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() 
	{	
		int count = 0;
		int endNum;
		if(deckRear.cardValue == 28)
			endNum = 27;
		else
			endNum = deckRear.cardValue;
		CardNode prev = deckRear;
		CardNode head = deckRear.next;
		CardNode place = deckRear.next;
		while (count <= endNum)
		{
			if(count == (endNum - 1))
			{
				if(place.next == deckRear)
					return;
				CardNode endPlace = place;
				CardNode mid = place.next;
				for(CardNode nextplace = place.next; nextplace != deckRear; nextplace = nextplace.next)
				{
					if(nextplace.next == deckRear)
					{
						CardNode midend = nextplace;
						midend.next = null;
						nextplace.next = head;
						endPlace.next = deckRear;
						deckRear.next = mid;
						return;
					}
				}
			}
			prev = prev.next;
			place = place.next;
			count++;
		}
	}
	
        /*
         * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
         * counts down based on the value of the first card and extracts the next card value 
         * as key, but if that value is 27 or 28, repeats the whole process.
         * 
         * @return Key between 1 and 26
         */
	int getKey() 
	{
		int key = -1;
		int count = 1;
		int firstcardValue = deckRear.next.cardValue;
		if(firstcardValue == 28)
			firstcardValue = 27;
		CardNode place = deckRear.next;
		while(place != deckRear)
		{
			if (count == firstcardValue)
			{
				if(place.next.cardValue == 27 || place.next.cardValue == 28)
				{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					place = deckRear;
					count = 0;
					firstcardValue = deckRear.next.cardValue;
					if(firstcardValue == 28)
						firstcardValue = 27;
				}
				else
				{
					key = place.next.cardValue;
					return key;
				}
			}
			place = place.next;
			count++;
		}
	    return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) 
	{	
		String msg = "";
		for(int i = 0 ; i < message.length(); i++)
		{
			if(!Character.isLetter(message.charAt(i)))
				continue;
			else
			{
				jokerA();
				jokerB();
				tripleCut();
				countCut();
				char ch = Character.toUpperCase(message.charAt(i));
				int c = ch - 'A'+1;
				int key = getKey();
				int code = c + key;
				if (code > 26)
					code -= 26;
				ch = (char)(code-1+'A');
				msg += ch;
			}
		}
	    return msg;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) 
	{	
		String msg = "";
		for(int i = 0 ; i < message.length(); i++)
		{
			if(!Character.isLetter(message.charAt(i)))
				continue;
			else
			{
				jokerA();
				jokerB();
				tripleCut();
				countCut();
				char ch = Character.toUpperCase(message.charAt(i));
				int c = ch - 'A'+1;
				int key = getKey();
				int code = c - key;
				if (code <= 0)
					code += 26;
				ch = (char)(code-1+'A');
				msg += ch;
			}
		}
		return msg;
	}
}
