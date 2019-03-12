
package whist;

import java.io.Serializable;
import java.util.*;
/**
 *
 * @author Kaloyan Valchev
 */
public class Card implements Serializable, Comparable<Card> {
    
    // -------------- Variables -----------
    private float cardSerialNum = 100;
    public Rank rank;
    public Suit suit;
    //Default constror
    public Card(){
        rank = null;
        suit = null;
    }
    //Constructor to populate card.
    public Card(Rank ranks, Suit suits){
        this.rank = ranks;
        this.suit = suits;
    }
    // ----------------------- Methods ------------------
    //Overwritten compareTo method 
    @Override
    public int compareTo(Card o) {
        int cardDifference = this.rank.getValue() - o.rank.getValue();
        if((cardDifference) == 0){
            return (this.suit.getValue() - o.suit.getValue());
        }
        else
        {
            return (this.rank.getValue() - o.rank.getValue());
        }
    }
    //Returns highest card from the arrayList
    public static Card max(ArrayList<Card> cards){
        Iterator<Card> iterator = cards.iterator();
        Card highestCard = null;
        while(iterator.hasNext()){
            highestCard = iterator.next();
            highestCard.compareTo(iterator.next());
        }
        return highestCard;
    }
    //Compares cards and returns AL in asscending order
    public static ArrayList chooseGreater(ArrayList<Card> cardList, Comparator comp, Card card){
        ArrayList<Card> greaterThan = new ArrayList();
        for(int i = 0; i < cardList.size(); i++){
            if(comp.compare(cardList.get(i), card) > 0){
                greaterThan.add(cardList.get(i));
            }
        }
        return greaterThan;
    }
    //Lamba test
    public static void selectTest(){
        CompareDescending compDesc = new CompareDescending();
        CompareRank compRank = new CompareRank();
        
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.FOUR, Suit.CLUBS);
        Card card3 = new Card(Rank.FIVE, Suit.CLUBS);
        Card card4 = new Card(Rank.SIX, Suit.CLUBS);
        Card card5 = new Card(Rank.TWO, Suit.DIAMONDS);
        Card card6 = new Card(Rank.THREE, Suit.DIAMONDS);
        Card card7 = new Card(Rank.FOUR, Suit.DIAMONDS);
        Card card8 = new Card(Rank.FIVE, Suit.HEARTS);
        Card card9 = new Card(Rank.KING, Suit.HEARTS);
        Card card10 = new Card(Rank.KING, Suit.SPADES);
        ArrayList<Card> cardsList = new ArrayList();
        ArrayList<Card> cardsToGo = new ArrayList();
        cardsToGo.add(card1);
        cardsToGo.add(card2);
        cardsToGo.add(card3);
        cardsToGo.add(card4);
        cardsToGo.add(card5);
        cardsToGo.add(card6);
        cardsToGo.add(card7);
        cardsToGo.add(card8);
        cardsToGo.add(card9);
        cardsToGo.add(card10);
        
        Comparator<Card> something = (Card a, Card b) -> a.rank.ordinal() - b.rank.ordinal();
        cardsList = chooseGreater(cardsToGo, something, card4);
        
        System.out.println(cardsList);
    }
    // ----------------------- Nested Classes --------------
    public static class CompareDescending implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            
        int cardDifference = o2.rank.getValue() - o1.rank.getValue();
        if((cardDifference) == 0){
            return (o1.suit.getValue() - o2.suit.getValue());
        }
        else
        {
            return (o2.rank.getValue() - o1.rank.getValue());
        }
        }
    }
    
    public static class CompareRank implements Comparator<Card>{

        @Override
        public int compare(Card o1, Card o2) {
            return o1.getRank().getValue() - o2.getRank().getValue();
        }
    }
// ------------------------- ENUMS ----------------------     
    protected enum Rank{
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);                  
        
        //Setting values to the eNums
        private int rankValue;
        private Rank(int value){
            rankValue = value;
        }
        
        //Array with values of eNums
        private static Rank[] vals = values();
        //Getting the next value of the eNum
        public Rank getNext(){
            if(vals[this.ordinal()] == ACE)
                return TWO;
            return vals[(this.ordinal()+1) % vals.length]; // Check if this is returning successfully TWO with ACE given.
        }
        
        public int getValue(){
            return rankValue;
        }
    }
// ---------------- Suit ----------------    
    protected enum Suit{
        CLUBS(1), DIAMONDS(2), HEARTS(3), SPADES(4);
        
        int suitValue;
        private Suit(int v){
            this.suitValue = v;
        }
        public int getValue(){
            return suitValue;
        }
        //Generating random suit
        public static Suit randomSuit(){
            int rand = new Random().nextInt(Suit.values().length);
            return Suit.values()[rand];
        }
    }
    // ------------------- End of Enum -------------------
    
    //Accessors
    public Suit getSuit(){
        return suit;
    }
    public Rank getRank(){
        return rank;
    }
    @Override
    public String toString(){
        return rank + " of " + suit;
    }
   //TEST MAIN
    public static void main(String[] args) {
        Card card = new Card(Rank.TEN, Suit.DIAMONDS);
        Card card3 = new Card(Rank.TEN, Suit.SPADES);
        Card card7 = new Card(Rank.TEN, Suit.CLUBS);
        Card card2 = new Card(Rank.FOUR, Suit.SPADES);
        Card card4 = new Card(Rank.TWO, Suit.CLUBS);
        Card card5 = new Card(Rank.SIX, Suit.HEARTS);
        Card card6 = new Card(Rank.THREE, Suit.CLUBS);
        Card card8 = new Card(Rank.ACE, Suit.HEARTS);
        
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        
//        System.out.println(cards + "\n");
//        System.out.println(card8.getRank().getNext());
        selectTest();
      
    }
    
}
