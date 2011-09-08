package CardGame;



import java.util.Stack;
import java.util.EmptyStackException;

public class Pile extends Stack<Object>{
    

	private boolean rules[];
    private Rank startingRank;
    
    /*Rules array:
     * 0 - Descending Rank Allowed
     * 1 - Ascending Rank Allowed
     * 2 - Same Rank Allowed
     * 3 - Same Color Allowed
     * 4 - Same Suit Required
     * 5 - Any card allowed
     * 6 - Ace lower than 2
     */
    
    
    public Pile()
    {
        rules = new boolean[7];
        setDefaultRules();
        startingRank = null;
    }
    
    public Pile(Rank rank)
    {
        rules = new boolean[7];
        setDefaultRules();
        startingRank = rank;
    }
    
    public boolean push(Card card)
    {
        if(!isAllowed(card))
            return false;
        this.addElement(card);
        return true;
    }
    
    public void forcePush(Card card)
    {
        this.addElement(card);
    }
    
    public Card peekBottom()
    {
        return (Card)this.firstElement();
    }
    
    @Override
    public Card peek()
    {
        int len = size();
        if (len == 0)
            throw new EmptyStackException();
        return (Card)elementAt(len - 1);
    }
    
    @Override
    public Card pop()
    {
        Card card;
        int len = size();
        card = peek();
        removeElementAt(len - 1);
        return card;
    }
    
    public void setStartingRank(Rank rank)
    {
        startingRank = rank;
    } 
    
    public void setRules(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f,boolean g)
    {
        rules[0] = a;
        rules[1] = b;
        rules[2] = c;
        rules[3] = d;
        rules[4] = e;
        rules[5] = f;
        rules[6] = g;
    }
   
    private void setDefaultRules()
    {
        rules[0] = true;
        rules[1] = true;
        rules[2] = true;
        rules[3] = true;
        rules[4] = false;
        rules[5] = true;
        rules[6] = true;
    }
    
    public boolean isAllowed(Card card)
    {
        int a,b,x;
        
        // if any card allowed        
        if(rules[5])
            return true;
        
        //if pile is empty check if is valid starting card
        if(this.isEmpty())
        {
            if(startingRank == null)
                return true;
            if(card.getRank() == startingRank)
                return true;
            return false;
        }
        
        //check each rule       
        Card top = (Card)this.peek();

        // Check color and suit requirements
        if(rules[4])
        {
            if(top.getSuit() != card.getSuit())
                return false;
        }
        
        else if(!rules[3])
        {
            if(top.getColor().equals(card.getColor()))
                return false;
        }
        
        //Check rank requirements
        
        if(rules[6] && top.getRank()==Rank.ACE)
            a = -1;
        else a = top.getRank().ordinal();
        
        if(rules[6] && card.getRank()==Rank.ACE)
            b = -1;
        else b = card.getRank().ordinal();
                
        x = a - b;
        
        if(x==0 && rules[2])
            return true;
        
        if(x==1 && rules[1])
            return true;
            
        if(x==-1 && rules[0])
            return true;
        
        return false;
    }
    
    
}
