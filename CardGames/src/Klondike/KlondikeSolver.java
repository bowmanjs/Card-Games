package Klondike;

import CardGame.*;
import java.util.Arrays;

public class KlondikeSolver {
    
    private SortPile[] sortPile;
    private Card deckCounter;
    private KlondikeGame kg;
    
    public KlondikeSolver()
    {
        kg = new KlondikeGame();
        sortPile = new SortPile[7];
        for(int i=0;i<7;i++)
            sortPile[i] = new SortPile();
    }
    
    public KlondikeSolver(KlondikeGame kg)
    {
        this.kg = kg;
        sortPile = new SortPile[7];
        for(int i=0;i<7;i++)
            sortPile[i] = new SortPile();
    }
    
    public void setGame(KlondikeGame kg)
    {
        this.kg = kg;
    }
    /*
     *      MAIN GAME LOOP
     */
    
    public boolean takeTurn()
    {
        kg.flipHidden();
//        kg.displayGame();
        
        if(moveNonEmptyColumn())
        {
            deckCounter = null;
            return true;
        }
        
        if(moveEmptyColumn())
        {
            deckCounter = null;
            return true;
        }
         
        if(moveTtoF())
        {
            deckCounter = null;
            return true;
        }
        
        if(moveWtoF())
        {
            deckCounter = null;
            return true;
        }
        
        if(moveWtoT())
        {
            deckCounter = null;
            return true;
        }
 
        if(flipDeck())
            return true;
        deckCounter = null;
        return false;
    }

    
    
    /*
     *  OTHER LOGIC
     */
    
    
    
   /*
     *  SORT VISIBLE PILES BY NUMBER OF HIDDEN CARDS UNDER THEM
     */
    
    private void sortPiles()
    {
        for(int i=0;i<7;i++)
        {
            sortPile[i].setPile(kg.getVisible(i));
            sortPile[i].setSize(kg.getHidden(i).size());
        }
        Arrays.sort(sortPile);
    }
    
    private boolean moveNonEmptyColumn()
    {
        sortPiles();
        for(int i=6;i>=0;i--)
        {
            if(sortPile[i].getSize()>0)
            {
               for(int j=0;j<7;j++)
                {
                    if(kg.movePile(sortPile[i].getPile(),sortPile[j].getPile()))
                        return true;
                }
            }
        }
        return false;
    }
    
    private boolean moveEmptyColumn()
    {
        sortPiles();
        if(checkKing())
        {
            for(int i=6;i>=0;i--)
            {
                if(sortPile[i].getSize()==0 && !sortPile[i].getPile().isEmpty())
                {
                    for(int j=0;j<7;j++)
                    {
                        if(sortPile[j].getSize()>0)
                        {
                            if(kg.movePile(sortPile[i].getPile(),sortPile[j].getPile()))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    private boolean moveTtoF()
    {
        sortPiles();
        for(int i=6;i>=0;i--)
        {
            if(!sortPile[i].getPile().isEmpty())
            {
                for(int j=0;j<4;j++)
                {
                    if(/*canMove(sortPile[i].getPile().peek()) && */kg.moveCard(sortPile[i].getPile().peek(),kg.getFoundation(j)))
                    {
                        sortPile[i].getPile().pop();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean moveWtoF()
    {
        if(!kg.getWaste().isEmpty())
        {
            for(int i=0;i<4;i++)
            {
                if(/*canMove(kg.getWaste().peek()) && */kg.moveCard(kg.getWaste().peek(),kg.getFoundation(i)))
                {
                            kg.getWaste().pop();
                            return true;
                } 
            }
        }
        return false;
    }
    
    private boolean moveWtoT()
    {
       if(!kg.getWaste().isEmpty())
        {
            for(int i=0;i<7;i++)
            {
                if(kg.moveCard(kg.getWaste().peek(),kg.getVisible(i)))
                {
                    kg.getWaste().pop();
                    return true;
                }
            }
        }
        return false; 
    }
    
    private boolean flipDeck()
    {
        if(!kg.flipDeck())
            return false;
        if(deckCounter != null && deckCounter == kg.getWaste().peek())
            return false;
        if(deckCounter == null)
            deckCounter = kg.getWaste().peek();
        return true;
    }
    
    private boolean checkKing()
    {
        for(int i=0;i<7;i++)
        {
            if(kg.getVisible(i).isEmpty()) continue;
            if(!kg.getHidden(i).isEmpty() && kg.getVisible(i).peekBottom().getRank()==Rank.KING)
                return true;
        }
        if(!kg.getWaste().isEmpty() && kg.getWaste().peek().getRank()==Rank.KING)
            return true;
        return false;
    }
    
    @SuppressWarnings("unused")
	private boolean canMove(Card card)
    {
        int x=0;
        int a,b;
        boolean colorMatch;
        a = card.getRank().ordinal();
        if(a == 12)
            a = 0;
        for(int i=0;i<4;i++)
        {
            if(kg.getFoundation(i).isEmpty())
            {
                b = 0;
                colorMatch = false;
            }
            else
            {
                b = kg.getFoundation(i).peek().getRank().ordinal();
                if(b == 12)
                    b = 0;
                colorMatch = kg.getFoundation(i).peek().getColor().equalsIgnoreCase(card.getColor());
            }
            if(!colorMatch && (a-b)<=2)
                x++;
        }      
        if(x>=2)
            return true;
        
        return false;
    }
    
}
