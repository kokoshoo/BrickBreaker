package breaker;

import java.util.Random;

public class Level {
	

	
	//block config
	Block[] block;
	int BLOCKW = 35; //width of block
	int BLOCKH = 10; //height of block
	int BLOCKYSTART = 25; 
	int BLOCKXSTART;
	int BLOCKDUR = 0; //how many hits a block can take/default 0 for filling in
	int totalW; //Width range of the current block set
	float rowNum; //Number of rows for the current block set
	int MAXCOL = 14; //default amount of blocks per column
	int MAXROW = 14; //default amount of blocks per row
	int BLOCKCT = MAXCOL * MAXROW;
	
	int appletW = 500;
	
	int level;
	
	public Level(int lev){
		level = lev;
		outlineLevel();
		
		//block[MAXCOL*i+column].setActive();
		
	}
	
	//Set max row and column for the level
	public void outlineLevel(){
		if(level == 1){
			MAXROW = 10;
			MAXCOL = 14;
		}
		
		else if(level == 2){
			MAXROW = 8;
			MAXCOL = 8;
		}
		else{
			Random ran = new Random();
			MAXROW = ran.nextInt(5)+9;
			MAXCOL = ran.nextInt(5)+9;
		}
		
		BLOCKCT = MAXCOL * MAXROW;

		block = new Block[BLOCKCT];
		
	}
	

	private void drawLevel(int level) {
		// TODO Auto-generated method stub
		if(level == 1){
			drawRowSection(2, 1, MAXROW/2-1);
			drawColSection(0, 1, 4);
			drawColSection(MAXROW-1,1,4);
			drawRow(MAXCOL-1);
			
		}
		else if(level == 2){
			drawCol(0);
			drawCol(MAXCOL-1);
			drawRow(MAXROW/2+1);
			drawRow(MAXROW/2-2);
			drawRow(MAXROW-1);
			drawRow(0);
		}
		//Draw a random level
		else{
			if(level%2==0){
				Random ran = new Random();
				ran.setSeed(System.currentTimeMillis() % 1000);
				int sequence = ran.nextInt((int) (System.currentTimeMillis() % 5)+1)+4;
				
				int count = 0;
				
				while(count < BLOCKCT){
					
					if(ran.nextBoolean()){
						for(int i = 0; i < sequence; i++){
							if(count >= BLOCKCT){
								break;
							}
							block[count].setActive();
							count++;
						}
					}
					else{
						count++;
					}
				}
				for(int i = 0; i < 3;i++){
					//if(ran.nextBoolean()){
					//System.out.println(new Random().nextInt(3)+MAXROW-1);
						eraseColumn(new Random().nextInt(2)+MAXROW-2);
					//}
					if(ran.nextBoolean()){
						eraseRow(new Random().nextInt(2)+MAXCOL-2);
					}
				}
			}
			else{
				Random ran = new Random();
				for(int i = 0; i < ran.nextInt((int) (System.currentTimeMillis() % 5)+1)+4;i++){
					int ranCol = (ran.nextInt((int) (System.currentTimeMillis() % 5)+1)+4)%MAXROW;
					int ranColSpan = ran.nextInt(MAXROW/2)+MAXCOL/2;
					int ranRow = (ran.nextInt((int) (System.currentTimeMillis() % 5)+1)+4)%MAXCOL;
					int ranRowSpan = ran.nextInt(MAXCOL/2)+MAXROW/2;
					
					drawColSection(ranCol,1,ranColSpan);
					drawRowSection(ranRow,1,ranRowSpan);
				}
				
				
				//drawColSection(int column, int section, int amount)
				
			}
			
			//drawRec(2,2,10);
		}
		
		
	}


