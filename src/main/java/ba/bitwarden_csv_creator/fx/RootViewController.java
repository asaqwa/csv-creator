package ba.bitwarden_csv_creator.fx;

import ba.bitwarden_csv_creator.fx.view.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RootViewController {
    private final FileChooser fileChooser = new FileChooser();
    private final Stage primaryStage;

    public RootViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public File selectFile() {
        return fileChooser.showOpenDialog(primaryStage);
    }

    public void showHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ba.util.fxml.home.fxml"));

//        HomeController controller = loader.getController();
//        controller.setRootController(this);

        try {
            Parent pane = loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showOpenFile(File file) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml.home.fxml"));

//        HomeController controller = loader.getController();
//        controller.setRootController(this);

        try {
            Parent pane = loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
