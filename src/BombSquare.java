import java.util.Random;

public class BombSquare extends GameSquare {
	private boolean thisSquareHasBomb;
	private boolean beenChecked = false;
	private int numOfBombs;
	public static final int MINE_PROBABILITY = 10;
	public static final int MAX_BOMBS = 8;
	public static final int MIN_BOMBS = 0;


	public BombSquare(int x, int y, GameBoard board) {
		super(x, y, "images/blank.png", board);
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
		numOfBombs = 0;
	}

	public boolean squareExists(int x, int y, GameBoard board){
		if (board.getSquareAt(x, y) != null){
			return true;
		}
		else{
			return false;
		}
	}

	public int getNumOfBombs() {
		return numOfBombs;
	}

	public void clicked() {
		if (thisSquareHasBomb) {
			setImage("images/bomb.png");
		} else {
			outputBombCount();
		}
	}

	public int countAdjacentBombs() {
		numOfBombs = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int adjX = xLocation + j;
				int adjY = yLocation + i;

				if (squareExists(adjX, adjY, this.board)) {
					BombSquare adjSquare = (BombSquare) board.getSquareAt(adjX, adjY);
					if (adjSquare.hasBomb()) {
						numOfBombs = numOfBombs + 1;
					}
				}
			}
		}
		return numOfBombs;
	}

	public Boolean hasBomb() {
		return thisSquareHasBomb;
	}

	public Boolean hasSet() {
		return beenChecked;
	}

	public void outputBombCount() {
		numOfBombs = countAdjacentBombs();
		if (numOfBombs > MAX_BOMBS && numOfBombs < MIN_BOMBS){
			//error check for invalid number of bombs
			System.out.println("This is an invalid number of surrounding bombs");
		}
		else {
			setImage("images/" + numOfBombs + ".png");
			if (numOfBombs < 1) {	//already done a bounds check. checks if there are no surrounding bombs
				clear3x3();			//recursive call
			}
		}
	}

	public void setbeenChecked() {
		this.beenChecked = true;
	}


	public void clear3x3() {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int adjX = xLocation + j;
				int adjY = yLocation + i;

				
				if (squareExists(adjX, adjY, this.board)) {
					BombSquare adjSquare = (BombSquare) board.getSquareAt(adjX, adjY);

					if (!adjSquare.hasSet()) {
						adjSquare.setbeenChecked();
						adjSquare.outputBombCount();
					}
				}

			}
		}
	}
}
