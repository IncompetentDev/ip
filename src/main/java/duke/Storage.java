package duke;

import duke.exceptions.DukeException;
import duke.exceptions.FileWriteException;
import duke.exceptions.FileReadException;
import duke.exceptions.IncorrectFileFormatException;

import duke.tasks.Task;
import duke.tasks.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Storage {

    private static final String FILEIO_ERR_CREATING_FILE = "creating data file.";
    private static final String FILEIO_ERR_WRITING_FILE = "writing to data file.";
    private String filePath;

    /**
     * Reads from filePath and constructs a TaskList based on the given file
     *
     * @param filePath the file to read from
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws DukeException {
        // load data into taskList
        ArrayList<Task> bufferTaskList = new ArrayList<Task>();
        try {
            File dataFile = new File(filePath);
            Scanner dataFileReader = new Scanner(dataFile);
            while (dataFileReader.hasNextLine()) {
                String data = dataFileReader.nextLine();
                Task taskToAdd = Parser.parseDataLine(data);
                bufferTaskList.add(taskToAdd);
            }
        } catch (FileNotFoundException e) {
            // No data file found
            DukeException exception = new FileReadException();
            throw exception;
        } catch (IllegalArgumentException e) {
            // Most probably attributed to error in data file format, specifically the column indicating task type.
            IncorrectFileFormatException exception = new IncorrectFileFormatException();
            throw exception;
        } catch (IndexOutOfBoundsException e) {
            // Most probably attributed to error in data file format, such as indicating "Deadline" without having a proper date.
            IncorrectFileFormatException exception = new IncorrectFileFormatException();
            throw exception;
        } catch (DateTimeParseException e) {
            // Wrong format in date
            IncorrectFileFormatException exception = new IncorrectFileFormatException();
            throw exception;
        }
        return bufferTaskList;
    }

    /**
     * Creates the required files/directories, then writes to the data file specified by this Storage object's filePath
     *
     * @param taskList list of tasks to write to file
     * @throws FileWriteException if any IO error occurs while writing
     */
    public void write(TaskList taskList) throws FileWriteException {
        File fileToWriteTo = new File(filePath);
        createRequiredFiles(fileToWriteTo);
        writeToFile(fileToWriteTo, taskList);
    }

    /**
     * Based on the File object, create the required directories and then create the data file.
     *
     * @param fileToWriteTo data file
     * @throws FileWriteException if any IO error occurs while writing
     */
    private void createRequiredFiles(File fileToWriteTo) throws FileWriteException {
        try {
            fileToWriteTo.getAbsoluteFile().getParentFile().mkdirs();
            fileToWriteTo.createNewFile();
        } catch (IOException e) {
            FileWriteException exception = new FileWriteException(FILEIO_ERR_CREATING_FILE);
            throw exception;
        }
    }

    /**
     * Based on the File object and taskList, write a proper representation of the taskList into the File.
     *
     * @param fileToWriteTo data file
     * @param taskList list of tasks
     * @throws FileWriteException if any IO error occurs while writing
     */
    private void writeToFile(File fileToWriteTo, TaskList taskList) throws FileWriteException {
        try {
            FileWriter writer = new FileWriter(fileToWriteTo, false);
            for (int i = 0; i<taskList.size(); i++) {
                Queue<String> infoToWrite = new LinkedList<String>();
                Task task = taskList.get(i);
                task.toDataFile(infoToWrite);
                String stringToWrite = infoToWrite.poll();
                // no information to write to begin with
                if (stringToWrite == null) {
                    continue;
                }
                while (infoToWrite.peek() != null) {
                    //delimiter
                    stringToWrite += " | ";
                    String infoBit= infoToWrite.poll();
                    stringToWrite += infoBit;
                }
                writer.write(stringToWrite+"\n");
            }
            writer.close();
        } catch (IOException e) {
            FileWriteException exception = new FileWriteException(FILEIO_ERR_WRITING_FILE);
            throw exception;
        }
    }
}
