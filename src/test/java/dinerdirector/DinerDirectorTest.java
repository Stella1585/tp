package dinerdirector;

import static org.junit.jupiter.api.Assertions.assertTrue;

import commands.Command;
import commands.IncorrectCommand;
import commands.meeting.AddMeetingCommand;
import commands.meeting.DeleteMeetingCommand;
import commands.meeting.ViewMeetingCommand;

import commands.ExitCommand;
import commands.HelpCommand;
import commands.deadlinecommand.AddDeadlineCommand;
import commands.deadlinecommand.DeleteDeadlineCommand;
import commands.deadlinecommand.ViewDeadlineCommand;

import org.junit.jupiter.api.Test;
import utils.Parser;

import java.util.ArrayList;

public class DinerDirectorTest {

    @Test
    void runCommandLoopUntilExit_userInput_meetingCommand() {
        ArrayList<String> listOfCommands = new ArrayList<>();
        listOfCommands.add("add_meeting n/a t/3pm");
        listOfCommands.add("view_meetings");
        listOfCommands.add("delete_meeting n/a");
        listOfCommands.add("view_meeting");

        for (String listOfCommand : listOfCommands) {
            Command command = new Parser().parseCommand(listOfCommand);
            if (listOfCommand.equals("view_meeting")){
                assertTrue(command instanceof IncorrectCommand);
            } else if (listOfCommand.equals("view_meetings")) {
                assertTrue(command instanceof ViewMeetingCommand);
            } else if (listOfCommand.equals("add_meeting n/a t/3pm")) {
                assertTrue(command instanceof AddMeetingCommand);
            } else if (listOfCommand.equals("delete_meeting n/a")) {
                assertTrue(command instanceof DeleteMeetingCommand);
            } 
        }
    }
    void runCommandLoopUntilExit_userInput_command() {
        ArrayList<String> listOfCommands = new ArrayList<>();
        listOfCommands.add("help");
        listOfCommands.add("exit");
        listOfCommands.add("asdf");

        for (String listOfCommand : listOfCommands) {
            Command command = new Parser().parseCommand(listOfCommand);
            if (listOfCommand.equals("help")) {
                assertTrue(command instanceof HelpCommand);
            } else if (listOfCommand.equals("exit")) {
                assertTrue(command instanceof ExitCommand);
            } else {
                assertTrue(command instanceof IncorrectCommand);
            }
        }
    }
    
    @Test
    void runCommandLoopUntilExit_userInput_deadlineCommand() {
        ArrayList<String> listOfCommands = new ArrayList<>();
        listOfCommands.add("add_deadline");
        listOfCommands.add("add_deadline n/add command");
        listOfCommands.add("add_deadline t/when to command");
        listOfCommands.add("add_deadline n/add command t/when to command");
        listOfCommands.add("view_deadline");
        listOfCommands.add("view_deadline dsjfnskldf");
        listOfCommands.add("delete_deadline");
        listOfCommands.add("delete_deadline 1");

        for (String listOfCommand : listOfCommands) {
            Command command = new Parser().parseCommand(listOfCommand);
            if (listOfCommand.equals("add_deadline") ||
                    listOfCommand.equals("add_deadline n/add command") ||
                    listOfCommand.equals("add_deadline t/when to command") ||
                    listOfCommand.equals("view_deadline dsjfnskldf") ||
                    listOfCommand.equals("delete_deadline")) {
                assertTrue(command instanceof IncorrectCommand);
            } else if (listOfCommand.equals("view_deadline")) {
                assertTrue(command instanceof ViewDeadlineCommand);
            } else if (listOfCommand.equals("add_deadline n/add command t/when to command")) {
                assertTrue(command instanceof AddDeadlineCommand);
            } else if (listOfCommand.equals("delete_deadline 1")) {
                assertTrue(command instanceof DeleteDeadlineCommand);
            }
        }
    }
}