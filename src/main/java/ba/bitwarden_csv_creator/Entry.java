package ba.bitwarden_csv_creator;

import java.util.*;

public class Entry {
    private final Map<DataType, List<EntryLine>> entryLines = new HashMap<>();

    public void setLine(EntryLine line) {
        entryLines.computeIfAbsent(line.type, k-> new ArrayList<>()).add(line);
    }

    public List<EntryLine> getLines(DataType type) {
        return entryLines.getOrDefault(type, null);
    }
}
