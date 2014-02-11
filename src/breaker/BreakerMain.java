package breaker;

import java.applet.Applet;
import java.awt.*;

@SuppressWarnings("serial")
public class BreakerMain extends Applet implements Runnable{
	
	//keeps the game running
	Thread runner = null;
	
	//size of the game
	int appletW = 500;
	int appletH = 350;
	

		
	//ball config
	int BALLSIZE = 5;
	int BALLYSPD = 3;
	int ballXSPD = 2;
	int BALLXLOC = appletW/2;
	int BALLYLOC = appletH/2;
	int ballTimer; //counts when to start moving the ball
	Ball ball;
	
	//player config
	int PW = 60;
	int PH = 10;
	int PSPD = 5;
	int PXLOC = 230;
	int PYLOC = appletH-10;
	int playerLives = 3;
	Player player;
	

	Block block[];
	
	//levels
	Level level;
	int GAMELEVEL = 1;
	
	int score = 0;
	
	int finishCount;
	int restartTimer;
	
	int gameResetCounter;
	
	//power up timer
	int powerUpTimer;
	int SEC = 66;
	
	//initialize game
	public void init(){
		
		level = new Level(GAMELEVEL);
		block = level.getLevel();
		
		this.setSize(appletW,appletH);
		
		//put in config of player
		player = new Player(PXLOC,PYLOC,PW,PH,PSPD,appletW,appletH,playerLives);
		//put in config of ball
		ball = new Ball(BALLXLOC,BALLYLOC,BALLSIZE,BALLSIZE,ballXSPD,BALLYSPD);
		restartTimer = (SEC*3); //3 seconds

		//set focus on the game when run, so no need to click on it
		this.setFocusable(isEnabled());
		
		this.addKeyListener(new MoveListener(player));
		
	}
	
	
	//start the game
	public void start(){
		if(runner == null){
			runner = new Thread(this);
			runner.start();
		}
	}
	
	public void nextLevel(){
		playerLives++;
		GAMELEVEL++;
		ballTimer = 0;
		score +=150;
		init();
	}
	

	
	//when players loses, start over
	public void reset(){
		gameResetCounter = 0;
		ballTimer = 0;
		playerLives = 3;
		score = 0;
		GAMELEVEL = 1;
		init();
	}
	
	//stop the game for any reason
	public void stop(){
		if(runner != null){
		runner = null;
		}
		reset();
	}
	
	public void paint(Graphics g){
		//paint background
		g.setColor(Color.white);
		g.fillRect(0, 0, appletW, appletH);
		
		//paint player
		g.setColor(Color.blue);
		g.fillRect(player.getX(), player.getY(), player.getW(), player.getH());
		
		//write player lives
		g.setColor(Color.red);
		g.setFont(new Font("monospaced", Font.BOLD,20));
		g.drawString("Lives: " + player.getLivesString(),5,20);
		
		//write player score
		g.setColor(Color.orange);
		g.setFont(new Font("monospaced", Font.BOLD, 20));
		g.drawString("Score: " + Integer.toString(score), appletW/2-60, 20);
		
		//write current level
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("monospaced", Font.BOLD, 20));
		g.drawString("Level: " + Integer.toString(GAMELEVEL) , appletW-120, 20);
		
		//write current level
		
		//paint the blocks that haven't been hit
		for(int i = 0; i < level.getBlockCount(); i++){
			if(block[i].isActive()){
				g.setColor(Color.ORANGE);
				g.fillRect(block[i].getX(),block[i].getY(),block[i].getW(),block[i].getH());
				g.setColor(Color.white);
				g.drawRect(block[i].getX(),block[i].getY(),block[i].getW(),block[i].getH());
			}

		}
		
