package ba.bitwarden_csv_creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ba.bitwarden_csv_creator.DataType.*;

public class CsvCreator {
    private final byte[] data;
    private String dataString;
    private List<String[]> blocks = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();

    public CsvCreator(byte[] data) {
        this.data = data;
    }

    public void parseFile() {
        dataString = new String(data);
    }

    public void parseBlocks() {
        if (dataString == null) parseFile();
        String[] lines = dataString.lines().toArray(String[]::new);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            int start = i;
            while (++i < lines.length || i < start+10) {
                if (lines[i].isBlank()) break;
            }
            blocks.add(Arrays.copyOfRange(lines, start, i));
        }
    }

    public List<String[]> getBlocks() {
        return blocks;
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public String getCsv() {
        StringBuilder csv = new StringBuilder("folder,favorite,type,name,notes,fields,reprompt,login_uri,login_username,login_password,login_totp").append('\n');
        for (Entry entry: entries) {
            for (DataType type: new DataType[] {FOLDER, FAVORITE, TYPE, TITLE, NOTES, FIELDS, REPROMPT, URI, USERNAME, PASSWORD}) {
                formatEntry(csv, entry, type);
            }
            csv.append('"').append(getEntryLinesValue(entry.getLines(TOTP))).append('"').append('\n');
        }
        return csv.toString();
    }

    private void formatEntry(StringBuilder csv, Entry entry, DataType type) {
        csv.append('"').append(getEntryLinesValue(entry.getLines(type))).append('"').append(',');

    }

    private String getEntryLinesValue(List<EntryLine> entryLines) {
        if (entryLines == null) return "";
        return entryLines.stream().map(line-> blocks.get(line.blockIndex)[line.lineIndex]).collect(Collectors.joining("\n"));
    }

    public int getEntriesQty() {
        return entries.size();
    }
}
