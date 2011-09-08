package Klondike;

import CardGame.*;

import java.util.Stack;

public class KlondikeGame {
    
    private Pile[] foundationPile;
    private Pile[] tableauVisible;
    private Pile[] tableauHidden;
    private Pile waste;
    private Deck deck;
    
    public KlondikeGame()
    {
        foundationPile = new Pile[4];
        tableauVisible = new Pile[7];
        tableauHidden = new Pile[7];
        waste = new Pile();
        deck = new Deck();     
        initialize();
    }
    
    
    /*
     *   INITIALIZE PILES AND DECK
     */
    
    private void initialize()
    {
        for(int i=0;i<4;i++)
        {
            foundationPile[i]= new Pile(Rank.ACE);
            foundationPile[i].setRules(true, false, false, false, true,false, true);
        }      
        for(int i=0;i<7;i++)
        {
            tableauHidden[i] = new Pile();
            tableauVisible[i] = new Pile(Rank.KING);
            tableauVisible[i].setRules(false, true, false, false, false,false, true);
        }        
        deck.shuffle();
        deck.shuffle();
        dealGame();
    }
    
    
     /* 
     * CHECK IF GAME IS WON
     */
    
    public boolean won()
    {
        for(int i=0;i<4;i++)
        {
            if(foundationPile[i].isEmpty() || foundationPile[i].peek().getRank()!=Rank.KING)
                return false;      
        }
        return true;
    }
    
    
    /*
     *  DEAL OUT CARDS FOR GAME
     */
    
    public void dealGame()
    {
        for(int i=0; i<7;i++)
            for(int j=i;j<7;j++)
            {
                tableauHidden[j].push(deck.deal());
            }
    }
    
    
    /*
     *  FLIP UNCOVERED HIDDEN CARDS
     */
    
    public void flipHidden()
    {
        for(int i=0;i<7;i++)
        {
            if(tableauVisible[i].isEmpty() && !tableauHidden[i].isEmpty())
                tableauVisible[i].forcePush(tableauHidden[i].pop());
        }        
    }
    
    
    /*
     *  MOVE PILE1 ONTO PILE2
     */
    
    public boolean movePile(Pile pile1, Pile pile2)
    {
        if(!pile2.isAllowed(pile1.peekBottom()))
            return false;
        Stack<Card> temp = new Stack<Card>();
        while(!pile1.isEmpty())       
            temp.push(pile1.pop());
        while(!temp.isEmpty())
            pile2.push((Card)temp.pop());
        return true;
    }
    
    
    /*
     *  MOVE A CARD TO A PILE
     */
    
    public boolean moveCard(Card card, Pile pile)
    {
        if(!pile.isAllowed(card))
            return false;
        pile.push(card);
        return true;
    }     
    
    
    /*
     *  FLIP NEXT CARD IN DECK
     */
    
    public boolean flipDeck()
    {
        if(deck.isEmpty())
        {
            if(waste.isEmpty())
                return false;
            resetDeck();
        }
        waste.push(deck.deal());        
        return true;
    }
    
    
    /*
     *  PUT WASTE BACK INTO DECK
     */
    
    private void resetDeck()
    {
        Stack<Card> temp = new Stack<Card>();
        while(!waste.isEmpty())
            temp.push(waste.pop());            
        while(!temp.isEmpty())
            deck.addCard((Card)temp.pop());
    }

    
    /*
     *  DISPLAY GAME AS TEXT
     */  
    
    public void displayGame()
    {   
        System.out.println("\n-------------------------------------------\n");
        for(int i=3;i>=0;i--)
        {
            if(foundationPile[i].isEmpty())
                System.out.println("[ ]");
            else System.out.println(foundationPile[i].peek());
        }
        System.out.println();
        for(int i=6;i>=0;i--)
        {
           for(int j=0;j<tableauHidden[i].size();j++)
                System.out.print("X");
            System.out.println(tableauVisible[i]);
        }
        System.out.println();
        if(waste.isEmpty())
            System.out.println("[ ]");
        else System.out.println(waste.peek());
        if(deck.isEmpty())
            System.out.println("[O]");
        else System.out.println("[X]");
    }
    
    public Pile getWaste()
    {
        return waste;
    }
    
    public Pile getFoundation(int i)
    {
        return foundationPile[i];
    }
    
    public Pile getVisible(int i)
    {
        return tableauVisible[i];
    }
    
    public Pile getHidden(int i)
    {
        return tableauHidden[i];
    }
    
}
