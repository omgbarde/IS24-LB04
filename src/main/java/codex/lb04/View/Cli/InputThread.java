package codex.lb04.View.Cli;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * This class represents the input thread
 */
public class InputThread implements Callable<String> {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String call() throws IOException{
        return scanner.nextLine().trim().toUpperCase();
    }

}
