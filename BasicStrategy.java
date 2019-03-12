/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;
import java.util.*;
/**
 *
 * @author Kaloyan Valchev
 */
public class BasicStrategy implements Strategy{
    
    /* 
        You bastard, finish the Serializable in Deck!!!!
    */
    //Methods used in the class later on.
    private boolean isTrump(Card c, Trick t){
        if(c.suit == t.trumps){
            return true;
        }
        else 
            return false;
    }
    
    private boolean isHigher(Card a, Card b){
        return a.rank.getValue() > b.rank.getValue();
    }
    
    private boolean isLower(Card a, Card b){
        if(a.rank.ordinal() < b.rank.ordinal()){
            return true;
        }
        else
            return false;
    }
    
    //returns the card with the higher rank
    private Card higherRank(Card a, Card b){
        if(a.rank.ordinal() > b.rank.ordinal()){
            return a;
        }
        else
            return b;
    }
    //Check which card is the lowest in the hand
    private Card returnLow(Hand h, Card.Suit s){
        h.sort();
        Card low = low = h.getHand().get(0);
        //find lowest card from that suit
        for(int i = 0; i < h.getHand().size(); i++){
            if(h.getHand().get(i).suit == s){
                low = h.getHand().get(i);
                return low;
            }
            
        }
        //If its the trump, return next low card.
        if(low.suit == Trick.trumps){
            return h.getHand().get(1);
        }
        return low;
    }
    //returns the highest ordinal value from the hand
    private Card returnHigh(Hand h){
        Card highest = null;
        for(Card c: h){
            highest = c;
            if(c.rank.ordinal() > highest.rank.ordinal()){
                highest = c;
            }
        }
        return highest;
    }
    //returns lowest trump card
    private Card returnTrump(Hand h, Trick t){
        //AL to store all trumps
        ArrayList<Card> trumps = new ArrayList<>();
        for(Card trump : h){
            if(isTrump(trump,t)){
                trumps.add(trump);
            }
        }
        //returns the smallest trump.
        Collections.sort(trumps);
        return trumps.get(0);
    }
    //Create methods for isTrump, HighestRank, LowestRank etc...
    
    @Override
    public Card chooseCard(Hand h, Trick t) {
        int numTrumps = 0;
        //If the first Player to play, return highest card in hand.
        if(t.cards.isEmpty()){
            int highest = 0;
            Card highestCard = null;
            for(Card c : h){
                //If its higher rank than 10
                if(c.rank.getValue() > Card.Rank.TEN.getValue()){
                    highestCard = c;
                    //if c is higher than highestCard - swap
                    if(isHigher(c, highestCard)){
                        highestCard = c;
                    }
                    //if c == to highestCard
                    else if(c.rank.ordinal() == highestCard.rank.ordinal()){
                        //Create arraylist of all the highest cards
                        ArrayList<Card> highCards = new ArrayList();

                        for(Card cc : h){
                            //if the cards are same ordinal as highestCard
                            //add them to array                            
                            if(cc.rank.ordinal() == highestCard.rank.ordinal()){
                                highCards.add(cc);
                                highest++;
                            }
                        }
                        //returns one random highest card
                        Random rand = new Random();
                        int n = rand.nextInt(highest) + 1;
                        //if the card is trump suit, return the next card from the array
                        if(isTrump(highCards.get(n), t)){
                            //If the trump card is the first one, return next
                            //else return previous
                            if(highCards.get(n) == highCards.get(1)){
                                h.removeCard(highCards.get(n+1));
                                return highCards.get(n+1);
                            }
                            else{
                                h.removeCard(highCards.get(n-1));
                                return highCards.get(n-1);
                               
                            }
                        }
                        else{
                            h.removeCard(highCards.get(n));
                            return highCards.get(n);
                        }

                    }
                }
            }
        }
        //Player is not first to play
        else{
            for(Card c : h){
                //AL of all the same suits.
                ArrayList<Card> suits = new ArrayList<>();
                for(Card cs : h){
                    if(cs.suit == t.getLeadSuit()){
                        suits.add(cs);
                    }
                }
            //If can follow suit
                if(!suits.isEmpty()){
                //Player is 2nd to play
                Card highestCard = c;
                    if(t.cards.size() == 1){
                    //set the highestCard so far to the played one.
                        highestCard = t.cards.get(0);
                        //this card is higher than the one that has been played
                        if(isHigher(c, t.cards.get(0))){
                            h.removeCard(highestCard);
                            return highestCard;
                        }
                        else
                            if(c.suit != t.trumps){
                                Card cardToRemove = returnLow(h, t.getLeadSuit());
                                h.removeCard(cardToRemove);
                                return cardToRemove;
                            }
                    }
                //Player is 3rd to play
                    else if(t.cards.size() == 2){
                    //Player 1(partner) is winning, play low
                        if(t.players.get(0).getID() == t.findWinner()){
                            Card lowCard = c;
                        //if the current card is lower in rank than the lowest - swap
                            if(isLower(c,lowCard)){
                                lowCard = c;
                                h.removeCard(lowCard);
                                return lowCard;
                            }
                        }
                    //Enemy team is winning, play high.
                        else
                        {
                            //Get winning team's card
                            //current card > winning team card - play it
                            highestCard = t.cards.get(1);
                            if(isHigher(c, highestCard)){
                                h.removeCard(c);
                                return c;
                            }
                            //loop through deck, add trumps to AL
                            else
                            {   
                                if(c.suit != t.trumps){
                                    Card cardToRemove = returnLow(h, t.getLeadSuit());
                                    h.removeCard(cardToRemove);
                                    return cardToRemove;
                                }
                            }
                        }
                    }
                //Player is last to play
                    else if(t.cards.size() == 3){
                //check if partner is winning
                        
                    if(t.players.get(1).getID() == t.findWinner()){
                            return returnLow(h, t.getLeadSuit());   
                    }
                    else{
                        //Get both enemy cards
                        //Check if this card is higher than both
                            //play it
                        for(Card cc : h){    
                            Card enemyCard1 = t.cards.get(0);
                            Card enemyCard2 = t.cards.get(2);
//HERE I AM STILL AT FIRST CARD.
                            if(isHigher(cc,enemyCard1) && isHigher(cc,enemyCard2)){
                                h.removeCard(cc);
                                return cc;
                            }
                            else{
                                Card cardToRemove = returnLow(h, t.getLeadSuit());
                                h.removeCard(cardToRemove);
                                return cardToRemove;
                            }
                        }
                    }
                }
                    
        //It cannot follow suit. Trump
            }
            else{
                Card highestCard = null;
                //Check which player is it in turn
                //If partner is not winning, trump it!
                if(t.cards.size() == 1){
                    highestCard = t.cards.get(0);
                    Card trumpToReturn = returnTrump(h, t);
                    h.removeCard(trumpToReturn);
                    return trumpToReturn;
                }
                else if(t.cards.size() == 2){
                    //if partner is NOT winning
                    if(t.players.get(0).getID() != t.findWinner()){
                        Card trumpToReturn = returnTrump(h, t);
                        h.removeCard(trumpToReturn);
                        return trumpToReturn;
                    }
                    //Partner wins
                    else{
                        if(!isTrump(c,t)){
                            Card cardToReturn = returnLow(h, t.getLeadSuit());
                            h.removeCard(cardToReturn);
                            return cardToReturn;
                        }
                    }
                }
                else if(t.cards.size() == 3){
                    if(t.players.get(1).getID() != t.findWinner()){
                        Card trumpToReturn = returnTrump(h, t);
                        h.removeCard(trumpToReturn);
                        return trumpToReturn;
                    }
                    //Partner wins
                    else{
                        if(!isTrump(c,t)){
                            Card cardToReturn = returnLow(h, t.getLeadSuit());
                            h.removeCard(cardToReturn);
                            return cardToReturn;
                        }
                    }
                }
            }
        }
    }
        
    return returnLow(h, t.getLeadSuit());
}

