package elevator;

import exceptions.InvalidRequestException;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the elevators of the DC-Tower.
 *
 * @author Johannes Hufnagl
 */
public class ElevatorHandler {

    /**
     * Represents the elevators in the DC-Tower.
     */
    private final ElevatorQueue elevators = new ElevatorQueue();
    /**
     * Represents all elevator requests in the DC-Tower.
     */
    private final ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();
    /**
     * Indicates if the handler is running.
     */
    private final AtomicBoolean state = new AtomicBoolean();
    private final Logger logger = Logger.getLogger("ElevatorHandler");


    public ElevatorHandler() {
    }

    public AtomicBoolean isRunning() {
        return state;
    }

    public void setRunning(boolean state) {
        this.state.set(state);
    }

    /**
     * Adds a request to the request queue.
     *
     * @param request the trip request to add
     * @throws InvalidRequestException when the request is null or the same request is already queued
     */
    public void addRequest(Request request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Invalid request");
        }
        if (requests.contains(request)) {
            throw new InvalidRequestException("Request already queued");
        }
        requests.add(request);
    }

    /**
     * Calculates the delay time to simulate the elevator trip.
     *
     * @param source      the starting floor position of the elevator
     * @param destination the destination floor position of the elevator
     */
    private int calculateDelay(int source, int destination) {
        return Math.abs(source - destination) * 1000;
    }

    /**
     * Method which manages the elevators and requests.
     * First a handler thread is started, which manages the trip threads.
     */
    public void startElevators() {
        Thread handler = new Thread(() -> {
            while (state.get()) {
                Thread trip = new Thread(() -> {
                    if (!elevators.allBusy() && !requests.isEmpty()) {
                        // if trip is possible, due to available elevator poll request and assign
                        Request request = requests.poll();
                        Elevator elevator = elevators.assignElevator(request);
                        // Define direction
                        String direction = request.getSource() - request.getDestination() >= 0 ? "DOWN" : "UP";
                        logger.info("Elevator " + elevator.getId() + " [current Floor: " + elevator.getRequest().getSource()
                                + ", destination floor: " + elevator.getRequest().getDestination() + ", direction: " + direction + "]");
                        try {
                            // Wait for transition
                            Thread.sleep(calculateDelay(request.getSource(), request.getDestination()));
                        } catch (InterruptedException e) {
                            logger.log(Level.WARNING, e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                        // Set new position and free up elevator for new trip
                        elevator.setPosition(request.getDestination());
                        elevators.freeElevator(elevator);
                        logger.info("Elevator " + elevator.getId() + " went from floor: " + request.getSource() + " -> " + request.getDestination());
                    }
                });
                trip.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });
        handler.start();
    }
}
