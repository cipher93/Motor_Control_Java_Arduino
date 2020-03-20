package serialcomm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This is the class which contains our main class
 * It starts by calling the port chooser screen
 * and after choosing the port, it will go on to the
 * motor controls
 */

/**
Steps of main:
 We will create a stage, and its scene will be the port chooser
 the stage will be irresizable because we want it so
 */
public class main extends Application {
    
    public static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLportchooser.fxml"));

        /**
         * These are the aesthetic properties of the stage like title and icon
        * */
        stage.setTitle("MotoGuru");
        stage.getIcons().add(new Image (getClass().getResourceAsStream("icon.jpg")));
        stage.setResizable(false);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

        /**This line is to pass the stage that is passed in the arguments to our static variable
         * Without this line, scene will not be able to change
        * */
        this.stage=stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
     
    
}
