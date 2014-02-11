package breaker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MoveListener implements KeyListener{

	Player pl;
	
	public MoveListener(Player p){
	pl = p;
	
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_LEFT){
			pl.setLeft(true);
		}
		if(key == KeyEvent.VK_RIGHT){
			pl.setRight(true);	
		}
		if(key == KeyEvent.VK_UP){
			pl.setUp(true);
		}
		if(key == KeyEvent.VK_DOWN){
			pl.setDown(true);
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_LEFT){
			pl.setLeft(false);
		}
		if(key == KeyEvent.VK_RIGHT){
			pl.setRight(false);
		}
		if(key == KeyEvent.VK_UP){
			pl.setUp(false);
		}
		if(key == KeyEvent.VK_DOWN){
			pl.setDown(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
