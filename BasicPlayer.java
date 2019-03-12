/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whist;

import java.util.ArrayList;

/**
 *
 * @author Kaloyan Valchev
 */
public class BasicPlayer implements Player{
    Hand hand = new Hand();
    Strategy strat = new BasicStrategy();
    int playerID;
    
    public BasicPlayer(int p){
        playerID = p;
    }
    
    @Override
    public void dealCard(Card c) {
        hand.addCard(c);
    }

    @Override
    public void setStrategy(Strategy s) {
        strat = s;
    }

    @Override
    public Card playCard(Trick t) {
        return strat.chooseCard(hand, t);
    }

    @Override
    public void viewTrick(Trick t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//I found player assigning trumps useless, since the trick has the method as well.
    @Override
    public void setTrumps(Card.Suit s) {
        Trick.trumps = s;
    }

    @Override
    public int getID() {
        return this.playerID;
    }
    
}
