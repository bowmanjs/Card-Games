package Klondike;

import CardGame.*;

public class SortPile implements Comparable<Object>{
    
    private int size;
    private Pile pile;
    
    public SortPile()
    {
        pile = new Pile();
        size = 0;
    }
    
    public SortPile(Pile pile, int size)
    {
        this.pile = pile;
        this.size = size;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public void setSize(int s)
    {
        size = s;
    }
    
    public Pile getPile()
    {
        return pile;
    }
    
    public void setPile(Pile pile)
    {
        this.pile = pile;
    }
    
    @Override
    public int compareTo(Object O)
    {
        SortPile p = (SortPile)O;
        if(this.size<p.getSize())
            return -1;
        if(this.size>p.getSize())
            return 1;
        return 0;        
    }
    
}
