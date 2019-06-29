// Author: Gabriel Granata, Nader El-Ghotmi
// Student number: 300057462, 300051343
// Course: ITI 1121
// Assignment: 3
// Question: 2

import java.util.ArrayList;


/**
 * The class <b>LightsOut</b> is the
 * class that implements the method to
 * computs solutions of the Lights Out game.
 * It contains the main of our application.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class LightsOut {

     /**
     * default width of the game.
     */
    public static final int DEFAULT_WIDTH = 3;
     /**
     * default height of the game.
     */
    public static final int DEFAULT_HEIGHT = 3;

    /**
     * The method <b>solve</b> finds all the
     * solutions to the <b>Lights Out</b> game
     * for an initially completely ``off'' board
     * of size <b>widthxheight</b>, using a
     * Breadth-First Search algorithm.
     *
     * It returns an <b>ArrayList&lt;Solution&gt;</b>
     * containing all the valid solutions to the
     * problem.
     *
     * This version does not continue exploring a
     * partial solution that is known to be
     * impossible. It will also attempt to complete
     * a solution as soon as possible
     *
     * During the computation of the solution, the
     * method prints out a message each time a new
     * solution  is found, along with the total time
     * it took (in milliseconds) to find that solution.
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     * @return
     *  an instance of <b>ArrayList&lt;Solution&gt;</b>
     * containing all the solutions
     */
    public static ArrayList<Solution> solve(int width, int height){

        Queue<Solution> q  = new QueueImplementation<Solution>();
        ArrayList<Solution> solutions  = new ArrayList<Solution>();

        q.enqueue(new Solution(width,height));
        long start = System.currentTimeMillis();
        while(!q.isEmpty()){
            Solution s  = q.dequeue();
            if(s.isReady()){
                // by construction, it is successfull
                System.out.println("Solution found in " + (System.currentTimeMillis()-start) + " ms" );
                solutions.add(s);
            } else {
                boolean withTrue = s.stillPossible(true);
                boolean withFalse = s.stillPossible(false);
                if(withTrue && withFalse) {
                    Solution s2 = new Solution(s);
                    s.setNext(true);
                    q.enqueue(s);
                    s2.setNext(false);
                    q.enqueue(s2);
                } else if (withTrue) {
                    s.setNext(true);
                    if(s.finish()){
                        q.enqueue(s);
                    }
                } else if (withFalse) {
                    s.setNext(false);
                    if( s.finish()){
                        q.enqueue(s);
                    }
                }
            }
        }
        return solutions;
    }


    public static ArrayList<Solution> solve(GameModel model){
        int width = model.getWidth();
        int height = model.getHeight();
        Queue<Solution> q  = new QueueImplementation<Solution>();
        ArrayList<Solution> solutions  = new ArrayList<Solution>();

        q.enqueue(new Solution(width,height));
        long start = System.currentTimeMillis();
        while(!q.isEmpty()){
            Solution s  = q.dequeue();
            if(s.isReady()){
                // by construction, it is successfull
                System.out.println("Solution found in " + (System.currentTimeMillis()-start) + " ms" );
                System.out.println(s);
                solutions.add(s);
            } else {
                boolean withTrue = s.stillPossible(true, model);
                boolean withFalse = s.stillPossible(false, model);
                if(withTrue && withFalse) {
                    Solution s2 = new Solution(s);
                    s.setNext(true);
                    q.enqueue(s);
                    s2.setNext(false);
                    q.enqueue(s2);
                } else if (withTrue) {
                    s.setNext(true);
                    if(s.finish(model)){
                        q.enqueue(s);
                    }
                } else if (withFalse) {
                    s.setNext(false);
                    if( s.finish(model)){
                        q.enqueue(s);
                    }
                }
            }
        }
        return solutions;
    }

    public static Solution solveShortest(GameModel model) {

      ArrayList<Solution> solList = solve(model);
      if(solList.isEmpty()){
        System.out.println("No solutions");
        return null;
      }
      Solution temp = solList.get(0);

          for(int i = 1; i<solList.size(); i++) {

              if(temp.getSize() > solList.get(i).getSize()) {

                temp = solList.get(i);
              }
          }
        return temp;
    }

    /**
     * <b>main</b> method  calls the method <b>solve</b>
     * and then prints out the number of solutions found,
     * as well as the details of each solution.
     *
     * The <b>width</b> and <b>height</b> used by the
     * main are passed as runtime parameters to
     * the program. If no runtime parameters are passed
     * to the program, or if the parameters are incorrect,
     * then the default values are used.
     *
     * @param args
     *  Strings array of runtime parameters
     */
    /*public static void main(String[] args) {


        int width   = DEFAULT_WIDTH;
        int height  = DEFAULT_HEIGHT;

        StudentInfo.display();

        if (args.length == 2) {

            try{
                width = Integer.parseInt(args[0]);
                if(width < 1) {
                    System.out.println("Invalid width, using default...");
                    width   = DEFAULT_WIDTH;
                }
                height = Integer.parseInt(args[1]);
                if(height < 1) {
                    System.out.println("Invalid height, using default...");
                    height  = DEFAULT_HEIGHT;
                }

            } catch(NumberFormatException e){
                System.out.println("Invalid argument, using default...");
                width   = DEFAULT_WIDTH;
                height  = DEFAULT_HEIGHT;
            }
        }
        ArrayList<Solution> results   = solve(width,height);
        for(int i =0; i < results.size(); i++){

            System.out.println("****");
            System.out.println(results.get(i));

        }
        System.out.println("In a board of "+ width + "x" + height +": " + results.size() + " solution" + (results.size() > 1 ? "s." : "."));

    }*/
   
    public static void main(String[] args) {

        int width=3;
        int height=3;

        StudentInfo.display();

        if(args.length == 2 && (Integer.valueOf(args[0]) >= 2 && Integer.valueOf(args[1]) >= 2)) {
            width = Integer.valueOf(args[0]);
            height = Integer.valueOf(args[1]);
        }

        else if (args.length == 1 && Integer.valueOf(args[0])>=2) {

            width = Integer.valueOf(args[0]);

        }
        GameController controller;
        controller = new GameController(width, height);
        
        
        
        
        
    }
}
