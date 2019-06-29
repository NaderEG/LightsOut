// Author: Gabriel Granata, Nader El-Ghotmi
// Student number: 300057462, 300051343
// Course: ITI 1121
// Assignment: 3
// Question: 2
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;

// YOUR OTHER IMPORTS HERE IF NEEDED

/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener, ItemListener {

    private int width;
    private int height;
    private GameModel model;
    private GameView view;
    private Object[] options;

    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     */
    public GameController(int width, int height) {

        options = new Object[2];
        options[0] = "Quit";
        options[1] = "Play Again";
        this.width = width;
        this.height = height;
        this.model = new GameModel(width, height);
        view = new GameView(model, this);
        view.update();
    }


    /**
     * Callback used when the user clicks a button (reset,
     * random or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {

        int buttonNum;
        int i = 0;
        int j = 0;
        int choice = 0;

        if(e.getActionCommand().equals("Reset")) {
          model.reset();
          view.update();
        }
        else if(e.getActionCommand().equals("Quit")) {
          System.exit(0);
          view.update();
        }
        else if(e.getActionCommand().equals("Random")) {
          model.randomize();
          view.update();
        }
        else{
          buttonNum = Integer.parseInt(e.getActionCommand());
          i = buttonNum/model.getWidth();
          j = buttonNum%model.getWidth();
          model.click(i, j);
          model.setSolution();
          view.update();
          if (model.isFinished()){
                    
                choice = JOptionPane.showOptionDialog(null, "Congratulations, you won in " + model.getNumberOfSteps() + " steps! Would you like to play again?", "You won!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (choice == 0){
                    System.exit(0);
                }
                else{
                    model.reset();
                    
                    view.update();
                 }
              }
        }
        
        
    }

    /**
     * Callback used when the user select/unselects
     * a checkbox
     *
     * @param e
     *            the ItemEvent
     */

    public void  itemStateChanged(ItemEvent e) {


        if (e.getStateChange()==ItemEvent.SELECTED){
            model.setSolution();
        }
        view.update();
        }



}
