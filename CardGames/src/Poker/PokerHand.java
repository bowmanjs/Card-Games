package Poker;

import java.util.*;
import CardGame.*;

public class PokerHand implements Hand,Comparable<Object>{

    private ArrayList<Card> cards;
    private boolean straight;
    private boolean flush;
    private boolean four;
    private boolean three;
    private int pairs;
    private Hands hand;
    private Rank main;
    private Rank secondary;


    PokerHand()
    {
        cards = new ArrayList<Card>(7);
        straight = false;
        flush = false;
        four = false;
        three = false;
        pairs = 0;
        hand = Hands.HIGHCARD;
    }

    PokerHand(ArrayList<Card> cards)
    {
        this.cards = cards;
    }

    private void remove(Rank rank)
    {
        ArrayList<Card> x = new ArrayList<Card>();
        for(Card card:cards)
            if(card.getRank().equals(rank))
                x.add(card);
        for(Card card:x)
            cards.remove(card);
    }

    @Override
    public void addCard(Card c) {
        cards.add(c);
    }

    @Override
    public void playCard(Card c) {
        cards.remove(c);
    }

    @Override
    public void sort() {
        Collections.sort(cards);
    }

    @SuppressWarnings("unchecked")
	@Override
    public int compareTo(Object o) {
        if(((PokerHand)o).hand.equals(hand))
        {
            if(((PokerHand)o).main.equals(main))
            {
                if(hand == Hands.FULLHOUSE)
                    return ((PokerHand)o).secondary.compareTo(secondary);
                else
                {
                    PokerHand a = new PokerHand((ArrayList<Card>)((PokerHand)o).cards.clone());
                    PokerHand b = new PokerHand((ArrayList<Card>)cards.clone());
                    a.remove(main);
                    b.remove(main);
                    if(hand == Hands.TWOPAIR)
                    {
                        a.remove(secondary);
                        b.remove(secondary);
                    }
                    else
                    {
                        for(int i=a.cards.size()-1;i>0;i--)
                            if(!a.cards.get(i).getRank().equals(b.cards.get(i).getRank()))
                                return a.cards.get(i).compareTo(b.cards.get(i));
                    }
                    return a.cards.get(0).compareTo(b.cards.get(0));
                }
            }
            return ((PokerHand)o).main.compareTo(main);
        }
        return ((PokerHand)o).hand.compareTo(hand);
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    @Override
    public String toString()
    {
        return cards.toString();
    }

    public String printHand()
    {
        return hand.toString()+ " "+main.toString();
    }
    
    public void Analyze()
    {
        sort();
        
        int numSuit[] = {0,0,0,0};
        int numRank[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        for(Card card:cards)
        {
            numSuit[card.getSuit().ordinal()]++;
            numRank[card.getRank().ordinal()]++;
            if(numRank[card.getRank().ordinal()]==2)
                main = card.getRank();
        }
        
        /*
         * check flush
         */
        for(int i=0;i<4;i++)
        {
            if(numSuit[i]==5)
                flush = true;
        }
        
        
        /*
         * check straight
         */
        int rank = cards.get(0).getRank().ordinal();
        int numConsec=0;
        for(;rank<13 && numRank[rank]==1;rank++)
            numConsec++;
        if(numConsec==5)
            straight = true;
                
        /*
         * check for multiples
         */
        for(int i=0;i<13;i++)
        {
            if(numRank[i]==4) four = true;            
            if(numRank[i]==3) three = true;
            if(numRank[i]==2) pairs++;
        }

        /*
         * set hand type
         */
        if(straight && flush) hand = Hands.STRAIGHTFLUSH;
        else if(four) hand = Hands.FOUROFAKIND;
        else if(three && pairs==1) hand = Hands.FULLHOUSE;
        else if(flush) hand = Hands.FLUSH;
        else if(straight) hand = Hands.STRAIGHT;
        else if(three) hand = Hands.THREEOFAKIND;
        else if(pairs==2) hand = Hands.TWOPAIR;
        else if(pairs==1) hand = Hands.PAIR;
        
        switch(hand)
        {
            case FOUROFAKIND:
            case THREEOFAKIND:
            case FULLHOUSE:
                main = cards.get(2).getRank();
                if(cards.get(2).getRank().equals(cards.get(0).getRank()))
                    secondary = cards.get(4).getRank();
                else secondary = cards.get(0).getRank();
                break;
            case TWOPAIR:
                main = cards.get(3).getRank();
                secondary = cards.get(1).getRank();
                break;
            case PAIR:
                break;
            default:
                main = cards.get(4).getRank();
                break;
        }
        
    }
}
