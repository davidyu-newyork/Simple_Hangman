import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class WordLetter extends StackPane {
	private Rectangle rect = new Rectangle(30, 50);
    private Text letter;
    private boolean visible = false;
    public WordLetter(char x) {
    	rect.setFill(Color.PEACHPUFF);
    	rect.setStroke(Color.BLACK);
    	
    	letter = new Text(String.valueOf(x).toUpperCase());
        letter.setFont(Font.font("Helvetica", 20));
        letter.setVisible(false);
        
        getChildren().addAll(rect,letter);
    	
    }
    public Rectangle getRect() {
    	return rect;
    }
    public boolean getVis() {
    	return visible;
    }
    public boolean checkEqual(char inputtedChar) {
    	//if they are the same character
        return letter.getText().equals(String.valueOf( inputtedChar).toUpperCase() );
    }
    public void setVisible() {
    	visible = true;
    	letter.setVisible(true);
    	return;
    }

    
  

}
