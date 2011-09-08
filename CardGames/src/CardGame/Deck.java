package CardGame;



import java.util.*;


public class Deck{

    private ArrayList<Card> cards;

    public Deck()
    {
        cards = new ArrayList<Card>(52);
        for(Suit suit : Suit.values())
            for(Rank rank : Rank.values())
            {
                cards.add(new Card(rank,suit));
            }
    }

    public Card deal()
    {
        return cards.remove(0);
    }

    public ArrayList<Card> deal(int n)
    {
        ArrayList<Card> temp = (ArrayList<Card>)cards.subList(0, n-1);
        cards.removeAll(temp);
        return temp;
    }

    public void shuffle()
    {
        Collections.shuffle(cards);
    }
    
    public boolean isEmpty()
    {
        return cards.isEmpty();
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }
    
    public int size()
    {
        return cards.size();
    }
}
