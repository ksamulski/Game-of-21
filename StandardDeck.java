import java.util.Random; // For generating random numbers

/**
   This class is for a deck of cards and
   has methods for building and shuffling 1 or more decks
*/

public class StandardDeck

{
   // String arrays of face cards and suits
   private String[] Faces = new String[13];
   private String[] Suits = new String[4];
   
   //Constructor
   
   public StandardDeck()
   {
      Faces[0] = "Ace";
      Faces[1] = "Two";
      Faces[2] = "Three";
      Faces[3] = "Four";
      Faces[4] = "Five";
      Faces[5] = "Six";
      Faces[6] = "Seven";
      Faces[7] = "Eight";
      Faces[8] = "Nine";
      Faces[9] = "Ten";
      Faces[10] = "Jack";
      Faces[11] = "Queen";
      Faces[12] = "King";
      Suits[0] = "Spades";
      Suits[1] = "Clubs";
      Suits[2] = "Hearts";
      Suits[3] = "Diamonds";
   }   
   
  

   /**
   The getFaces method returns a String array of face cards
    @return Faces[] String array of face cards
   */
   public String[] getFaces()
	{
	   return Faces;
	}

   /**
   The getSuits method returns a String array of suits
   @return Suits[] String array of suits
   */
   public String[] getSuits()
	{
   	return Suits;
	}

   /**
   The deckMaker method builds a single deck of cards
   as an array of strings
   @param Faces[] String array of faces
   @param Suits[] String array of suits
   @return GameDeck[] String array of cards
   */

   public String[] deckMaker(String[] Faces,	String[]	Suits)
	{
   	String[]	GameDeck	= new	String[52];
	
   	for (int	index	= 0; index < 13; index ++)
 		{
   		GameDeck[index] =	Faces[index] +	" of " +	Suits[0];
		}
   	for (int	index	= 13;	index	< 26;	index++)
		{
	   	GameDeck[index] =	Faces[index-13] +	" of " +	Suits[1];
 		}
   	for (int	index	= 26;	index	< 39;	index++)
		{
	   	GameDeck[index] =	Faces[index-26] +	" of " +	Suits[2];
		}
	   for (int	index	= 39;	index	< 52;	index++)
	 	{
		   GameDeck[index] =	Faces[index-39] +	" of " +	Suits[3];
		}
	   return GameDeck;
	}

   /**
   The deckMultiplier method builds a multi-deck string array
   @param SinglePlayingDeck[] String array of a single deck
   @param numberOfDecks Integer of number of decks chosen by user
   @return MultiDeck[] String array of multiple decks in order
   */

   public String[] deckMultiplier(String[] SinglePlayingDeck, int	numberOfDecks)
	{
   	int totalNumberCards	= numberOfDecks *	52;
   	String[]	MultiDeck =	new String[totalNumberCards];
	
   	for (int	i = 0; i	< (totalNumberCards); i++)
		{	 
   		MultiDeck[i] =	SinglePlayingDeck[i%52];
		}
      return MultiDeck;
	}

   /**
   The shuffle method shuffles the multi-deck string array
   @param MultiDeck[] String array of a multiple decks in order
   @param numberOfDecks Integer of number of decks chosen by user
   @return ShuffledDeck[] String array of multiple decks now shuffled
   */

   public String[] shuffle(String[] MultiDeck, int numberOfDecks)
   {
      int randomIndex; // for choosing a random card
      int totalNumberCards	= numberOfDecks *	52; // total cards
      Random randomNumber = new Random(); // for a random number
      String Temp; // for swapping
      String[] ShuffledDeck = new String[totalNumberCards]; // New String array
   
      // Duplicates the multideck
      for (int index = 0; index < totalNumberCards; index++)
      {
         ShuffledDeck[index] = MultiDeck[index];
      }
   
      // Shuffles 7 times for each deck in the pile
      for (int count = 1; count <= numberOfDecks*7; count++)
      {
         for (int index = 0; index < totalNumberCards; index++)
         {
            randomIndex = randomNumber.nextInt(totalNumberCards);
      
            Temp = ShuffledDeck[randomIndex];
      
            ShuffledDeck[randomIndex] = ShuffledDeck[index];
         
            ShuffledDeck[index] = Temp;
         }
      }
      return ShuffledDeck;
   }


   /**
   The cardValues method determines the numerical value of
   each card in the shuffled deck and returns an integer array
   @param ShuffledDeck[] String array of the Shuffled deck
   @return cardValues[] Int array of card card values corresponding to the shuffled deck
   */

