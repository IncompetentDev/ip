package duke.commands;

import duke.tasks.TaskList;
import duke.Storage;
import duke.Ui;

public class ByeCommand extends Command {

    private static final String EXIT_MESSAGE = "Bye. Hope to see you again soon!";

    public ByeCommand() {
        this.isExit = true;
    }

    /**
     * Show exit output message.
     *
     * @param taskList the taskList to act on
     * @param ui the provided Ui to output on
     * @param storage the provided filename to update data to
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showOutput(EXIT_MESSAGE);
    }
}
