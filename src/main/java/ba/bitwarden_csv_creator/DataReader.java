package ba.bitwarden_csv_creator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DataReader {
    public static byte[] readFromFile(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