   public int[] cardValues(String[] ShuffledDeck)
   {
   
      int[] cardValues = new int[ShuffledDeck.length];
   
      for (int index = 0; index < ShuffledDeck.length; index++)
      {
         if (ShuffledDeck[index].equals("Ace of Spades"))
         {
            String AceSpades = "1";
            cardValues[index] = Integer.parseInt(AceSpades);
         }
      
         if (ShuffledDeck[index].equals("Ace of Clubs"))
         {
            String AceClubs = "1";
            cardValues[index] = Integer.parseInt(AceClubs);
         }
         
         if (ShuffledDeck[index].equals("Ace of Hearts"))
         {
            String AceHearts = "1";
            cardValues[index] = Integer.parseInt(AceHearts);
         }
         
         if (ShuffledDeck[index].equals("Ace of Diamonds"))
         {
            String AceDiamonds = "1";
            cardValues[index] = Integer.parseInt(AceDiamonds);
         }
    
         if (ShuffledDeck[index].equals("Two of Spades"))
         {
            String TwoSpades = "2";
            cardValues[index] = Integer.parseInt(TwoSpades);
         }
      
         if (ShuffledDeck[index].equals("Two of Clubs"))
         {
            String TwoClubs = "2";
            cardValues[index] = Integer.parseInt(TwoClubs);
         }
         if (ShuffledDeck[index].equals("Two of Hearts"))
         {
            String TwoHearts = "2";
            cardValues[index] = Integer.parseInt(TwoHearts);
         }
         if (ShuffledDeck[index].equals("Two of Diamonds"))
         {
            String TwoDiamonds = "2";
            cardValues[index] = Integer.parseInt(TwoDiamonds);
         }
         if (ShuffledDeck[index].equals("Three of Spades"))
         {
            String ThreeSpades = "3";
            cardValues[index] = Integer.parseInt(ThreeSpades);
         }
         if (ShuffledDeck[index].equals("Three of Clubs"))
         {
            String ThreeClubs = "3";
            cardValues[index] = Integer.parseInt(ThreeClubs);
         }
         if (ShuffledDeck[index].equals("Three of Hearts"))
         {
            String ThreeHearts = "3";
            cardValues[index] = Integer.parseInt(ThreeHearts);
         }
         if (ShuffledDeck[index].equals("Three of Diamonds"))
         {
            String ThreeDiamonds = "3";
            cardValues[index] = Integer.parseInt(ThreeDiamonds);
         }
         if (ShuffledDeck[index].equals("Four of Spades"))
         {
            String FourSpades = "4";
            cardValues[index] = Integer.parseInt(FourSpades);
         }
         if (ShuffledDeck[index].equals("Four of Clubs"))
         {
            String FourClubs = "4";
            cardValues[index] = Integer.parseInt(FourClubs);
         }
         if (ShuffledDeck[index].equals("Four of Hearts"))
         {
            String FourHearts = "4";
            cardValues[index] = Integer.parseInt(FourHearts);
         }
         if (ShuffledDeck[index].equals("Four of Diamonds"))
         {
            String FourDiamonds = "4";
            cardValues[index] = Integer.parseInt(FourDiamonds);
         }
         if (ShuffledDeck[index].equals("Five of Spades"))
         {
            String FiveSpades = "5";
            cardValues[index] = Integer.parseInt(FiveSpades);
         }
         if (ShuffledDeck[index].equals("Five of Clubs"))
         {   
            String FiveClubs = "5";
            cardValues[index] = Integer.parseInt(FiveClubs);
         }
         if (ShuffledDeck[index].equals("Five of Hearts"))
         {
            String FiveHearts = "5";
            cardValues[index] = Integer.parseInt(FiveHearts);
         }
         if (ShuffledDeck[index].equals("Five of Diamonds"))
         {
            String FiveDiamonds = "5";
            cardValues[index] = Integer.parseInt(FiveDiamonds);
         }
         if (ShuffledDeck[index].equals("Six of Spades"))
         {
            String SixSpades = "6";
            cardValues[index] = Integer.parseInt(SixSpades);
         }
         if (ShuffledDeck[index].equals("Six of Clubs"))
         {
            String SixClubs = "6";
            cardValues[index] = Integer.parseInt(SixClubs);
         }
         if (ShuffledDeck[index].equals("Six of Hearts"))
         {
            String SixHearts = "6";
            cardValues[index] = Integer.parseInt(SixHearts);
         }
         if (ShuffledDeck[index].equals("Six of Diamonds"))
         {
            String SixDiamonds = "6";
            cardValues[index] = Integer.parseInt(SixDiamonds);
         }
         if (ShuffledDeck[index].equals("Seven of Spades"))
         {
            String SevenSpades = "7";
            cardValues[index] = Integer.parseInt(SevenSpades);
         }
         if (ShuffledDeck[index].equals("Seven of Clubs"))
         {
            String SevenClubs = "7";
            cardValues[index] = Integer.parseInt(SevenClubs);
         }
         if (ShuffledDeck[index].equals("Seven of Hearts"))
         {
            String SevenHearts = "7";
            cardValues[index] = Integer.parseInt(SevenHearts);
         }
         if (ShuffledDeck[index].equals("Seven of Diamonds"))
         {
            String SevenDiamonds = "7";
            cardValues[index] = Integer.parseInt(SevenDiamonds);
         }
         if (ShuffledDeck[index].equals("Eight of Spades"))
         {
            String EightSpades = "8";
            cardValues[index] = Integer.parseInt(EightSpades);
         }
         if (ShuffledDeck[index].equals("Eight of Clubs"))
         {
            String EightClubs = "8";
            cardValues[index] = Integer.parseInt(EightClubs);
         }
         if (ShuffledDeck[index].equals("Eight of Hearts"))
         {
            String EightHearts = "8";
            cardValues[index] = Integer.parseInt(EightHearts);
         }
         if (ShuffledDeck[index].equals("Eight of Diamonds"))
         {
            String EightDiamonds = "8";
            cardValues[index] = Integer.parseInt(EightDiamonds);
         }
         if (ShuffledDeck[index].equals("Nine of Spades"))
         {
            String NineSpades = "9";
            cardValues[index] = Integer.parseInt(NineSpades);
         }
         if (ShuffledDeck[index].equals("Nine of Clubs"))
         {
            String NineClubs = "9";
            cardValues[index] = Integer.parseInt(NineClubs);
         }
         if (ShuffledDeck[index].equals("Nine of Hearts"))
         {
            String NineHearts = "9";
            cardValues[index] = Integer.parseInt(NineHearts);
         }
         if (ShuffledDeck[index].equals("Nine of Diamonds"))
         {
            String NineDiamonds = "9";
            cardValues[index] = Integer.parseInt(NineDiamonds);
         }
         if (ShuffledDeck[index].equals("Ten of Spades"))
         {
            String TenSpades = "10";
            cardValues[index] = Integer.parseInt(TenSpades);
         }
         if (ShuffledDeck[index].equals("Ten of Clubs"))
         {
            String TenClubs = "10";
            cardValues[index] = Integer.parseInt(TenClubs);
         }
         if (ShuffledDeck[index].equals("Ten of Hearts"))
         {
            String TenHearts = "10";
            cardValues[index] = Integer.parseInt(TenHearts);
         }
         if (ShuffledDeck[index].equals("Ten of Diamonds"))
         {
            String TenDiamonds = "10";
            cardValues[index] = Integer.parseInt(TenDiamonds);
         }
         if (ShuffledDeck[index].equals("Jack of Spades"))
         {
            String JackSpades = "10";
            cardValues[index] = Integer.parseInt(JackSpades);
         }
         if (ShuffledDeck[index].equals("Jack of Clubs"))
         {
            String JackClubs = "10";
            cardValues[index] = Integer.parseInt(JackClubs);
         }
         if (ShuffledDeck[index].equals("Jack of Hearts"))
         {
            String JackHearts = "10";
            cardValues[index] = Integer.parseInt(JackHearts);
         }
         if (ShuffledDeck[index].equals("Jack of Diamonds"))
         {
            String JackDiamonds = "10";
            cardValues[index] = Integer.parseInt(JackDiamonds);
         }
         if (ShuffledDeck[index].equals("Queen of Spades"))
         {
            String QueenSpades = "10";
            cardValues[index] = Integer.parseInt(QueenSpades);
         }
         if (ShuffledDeck[index].equals("Queen of Clubs"))
         {
            String QueenClubs = "10";
            cardValues[index] = Integer.parseInt(QueenClubs);
         }
         if (ShuffledDeck[index].equals("Queen of Hearts"))
         {
            String QueenHearts = "10";
            cardValues[index] = Integer.parseInt(QueenHearts);
         }
         if (ShuffledDeck[index].equals("Queen of Diamonds"))
         {
            String QueenDiamonds = "10";
            cardValues[index] = Integer.parseInt(QueenDiamonds);
         }
         if (ShuffledDeck[index].equals("King of Spades"))
         {
            String KingSpades = "10";
            cardValues[index] = Integer.parseInt(KingSpades);
         }
         if (ShuffledDeck[index].equals("King of Clubs"))
         {
            String KingClubs = "10";
            cardValues[index] = Integer.parseInt(KingClubs);
         }
         if (ShuffledDeck[index].equals("King of Hearts"))
         {
            String KingHearts = "10";
            cardValues[index] = Integer.parseInt(KingHearts);
         }
         
         if (ShuffledDeck[index].equals("King of Diamonds"))
         {
            String KingDiamonds = "10";
            cardValues[index] = Integer.parseInt(KingDiamonds);
         }
      }
         return cardValues;   
   }
}