package breaker;

import java.awt.Rectangle;

public class Block {
	int x = 0;
	int y = 0;
	int w = 0;
	int h = 0;
	int dur = 0; //dur = durability (life the block has)
	
	public Block(int X, int Y, int W, int H, int hits){
		x = X;
		y = Y;
		w = W;
		h = H;
		dur = hits;
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
	//get the amount of hits a block can take
	public int getHits(){
		return dur;
	}
	public void setX(int in){
		x = in;
	}
	
	
	public Rectangle getBounds(){
		return new Rectangle(this.x,this.y,this.w,this.h);
	}
	
	//set the amount of hits a block can take
	public void setHits(int in) {
		// TODO Auto-generated method stub
		dur = in;
	}
	
	//Check if the block should be drawn
	public boolean isActive(){
		if(dur>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Make the block inactive so it won't be drawn
	public void setInactive(){
		dur = 0;
	}
	
	public void setActive(){
		dur = 1;
	}
	
}

