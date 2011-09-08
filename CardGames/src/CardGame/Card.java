package CardGame;



public class Card implements Comparable<Object>{

    private Rank rank;
    private Suit suit;
    private String color;
    private boolean visible;

    public Card(Rank rank, Suit suit)
    {
        this.rank=rank;
        this.suit=suit;
        if(suit.equals(Suit.DIAMONDS)||suit.equals(Suit.HEARTS))
            color = "Red";
        else color = "Black";
        visible = false;
    }

    public Rank getRank()
    {
        return rank;
    }

    public Suit getSuit()
    {
        return suit;
    }

    public String getColor()
    {
        return color;
    }
    
    public boolean isVisible()
    {
        return visible;
    }

    @Override
    public int compareTo(Object o)
    {
        return this.rank.compareTo(((Card)o).getRank());
    }

    @Override
    public String toString()
    {
        String r="";
        String s="";
        switch(rank)
        {
            case TWO:   r="2"; break;
            case THREE: r="3"; break;
            case FOUR:  r="4"; break;
            case FIVE:  r="5"; break;
            case SIX:   r="6"; break;
            case SEVEN: r="7"; break;
            case EIGHT: r="8"; break;
            case NINE:  r="9"; break;
            case TEN:   r="T"; break;
            case JACK:  r="J"; break;
            case QUEEN: r="Q"; break;
            case KING:  r="K"; break;
            case ACE:   r="A"; break;
        }
        switch(suit)
        {
            case HEARTS:    s="H"; break;
            case CLUBS:     s="C"; break;
            case DIAMONDS:  s="D"; break;
            case SPADES:    s="S"; break;
        }
        return r+s;
    }

}

