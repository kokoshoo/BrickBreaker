package breaker;

import java.awt.Rectangle;

public class Player {
	int x = 0;
	int y = 0;
	int w = 0;
	int h = 0;
	int sp = 5;
	int lives = 0;
	int SCREENWID;
	int SCREENHEI;
	
	//Player travel direction, allows for smooth movement
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;
	
	public Player(int X, int Y, int W, int H, int s, int scrW, int scrH, int life){
		x = X;
		y = Y;
		w = W;
		h = H;
		sp = s;
		lives = life;
		SCREENWID = scrW;
		SCREENHEI = scrH;
	}
	
	public void getMovement(){
		if(x>0){
			if(left == true){
				x-=sp;
			}
		}
		if(x<(SCREENWID-w)){
			if(right == true){
				x+=sp;
			}
		}
		if(y > (SCREENHEI-(SCREENHEI/3.5))){
			if(up == true){
				y-=sp;
			}
		}
		if(y < (SCREENHEI - h)){
			if(down == true){
				y+=sp;
			}
		}
		
		
	}
	
	public int getH(){
		return h;
	}
	
	public int getW(){
		return w;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	//get paddle speed
	public int getSp(){
		return sp;
	}
	//check if the paddle is going left
	public boolean goingLeft(){
		return left;
	}
	//check if the paddle is going right
	public boolean goingRight(){
		return right;
	}
	
	public boolean goingUp(){
		return up;
	}
	
	public boolean goingDown(){
		return down;
	}
	
	//set value of going left
	public void setLeft(boolean in){
		left = in;
	}
	
	//set value of going right
	public void setRight(boolean in){
		right = in;
	}
	
	public void setUp(boolean in){
		up = in;
	}
	
	public void setDown(boolean in){
		down = in;
	}
	
	//get the right corner of the paddle (last 1/8th of it)
	public int rightCorner(){
		int rc = (int) (x + (w *.875));
		return rc;
	}
	//get the left corner of the paddle (first 1/8th of it)
	public int leftCorner(){
		int lc =(x + (w/8)); 
		return lc;
	}
	
	public int getLives(){
		return lives;
	}
	
	public void setLives(int in){
		lives = in;
	}
	
	public void LoseLife(){
		lives -=1;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(this.x,this.y,this.w,this.h);
	}

	public String getLivesString() {
		// TODO Auto-generated method stub
		String lifeString;
		lifeString = Integer.toString(lives);
		return lifeString;
	}
}