    @Override
    public void updateData(Trick c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        Strategy s = new BasicStrategy();
        Hand h = new Hand();
        Trick.setTrumps(Card.Suit.CLUBS);
        Player p1 = new BasicPlayer(1);
        Player p2 = new BasicPlayer(2);
        Player p3 = new BasicPlayer(3);
        Player p4 = new BasicPlayer(4);
        
/* Testing for first player        
        h.addCard(new Card(Card.Rank.JACK,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.QUEEN,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.THREE,Card.Suit.DIAMONDS));
        h.addCard(new Card(Card.Rank.FOUR,Card.Suit.SPADES));
        h.addCard(new Card(Card.Rank.TEN,Card.Suit.SPADES));
        h.addCard(new Card(Card.Rank.ACE,Card.Suit.SPADES));
        h.addCard(new Card(Card.Rank.FOUR,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.KING,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.ACE,Card.Suit.DIAMONDS));
        h.addCard(new Card(Card.Rank.ACE,Card.Suit.CLUBS));
*/
        h.addCard(new Card(Card.Rank.SIX,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.FIVE,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.TEN,Card.Suit.CLUBS));
        h.addCard(new Card(Card.Rank.FOUR,Card.Suit.CLUBS));
        h.addCard(new Card(Card.Rank.THREE,Card.Suit.CLUBS));
        h.addCard(new Card(Card.Rank.JACK,Card.Suit.SPADES));
        h.addCard(new Card(Card.Rank.KING,Card.Suit.SPADES));
        h.addCard(new Card(Card.Rank.TEN,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.THREE,Card.Suit.HEARTS));
        h.addCard(new Card(Card.Rank.SEVEN,Card.Suit.DIAMONDS));
        h.addCard(new Card(Card.Rank.QUEEN,Card.Suit.DIAMONDS));
        
        Trick t = new Trick(3);
        t.setCard(new Card(Card.Rank.FIVE,Card.Suit.DIAMONDS), p1);
        t.setCard(new Card(Card.Rank.NINE,Card.Suit.DIAMONDS), p2);
        t.setCard(new Card(Card.Rank.SIX,Card.Suit.DIAMONDS), p3);
        System.out.println(t);
        System.out.println("Player who's winning so far is : " + t.findWinner());
        System.out.println("My partner's ID is : " + t.players.get(1).getID());
        System.out.println("Leading suit is : " +t.getLeadSuit());
        System.out.println("Card returned is : " +s.chooseCard(h, t));
    }
}
