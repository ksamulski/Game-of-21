/**
   Kirk Samulski CIS 111B Final Project
   This is the program to compile and run.
   The main method is at the end of this file.
        
   
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList; // For ArrayList objects
import java.util.Date; // For the Date object


/**
   This class builds the framework for the game
*/

public class GameOf21 extends JFrame
{
   Date date; // Date object for writing the timestamp in the log file
   
   // Constants for set up of the note taking area
   public static final int WIDTH = 600;
   public static final int HEIGHT = 610;
   public static final int LINES = 28;
   public static final int CHAR_PER_LINE = 45;

   // Objects in GUI
   private static JTextArea theText; // Text area to take notes
   private JMenuBar mBar;     // Horizontal menu bar
   private JPanel textPanel;  // Scrolling text area panel
   private JMenu notesMenu;   // Vertical menu for notes
   private static JTextField theTextField; // for entering values
   
   private JButton button; // Button for panel
   private JMenu viewMenu; // Vertical menu for views
   private JMenu lafMenu;  // Vertical menu look and feel
   private JMenu sbMenu;   // Vertical menu for scroll bar
   private JScrollPane scrolledText;   // Scroll bars
   
   private String[] ShuffledDeck; // Array to hold the shuffled cards
   private int[] cardValues;  // Array of the values of the cards
   private int numberOfDecks = 2; // Initializes default number of decks
      
   private static String introduction =""; // Initializes the introduction string
      
   private StandardDeck GameDeck = new StandardDeck(); // GameDeck object
   
      
   private String[] Suits = GameDeck.getSuits(); // Gets an array of suits
      
   private String[] Faces = GameDeck.getFaces(); // Gets an array of face cards
      
   private String SinglePlayingDeck[] = GameDeck.deckMaker(Faces, Suits); // Builds an array, single deck of 52 cards


   private ArrayList<String> playersHand = new ArrayList<String>(); // ArrayList to build player's hand
   private ArrayList<String> dealersHand = new ArrayList<String>(); // ArrayList to build dealer's hand  
   
   private int playersHandValue = 0; // initializes the value of the player's hand
   private int dealersHandValue = 0; // initializes the value of the dealer's hand

   private String[][] GameHand = new String[2][]; // Two dimensional array to return   
     
   // indexes for after the initial deal
   private int index = 4;
   private int playerHandIndex = 2;
   private int dealerHandIndex = 2; 

   private String Hit = ""; // For resetting the Hit string variable
   
   /**
      Constructor
   */
   
   public GameOf21()
   {
      // Create a closeable JFrame with a specific size
      super("Game of 21");
      setSize(WIDTH, HEIGHT);
      setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      // Get contentPane and set layout of the window
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      // Creates the vertical menus
      createGame();
      createOptions();

      // Creates horizontal menu bar and
      // adds vertical menus to it
      mBar = new JMenuBar();
      mBar.add(notesMenu);
      mBar.add(viewMenu);
      
      // ADD THE viewMenu TO THE MENU BAR HERE
      setJMenuBar(mBar);

      // Creates a panel to take notes on
      textPanel = new JPanel();
      textPanel.setBackground(Color.black);
      
      // For the JTextArea
      theText = new JTextArea(LINES, CHAR_PER_LINE);
      theText.setBackground(Color.white);
      theText.setEditable(false);
      theText.setLineWrap(true);
      theText.setWrapStyleWord(true);
      
      // For the JTextField
      theTextField = new JTextField(CHAR_PER_LINE);
      
      //For the button
      button = new JButton("Quit Game");
      
      // CREATE A JScrollPane OBJECT HERE CALLED
      // scrolledText AND PASS IN theText, THEN
      // CHANGE THE LINE BELOW BY PASSING IN scrolledText
      
      scrolledText = new JScrollPane(theText);
      textPanel.add(scrolledText);
      textPanel.add(theTextField);
      textPanel.add(button);
      contentPane.add(textPanel, BorderLayout.CENTER);
            
      scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);   
      
      // ActionListener for the JButton, quites the program
      button.addActionListener(new ActionListener()
      {
         public void actionPerformed (ActionEvent e)
         {
            System.exit(0);
         }
      });
      
