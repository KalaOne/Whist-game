/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * Block for codes be4 classes
 * Method description comments
 */
public class Deck implements Iterable<Card>, Serializable{
    private float deckSerialNum = 49;
    public ArrayList<Card> cardsDeck = new ArrayList<>(52); //Fixed size arrayList
    
    // ------------------------ Constructor --------------
    //recreate new deck
    public Deck(){ 
        newDeck();
    }
    
    // ------------------------ Methods --------------------
    //returns whats left of the size of deck
    public int size(){
        return cardsDeck.size();
    }
    
    //Loops through suits and ranks and adds a card with corresponding index
    public final void newDeck(){
        
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cardsDeck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cardsDeck);
    }
    //Method to deal cards, starting from position 0.
    public Card deal(){
        Iterator<Card> it = new DeckIterator();
        Card removedCard = null;
        if(it.hasNext()){
            removedCard = it.next();
            it.remove();
        }
        return removedCard;
    }

    //Serializable attempt
    public void serialize(String filepath){
        ArrayList<Card> spades = new ArrayList<>();
        Iterator spadesIt = new SpadeIterator();
        //Creates a deck full of spades only
        for(Card c : cardsDeck){
            if(c.suit == Card.Suit.SPADES){
                spades.add(c);
            }
        }
        //Serializes this deck.
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(spades);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + filepath);
        } catch (Exception i) {
        i.printStackTrace();
      }
    }
    public void deserialize(String filepath){
        Deck d = null;
        try {
           FileInputStream fileIn = new FileInputStream(filepath);
           ObjectInputStream in = new ObjectInputStream(fileIn);
           d = (Deck) in.readObject();
           in.close();
           fileIn.close();
        } catch (Exception i) {
           i.printStackTrace();
           return;
        }
        
        assignVars(d);
    }
    //Assigning d to 'this' so it can be accessed in above method
    private void assignVars(Deck d){
        this.deckSerialNum = d.deckSerialNum;
        this.cardsDeck = d.cardsDeck;
    }
    
    @Override
    public DeckIterator iterator() {
        return new DeckIterator();
    }
    
    public SpadeIterator sIterator(){
        return new SpadeIterator();
    }
    
    /***************************Nested class Iterator **********************/
    private class DeckIterator implements Iterator<Card>{
        int currentPosition = 0;
        @Override
        public boolean hasNext(){
            return currentPosition < cardsDeck.size() && !cardsDeck.isEmpty();
        }
        @Override
        public Card next(){
            return cardsDeck.get(currentPosition++);
        }
        @Override
        public void remove(){
            cardsDeck.remove(currentPosition - 1);
        }
    }
    /*Iterate through the deck, but store only cards of SPADES */
    private class SpadeIterator implements Iterator<Card>, Serializable{
        int currentPos = 0;
        ArrayList<Card> spadesDeck = new ArrayList<>();
        //Traverse through deck and checks if suit is Spades
        //Adds create a deck full of SPADES only
        public SpadeIterator(){
        //While there is a next card, get the card that is SPADES
        for(Card c : cardsDeck){
            if(c.suit == Card.Suit.SPADES){
                spadesDeck.add(c);
            }
            }
        }
                
        @Override
        public boolean hasNext() {
            return currentPos < spadesDeck.size();
        }

        @Override
        public Card next() {
            return spadesDeck.get(currentPos++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
    
    //-------------------- Test Main ----------
    public static void main(String[] args) {
        Deck deck = new Deck();
        DeckIterator deckIt = deck.iterator();
        SpadeIterator spadeIt = deck.sIterator();
//        while(deckIt.hasNext()){
//              System.out.println(deckIt.next());
//        }
//        
//        System.out.println(deck.size());
        
        System.out.println("Deck of spades \n");
        while(spadeIt.hasNext()){
            System.out.println(spadeIt.next());
        }
//        
//        while(deckIt.hasNext()){
//            deck.deal();
//        }
        System.out.println(spadeIt.spadesDeck.size());
        System.out.println(deck.size());
    }
}
