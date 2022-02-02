package elevator;

import exceptions.InvalidFloorException;
import exceptions.InvalidRequestException;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simulates the software by creating 50 random trips in the DC-Tower.
 *
 * @author Johannes Hufnagl
 */
public class Main {

    public static void main(String[] args) {
        ElevatorHandler elevatorHandler = new ElevatorHandler();
        Random random = new Random();
        final Logger logger = Logger.getLogger("Main");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            elevatorHandler.setRunning(false);
            logger.info("Application finished successfully!");
        }));

        elevatorHandler.setRunning(true);
        elevatorHandler.startElevators();

        try {
            for (int i = 0; i < 50; i++) {
                elevatorHandler.addRequest(new Request(i, random.nextInt(55), random.nextInt(55)));
            }

        } catch (InvalidFloorException | InvalidRequestException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
