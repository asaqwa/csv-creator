package ba.bitwarden_csv_creator.fx;

import ba.bitwarden_csv_creator.DataType;
import ba.bitwarden_csv_creator.EntryLine;
import ba.bitwarden_csv_creator.fx.view.FileProzessorController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class FxBlock {
    private static final String DISABLE = "Disable";
    private static final String ACTIVATE = "Activate";

    private final String[] content;
    private final int blockNumber;
    private final String blockName;
    private FileProzessorController controller;
    private HBox rootBox;
    private Label blockNameLabel;
    private Button activate;
    private CheckBox reuse;
    private GridPane table;
    private ChoiceBox<DataType>[] types;

    public FxBlock(String[] content, int blockNumber, String blockName, FileProzessorController controller) {
        this.content = content;
        this.blockNumber = blockNumber;
        this.blockName = blockName;
        this.controller = controller;
    }

    public HBox getBlockView() {
        if (rootBox == null) initView();
        return rootBox;
    }

    public void initView() {
        initRootBox();
        initButtonBox();
        initTable();
    }

    private void initRootBox() {
        rootBox = new HBox();
        rootBox.setAlignment(Pos.TOP_RIGHT);
    }

    private void initButtonBox() {
        VBox buttonBox = new VBox();
        blockNameLabel = new Label(blockName);
        reuse = new CheckBox("Reuse this block");
        activate = new Button(DISABLE);
        activate.setOnAction(e-> {
            if (activate.getText().equals(DISABLE)) {
                controller.disable(blockNumber);
                disable();
            } else {
                controller.activate(blockNumber);
                activate();
                activate.setDisable(false);
            }
        });
        buttonBox.getChildren().addAll(blockNameLabel, reuse, activate);
        rootBox.getChildren().add(buttonBox);
    }

    @SuppressWarnings("unchecked")
    private void initTable() {
        table = new GridPane();
        table.setGridLinesVisible(true);

        ColumnConstraints contentColumn = new ColumnConstraints();
        contentColumn.setMinWidth(240);
        ColumnConstraints typeColumn = new ColumnConstraints();
        typeColumn.setMinWidth(100);
        table.getColumnConstraints().addAll(contentColumn, typeColumn);

        types = new ChoiceBox[content.length];
        for (int i = 0; i < content.length; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(22);
            row.setMaxHeight(22);
            table.getRowConstraints().add(row);

            Label label = new Label(content[i]);
            ChoiceBox<DataType> choiceBox = new ChoiceBox<>();
            choiceBox.getItems().addAll(DataType.values());
            choiceBox.getSelectionModel().selectLast();

            table.add(label, 0, i);
            table.add(choiceBox, 1, i);
            types[i] = choiceBox;
        }
        rootBox.getChildren().add(table);
    }

    public void disable() {
        table.setDisable(true);
        blockNameLabel.setDisable(true);
        reuse.setVisible(false);
        activate.setText(ACTIVATE);
    }

    public void activate() {
        table.setDisable(false);
        blockNameLabel.setDisable(false);
        reuse.setVisible(true);
        activate.setText(DISABLE);
    }

    public boolean reusePressed() {
        return reuse.isSelected();
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public ChoiceBox<DataType>[] getTypes() {
        return types;
    }
}
