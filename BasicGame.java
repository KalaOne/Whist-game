package whist;

import whist.Card.Suit;

/**
 *
 * @author ajb
 */
public class BasicGame {
    static final int NOS_PLAYERS=4;
    static final int NOS_TRICKS=13;
    static final int WINNING_POINTS=7;
    int team1Points=0;
    int team2Points=0;
    Player[] players;
    public BasicGame(Player[] pl){
        players = pl;
    }
    public void dealHands(Deck newDeck){
        for(int i=0;i<52;i++){
            System.out.println("dealing hand at " + i );
            players[i%NOS_PLAYERS].dealCard(newDeck.deal());
        }
    }
    public Trick playTrick(Player firstPlayer){
        Trick t=new Trick(firstPlayer.getID());
        int playerID=firstPlayer.getID();
        for(int i=0;i<NOS_PLAYERS;i++){
            int next=(playerID+i)%NOS_PLAYERS;
            t.setCard(players[next].playCard(t),players[next]);
        }
        return t;
    }
    public void playGame(){
        Deck d=new Deck();
        System.out.println(d);
        dealHands(d);
        int firstPlayer=(int)(NOS_PLAYERS*Math.random());
        Suit trumps=Suit.randomSuit();
        Trick.setTrumps(trumps);
        for(int i=0;i<NOS_PLAYERS;i++)
            players[i].setTrumps(trumps);
        
        for(int i=0;i<NOS_TRICKS;i++){
            Trick t=playTrick(players[firstPlayer]);            
            System.out.println("Trick ="+t);
            firstPlayer=t.findWinner();
            System.out.println("Winner ="+firstPlayer);
            
            
        }
    }
/**
 * Method to find the winner of a trick. Note 
 * @param t: current trick
 * @return the index of the winning player
 */    
    public void playMatch(){
        team1Points=0;
        team2Points=0;
        while(team1Points<WINNING_POINTS && team2Points<WINNING_POINTS){
            playGame();
        }
        if(team1Points>=WINNING_POINTS)
            System.out.println("Winning team is team1 1 with"+team1Points);
        else
            System.out.println("Winning team is team2 1 with"+team2Points);
            
    }
    public static void playTestGame(){
        Player[] p = new Player[NOS_PLAYERS];
        for(int i=0;i<p.length;i++){
            p[i]=new BasicPlayer(i);//CREATE YOUR PLAYERS HERE
            System.out.println(p[i] + " has been created.");
        }
        BasicGame bg=new BasicGame(p);
        bg.playMatch(); //Just plays a single match
        
}
    public static void main(String[] args) {
        
        playTestGame();
    }
    
}
