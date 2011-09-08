package Poker;

import java.util.*;

import CardGame.*;

public class PokerGame{

    ArrayList<Player> players;
    Deck deck;
    PokerHand community;
    int dealer;
    Scanner in;
    int pot;
    int betsize;

    PokerGame()
    {
        in = new Scanner(System.in);
        players = new ArrayList<Player>();
        deck = new Deck();
        community = new PokerHand();
        dealer = 0;
        pot = 0;
        betsize = 5;
    }

    void inputPlayers()
    {

        int numPlayers,balance;
        String name;

        System.out.print("Number of players: ");
        numPlayers = in.nextInt();
        System.out.print("Starting amount: ");
        balance = in.nextInt();
        in.nextLine();
        for(int i=0;i<numPlayers;i++)
        {
            System.out.print("Enter player name: ");
            name = in.nextLine();
            players.add(new Player(name,balance));
        }

    }

    void playRound()
    {
        
        community = new PokerHand();
        deck = new Deck();
        deck.shuffle();
        pot+=betting();
//        int total = pot;
        dealHands();
        System.out.println();
        for(Player player:players)
        {
            System.out.println(player.getName()+": "+player.getHand()+" Balance: "+player.getBalance());
//            total+=player.getBalance();
        }
//        System.out.println("Total: "+total);    
        dealFlop();
        System.out.println(community);
        deal();
        System.out.println(community);
        deal();
        System.out.println(community);
        determineWinner();
        checkBroke();
        changeDealer();
    }

    int betting()
    {
        int p=0;
        for(Player player: players)
        {
            player.bet(betsize);
            p+=betsize;
        }
        return p;
    }

    void checkBroke()
    {
        ArrayList<Player> x = new ArrayList<Player>();
        for(Player player: players)
            if(player.getBalance()<betsize)
                x.add(player);
        for(Player player: x)
            players.remove(player);
    }

    void changeDealer()
    {
        dealer++;
        if(dealer >= players.size())
            dealer=0;
    }

    void dealHands()
    {
        int numCards = 2;
        for(Player player:players)
            player.newHand();
        for(int i=0;i<numCards;i++)
        {
            for(int j=0;j<dealer;j++)
                players.get(j).getHand().addCard(deck.deal());
            for(int j=dealer;j<players.size();j++)
                players.get(j).getHand().addCard(deck.deal());
        }
    }

    void dealFlop()
    {
        deck.deal();
        community.addCard(deck.deal());
        community.addCard(deck.deal());
        community.addCard(deck.deal());
    }

    void deal()
    {
        deck.deal();
        community.addCard(deck.deal());
    }

    void determineWinner()
    {
        ArrayList<Player> temp = new ArrayList<Player>();
        int x = 0;

        for(Player player:players)
        {
            player.bestHand(community);
            System.out.println(player.getName()+": "+player.getHand()+" "+player.getHand().printHand());
            temp.add(player);
        }
        Collections.sort(temp);
        for(Player player:temp)
            if(player.compareTo(temp.get(0))==0)
                x++;
        if(x>1)
        {
            System.out.println("Split pot!\n");
            int winnings = pot/x;
            for(int i=0;i<x;i++)
            {
                temp.get(i).addBalance(winnings);
                pot -= winnings;
            }
                
        }
        else
        {
            System.out.println(temp.get(0).getName()+" has the best hand!");
            temp.get(0).addBalance(pot);
            pot = 0;
        }
    }

    int numPlayers()
    {
        return players.size();
    }

}
