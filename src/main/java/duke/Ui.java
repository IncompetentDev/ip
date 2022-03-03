package duke;

import java.util.Scanner;

public class Ui {
    // Fixed string output for startup and exit
    private static final String LOGO =
            " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String WELCOME_BANNER =
            "Hello! I'm Dook!"
            + "\nWhat can I do for you?";

    private Scanner scannerInput;

    public Ui() {
        scannerInput = new Scanner(System.in);
    }

    /**
     * Prints the welcome message when user starts Duke
     */
    public void showWelcome() {
        System.out.println(LOGO);
        this.showLine();
        System.out.println(WELCOME_BANNER);
        this.showLine();
    }

    /**
     * Prints any error messages
     *
     * @param errorMessage the error message associated with an exception
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Reads a line of input from the user
     *
     * @return a line of the user input
     */
    public String readCommand() {
        String userInput = scannerInput.nextLine();
        return userInput;
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showLoadingError() {
        System.out.println("Error loading duke.txt!");
    }

    /**
     * Prints output
     *
     * @param output
     */
    public void showOutput(String output) {
        System.out.println(output);
    }
}
