package serialcomm;

import com.fazecast.jSerialComm.SerialPort;

/**
This class handles opening the serial port
 */

public class SerialComm {

    /**
     * This class has only one member which is the serialPort
     * It is defined static so that no exception will be thrown
     * in the try catch and in case we would like to add new static
     * methods to it
     */

    static SerialPort serialPort;

    /**
    This method is used to open a serial port with the name chosen by the user
     Baudrate is set to 9600 which is the default for our program
     */
    public static void connect(String comName)
    {
        char[] stringToChar = comName.toCharArray();
        System.out.println("Name is "+ (int)stringToChar[0]);

        if (comName!="") {
            try {

                serialPort = SerialPort.getCommPort(comName);
                serialPort.setBaudRate(9600);
                serialPort.openPort();
            } catch (Exception e) {
                //System.out.println("Serial Port Failed to Initialize");
            }
        }


    }
}
