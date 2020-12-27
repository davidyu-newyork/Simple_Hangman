
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Hangman extends Application implements Serializable{
	private Button button3,button2;
	private BorderPane layout;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;
	private Button startPlaying;
    private ObservableList<Node> word;
    private Boolean playing = false;
    private int howManyLetters = 0;
    private Text yabba;
    private TheHangMan hangmanToDisplay = new TheHangMan();
    private String guessWord = "";
    private HashMap<Character, Text> aToZ = new HashMap<Character, Text>();
    private GetWord newWord = new GetWord();
    private boolean won = false;
    private boolean savable = false;
    private Scene scene;
    private boolean notNew = true;
    private boolean toNewGame = false;
    private ArrayList<String> guessesB = new ArrayList<>();
    private int guessesMade = 0;
	public static void main (String[] args) {
		launch(args);
	}
	public HBox topButtons() {
		 Image img1 = new Image("/New.png");
		 Button button1 = new Button("", new ImageView(img1));
		 button1.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				 if(!playing) {
					startPlaying.setVisible(true);
					startPlaying.setDisable(false);}				
				else {
					toNewGame = true;
					yesNoScreen();
				}					
				} 

		});
		 Image img2 = new Image("/Load.png");
		 
		 button2 = new Button("", new ImageView(img2));
		 button2.setOnAction(action -> loadGame(action));

		 Image img3 = new Image("/Save.png");
		 button3 = new Button("", new ImageView(img3));
		 button3.setOnAction(action -> saveGame(action));
		 button3.setDisable(true);
	     Image img4 = new Image("/Exit.png");
	     Button button4 = new Button("", new ImageView(img4));
	     button4.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				 if(playing) {
					 toNewGame = false;
					 yesNoScreen();
				 }
				 else
					 System.exit(1);	
				}					
		});

	     HBox topButtons = new HBox();
	     topButtons.getChildren().addAll(button1,button2,button3,button4);
	     return topButtons;
		
	}
	public void loadGame(ActionEvent event) {
		 FileChooser fileChooser = new FileChooser();
		  try {
			  Window win = ((Node)(event.getSource())).getScene().getWindow();
			  File file = fileChooser.showOpenDialog(((Node)(event.getSource())).getScene().getWindow());
			  if (file.getAbsolutePath().endsWith(".hng")) { 
				  BufferedReader br = new BufferedReader(new FileReader(file));
				  int temp = Integer.parseInt(br.readLine());
				  guessesB.clear();
				  for(int i = 0; i<temp;i++) {
					  guessesB.add(br.readLine());
				  }
				  guessWord = br.readLine();
				  howManyLetters= Integer.parseInt(br.readLine());
				  hangmanToDisplay.setGuesses(Integer.parseInt(br.readLine()));
				  loadNewGame();
             } 

		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public void yesNoScreen() {
		Text youWon = new Text("want to save da game?");
		if (won)
			 youWon = new Text("You WON!!!!!");
	    youWon.setFont(Font.font("Helvetica", 25));
	    Button yes = new Button("Yes");
	    Button no = new Button("No");
	    Button cancel = new Button("Cancel");
		Stage gameWonWindow = new Stage();
	    yes.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				 if (toNewGame) {
					 saveGame(event);
					 newGame();	
				}
				 else {
					 saveGame(event);
					 System.exit(1);
				 }
				 gameWonWindow.close();
				} 
		});
	    no.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				 if (toNewGame) {
					 newGame();	
				}
				 else {
					 System.exit(1);
				 }
				 gameWonWindow.close();
				} 
		});
	    cancel.setOnAction(event -> gameWonWindow.close());
	    VBox box =new VBox(20);
	    box.getChildren().addAll(youWon,yes,no,cancel);
	    box.setAlignment(Pos.CENTER);
	
	    Scene display = new Scene(box,700,300);
	    gameWonWindow.setScene(display);
	    gameWonWindow.showAndWait();
		
	}
	public void loadNewGame() {
		howManyLetters=0;
		startPlaying.setVisible(false);
		startPlaying.setDisable(true);
		for (Text letter : aToZ.values()) {
			letter.setVisible(true);
            letter.setStrikethrough(false);
            letter.setFill(Color.DARKGREEN);
        }
        word.clear();
        for (char c : guessWord.toCharArray()) {
        	word.add(new WordLetter(c));
        	howManyLetters++;
        }
        for(String c: guessesB) {
			char ch = c.charAt(0);
            Text t = aToZ.get(ch);
            t.setStrikethrough(true);
            t.setFill(Color.CORAL);
			for (Node cd : word) {
                WordLetter letter = (WordLetter)cd;
                if (letter.checkEqual(ch)) {
                    letter.setVisible();
                	howManyLetters--;
                	break;
                }
            }
		}
        int yo = 10-hangmanToDisplay.getGuesses();
        hangmanToDisplay.setGuesses(10);
        for(int i = 0; i < yo; i++) {
        	hangmanToDisplay.dealDamange();
        }
		playing = true;
		won = false;
		savable = true;
        yabba.setText("   Guesses Left : " +hangmanToDisplay.getGuesses());
	}
	public void saveGame(ActionEvent event) {
		  FileChooser fileChooser = new FileChooser();
		  try {
			  File file = fileChooser.showSaveDialog(((Node)(event.getSource())).getScene().getWindow());
			  if (file.getAbsolutePath().endsWith(".hng")) { 
				  PrintWriter pw = new PrintWriter(file);
				  pw.println(guessesMade);
				  for(String s: guessesB) {
					  pw.println(s);
				  }
				  pw.println(guessWord);
				  System.out.println(guessWord);
				  pw.println(howManyLetters);
				  pw.println(hangmanToDisplay.getGuesses());
				  pw.close();		  
				  if(toNewGame) {
					  newGame();
				  }
              } 
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public Parent createScene() {
		notNew = false;
		HBox wordToGuess = new HBox();
		// SET ALIGNMENt
		wordToGuess.setAlignment(Pos.CENTER);
		word = wordToGuess.getChildren();
		 HBox topButtons = topButtons();
		
		 startPlaying = new Button("Start Playing");
		 startPlaying.setVisible(false);
		 startPlaying.setDisable(true);
		 startPlaying.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				newGame();
			}
		});
		  // SET ACTIONS FOR BUTTONS	     
		 HBox firstTen = new HBox(4);
		 firstTen.setAlignment(Pos.CENTER);
		
		 for (char c = 'A'; c <= 'A'+10; c++) {	
		        Text t = new Text(String.valueOf(c));  t.setVisible(false);  t.setFont(Font.font("Helvetica", 30));	  aToZ.put(c, t); firstTen.getChildren().add(t);
		    }
		 HBox nextTen = new HBox(4);
		 nextTen.setAlignment(Pos.CENTER);
		
		 for (char c = 'A'+10; c <= 'A'+20; c++) {
		        Text t = new Text(String.valueOf(c)); t.setFont(Font.font("Helvetica", 30)); t.setVisible(false); aToZ.put(c, t); nextTen.getChildren().add(t);
		    }
		
		 HBox toZ = new HBox(4);
		 toZ.setAlignment(Pos.CENTER);
		
		 for (char c = 'A'+20; c <= 'Z'; c++) {
		        Text t = new Text(String.valueOf(c)); t.setVisible(false);  t.setFont(Font.font("Helvetica", 30)); aToZ.put(c, t); toZ.getChildren().add(t);
		    }
		 
		 VBox verticalBox = new VBox(15);
		 Text textt = new Text("THE HANGMAN");
		 textt.setFont(Font.font("Helvetica", 40));
		 HBox padding = new HBox(textt);
		 yabba = new Text("");
		 padding.getChildren().add(yabba);
		 padding.setAlignment(Pos.CENTER);
		 HBox padding2 = new HBox();
		 HBox padding3 = new HBox();
		 verticalBox.getChildren().addAll(topButtons,padding,wordToGuess,padding2,padding3,firstTen,nextTen,toZ);
		
		 BorderPane pane = new BorderPane();
		 pane.setTop(verticalBox);
		 pane.setAlignment(hangmanToDisplay, Pos.TOP_CENTER);
		 BorderPane.setMargin(hangmanToDisplay, new Insets(-250,0,0,100)); // optional
		 pane.setAlignment(startPlaying, Pos.BOTTOM_CENTER);
		 pane.setBottom(startPlaying);
		 pane.setLeft(hangmanToDisplay);
		 return pane;
       
	}
	public void gameWon() {		
		Text youWon = new Text("You LOST!!!!!");
		if (won)
			 youWon = new Text("You WON!!!!!");
	    youWon.setFont(Font.font("Helvetica", 40));
	    Button close = new Button("CLOSE");
		Stage gameWonWindow = new Stage();
	    close.setOnAction(action -> gameWonWindow.close() );
	    VBox box =new VBox(20);
	    box.getChildren().addAll(youWon,close);
	    box.setAlignment(Pos.CENTER);
	    if(!won) {
	    	box.getChildren().add(new Text("Word was :" + guessWord.toString()));

	    }
	    Scene display = new Scene(box,300,300);
	    gameWonWindow.setScene(display);
	    gameWonWindow.showAndWait();
	    for (Node c : word) {
            WordLetter letter = (WordLetter)c;
            if (!letter.getVis()) {
                letter.setVisible();
                letter.getRect().setFill(Color.GRAY);
            }
        }
	}
	public void newGame() {
		guessesMade = 0; toNewGame = false;
		startPlaying.setVisible(false);
		startPlaying.setDisable(true);
		button3.setDisable(true);
		for (Text letter : aToZ.values()) {
			letter.setVisible(true);
            letter.setStrikethrough(false);
            letter.setFill(Color.DARKGREEN);
        }

		guessWord = (newWord.getRandomWord().toUpperCase());
        word.clear();
        for (char c : guessWord.toCharArray()) {
        	word.add(new WordLetter(c));
        	howManyLetters++;
        }
		hangmanToDisplay.setNotVisible();
		playing = true;
		won = false;
		savable = true;
        yabba.setText("   Guesses Left : " +hangmanToDisplay.getGuesses());
	}

	public void start(Stage primaryStage) {
		if(notNew) {
			scene = new Scene(createScene());
		}
		
		scene.setOnKeyPressed((KeyEvent event) -> {  
            if (event.getText().isEmpty())
                return;
            char key = event.getText().toUpperCase().charAt(0);
            if (key>'Z' || key<'A') //if not A throuhg Z
                return;

            if (playing) {
        		button3.setDisable(false);
                Text t = aToZ.get(key);
                
                if (t.isStrikethrough()) 
                	return;
                t.setFill(Color.CORAL);
                t.setStrikethrough(true);
                boolean correctGuess = false;
                guessesMade++;
                guessesB.add(key +"");
                for (Node c : word) {
                    WordLetter letter = (WordLetter)c;
                    if (letter.checkEqual(key)) {
                        letter.setVisible();
                    	correctGuess = true;
                    	howManyLetters--;
                    }
                }
                if(howManyLetters==0) {
                	playing = false;
                	won = true;
                	gameWon();
                }
 
                if (!correctGuess) {
                    hangmanToDisplay.dealDamange();
                }
                yabba.setText("   Guesses Left : " +hangmanToDisplay.getGuesses());
                if(!hangmanToDisplay.alive()) { // if deead end the game
                	playing = false;
                	gameWon();
                }
            }
        });
		hangmanToDisplay.setNotVisible();
        primaryStage.setResizable(false);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setTitle("HANGMAN!");
		primaryStage.setScene(scene);
		primaryStage.show();
		}
}