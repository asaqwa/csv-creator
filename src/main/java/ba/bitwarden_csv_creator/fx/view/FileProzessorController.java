package ba.bitwarden_csv_creator.fx.view;

import ba.bitwarden_csv_creator.CsvCreator;
import ba.bitwarden_csv_creator.fx.FxBlockManager;
import ba.bitwarden_csv_creator.fx.FxStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileProzessorController {
    private FxStarter fxStarter;
    private CsvCreator csvCreator;
    private FxBlockManager blockManager;

    @FXML
    private Label entriesQty;

    @FXML
    private VBox blockBox;

    public void initController(FxStarter fxStarter, CsvCreator csvCreator) {
        this.fxStarter = fxStarter;
        this.csvCreator = csvCreator;
        csvCreator.parseBlocks();
        blockManager = new FxBlockManager(csvCreator, this);
        updateBlocks();

//        GridPane.setHgrow(testVBox, Priority.ALWAYS);
    }

    private void updateBlocks() {
        blockBox.getChildren().remove(1, blockBox.getChildren().size());
        Node[] nodes = blockManager.getSeparatedActiveBlocks();
        if (nodes != null) {
            blockBox.getChildren().addAll(nodes);
        }
        nodes = blockManager.getNext();
        if (nodes != null) {
            blockBox.getChildren().addAll(nodes);
        }
        entriesQty.setText("Entries: " + csvCreator.getEntriesQty());
    }

    public void disable(int blockNumber) {
        blockManager.setInactiveBlock(blockNumber);
        updateBlocks();
    }

    public void activate(int blockNumber) {
        blockManager.setActiveBlock(blockNumber);
        updateBlocks();
    }

    public void saveEntry(ActionEvent actionEvent) {
        blockManager.saveEntry();
        updateBlocks();
    }

    public void previus(ActionEvent actionEvent) {

    }

    public void next(ActionEvent actionEvent) {

    }

    public void newFile(ActionEvent actionEvent) {

    }

    public void saveCsv(ActionEvent actionEvent) {
        String csv = csvCreator.getCsv();
        File file = fxStarter.selectFileToSave();
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(csv);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
