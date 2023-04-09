package manager;

import entity.Deadline;
import exceptions.DinerDirectorException;
import ui.TextUi;

import java.io.IOException;
import java.util.ArrayList;

import common.Messages;
import utils.DeadlineStorage;

public class DeadlineManager {
    //Solution below adapted from https://github.com/Stella1585/ip/blob/master/src/main/java/duke/TaskList.java
    private static ArrayList<Deadline> deadlines = new ArrayList<>();

    /**
     * Creates DeadlineList with input list.
     *
     * @param deadlines list of deadlines.
     */
    public DeadlineManager(ArrayList<Deadline> deadlines) {
        DeadlineManager.deadlines = deadlines;
    }

    /**
     * Adds a deadline item to the deadline list.
     *
     * @param deadline the deadline item to be added.
     * @param ui       manages user output.
     */
    public static void addDeadline(Deadline deadline, TextUi ui) {
        int len = deadlines.size();
        deadlines.add(deadline);
        //Solution below adapted from https://github.com/darrenangwx/ip/blob/6d3f1bc5f1
        // a281f9459a67650b043705d3096a8f/src/main/java/task/TaskParser.java
        ui.printMessage(Messages.MESSAGE_DEADLINE_ADDED +
                deadlines.get(deadlines.size() - 1).toString() +
                String.format(Messages.MESSAGE_NUMBER_OF_DEADLINES, deadlines.size()));
        assert deadlines.size() == len + 1 : "Length of deadline list should increase by 1";
        try {
            DeadlineStorage deadlineStorage = new DeadlineStorage();
            deadlineStorage.writeToDeadlineFile(deadlines);
        } catch (IOException e) {
            ui.printMessage(Messages.ERROR_STORAGE_INVALID_WRITE_LINE);
        }
    }

    /**
     * Print the deadline list.
     *
     * @param ui manages user output.
     */
    public static void viewDeadlines(TextUi ui) {
        try {
            if (deadlines.isEmpty()) {
                throw new DinerDirectorException(Messages.MESSAGE_DEADLINE_EMPTY_LIST);
            }
            System.out.println(Messages.MESSAGE_DEADLINE_VIEW_LIST);
            for (int i = 1; i <= deadlines.size(); i++) {
                ui.printMessage(i + ". " + deadlines.get(i - 1).toString());
            }
        } catch (DinerDirectorException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a deadline from the deadline list.
     *
     * @param index index of deadline to be deleted.
     * @param ui    manages user output.
     */
    public static void deleteDeadline(int index, TextUi ui) {
        int len = deadlines.size();
        try {
            System.out.print(Messages.MESSAGE_DEADLINE_REMOVED + deadlines.get(index).toString());
            deadlines.remove(index);
            ui.printMessage(String.format(Messages.MESSAGE_NUMBER_OF_DEADLINES, deadlines.size()));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Messages.ERROR_DEADLINE_INVALID_INDEX);
            return;
        }
        assert deadlines.size() == len - 1 : "Length of deadline list should decrease by 1.";
        try {
            DeadlineStorage deadlineStorage = new DeadlineStorage();
            deadlineStorage.writeToDeadlineFile(deadlines);
        } catch (IOException e) {
            ui.printMessage(Messages.ERROR_STORAGE_DELETE_FAILED);
        }
    }

    /**
     * Prints a list of deadline that matches the keyword.
     *
     * @param keyword the keyword to search for.
     * @param ui manages user output.
     */
    public static void findDeadline(String keyword, TextUi ui) {
        ArrayList<Deadline> matchingDeadlines = new ArrayList<>();
        for (Deadline x : deadlines) {
            if (((x.description).toLowerCase()).contains(keyword.toLowerCase())){
                matchingDeadlines.add(x);
            }
        }
        try {
            if (matchingDeadlines.isEmpty()) {
                throw new DinerDirectorException(Messages.MESSAGE_DEADLINE_EMPTY_SEARCH_LIST);
            }
            System.out.println(Messages.MESSAGE_DEADLINE_VIEW_SEARCH_LIST);
            for (int i = 1; i <= matchingDeadlines.size(); i++) {
                ui.printMessage(i + ". " + matchingDeadlines.get(i - 1).toString());
            }
        } catch (DinerDirectorException e) {
            System.out.println(e.getMessage());
        }
    }
}
