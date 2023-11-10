import java.util.Random;

public class BombSquare extends GameSquare {
	/**
	 * Class for squares with bombs
	 * @author Joseph James
	 */
	private boolean thisSquareHasBomb;
	private boolean beenChecked = false;
	private int numOfBombs;
	public static final int MINE_PROBABILITY = 10;
	public static final int MAX_BOMBS = 8;
	public static final int MIN_BOMBS = 0;


	/**
	 * @param x x-axis position on GameBoard
	 * @param y y-axis position on GameBoard
	 * @param board GameBoard on which the grid appears
	 */
	public BombSquare(int x, int y, GameBoard board) {
		super(x, y, "images/blank.png", board);
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
		numOfBombs = 0;
	}

	/**
	 * @param x x-axis position on GameBoard
	 * @param y y-axis position on GameBoard
	 * @param board GameBoard on which the grid appears
	 * @return returns true or false
	 */

	public boolean squareExists(int x, int y, GameBoard board){
		if (board.getSquareAt(x, y) != null){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * @return returns number of bombs
	 */
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

	/**
	 * @return returns number of bombs in the surrounding 3x3
	 */
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

	/**
	 * @return returns if the current square has bomb
	 */
	public Boolean hasBomb() {
		return thisSquareHasBomb;
	}

	/**
	 * @return has the current bomb been checked for containing a bomb
	 */
	public Boolean hasSet() {
		return beenChecked;
	}

	/**
	 * adds the number squares to the board
	 */
	public void outputBombCount() {
		numOfBombs = countAdjacentBombs();
		if (numOfBombs > MAX_BOMBS && numOfBombs < MIN_BOMBS){
			System.out.println("This is an invalid number of surrounding bombs");
		}
		else {
			setImage("images/" + numOfBombs + ".png");
			if (numOfBombs < 1) {
				clear3x3();
			}
		}
	}

	/**
	 * set current square to have been checked
	 */
	public void setbeenChecked() {
		this.beenChecked = true;
	}

	/**
	 * clears a 3x3 around the current square
	 */
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
