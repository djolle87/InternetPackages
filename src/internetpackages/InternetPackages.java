package internetpackages;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InternetPackages extends Application{

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getClassLoader().getResource("view/FXML.fxml");
        URL icon = getClass().getClassLoader().getResource("resources/icon.png");
        AnchorPane root = FXMLLoader.<AnchorPane>load(fxmlUrl);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        primaryStage.getIcons().add(new Image(icon.toString()));
        primaryStage.setTitle("Internet Packages | High Speed Internet Provider");
        primaryStage.show();
    }
    
}
