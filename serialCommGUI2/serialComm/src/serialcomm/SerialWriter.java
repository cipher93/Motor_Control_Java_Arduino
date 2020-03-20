/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialcomm;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static serialcomm.FXMLDocumentController.sliderValue;
import static serialcomm.SerialComm.serialPort;

/**
 *This is the class that handles sending data over to Arduino
 * This class implements runnable so that we can use it as threads
 */


public class SerialWriter implements Runnable
    {
        /**
         * In this class, we will create dataOutputStream which will utilize outputStream
         * Data will be sent over to Arduino as strings, which the Arduino then will decode
         * to variables that will handle motor
         */


        /**Class Members:
        DataOutputStream: Stream that we will use to send data as strings
         OutputStream: This is the low level stream which will be used by DataOutputStream
         msg: This is the message that will be sent to Arduino
         */
        OutputStream outputStream = serialPort.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String msg = "";

        public void run ()
        {

            msg = "";
            if (FXMLDocumentController.startFlag) {
                //dataOutputStream.writeUTF("1");
                msg += '1';
            }

            else {
                // dataOutputStream.writeUTF("0");
                msg += '0';
            }
            msg += ',';
            
            if (FXMLDocumentController.directionFlag) {
                //dataOutputStream.writeUTF("3");
                msg += '1';
            }

            else {
                //dataOutputStream.writeUTF("4");
                msg += '0';
                          
                
            }
            
       msg += ',';
            //dataOutputStream.writeUTF(sliderValue.toString());
        
            
                msg +=String.format("%03d", sliderValue);
               
            try { 
                dataOutputStream.writeUTF(msg);
            }
            /**
             * If serial port gets disconnected, it will show
             * an error message telling the user that the port got disconnected
             * and the program will close
             */
            catch (IOException ex) {

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
                        alert.setContentText("Port Disconnected, exiting application");
                        alert.showAndWait();
                        Platform.exit();
                    }
                });
            }
                //System.out.println(msg);
        }
    }