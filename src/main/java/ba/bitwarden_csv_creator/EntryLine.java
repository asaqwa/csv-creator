package ba.bitwarden_csv_creator;

public class EntryLine {
    public final int blockIndex;
    public final int lineIndex;
    public final DataType type;

    public EntryLine(int blockIndex, int lineIndex, DataType type) {
        this.blockIndex = blockIndex;
        this.lineIndex = lineIndex;
        this.type = type;
    }
}
