/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Kaloyan Valchev
 */
public class Hand implements Iterable<Card>, Serializable {
    private float handSerialNum = 300;
    private int numDiamonds = 0;
    private int numHearts = 0;
    private int numClubs = 0;
    private int numSpades = 0;
    public int handTotalValue = 0;
    private ArrayList<Card> hand = new ArrayList<>();
    //For part 7. The order cards were added to the hand.
    private ArrayList<Card> originalHand = new ArrayList<>();
    
    
    /*************************** Constructors ********************/
    //default constructor
    public Hand(){
        hand = new ArrayList<>();
    }
    //taking AL of cards and adding them to hand
    public Hand(ArrayList<Card> cards){
        int numOfCards = 0;
        for(Card c : cards){
            hand.add(c);
            numOfCards++;
        }
        System.out.println(numOfCards + " have been added to the hand.");
    }
    //taking hand and adds all cards to this hand
    public Hand(Hand otherHand){
        for(Card c : otherHand){
            hand.add(c);
        }
    }
    /*************************** Methods *************************************/
    public ArrayList<Card> getHand(){
        return hand;
    }
    //Calculates numOfSuits in the hand
    public String calcSuits(){
        for(Card c : hand){
            if(null != c.suit)
                switch (c.suit) {
                    case CLUBS:
                        numClubs++;
                        break;
                    case HEARTS:
                        numHearts++;
                        break;
                    case DIAMONDS:
                        numDiamonds++;
                        break;
                    case SPADES:
                        numSpades++;
                        break;
                    default:
                        break;
                }
        }
        return "There are " + numClubs + " clubs, " + numHearts + " hearts, " + numDiamonds + " diamonds, " + numSpades + " spades.";
    }
    //Total value of hand
    public void calcTotalVal(){
        int numAces = 0;
        //Get number of Aces
        for(Card c : hand){
            if(c.getRank() == Card.Rank.ACE){
                numAces++;
            }
        }
        //Array to store total values. Ace + 1 because if there are no ACEs at all
        // it displays just one result.
        int value[] = new int[numAces + 1];
        //Calculate total value of cards with ACE counting as 11
        for(Card c : hand){
           handTotalValue += c.getRank().getValue();
        }
        int x = 0;
        //Display all possible total values dependng on aces. Remove 10 for x many ACEs.
        for(int i = value.length - 1; i >= 0; i--){
            value[i] = handTotalValue - (10 * x);
            x++;
        }
        System.out.println(Arrays.toString(value));
    }
    /****************** Sorting ********************/
    public void sort(){
       Collections.sort(hand);
    }
    
    public void sortByRank(){
        Collections.sort(hand, new Card.CompareRank());
    }
    
