import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TheHangMan extends Parent{
	 private int x = 0;
	    private int y = 20;
	    private int endY = 70;
	    private int guessesLeft = 0;
	    
	    public TheHangMan() { //CONSTRUCTOR FOR THE HANGMAN
	        Circle head = new Circle(25);
	        head.setTranslateX(x);
	        head.setStroke(Color.CORAL);
	
	        Line rope2 = new Line();
	        rope2.setStartX(x);
	        rope2.setStartY(y -100);
	        
	        rope2.setEndX(x-100);
	        rope2.setEndY(y -150);
	        rope2.setStroke(Color.ROSYBROWN);
	        Line pillerBase = new Line();
	        
	        pillerBase.setStartX(x-150);
	        pillerBase.setStartY(y +100);
	        
	        pillerBase.setEndX(x-25);
	        pillerBase.setEndY(y +100);
	        pillerBase.setStroke(Color.ROSYBROWN);
	        
	        Line piller = new Line();
	        piller.setStroke(Color.ROSYBROWN);
	        piller.setStartX(x-100);
	        piller.setStartY(y -150);
	        
	        piller.setEndX(x-100);
	        piller.setEndY(y +100);   
	        
	        Line rope = new Line();
	        rope.setStroke(Color.DEEPPINK);
	
	        rope.setStartX(x);
	        rope.setStartY(y-40);
	        
	        rope.setEndX(x);
	        rope.setEndY(y -100);
	    
	        Line left = new Line();
	        
	        left.setStartX(x);
	        left.setStartY(y);
	        
	        left.setEndX(x + 60);
	        left.setEndY(y - 50);
	
	        Line right = new Line();
	        
	        right.setStartX(x);
	        right.setStartY(y);
	        
	        right.setEndX(x - 60);
	        right.setEndY(y - 50);
	        
	        Line bigBody = new Line();
	        
	        bigBody.setStartX(x);
	        bigBody.setStartY(y);
	        
	        bigBody.setEndX(x);
	        bigBody.setEndY(endY);
  
	        Line legTwo = new Line();
	        
	        legTwo.setStartX(x);
	        legTwo.setStartY(endY);
	        
	        legTwo.setEndX(x - 30);
	        legTwo.setEndY(endY + 100);
	
	        Line legOne = new Line();
	        
	        legOne.setStartX(x);
	        legOne.setStartY(endY);
	        
	        legOne.setEndX(x + 30);
	        legOne.setEndY(endY + 100);
	
	      
	        getChildren().addAll(pillerBase,piller,rope2,rope,head,bigBody,left,right,legOne,legTwo);
	        guessesLeft = (getChildren().size());
	    }
	    public int getGuesses() {
	    	return guessesLeft;
	    }
	    public void setGuesses(int x) {
	    	guessesLeft = x;
	    }
	     
	    public void dealDamange()  {
	        for (Node x :this.getChildren() ) {
	            if (!x.isVisible())  {
	       	
	               x.setVisible(true);
	               guessesLeft = guessesLeft -1;
	               break;
	         } 
	       } 
	    } 
	    public boolean alive() {
	    	if(guessesLeft==0)
	    		return false;
	    	else 
	    		return true;
	    }
	    
	    public void setNotVisible()  {
	    	for(Node x : this.getChildren()) {
	    		x.setVisible(false);
	    	}
	    	guessesLeft = this.getChildren().size();
	    }

}
