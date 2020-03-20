package serialcomm;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author basma
 * This class handles GUI for choosing port
 * It has a dropdown list which populates the ports and confirmation button
 */
public class FXMLportchooserController implements Initializable {

    /**
    In this class we have two objects:
    connectPortButton: On clicking this button, the chosen port will be opened
    postsList: It will display a list of available ports to choose from

     */
    @FXML
    private Button connectPortButton;
    @FXML
    private ComboBox portsList;

    private SerialPort[] serialPorts;


    /**
    connectPort event is the event that fires upon clicking on the connectPortButton
    It will open a port according to the chosen port from portsList
     */
    @FXML
    private void connectPort(ActionEvent event) {


        try
        {
            /**
            Here the program will try to open port according to the value chosen from the list
            It will compare the value in the list to the values of serial ports to know its real name
            then add its real name to connect method
             */

            String chosenPortDescriptiveName = portsList.getValue()+"";
            String chosenPortName="";

            /**
             * This is the for loop that will do the mentioned comparison above
             */
            for(int i=0;i<serialPorts.length;i++)
            {
                if(chosenPortDescriptiveName.equals(serialPorts[i].getDescriptivePortName()))
                {
                    chosenPortName = serialPorts[i].getSystemPortName();
                }
            }

            /**Now that the comparison is complete, open serial port
            **/

            SerialComm.connect(chosenPortName);

            /**
             * After Serial port is opened, open the GUI Controls for the motor
             */

            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Scene scene = new Scene(root);

                main.stage.setScene(scene);
                main.stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLportchooserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /**
        *If serial Port couldn't be open, show a window to the user telling
         * him that the port couldn't be opened
         * This window will be modal, meaning that it must be closed first
         * before user can continue using the program again
        */
        catch ( Exception e )
        {

            /**
             * Here, the error message is wrapped in Platform.runlater so that it
             * stops the current thread and launches this thread. This is done to
             * prevent throwing exceptions when making another thread through creating
             * stages
             */

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Couldn't open serial port");
                    alert.showAndWait();
                }
            });
        }

    }

    /**This event handler fires on clicking on the dropdown list
     it asks the system to return a list of items by their descriptive names
     so that user can choose Arduino Easily
     */
    @FXML
    private void populateList (MouseEvent e)
    {
        /**
         * First, we will remove all the items that were present
         * in the list before. This will be done by nullifying the
         * serialPorts list and clearing the dropdown menu
        * */
        serialPorts=null;
        serialPorts = SerialPort.getCommPorts();
        portsList.getItems().clear();

        for (int i=0;i<serialPorts.length;i++) {
            //System.out.println(serialPorts[i]);
            portsList.getItems().add(serialPorts[i].getDescriptivePortName());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
