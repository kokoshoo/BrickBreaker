package breaker;

import java.awt.Rectangle;

public class Ball {
	int x = 0;
	int y = 0;
	int w = 0;
	int h = 0;
	int ysp = 5;
	int xsp = 3;
	//for powerup
	boolean powered = false;
	
	public Ball(int X, int Y, int W, int H,int xs, int ys){
		x = X;
		y = Y;
		w = W;
		h = H;
		ysp = ys;
		xsp = xs;
	}
	//move the ball with the current xsp and ysp
	public void move(){
		x+=xsp;
		y+=ysp;
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
	//get speed at which ball goes left and right
	public int getXSp(){
		return xsp;
	}
	//get speed at which ball goes up and down
	public int getYSP(){
		return ysp;
	}
	//set speed at which ball goes left and right
	public void setXsp(int in){
		xsp = in;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,w,h);
	}
	//speed at which ball goes up and down
	public void setYsp(int in) {
		// TODO Auto-generated method stub
		ysp = in;
		
	}
	
	//bounce x axis
	public void reverseX(){
		xsp = -xsp;
	}
	
	//bounce y axis
	public void reverseY(){
		ysp = -ysp;
	}
	
	//speed the ball up going left or right
	public void speedUp(){
		if(xsp < 0){
			if(xsp > -5){
				xsp--;
			}
		}
		else{
			if(xsp < 5){
				xsp++;
			}
		}
	}
	
	
	//slow the ball down going left or right
	public void slowDown(){
		if(xsp>0){
			xsp = 2;
		}
		else{
			xsp = -2;
		}
	}
	
	public void slowDown(double amount){
		if(xsp>0){
			xsp-=amount;
		}
		else{
			xsp+=amount;
		}
	}
	
	//check if ball is going right
	public boolean goingRight(){
		if(xsp < 0){
			return false;
		}
		else{
			return true;
		}
	}
	

	public void setpower(boolean bool){
		powered = bool;
	}
	
	public boolean isPowerUp(){
		return powered;
	}
	public void setX(int i) {
		// TODO Auto-generated method stub
		x = i;
	}
	public void setY(int i) {
		// TODO Auto-generated method stub
		y = i;
	}
	public void reset(int xIn, int yIn, int spdIn) {
		// TODO Auto-generated method stub
		x = xIn;
		y = yIn;
		xsp = spdIn;
	}
	public int getCenter() {
		// TODO Auto-generated method stub
		return x+(w/2);
	}
	
}
