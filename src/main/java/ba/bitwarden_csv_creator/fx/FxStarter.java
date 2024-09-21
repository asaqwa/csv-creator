package ba.bitwarden_csv_creator.fx;

import ba.bitwarden_csv_creator.CsvCreator;
import ba.bitwarden_csv_creator.DataReader;
import ba.bitwarden_csv_creator.fx.view.FileProzessorController;
import ba.bitwarden_csv_creator.fx.view.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FxStarter extends Application {
    private Stage primaryStage;
    private Scene scene;
    private final FileChooser fileChooser = new FileChooser();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));

        try {
            Parent pane = loader.load();

            HomeController controller = loader.getController();
            controller.setFxStarter(this);

            scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File selectFile() {
        return fileChooser.showOpenDialog(primaryStage);
    }

    public File selectFileToSave() {
        return fileChooser.showSaveDialog(primaryStage);
    }

    public void showFileProzessor(File file) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fileProzessor.fxml"));
//        CsvCreator csvCreator = new CsvCreator(DataReader.readFromFile(file));
        file = new File("C:\\projects\\bitwarden.scv.creator\\src\\main\\resources\\ocr5\\ocr.txt");
        CsvCreator csvCreator = new CsvCreator(DataReader.readFromFile(file));
//        CsvCreator csvCreator = new CsvCreator(new byte[0]);
        try {
            Parent pane = loader.load();

            FileProzessorController controller = loader.getController();
            controller.initController(this, csvCreator);

            scene.setRoot(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