	public Block[] getLevel(){
		
		//get the amount of rows with the given # of blocks
		if(BLOCKCT <= MAXROW){
			totalW = BLOCKW * BLOCKCT;
		}
		else{
			rowNum = BLOCKCT/MAXROW;
			Math.ceil(rowNum);
			totalW = MAXROW*BLOCKW;
		}

		int BLOCKXSTART = (appletW - totalW)/2;
		
		//give each block its location and setting
		for(int i = 0; i < BLOCKCT; i++){
			if(i%MAXROW == 0 && i>0){
				BLOCKYSTART+=BLOCKH;
				BLOCKXSTART -= (totalW);
			}
			block[i] = new Block(BLOCKXSTART,BLOCKYSTART,BLOCKW,BLOCKH,BLOCKDUR);
			BLOCKXSTART+=BLOCKW;
		}
		
	    //Can now draw levels by activating blocks
		drawLevel(level);
		
		return block;
	}
	
	
	public void eraseRow(int row){
		for(int i = 0; i < MAXROW; i++){
			block[row*MAXROW+i].setInactive();
		}
	}
	
	
	public void eraseColumn(int column){
		for(int i = 0; i < MAXCOL; i++){
			block[MAXROW*i+column].setInactive();
		}
	}
	
	public void drawRow(int row){
		for(int i = 0; i < MAXROW; i++){
			block[row*MAXROW+i].setActive();
		}
	}
	
	//Section: 0 = front, 1 = middle, 2 = back
	public void drawRowSection(int row, int section, int amount){
		if(section == 0){
			for(int i = 0; i < amount; i++){
				block[row*MAXROW+i].setActive();
			}
		}
		else if(section == 1){
			int start = (int) ((Math.ceil(MAXROW/2))-(Math.ceil(amount/2)));
			int end = start+amount;
			System.out.print(start);
			for(int i = 0; i < amount;i++){
				block[(row*MAXROW+i)+start].setActive();
			}
		}
		else if(section == 2){
			//Avoid null errors
			if(amount < MAXROW)
				for(int i = MAXROW-1; i > MAXROW-amount-1;i--){
					block[row*MAXROW+i].setActive();
				}
		}
	}
	
	
	public void drawCol(int column){
		for(int i = 0; i < MAXCOL; i++){
			block[MAXROW*i+(column%MAXROW)].setActive();
			//block[i*column].setActive();
		}
	}
	
	//Section: 0 = top, 1 = middle, 2 = bottom
	public void drawColSection(int column, int section, int amount){
		if(section == 0){
			for(int i = 0; i < amount; i++){
				block[MAXROW*i+(column%MAXROW)].setActive();
			}
		}
		else if(section == 1){
			int start = (int) (MAXROW*(Math.ceil(MAXCOL/2)));
			start -= (amount*MAXROW)/2;
			
			//int end = start+amount;
			for(int i = 0; i < amount; i++){
				System.out.println("COL: "+ column + " AMOUNT: "+ amount);
				System.out.println("MAXROW: "+ MAXROW + " MAXCOL: "+ MAXCOL);
				block[(MAXROW*i+start)+column].setActive();
			}
		}
		else if(section == 2){
			for(int i = 0; i < amount;i++){
				block[(BLOCKCT-(MAXROW-column))-(i*MAXROW)].setActive();
			}
		}
		else{
			
		}
	}
	
	public void drawRec(int width,int height, int startPos){
		
		if(width + (startPos%MAXROW) > MAXROW){
			if(height + (startPos%MAXCOL) > MAXCOL){
				for(int i = 0; i < height; i++){
					for(int j = 0; j < width; j++){
						block[(startPos-j)-(i*MAXROW)].setActive();
					}
				}
			}
			else{
				for(int i = 0; i < height; i++){
					for(int j = 0; j < width; j++){
						block[(startPos-j)+(i*MAXROW)].setActive();
					}
				}
			}
		}
		else{
			System.out.println("Else");
			if(height + (startPos%MAXCOL) > MAXCOL){
				//System.out.println("Else");
				for(int i = 0; i < height; i++){
					for(int j = 0; j < width; j++){
						block[(startPos+j)-(i*MAXROW)].setActive();
					}
				}
			}
			else{
				System.out.println(height + (startPos%MAXCOL));
				for(int i = 0; i < height; i++){
					for(int j = 0; j < width; j++){
						block[(startPos+j)+(i*MAXROW)].setActive();
					}
				}
			}
		}
	}
	
	
	public int getBlockCount(){
		return BLOCKCT;
	}
	
	//Check if the level is finished
	public boolean isFinished() {
		// TODO Auto-generated method stub
		boolean finished = true;
		for(int i = 0; i < BLOCKCT; i++){
			if(block[i].isActive()){
				finished = false;
			}
		}
		return finished;
	}
	
}
