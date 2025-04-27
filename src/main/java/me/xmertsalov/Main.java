package me.xmertsalov;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code Main} class serves as the entry point for the GravityRunner application.
 * It processes command-line arguments to configure the game settings and initializes the game.
 */
public class Main {

    /**
     * The main method is the entry point of the application.
     * It processes command-line arguments to enable or disable specific game features
     * and starts the game with the specified configuration.
     *
     * @param args Command-line arguments passed to the application.
     *             Supported arguments:
     *             <ul>
     *                 <li>{@code --debug-colliders} or {@code -dc}: Enables debug mode for colliders.</li>
     *                 <li>{@code --show-fps} or {@code -f}: Displays the frames per second (FPS) counter.</li>
     *             </ul>
     */
    public static void main(String[] args) {
        // Convert the command-line arguments to a list for easier processing
        List<String> argsList = Arrays.stream(args).toList();
        boolean debugColliders = false;
        boolean showFps = false;

        // Check if any arguments are provided and set the corresponding flags
        if (args.length > 0) {
            if (argsList.contains("--debug-colliders") || argsList.contains("-dc")) {
                debugColliders = true;
            } else if (argsList.contains("--show-fps") || argsList.contains("-f")) {
                showFps = true;
            }
        }

        // Initialize the game with the specified settings
        new Game(debugColliders, showFps);
    }
}