    //returns how many suits are in the hand
    public int countSuit(Card.Suit suit){
        if(null != suit)
            switch (suit) {
                case CLUBS:
                    return numClubs;
                case HEARTS:
                    return numHearts;
                case DIAMONDS:
                    return numDiamonds;
                case SPADES:
                    return numSpades;
                default:
                    break;
            }
        return 0;
    }
    //counts how many ranks of given rank are there
    public int countRank(Card.Rank rank){
        int numRanks = 0;
        for(Card c : hand){
            if(c.getRank() == rank){
                numRanks++;
            }
        }
        return numRanks;
    }
    //returns if the suit given is present
    public boolean hasSuit(Card.Suit suit){
        for(Card c : hand){
            if(c.getSuit() == suit)
                return true;
        }
        return false;
    }
    
    
    /*********** Add methods *****************/
    //Updating suits values when cards removed
    private void updateRemoveSuit(Card c){
        switch (c.getSuit()) {
                case CLUBS:
                    numClubs--;
                    if(numClubs < 0)
                        numClubs = 0;
                    break;
                case HEARTS:
                    numHearts--;
                    if(numHearts < 0)
                        numHearts = 0;
                    break;
                case DIAMONDS:
                    numDiamonds--;
                    if(numDiamonds < 0)
                        numDiamonds = 0;
                    break;
                case SPADES:
                    numSpades--;
                    if(numSpades < 0)
                        numSpades = 0;
                    break;
                default:
                    break;
                }
    }
    //Updating suits values when cards added
    private void updateAddSuit(Card c){
        switch (c.suit) {
            case CLUBS:
                numClubs++;
                break;
            case HEARTS:
                numHearts++;
                break;
            case DIAMONDS:
                numDiamonds++;
                break;
            case SPADES:
                numSpades++;
                break;
            default:
                break;
        }
    }
    //Adding card to hand
    public void addCard(Card c){
        hand.add(c);
        originalHand.add(c);
        //Check if what suit is the card. Adds 1 to that suit.
        updateAddSuit(c);
    }
    //Adding card from deck to the hand
    public void addDeck(Deck d){
        for(Card c: d){
            hand.add(c);
            originalHand.add(c);
            updateAddSuit(c);
        }
    }
    //adding another hand to this hand.
    public void addHand(Hand h){
        for(Card c : h.hand){
            this.hand.add(c);
            this.originalHand.add(c);
            updateAddSuit(c);
        }
    }
    /********************** Remove methods *********************/
    //Removes a single card
    public boolean removeCard(Card c){
        if(c != null){
            hand.remove(c);
            originalHand.remove(c);
            updateRemoveSuit(c);
            return true;
        }
        return false;
    }
    //Removes all cards from the hand
    public boolean removeHand(Hand h){
        if(h != null){
            for(Card c: h){
                h.removeCard(c);
                originalHand.remove(c);
                updateRemoveSuit(c);
            }
            return true;
        }
        return false;
    }
    //Removes a card at given position
    public Card removeCardPosition(int pos){
        //removes card from the manipulated hand
        Card card = hand.get(pos);
        hand.remove(pos);
        //Removes card from the original hand
        originalHand.remove(card);
        updateRemoveSuit(card);
        
        return card;
    }
    
    @Override
    public String toString(){
        String string = "";
        for(int i = 0; i<hand.size(); i++){
            string += "[" + hand.get(i).rank + "]" + " of " + hand.get(i).suit +"\n";
        }
        return string;
    }
    
    @Override
    public Iterator iterator() {
        return originalHand.iterator();
    }
    
        
    public static void main(String[] args) {
        
        Hand hand = new Hand();
        Hand hand2 = new Hand();
        
        Card card1 = new Card(Card.Rank.THREE, Card.Suit.CLUBS);
        Card card2 = new Card(Card.Rank.FOUR, Card.Suit.CLUBS);
        Card card3 = new Card(Card.Rank.FIVE, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.SIX, Card.Suit.CLUBS);
        Card card5 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
        Card card6 = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
        Card card7 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card8 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);
        Card card9 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card card10 = new Card(Card.Rank.TEN, Card.Suit.DIAMONDS);
        Card card11 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card12 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card13 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        
        hand.hand.add(card4);
        hand.hand.add(card1);
        hand.hand.add(card12);
        hand.hand.add(card13);
        hand.addCard(card5);
        
        hand2.hand.add(card1);
        hand2.hand.add(card2);
        hand2.hand.add(card3);
        hand2.hand.add(card4);
        hand2.hand.add(card5);
        hand2.hand.add(card6);
        hand2.hand.add(card7);
        hand2.hand.add(card8);
        hand2.hand.add(card9);
        
        hand.removeCard(card10);
        //hand.addHand(hand2);        
//        hand.removeCardPosition(2);
        System.out.println(hand);
        hand.calcTotalVal();
        hand.sort();
        System.out.println(hand.calcSuits());
        System.out.println(hand);
        System.out.println(hand.countRank(Card.Rank.ACE));
        System.out.println(hand.hasSuit(Card.Suit.CLUBS));
    }
}
