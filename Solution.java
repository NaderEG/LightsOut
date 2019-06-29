// Author: Gabriel Granata, Nader El-Ghotmi
// Student number: 300057462, 300051343
// Course: ITI 1121
// Assignment: 3
// Question: 2
/**
 * The class <b>Solution</b> is used
 * to store a (partial) solution to the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class Solution {


    /**
     * our board. board[i][j] is true is in this
     * solution, the cell (j,i) is tapped
     */
    private boolean[][] board;

    /**
     *  width of the game
     */
    private int width;

    /**
     * height of the game
     */
    private int height;

    /**
     * how far along have we constructed that solution.
     * values range between 0 and height*width-1
     */
    private int currentIndex;



    /**
     * Constructor. Creates an instance of Solution
     * for a board of size <b>widthxheight</b>. That
     * solution does not have any board position
     * value explicitly specified yet.
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     */
    public Solution(int width, int height) {

        this.width = width;
        this.height = height;

        board = new boolean[height][width];
        currentIndex = 0;

    }

   /**
     * Constructor. Creates an instance of Solution
     * wich is a deep copy of the instance received
     * as parameter.
     *
     * @param other
     *  Instance of solution to deep-copy
     */
     public Solution(Solution other) {

        this.width = other.width;
        this.height = other.height;
        this.currentIndex = other.currentIndex;

        board = new boolean[height][width];

        for(int i = 0; i < currentIndex; i++){
            board[i/width][i%width] = other.board[i/width][i%width];
        }

    }


    /**
     * returns <b>true</b> if and only the parameter
     * <b>other</b> is referencing an instance of a
     * Solution which is the ``same'' as  this
     * instance of Solution (its board as the same
     * values and it is completed to the same degree)
     *
     * @param other
     *  referenced object to compare
     */

    public boolean equals(Object other){

        if(other == null) {
            return false;
        }
        if(this.getClass() != other.getClass()) {
            return false;
        }

        Solution otherSolution = (Solution) other;

        if(width != otherSolution.width ||
            height != otherSolution.height ||
            currentIndex != otherSolution.currentIndex) {
            return false;
        }

        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width; j++) {
                if(board[i][j] != otherSolution.board[i][j]){
                    return false;
                }
            }
        }

        return true;

    }


    /**
    * returns <b>true</b> if the solution
    * has been entirely specified
    *
    * @return
    * true if the solution is fully specified
    */
    public boolean isReady(){
        return currentIndex == width*height;
    }

    /**
    * specifies the ``next'' value of the
    * solution.
    * The first call to setNext specifies
    * the value of the board location (1,1),
    * the second call specifies the value
    *  of the board location (1,2) etc.
    *
    * If <b>setNext</b> is called more times
    * than there are positions on the board,
    * an error message is printed out and the
    * call is ignored.
    *
    * @param nextValue
    *  the boolean value of the next position
    *  of the solution
    */
    public void setNext(boolean nextValue) {

        if(currentIndex >= width*height) {
            System.out.println("Board already full");
            return;
        }
        board[currentIndex/width][currentIndex%width] = nextValue;
        currentIndex++;
    }

    /**
    * returns <b>true</b> if the solution is completely
    * specified and is indeed working, that is, if it
    * will bring a board of the specified dimensions
    * from being  entirely ``off'' to being  entirely
    * ``on''.
    *
    * @return
    *  true if the solution is completely specified
    * and works
    */
    public boolean isSuccessful(){

        if(currentIndex < width*height) {
            System.out.println("Board not finished");
            return false;
        }

        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width; j++) {
                if(!oddNeighborhood(i,j)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSuccessful(GameModel model){

      if (currentIndex < width*height){
        System.out.println("Board not finished");
        return false;
      }

      for (int i = 0; i < height; i++){
        for (int j = 0; j < width; j++){
          if (model.isON(i, j) && oddNeighborhood(i, j)){
            return false;
          }
          if (!model.isON(i, j) && !oddNeighborhood(i, j)){
            return false;
          }
        }
      }
      return true;

    }


   /**
    * this method ensure that add <b>nextValue</b> at the
    * currentIndex does not make the current solution
    * impossible. It assumes that the Solution was
    * built with a series of setNext on which
    * stillPossible was always true.
    * @param nextValue
    *         The boolean value to add at currentIndex
    * @return true if the board is not known to be
    * impossible (which does not mean that the board
    * is possible!)
    */
    public boolean stillPossible(boolean nextValue) {

        if(currentIndex >= width*height) {
            System.out.println("Board already full");
            return false;
        }

        int i = currentIndex/width;
        int j = currentIndex%width;
        boolean before = board[i][j];
        boolean possible = true;

        board[i][j] = nextValue;

        if((i > 0) && (!oddNeighborhood(i-1,j))){
            possible = false;
        }
        if(possible && (i == (height-1))) {
            if((j > 0) && (!oddNeighborhood(i,j-1))){
                possible = false;
            }
            if(possible && (j == (width-1))&& (!oddNeighborhood(i,j))){
                possible = false;
            }
        }
        board[i][j] = before;
        return possible;
    }

    public boolean stillPossible(boolean nextValue, GameModel model){

      if (currentIndex >= width * height){
        System.out.println("Board already full");
        return false;
      }
      int i = currentIndex/width;
      int j = currentIndex%width;
      if(i >0 && !model.isON(i-1, j)) {
          //checks if a value on the board will be even after set next leaves its range
          return (!oddNeighborhood(i-1, j) && nextValue) || (oddNeighborhood(i-1, j) && !nextValue);
      }
      else if(i>0 && model.isON(i-1, j)){
        return (oddNeighborhood(i-1, j) && nextValue) || (!oddNeighborhood(i-1, j) && !nextValue);
      }
      return true;
/*
      int i = currentIndex/width;
      int j = currentIndex%width;
      boolean before = board[i][j];
      boolean possible = true;

      board[i][j] = nextValue;

      if((i > 0) && (!model.isON(i-1, j))) {
        possible = stillPossible(nextValue);
      }
      else if ((i > 0 && i!= height-1) && (model.isON(i-1, j))) {
        possible = !oddNeighborhood(i-1, j);
      }
      else{
        if (j>0 && j!=width-1 && model.isON(i, j-1)){
          possible = !oddNeighborhood(i, j-1);
        }
        if (j == width -1 && model.isON(i, j)){
          possible = !oddNeighborhood(i, j);
        }
      }
      board[i][j] = before;
      return possible;
      */
    }


    /**
    * this method attempts to finish the board.
    * It assumes that the Solution was
    * built with a series of setNext on which
    * stillPossible was always true. It cannot
    * be called if the board can be extended
    * with both true and false and still be
    * possible.
    *
    * @return true if the board can be finished.
    * the board is also completed
    */
    public boolean finish(){


        int i = currentIndex/width;
        int j = currentIndex%width;

/*
        if(i == 0 && height > 1) {
            System.out.println("First line incomplete, can't proceed");
            return false;
        }
*/

        while(currentIndex < height*width) {
            if(i < height - 1 ) {
                setNext(!oddNeighborhood(i-1,j));
                i = currentIndex/width;
                j = currentIndex%width;
            } else { //last raw
                if(j == 0){
                    setNext(!oddNeighborhood(i-1,j));
                } else {
                   if((height > 1) && oddNeighborhood(i-1,j) != oddNeighborhood(i,j-1)){
                     return false;
                   }
                   setNext(!oddNeighborhood(i,j-1));
                }
                i = currentIndex/width;
                j = currentIndex%width;
            }
        }
        if(!oddNeighborhood(height-1,width-1)){
            return false;
        }
        // here we should return true because we could
        // successfully finish the board. However, as a
        // precaution, if someone called the method on
        // a board that was unfinishable before calling
        // the method, we do a complete check

        if(!isSuccessful()) {
            System.out.println("Warning, method called incorrectly");
            return false;
        }

        return true;

    }

    public boolean finish(GameModel model){
            //while the solution is not ready completes the solution and returns its succesfulness
        while(!this.isReady()) {

        	if(this.stillPossible(true, model)) {

        		this.setNext(true);

        	}
        	else{
        		this.setNext(false);
            	}
          }
        return this.isSuccessful(model);
      }

    public int getSize(){

      if (!isReady()){
        throw new IllegalStateException("Board not ready");
      }
      int size = 0;
      for (int i = 0; i < height; i++){
        for (int j = 0; j < width; j++){
          if (board[i][j]){
            size += 1;
          }
        }
      }
      return size;
    }

    /**
     * checks if board[i][j] and its neighborhood
     * have an odd number of values ``true''
     */

    private boolean oddNeighborhood(int i, int j) {

        if(i < 0 || i > height - 1 || j < 0 || j > width - 1) {
            return false;
        }

        int total = 0;
        if(board[i][j]){
            total++;
        }
        if((i > 0) && (board[i-1][j])) {
            total++;
        }
        if((i < height -1 ) && (board[i+1][j])) {
            total++;
        }
        if((j > 0) && (board[i][j-1])) {
            total++;
        }
        if((j < (width - 1)) && (board[i][j+1])) {
            total++;
        }

        return (total%2)== 1 ;
    }

    /**
    * @return
    returns true if the position at column i, row j
    is tapped in the solution, false otherwise */
    public boolean get(int i, int j){
      return board[i][j];
    }

    /*private boolean oddNeighborhood(int i, int j, GameModel model){

      if (i < 0 || i > height - 1 || j < 0 || j > width - 1){
        return false;
      }

      int total = 0;
      if (model.isON(i, j)){
        total++;
      }
      if((i > 0) && (model.isON(i-1, j))){
        total++;
      }
      if((i < height - 1) && (model.isON(i+1, j))){
        total++;
      }
      if((j > 0) && (model.isON(i, j-1))){
        total++;
      }
      if((j < (width - 1)) && (model.isON(i, j+1))){
        total++;
      }
      return (total%2) == 1;
    } */

    /**
     * returns a string representation of the solution
     *
     * @return
     *      the string representation
     */
    public String toString() {

      String arrayStr = "[";

      for (int i = 0; i<height; i++){
          for (int j = 0; j<width; j++){
              if (j==0){
                  arrayStr += "[";
              }
              if (i == height-1 && j == width-1){
                  arrayStr += (Boolean.toString(board[i][j]) + "]");
              } else if (j != width-1){
                  arrayStr += (Boolean.toString(board[i][j]) + ",");
              } else if (j==width-1 && i != height-1){
                  arrayStr += (Boolean.toString(board[i][j]) + "],\n");
              }
          }
      }
      arrayStr += "]";
      return arrayStr;

    }

}
