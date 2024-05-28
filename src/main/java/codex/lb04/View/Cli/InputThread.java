package codex.lb04.View.Cli;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * callable class to get input from the user when you need to save it in a variable
 */
public class InputThread implements Callable<String> {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * gets the input from the user, called by the CLI controller
     *
     * @return the input from the user converted to uppercase for consistency of checks
     * @throws IOException if an input/output error occurs
     */
    @Override
    public String call() throws IOException {
        return scanner.nextLine().trim().toUpperCase();
    }

}
