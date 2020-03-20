/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialcomm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
/**
 *
 * @author atheer pc
 * This class handles what will appear on GUI after choosing port
 * Motor controls are on/off, speed control and direction control
 */
public class FXMLDocumentController implements Initializable {
    
    public static boolean startFlag = false;
    public static boolean directionFlag=true;
    public static Integer sliderValue = 0;
    public int rotateTest=0;
    Thread thread;
    Image onPNG;
    Image offPNG;
    Image clockwiseGIF;
    Image counteclockwiseGIF;
    @FXML
    private ImageView directionGIF;
    @FXML
    private Label onOffCondition;
    @FXML
    private Label directionCondtion;
    @FXML
    private Label speedValue;
    @FXML
    private  Button startButton;
    @FXML
    private RadioButton counterclockwise;
    @FXML
    private RadioButton clockwise;
    @FXML
    private Slider speedSlider;

    /**
     * This is the event that fires when clicking on the on/off button
     * It toggles the button graphic between on and off, and starts a new
     * thread of SerialWriter
     */

    @FXML
    private void handleButtonAction(ActionEvent event) {
        /**
         * We will change startFlag and its return value will determine whether
         * the motor will be on or off
         */


        if(startFlag=!startFlag){

            thread = new Thread(new SerialWriter());
            thread.start();
            startButton.setGraphic(new ImageView(onPNG));
        }
        else{
            thread = new Thread(new SerialWriter());
            thread.start();
            startButton.setGraphic(new ImageView(offPNG));
        }
    
    }

    /**
     *This is the event that fires when clicking on the clockwise rotation button
     * It changes the graphics that indicate the motor rotation
     * and sends data over serialWriter
     */

    @FXML
    private void handleClockwiseRadioButtonAction(ActionEvent event) {
          //directionCondtion.setText("    Clockwise Direction");
          directionFlag=true;
          directionGIF.setImage(clockwiseGIF);
          //directionGIF.setRotate(rotateTest+=10);
          thread = new Thread(new SerialWriter());
          thread.start();
    
    }

    /**
     *This is the event that fires when clicking on the anticlockwise rotation button
     * It changes the graphics that indicate the motor rotation
     * and sends data over serialWriter
     */
     @FXML
    private void handleAntiClockwiseRadioButtonAction(ActionEvent event) {
          //directionCondtion.setText(" Anticlockwise Direction");
          directionFlag=false;
          directionGIF.setImage(counteclockwiseGIF);
          //directionGIF.setRotate(rotateTest+=10);
          thread = new Thread(new SerialWriter());
          thread.start();
    
    }

    /** This is the event that fires upon changing motor speed through slider values
     * It sends value to motor and displays it on the GUI
     */
    @FXML
    public void onSliderChanged() {
    sliderValue = (int) speedSlider.getValue();
    speedValue.setText(" Motor speed is "+sliderValue.toString());
    System.out.println(sliderValue + " ");
    thread = new Thread(new SerialWriter());
    thread.start();
}


    /**
     * This method runs at the initialization of the GUI
     * It loads the graphics that will be used in the file
     *like the pictures that show status of motor
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onPNG = new Image(getClass().getResourceAsStream("on.png"));
        offPNG = new Image(getClass().getResourceAsStream("off.png"));
        clockwiseGIF = new Image(getClass().getResourceAsStream("clockwise.gif"));
        counteclockwiseGIF = new Image(getClass().getResourceAsStream("counterclockwise.gif"));
    }    
    
}
