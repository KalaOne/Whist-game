package whist;
import java.util.*;
import java.io.*;
import java.lang.reflect.Array;
import whist.Card.*;

public class Trick{
   public static Suit trumps;
   ArrayList<Player> players = new ArrayList<>();
   ArrayList<Card> cards = new ArrayList<>();
   int leadPlayerID;
   
   public Trick(int p){
       leadPlayerID = p;
   }    //p is the lead player 
   
   public static void setTrumps(Suit s){
       trumps = s;
   }
    
/**
 * 
 * @return the Suit of the lead card.
 */    
    public Suit getLeadSuit(){
        return cards.get(0).getSuit();
    }
/**
 * Records the Card c played by Player p for this trick
 * @param c
 * @param p 
 */
    public void setCard(Card c, Player p){
        cards.add(c);
        players.add(p);
    }
/**
 * Returns the card played by player with id p for this trick
 * @param p
 * @return 
 */    
    public Card getCard(Player p){
        return p.playCard(this);
    }
    
/**
 * Finds the ID of the winner of a completed trick
 */    
    public int findWinner(){
        Card highestCard = cards.get(0);
        int playerID = 0;
        Suit leadingSuit = getLeadSuit();
        for(int player = 1; player < players.size(); player++){
            //If card is neither the lead suit or trump, ignore player ID.
            if(!cards.get(player).suit.equals(leadingSuit)){
                if(cards.get(player).suit != trumps){
                    continue;
                }
                //Card is trump
                else{
                    highestCard = cards.get(player);
                    playerID = players.get(player).getID();
                    if(cards.get(player).rank.compareTo(highestCard.rank) > 0){
                        highestCard = cards.get(player);
                        playerID = players.get(player).getID();
                    }
                }
            }
            //Same suit, compare ranks
            else{
                if(cards.get(player - 1).rank.compareTo(highestCard.rank) > 0){
                    highestCard = cards.get(player - 1);
                    playerID = players.get(player - 1).getID();
                }
                else{
                    playerID = players.get(player).getID();
                }
            }
        }
        return playerID;
    }
    
    @Override
    public String toString(){
        String toReturn = "";
        for(int i = 0; i < players.size(); i++ ){
            toReturn += "Player " + players.get(i).getID() + " has played " + this.cards.get(i) + ". ";
        }
        return toReturn;
    }
    public static void main(String[] args) {
        BasicPlayer[] players = new BasicPlayer[4];
        BasicPlayer p = new BasicPlayer(1);
        BasicPlayer p2 = new BasicPlayer(2);
        BasicPlayer p3 = new BasicPlayer(3);
        BasicPlayer p4 = new BasicPlayer(4);
        Trick t = new Trick(p.getID());
        players[0] = p;
        players[1] = p2;
        players[2] = p3;
        players[3] = p4;
        
        p.dealCard( new Card(Card.Rank.TWO, Suit.SPADES));
        p2.dealCard(new Card(Card.Rank.FIVE, Suit.SPADES));
        p3.dealCard(new Card(Card.Rank.NINE, Suit.SPADES));
        p4.dealCard(new Card(Card.Rank.SIX, Suit.SPADES));
        setTrumps(Card.Suit.SPADES);
        System.out.println("Trump suit is " + trumps);
        
        System.out.println("P has "  + p.hand);
        System.out.println("P2 has " +p2.hand);
        System.out.println("P3 has " +p3.hand);
        System.out.println("P4 has " +p4.hand);
        
        t.setCard(p.playCard(t),  p);
        t.setCard(p2.playCard(t), p2);
        t.setCard(p3.playCard(t), p3);
        t.setCard(p4.playCard(t), p4);
        // Player 0 is winning... WRONG should be 1-4.... Check logic
        
        System.out.println(t);
        System.out.println("Winning player is Player" + t.findWinner());
        
    }
}