		//paint the ball black
		g.setColor(Color.black);
		g.fillRect(ball.getX(), ball.getY(), ball.getW(), ball.getH());
		
		
		//Game Over
		if(player.getLives() == 0){
			g.setColor(Color.RED);
			g.setFont(new Font("monospaced",Font.BOLD,100));
			g.drawString("GameOver", appletW/50, appletH/2);
			gameResetCounter++;
			if(gameResetCounter == 300){
				reset();
			}
		}
	}
	

	

	
	public void checkCollision(){

		/*~~~~~~~~~~~~~~~~~~~~~ Paddle Collision Start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		/*
		Check for the direction the ball is going to change the behavior of the bounce...
		When the ball and player are going in the same direction, increase ball speed.
		Depending on the balls direction and area of collision on the paddle, 
		it may reverse direction.
		*/
		if(ball.getBounds().intersects(player.getBounds())){
			//If the ball speed is going up, don't allow it to hit the paddle again (gets buggy)
			if(ball.ysp > 0){
				
					//if ball is going left
					if(!ball.goingRight()){
						//ball hits left corner of paddle, speed up
						if(ball.getCenter() < player.leftCorner()){
							ball.speedUp();
						
						}
						//ball hits right corner of paddle, reverse
						else if(ball.getCenter() > player.rightCorner()){
							ball.reverseX();
							ball.slowDown(.3);
						}
						else{
							//player is going right, ball left, slow the ball down
							if(player.goingRight()){
								ball.slowDown();
								System.out.println("slowing down");
							}
							//player is going left, ball left, speed the ball up
							else if(player.goingLeft()){
								ball.speedUp();
							}
							//player is stationary
							else{
								//nothing but keep ideas in mind
							}
						
						}
					}
					//ball is going right
					else{
						//ball hits left corner, reverse
						if(ball.getCenter() < player.leftCorner()){
							ball.reverseX();
							ball.slowDown(.3);
						}	
						//ball hits right corner, speed up
						else if(ball.getCenter() > player.rightCorner()){
							ball.speedUp();
							System.out.println("speeding up");
						}
						else{
							//player is moving left, ball right, slow the ball down
							if(player.goingLeft()){
								ball.slowDown();
								System.out.println("slowing down");
							}
							//player is moving left, speed the ball up
							else if(player.goingRight()){
								//nothing
								ball.speedUp();
							}
							//player is stationary
							else{
								//nothing but keep ideas in mind
							}
						}
				}
				//make ball bounce up
				ball.reverseY();
			}
		}
		/*~~~~~~~~~~~~~~~~~~~~~Paddle Collision Finish~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		
		/*~~~~~~~~~~~~~~~~~~~~~ Brick Collision Start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		//do work, bounces weirdly off the bricks
		for(int i = 0; i < level.getBlockCount(); i++){
			if(ball.getBounds().intersects(block[i].getBounds())){
				if(block[i].isActive()){
					if((ball.getY()+(ball.getH()/2)) <= (block[i].getY()+block[i].getH()-1)
						&& ((ball.getY()+(ball.getH()/2)) >= block[i].getY()+1) &&
						
						((ball.getX()+ball.getW() <= block[i].getX()) ||
						  (ball.getX() <= block[i].getX()+block[i].getW()))){
						System.out.println("loop 1");
						ball.reverseX();
					}
					else{
						ball.reverseY();
					}
					block[i].setHits(block[i].getHits()-1);
					finishCount++;
					score+=10;
				}
				else{
				}
				
			}
		}
		/*~~~~~~~~~~~~~~~~~~~~~ Brick Collision Finish~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
		
		//bouncing ball of the walls
		if(ball.getX() < 0){
			ball.reverseX();
		}
		if(ball.getY() < 0){
			ball.reverseY();
		}
		if(ball.getX() > (appletW-ball.getW())){
			ball.reverseX();
		}
		//if the ball passes the paddle, reset
		if(ball.getY() > (appletH+300)){
			player.LoseLife();
			ball.reset(appletW/2, appletH/2,ballXSPD);
			ballTimer = 0;
		}
	}
	


	public void update(Graphics g){
		//double buffering, use second image to relay the images properly so no image is left behind
		Graphics gc;
		Image offscreen = null;

		@SuppressWarnings("deprecation")
		Dimension d = size();
		
		offscreen = createImage(d.width,d.height);
		gc = offscreen.getGraphics();
		gc.setColor(getBackground());
		gc.fillRect(0, 0, d.width, d.height);
		gc.setColor(getForeground());
		paint(gc);
		g.drawImage(offscreen,0,0,this);
	}
	
	public void update(){
		repaint();
		player.getMovement();
		
		if(ballTimer>200){  //ball starts after set time
			ball.move();
		}
		else{
			ballTimer++;
		}
		checkCollision();
		
		//No more blocks, next level
			if(level.isFinished()){
				nextLevel();
			}

		
		
		//supposed to reset but doesnt, do work
		if(player.getLives() == 0){
			ballTimer = 0;
			while(ballTimer < 200){
				ballTimer++;
			}
			if(ballTimer>200){
				reset();	
			}
		}
	}

	@Override
	public void run(){
		// TODO Auto-generated method stub
		//int counter = 0;
		while(runner!=null){
			update();
			try {
				Thread.sleep(18);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