      // ActionListener for the JTextField
      // This controls the basic functionality of the program
      theTextField.addActionListener(new ActionListener() 
      {
         public void actionPerformed (ActionEvent e)
         {

            String Str = e.getActionCommand();

            
            
            if ((Str.equalsIgnoreCase("1") || Str.equalsIgnoreCase("2") || Str.equalsIgnoreCase("3") || Str.equalsIgnoreCase("4")))
            {
               if (Character.isDigit(Str.charAt(0)))
               {  
                  numberOfDecks = Integer.parseInt(Str);
                  playersHand = new ArrayList<String>();
                  dealersHand = new ArrayList<String>();
                  playersHandValue = 0;
                  dealersHandValue = 0;
                  index = 4;
                  playerHandIndex = 2;
                  dealerHandIndex = 2;
                  
                  try
                  {
                     play();
                  }
                  catch(IOException ex)
                  {
                     theText.append("\nCannot play game!\n");
                     theText.setCaretPosition(theText.getDocument().getLength());
                  }
         
                  initializeHand(ShuffledDeck, cardValues, numberOfDecks);
   
                  theText.append("\nYou have " + playersHandValue + "\n" + "The dealer is showing " +
                                   (dealersHandValue-cardValues[2]) + "\nDo you want another card? Y or N\n");         
                  theTextField.setText("");
               }
            }
            

            
            if (Str.equalsIgnoreCase("Y") || Str.equalsIgnoreCase("N"))
            {
               if (Character.isLetter(Str.charAt(0)))
               {
                  try
                  {
                     Hit = Str;
                     roundOfPlay(ShuffledDeck, cardValues, numberOfDecks);
                     Str = "";
                  }
                  catch(Exception exy)
                  {
                     theText.append("\nError: enter number of decks!\n");       
                     theText.setCaretPosition(theText.getDocument().getLength());
                  }
               }
            
            }
            if (Str.equalsIgnoreCase("Call"))
            {
               finishHand(ShuffledDeck, cardValues, numberOfDecks);
               gameHand(ShuffledDeck, cardValues, numberOfDecks);
       
               try
               {
                  score(date);
               }
                  catch (Exception exc)
               {
                  theText.append("\nCannot calculate score!\n");       
                  theText.setCaretPosition(theText.getDocument().getLength());
               }
               theText.append("\nTo play again, enter the number of decks, 1-4.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         }
      });
   }
   
   

   /**
      Creates vertical menu associated with Games
      menu item on menu bar.
      Options include starting a new game, viewing the winners log, and erasing the winners log
   */  
   
   public void createGame()
   {
      notesMenu = new JMenu("Game");
      JMenuItem item;

      item = new JMenuItem("New Game");
      item.addActionListener(new MenuListener());
      notesMenu.add(item);

      item = new JMenuItem("Clear Screen");
      item.addActionListener(new MenuListener());
      notesMenu.add(item);
      
      item = new JMenuItem("View Winners");
      item.addActionListener(new MenuListener());
      notesMenu.add(item);

      item = new JMenuItem("Erase Winners Log");
      item.addActionListener(new MenuListener());
      notesMenu.add(item);      
      
      item = new JMenuItem("Exit");
      item.addActionListener(new MenuListener());
      notesMenu.add(item);
   }

   /**
      Creates vertical menu associated with Options
      menu item on the menu bar.
   */

   public void createOptions()
   {
      viewMenu = new JMenu("Options");
      createLookAndFeel();
      lafMenu.addActionListener(new MenuListener());
      viewMenu.add(lafMenu);
      
      createScrollBars();
      sbMenu.addActionListener(new MenuListener());
      viewMenu.add(sbMenu);
   
   }

   /**
      Creates the look and feel submenu.
      Option include converting existing visible text to all uppercase or all lowercase
   */

   public void createLookAndFeel()
   {
      lafMenu = new JMenu("Look and Feel");
      JMenuItem item;
      
      
      item = new JMenuItem("Metal");
      item.addActionListener(new MenuListener());
      lafMenu.add(item);
      
      item = new JMenuItem("Motif");
      item.addActionListener(new MenuListener());
      lafMenu.add(item);
 
      item = new JMenuItem("Windows");
      item.addActionListener(new MenuListener());
      lafMenu.add(item);  
      
      item = new JMenuItem("To Lowercase");
      item.addActionListener(new MenuListener());
      lafMenu.add(item);
   
      item = new JMenuItem("To Uppercase");
      item.addActionListener(new MenuListener());
      lafMenu.add(item); 
   }

   /**
      Creates the scroll bars submenu.
   */

   public void createScrollBars()
   {
      sbMenu = new JMenu("Scroll Bars");
      JMenuItem item;
      
      
      item = new JMenuItem("Never");
      item.addActionListener(new MenuListener());
      sbMenu.add(item);
     
      item = new JMenuItem("Always");
      item.addActionListener(new MenuListener());
      sbMenu.add(item);     
      
      item = new JMenuItem("As Needed");
      item.addActionListener(new MenuListener());
      sbMenu.add(item);
   
   }


   /**
   The initializeHand method starts the dealing
   @param ShuffledDeck[] String array of the Shuffled deck
   @param cardValues[] the numerical value of each card
   @param numberOfDecks the number of decks
   */
  
     
   public void initializeHand(String[] ShuffledDeck, int[] cardValues, int numberOfDecks)
   {
     // Populates the first two indexes of the player
     playersHand.add(ShuffledDeck[0]);
     playersHand.add(ShuffledDeck[1]);
     
     // Populates the first two indexes of the dealer    
     dealersHand.add(ShuffledDeck[2]);
     dealersHand.add(ShuffledDeck[3]);        

     
     // Gets the initial value of the player's cards    
     playersHandValue = cardValues[0];
     playersHandValue = playersHandValue + cardValues[1];
     
     // Gets the initial value of the dealer's cards    
     dealersHandValue = cardValues[2];
     dealersHandValue = dealersHandValue + cardValues[3];      

     theText.append("\nYou have a " + playersHand.get(0) + " and a " + playersHand.get(1) + " \n" + 
                                          "Dealer is showing an unknown card and a " + dealersHand.get(1) + "\n");
     theText.setCaretPosition(theText.getDocument().getLength());
   }

   /**
   The roundOfPlay method is the midgame with conditions for standing for the dealer
   @param ShuffledDeck[] String array of the Shuffled deck
   @param cardValues[] the numerical value of each card
   @param numberOfDecks the number of decks
   */


   public void roundOfPlay(String[] ShuffledDeck, int[] cardValues, int numberOfDecks)
   {
      if (dealersHandValue < 17)
      {
         if (Hit.equalsIgnoreCase("Y")) 
         {
            playersHand.add(ShuffledDeck[index]);
            dealersHand.add(ShuffledDeck[index + 1]);
              
            playersHandValue = playersHandValue + cardValues[index];
            dealersHandValue = dealersHandValue + cardValues[index + 1]; 
             
            theText.append("\nYou were dealt a " + playersHand.get(playerHandIndex) + "\n" + "The dealer was dealt a " + dealersHand.get(dealerHandIndex) +
                               "\nYou have " + playersHandValue + "\n" + "The dealer is showing " + (dealersHandValue-cardValues[2]) + "\nDo you want another card?\n");
            theTextField.setText("");
            theText.setCaretPosition(theText.getDocument().getLength());
            index++;
            playerHandIndex++;
            dealerHandIndex++;
            Hit = "";
         }
  
         if (Hit.equalsIgnoreCase("N"))
         {
            dealersHand.add(ShuffledDeck[index]);
            dealersHandValue = dealersHandValue + cardValues[index];
             
            theText.append("\nThe dealer was dealt a " + dealersHand.get(dealerHandIndex) + "\nYou have " + playersHandValue + "\n" + "The dealer is showing " 
                                             + (dealersHandValue-cardValues[2]) + "\nDo you want another card?\n");             
            theTextField.setText("");
            theText.setCaretPosition(theText.getDocument().getLength());               
             
            dealerHandIndex++;
            Hit = "";
         }
      index++;
      }
    
    
      index = index;
      playerHandIndex = playerHandIndex;
      dealerHandIndex = dealerHandIndex;
          
      if (dealersHandValue >= 17)   
      {
         if (Hit.equalsIgnoreCase("Y")) 
         {
            playersHand.add(ShuffledDeck[index]);
            playersHandValue = playersHandValue + cardValues[index];
             
            theText.append("\nYou were dealt a " + playersHand.get(playerHandIndex) + "\nYou have " + playersHandValue + "\n" + "The dealer is showing " 
                                             + (dealersHandValue-cardValues[2]) + "\nDealer stands.\nDo you want another card? Y or N\n");
            theTextField.setText("");
            theText.setCaretPosition(theText.getDocument().getLength());
            index++;
            playerHandIndex++;
            Hit = "";
         }    
         
         if  (Hit.equalsIgnoreCase("N"))
         {
            Hit = "";
            theText.append("\nYou have " + playersHandValue + "\n" + "The dealer is showing " 
                                             + (dealersHandValue-cardValues[2]) + "\nDealer stands.\nType call.\n");
            theTextField.setText("");
         }
      }
   }

   /**
   The finishHand method lets the player know who went over 21 and builds an array of the cards dealt
   @param ShuffledDeck[] String array of the Shuffled deck
   @param cardValues[] the numerical value of each card
   @param numberOfDecks the number of decks
   */   
   
   public void finishHand(String[] ShuffledDeck, int[] cardValues, int numberOfDecks)
   {
      if (playersHandValue > 21)
         {
            theText.append("\nYou went over 21.  Bust!\n");
            theText.setCaretPosition(theText.getDocument().getLength());
         }
 
      if (dealersHandValue > 21)
         {
            theText.append("\nDealer went over 21.  Bust!\n");
            theText.setCaretPosition(theText.getDocument().getLength());
         }             
     
      GameHand[0] = new String [playersHand.size()]; // Initializes player's array size
      GameHand[1] = new String [dealersHand.size()]; // Initializes dealer's array size          

      // Builds the array  
      for (int playersIndex = 0; playersIndex < playersHand.size(); playersIndex++)
         {
            GameHand[0][playersIndex] = playersHand.get(playersIndex);
         }
      for (int dealersIndex = 0; dealersIndex < dealersHand.size(); dealersIndex++)
         {
            GameHand[1][dealersIndex] = dealersHand.get(dealersIndex);
         }
   }      

   /**
   The gameHand method returns an array of the player's and dealer's hands
   @param ShuffledDeck[] String array of the Shuffled deck
   @param cardValues[] the numerical value of each card
   @param numberOfDecks the number of decks
   @return Two dimensional array
   */
   public String[][] gameHand(String[] ShuffledDeck, int[] cardValues, int numberOfDecks)
   {
      return GameHand;
   }

   /**
   The play method initializes the game
   */
   public void play() throws IOException
   {
      String MultiPlayingDeck[] = GameDeck.deckMultiplier(SinglePlayingDeck, numberOfDecks); // Builds array deck out of multiple decks
      ShuffledDeck = GameDeck.shuffle(MultiPlayingDeck, numberOfDecks); // Shuffles array deck
      cardValues = GameDeck.cardValues(ShuffledDeck); // Array of integer values of the deck to be used
   }      

   /**
   The OVERLOADED winsAndLosses method overwrites the winners file with a blank one
   @param String str File name
   */   
   public void winsAndLosses(String str) throws FileNotFoundException
   {
      
      PrintWriter pw = new PrintWriter(str);
      pw.close();
      
   }

   /**
   The OVERLOADED winsAndLosses method displays the contents of the win.log file
   */   
   public void winsAndLosses() throws IOException, FileNotFoundException
   {
      // FileReader reads text files in the default encoding.
      FileReader fileReader = new FileReader("wins.log");

      // Wrap FileReader in BufferedReader.
      BufferedReader bufferedReader =  new BufferedReader(fileReader);
      
      String line = null;
      
      while((line = bufferedReader.readLine()) != null) 
      {
         theText.append("\n" + line + "\n");
         theText.setCaretPosition(theText.getDocument().getLength());
      }
      if (line == null)
      {
         theText.append("\n--End of log--\n");
         theText.setCaretPosition(theText.getDocument().getLength());
      }
      bufferedReader.close();
   }
   
   /**
   The score method determines the winner, informs the player and writes to the log
   @param d Date object to write to the log
   */   
   public void score(Date d) throws IOException
   {
      d = new Date();
      String currentDate = d.toString();
      
      FileWriter fwriter = new FileWriter("wins.log", true); // For writing the next line of the file
      
      PrintWriter outputFile = new PrintWriter(fwriter); // For writing a file
      String[][] GameHand = gameHand(ShuffledDeck, cardValues, numberOfDecks); // Array of the players card and the dealers cards
      
      int playersHandLength = GameHand[0].length; // Number of cards in the players hand
      int dealersHandLength = GameHand[1].length; // Number of cards in the dealers hand

      String PlayersCards = ""; // Initializes string
      String DealersCards = ""; // Initializes string

      int[] playersValues = GameDeck.cardValues(GameHand[0]); // Array of values of the players hand
      int[] dealersValues = GameDeck.cardValues(GameHand[1]); // Array of values of the dealers hand
      
      int playersTotal = 0; // Initializes player's card total
      int dealersTotal = 0; // Initializes dealer's card total
      
      // Counts the player's total
      for (int index = 0; index < playersHandLength; index++)
         {
         playersTotal = playersTotal + playersValues[index];
         }
      
      // Counts the dealer's total
      for (int index = 0; index < dealersHandLength; index++)
         {
         dealersTotal = dealersTotal +dealersValues[index];
         }      
      
      // Builds string of player's card
      for (int index = 0; index < playersHandLength; index++)
         {
         PlayersCards += GameHand[0][index] + "\n";
         }
      
      // Builds string of dealer's cards
      for (int index = 0; index < dealersHandLength; index++)
         {
         DealersCards += GameHand[1][index] + "\n";
         } 

      theText.append("\nThe player had \n" + PlayersCards + "\nPlayer score " + playersTotal +
                                          "\n\nThe dealer had \n" + DealersCards + "\nDealer score " + dealersTotal + "\n");
      
      theText.setCaretPosition(theText.getDocument().getLength());
      // Conditions to determine the winner and write the winner to the file
      if (dealersTotal == playersTotal)
         {
         theText.append("\nDealer wins!\n");
         theText.setCaretPosition(theText.getDocument().getLength());
         theTextField.setText("");
         outputFile.println("Dealer " + currentDate);
         }
      
      else if (playersTotal > 21)
         {
         theText.append("\nDealer wins!\n");
         theText.setCaretPosition(theText.getDocument().getLength());
         theTextField.setText("");
         outputFile.println("Dealer " + currentDate);
         }
      
      
      else if (playersTotal < 22 && dealersTotal < 22 && dealersTotal > playersTotal)
         {
         theText.append("\nDealer wins!\n");
         theText.setCaretPosition(theText.getDocument().getLength());
         theTextField.setText("");
         outputFile.println("Dealer " + currentDate);
         }
      
      
      else if (playersTotal < 22 && dealersTotal < 22 && playersTotal > dealersTotal)
         {
         theText.append("\nPlayer wins!\n");
         theText.setCaretPosition(theText.getDocument().getLength());
         theTextField.setText("");
         outputFile.println("Player " + currentDate);
         }
      
      else if (playersTotal < 22 && dealersTotal > 21)
         {
         theText.append("\nPlayer wins!\n");
         theText.setCaretPosition(theText.getDocument().getLength());
         theTextField.setText("");
         outputFile.println("Player " + currentDate);
         }

      theText.setCaretPosition(theText.getDocument().getLength());
      outputFile.close(); // Closes the file
   }     
   
   /**
   The introStart method provides instructions to the player
   */   
   public static void introStart()
   {
      // Introduction with instructions
      

      introduction = "\nWelcome to Game Of 21.\n\nThis is a simplfied Blackjack-like game.  Number cards are worth\n" +
                                          "their numerical value. Aces are worth 1 (not 1 or 11.) Other face cards are \n" +
                                          "worth 10. Have fun. If you have a higher score than the dealer without going over\n" +
                                          "21, you win. If you go over 21, dealer wins. If you tie, dealer wins.\n" +
                                          "Again have fun. Please follow the prompts. Type commands into the bottom text field\n" +
                                          "and press enter to control the game.\n";      
      theText.append(introduction);
      theText.setCaretPosition(theText.getDocument().getLength());

      theText.append("\nHow many decks? More decks means the possibility of\na hand having more than one of the same card. Enter 1 - 4.\n");
      theTextField.setText("");
      theTextField.requestFocusInWindow();
      theText.setCaretPosition(theText.getDocument().getLength());         
   }

   /**
      This is a private inner class that handles the Menu object's
      action events.
   */

   private class MenuListener implements ActionListener
   {

      public void actionPerformed(ActionEvent e)
      {
         String actionCommand = e.getActionCommand();
         
         if (actionCommand.equals("New Game"))
            {
               theText.setText("");
               introStart();
            }

         else if (actionCommand.equals("View Winners"))
            {
               try
               {
                  winsAndLosses();
               }
               
               catch (FileNotFoundException exp)
               {
                  theText.append("\nFile not found!\n");
                  theText.setCaretPosition(theText.getDocument().getLength());
               }
              
               
               catch (IOException IOe)
               {
                  theText.append("\nCannot view winners!\n");
                  theText.setCaretPosition(theText.getDocument().getLength());
               }
            
            }
         
         else if (actionCommand.equals("Erase Winners Log"))
            {
            try
               {
                  winsAndLosses("wins.log");
               }
               
               catch (FileNotFoundException exp)
               {
                  theText.append("\nFile not found!\n");
                  theText.setCaretPosition(theText.getDocument().getLength());
               }
            }
         
         
         
         else if (actionCommand.equals("Clear Screen"))
            theText.setText("");

         else if (actionCommand.equals("Exit"))
            System.exit(0);
        
        
         
         else if (actionCommand.equals("Metal"))
         {
            try
            {
               UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
               SwingUtilities.updateComponentTreeUI(getContentPane());  
            }
            catch (Exception except)
            {
               theText.append("\nCould not load the Metal look and feel.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         
         }

 
 
         else if (actionCommand.equals("Motif"))
         {
            try
            {
               UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
               SwingUtilities.updateComponentTreeUI(getContentPane());  
            }
            catch (Exception except)
            {
               theText.append("\nCould not load the Motif look and feel.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         
         }
         
         else if (actionCommand.equals("To Lowercase"))
         {
            try
            {
               String text = theText.getText();
               theText.setText(text.toLowerCase());
               theText.setCaretPosition(theText.getDocument().getLength()); 
            }
            catch (Exception except)
            {
               theText.append("\nCould not set text to lowercase.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         
         }         
         
         else if (actionCommand.equals("To Uppercase"))
         {
            try
            {
               String text = theText.getText();
               theText.setText(text.toUpperCase());
               theText.setCaretPosition(theText.getDocument().getLength()); 
            }
            catch (Exception except)
            {
               theText.append("\nCould not set text to uppercase.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         
         }          
         
         
         
         
         
         else if (actionCommand.equals("Windows"))
         {
            try
            {
               UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
               SwingUtilities.updateComponentTreeUI(getContentPane());  
            }
            catch (Exception except)
            {
               theText.append("\nCould not load the Windows look and feel.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         
         }         
         
        
         else if (actionCommand.equals("Never"))
         {
            try
            {
               scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
               
               scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
               
               SwingUtilities.updateComponentTreeUI(getContentPane());
            }
            catch (Exception except)
            {
               theText.append("\nThe Never Scrollbar Policy was not set.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         }
        
         else if (actionCommand.equals("Always"))
         
         {
            try
            {
            scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            SwingUtilities.updateComponentTreeUI(getContentPane());
            }
            
            catch (Exception except)
            {
               theText.append("\nThe Always Scrollbar Policy was not set.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         
         }         
         
         else if (actionCommand.equals("As Needed"))
         
         {
            
            try
            {
            scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            SwingUtilities.updateComponentTreeUI(getContentPane());
            }
            
            catch (Exception except)
            {
               theText.append("\nThe As Needed Scrollbar Policy was not set.\n");
               theText.setCaretPosition(theText.getDocument().getLength());
            }
         }          
         else  
            {
            theText.setText("\nError in memo interface.\n");     
            theText.setCaretPosition(theText.getDocument().getLength());
            }
      }
   }
   


   
   public static void main(String[] args) throws IOException
   {
      GameOf21 gui = new GameOf21();
      gui.setVisible(true);
      introStart();
   }
}