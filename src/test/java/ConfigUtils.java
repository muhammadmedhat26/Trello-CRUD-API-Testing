import java.io.*;
import java.util.Properties;

public class ConfigUtils {
    private static final String FILE_PATH = "src/test/resources/trello_runtime.properties";

    // Saves the current IDs to the file
    public static void saveIDs(String boardId, String listId, String cardId, String checklistId) {
        Properties prop = new Properties();
    // Handle nulls safely so the file doesn't crash if something wasn't created
        prop.setProperty("boardId", boardId != null ? boardId : "");
        prop.setProperty("listId", listId != null ? listId : "");
        prop.setProperty("cardId", cardId != null ? cardId : "");
        prop.setProperty("checklistId", checklistId != null ? checklistId : "");

        new File("src/test/resources").mkdirs();

        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            prop.store(output, "Stored Trello IDs from last active execution");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads a specific ID from the file
    public static String getID(String key) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException e) {
    // File might not exist yet on the very first run
            return null;
        }
    }
}