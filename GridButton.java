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
public class GridButton extends JButton {

    private int column;
    private int row;
    private static int count = 0;


    /**
     * Constructor used for initializing a GridButton at a specific
     * Board location.
     *
     * @param column
     *            the column of this Cell
     * @param row
     *            the row of this Cell
     */

    public GridButton(int column, int row) {
        this.column = column;
        this.row = row;
        setActionCommand(Integer.toString(count));
        count++;
        
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

   /**
    * sets the icon of the button to reflect the
    * state specified by the parameters
    * @param isOn true if that location is ON
    * @param isClicked true if that location is
    * tapped in the model's current solution
    */
    public void setState(boolean isOn, boolean isClicked) throws FileNotFoundException{
        String icon="null";
        if(isOn && isClicked){

            icon = "Icons/Light-2.png";

        }
        else if(isOn && !isClicked) {

            icon = "Icons/Light-0.png";

        }
        else if(!isOn && isClicked) {

            icon = "Icons/Light-3.png";

        }
        else if(!isOn && !isClicked) {

            icon = "Icons/Light-1.png";

        }
        try{
            setIcon(new ImageIcon(icon));
        }
        catch(Exception e){
            System.out.println("File not found");
        }
    }



    /**
     * Getter method for the attribute row.
     *
     * @return the value of the attribute row
     */

    public int getRow() {
        return row;
    }

    /**
     * Getter method for the attribute column.
     *
     * @return the value of the attribute column
     */

    public int getColumn() {
        return column;
    }

    // YOUR OTHER METHODS HERE
}
