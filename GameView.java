// Author: Gabriel Granata, Nader El-Ghotmi
// Student number: 300057462, 300051343
// Course: ITI 1121
// Assignment: 3
// Question: 2
import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.io.*;


// your other import here if needed

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>GridButton</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

    private JLabel steps;
    private GameModel model;
    private JPanel grid;
    private JButton random;
    private JButton reset;
    private JButton quit;
    private JCheckBox solution;
    private JPanel buttons;
    private GridButton[][] square;
    private JFrame theFrame;
    private JPanel stepsLayout;
    


    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        
        setLayout(new BorderLayout());
        this.model = gameModel;
        solution = new JCheckBox("Solution");
        solution.addItemListener(gameController);
        reset = new JButton("Reset");
        reset.addActionListener(gameController);
        quit = new JButton("Quit");
        quit.addActionListener(gameController);
        random = new JButton("Random");
        random.addActionListener(gameController);

        grid = new JPanel();
        grid.setLayout(new GridLayout(model.getHeight(), model.getWidth(), 2, 2));

        steps = new JLabel("Number of steps: " + model.getNumberOfSteps());
        square = new GridButton[model.getHeight()][model.getWidth()];
        for (int i = 0; i<gameModel.getHeight(); i++){

          for (int j = 0; j<gameModel.getWidth(); j++){
            square[i][j] = new GridButton(j, i);
            square[i][j].setSize(1, 1);
            square[i][j].addActionListener(gameController);
            grid.add(square[i][j]);
          }
        }

        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.add(reset);
        buttons.add(random);
        buttons.add(solution);
        buttons.add(quit);

        stepsLayout = new JPanel();
        stepsLayout.setLayout(new BorderLayout());
        stepsLayout.add(steps, BorderLayout.CENTER);

        add(grid, BorderLayout.LINE_START);
        add(buttons, BorderLayout.LINE_END);
        add(stepsLayout, BorderLayout.PAGE_END);
        setSize(750, 375);
        setLocationRelativeTo(null);
        setTitle("LightsOut - The ITI1121 Version");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * updates the status of the board's GridButton instances based
     * on the current game model, then redraws the view
     */

    public void update() {
        
        for(int i = 0; i < model.getHeight(); i++) {
            for(int j = 0; j < model.getWidth(); j++) {
              try{
                if(solutionShown()){

                    square[i][j].setState(model.isON(i, j), model.solutionSelects(i,j));
                }
                else {
                    square[i][j].setState(model.isON(i, j), false);
                }
              } catch (FileNotFoundException e){
                  System.out.println("File not found");
              }

            
            }
        }
        steps.setText("Number of steps: " + model.getNumberOfSteps());

    }

    /**
     * returns true if the ``solution'' checkbox
     * is checked
     *
     * @return the status of the ``solution'' checkbox
     */

    public boolean solutionShown(){

        return solution.isSelected() == true;

    }

}
