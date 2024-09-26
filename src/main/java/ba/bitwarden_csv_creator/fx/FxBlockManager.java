package ba.bitwarden_csv_creator.fx;

import ba.bitwarden_csv_creator.CsvCreator;
import ba.bitwarden_csv_creator.DataType;
import ba.bitwarden_csv_creator.Entry;
import ba.bitwarden_csv_creator.EntryLine;
import ba.bitwarden_csv_creator.fx.view.FileProzessorController;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class FxBlockManager {
    private final FileProzessorController controller;
    private final CsvCreator csvCreator;
    private final int blocksQty;

    private final Map<Integer, FxBlock> blockMap = new HashMap<>();
    private final Map<FxBlock, Integer> indexMap = new HashMap<>();
    private final List<Integer> processed = new ArrayList<>();
    private final List<Integer> active = new ArrayList<>();
    private FxBlock next;



    public FxBlockManager(CsvCreator csvCreator, FileProzessorController controller) {
        this.csvCreator = csvCreator;
        this.controller = controller;
        blocksQty = csvCreator.getBlocks().size();
        initMaps();
    }

    private void initMaps() {
        for (int i = 0; i < blocksQty; i++) {
            String blockName = "Block " + (i+1) + "/" + blocksQty;
            FxBlock block = new FxBlock(csvCreator.getBlocks().get(i), i , blockName, controller);
            block.initView();
            blockMap.put(i, block);
            indexMap.put(block, i);
        }
        if (blocksQty > 0) {
            active.add(0);
        }
        findNext();
    }

    private void findActive() {
        if (active.isEmpty()) {
            for (int i = 0; i < blocksQty; i++) {
                if(!processed.contains(i)) {
                    active.add(i);
                    blockMap.get(i).activate();
                    return;
                }
            }
        }
    }

    private void findNext() {
        for (int i = 0; i < blocksQty; i++) {
            if (!processed.contains(i) && !active.contains(i)) {
                if (next != null && next.getBlockNumber() != i) {
                    next.activate();
                }
                next = blockMap.get(i);
                next.disable();
                return;
            }
        }
        next = null;
    }

    public HBox[] getActiveBlocks() {
        return active.stream().map(blockMap::get)
                .map(FxBlock::getBlockView)
                .toArray(HBox[]::new);
    }

    public Node[] getSeparatedActiveBlocks() {
        if (active.size()<1) return null;
        Node[] nodes = new Node[active.size() + active.size()-1];
        for (int i = 0; i < nodes.length; i++) {
            if ((i&1) == 0) {
                nodes[i] = blockMap.get(active.get(i >> 1)).getBlockView();
            } else {
                nodes[i] = getBlockSeparator();
            }
        }
        return nodes;
    }

    private Node getBlockSeparator() {
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMinWidth(480);
        VBox.setMargin(separator, new Insets(2, 0, 2, 0));
        return separator;
    }

    public void setActiveBlock(int blockIndex) {
        active.add(blockIndex);
        Collections.sort(active);
        findNext();
    }

    public void setInactiveBlock(int blockIndex) {
        active.remove((Object) blockIndex);
        if (active.isEmpty()) {
            for (int j = 0; j < blocksQty; j++) {
                if (!processed.contains(j) && j != blockIndex) {
                    active.add(j);
                }
            }
            if (active.isEmpty()) {
                active.add(blockIndex);
            }
        }
        findNext();
    }

    public Node[] getNext() {
        return next == null ? null : new Node[] {getBlockSeparator(), next.getBlockView()};
    }

    public void saveEntry() {
        createAndSaveEntry();
        moveBlocksToProcessed();
        findActive();
        findNext();
    }

    private void moveBlocksToProcessed() {
        for (int i = 0; i < active.size(); ) {
            if (!blockMap.get(active.get(i)).reusePressed()) {
                processed.add(active.remove(i));
            } else i++;
        }
    }

    private void createAndSaveEntry() {
        Entry entry = new Entry();
        FxBlock[] blocks = active.stream().map(blockMap::get).toArray(FxBlock[]::new);
        for (FxBlock block: blocks) {
            ChoiceBox<DataType>[] lineTypes = block.getTypes();
            for (int i = 0; i < lineTypes.length; i++) {
                DataType type = lineTypes[i].getValue();
                if (type != null && type != DataType.UNASSIGNED) {
                    entry.setLine(new EntryLine(block.getBlockNumber(), i, type));
                }
            }
        }
        csvCreator.addEntry(entry);
    }
}
