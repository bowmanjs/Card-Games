package Poker;

import java.util.*;
import CardGame.*;


public class Player implements Comparable<Object>{

    private int balance;
    private String name;
    private PokerHand hand;
    private ArrayList<PokerHand> possibleHands;
    private boolean playing;

    Player()
    {
        balance = 0;
        name = "";
        hand = new PokerHand();
        playing = true;
    }

    Player(String name, int balance)
    {
        this.name = name;
        this.balance = balance;
        hand = new PokerHand();
        playing = true;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addBalance(int amount)
    {
        this.balance += amount;
    }

    public void play()
    {
        playing = true;
    }

    public void fold()
    {
        playing = false;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public PokerHand getHand()
    {
        return hand;
    }

    public int getBalance()
    {
        return balance;
    }

    public String getName()
    {
        return name;
    }

    public void newHand()
    {
        hand = new PokerHand();
    }

    public boolean bet(int amount)
    {
//        if(amount>balance)
//            return false;
        balance-=amount;
        return true;
    }

    public void bestHand(PokerHand community)
    {
        possibleHands = new ArrayList<PokerHand>();
        CombinationGenerator cg = new CombinationGenerator(7,5);
        ArrayList<Card> cards = new ArrayList<Card>();
        PokerHand pHand = new PokerHand();
        int[] combos = new int[5];
        for(Card card:hand.getCards())
            cards.add(card);
        for(Card card:community.getCards())
            cards.add(card);
        while(cg.hasMore())
        {
            combos = cg.getNext();
            for(int i=0;i<5;i++)
            {
                pHand.addCard(cards.get(combos[i]));
            }
            possibleHands.add(pHand);
            pHand = new PokerHand();
        }
        for(PokerHand x: possibleHands)
            x.Analyze();
        Collections.sort(possibleHands);
        hand = possibleHands.get(0);
    }



    @Override
    public String toString()
    {
        return "Name: "+name+"\nBalance: $"+balance;
    }

    @Override
    public int compareTo(Object o) {
        return this.getHand().compareTo(((Player)o).getHand());
    }

}
