package elevator;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * Represents a elevator queue,
 * which contains 7 elevators in the case of the DC-Tower.
 *
 * @author Johannes Hufnagl
 */
public class ElevatorQueue {

    /**
     * Represents the elevator queue.
     */
    private final ConcurrentLinkedQueue<Elevator> elevators = new ConcurrentLinkedQueue<>();
    private final Logger logger = Logger.getLogger("ElevatorQueue");


    public ElevatorQueue() {
        Elevator elevator1 = new Elevator(1);
        Elevator elevator2 = new Elevator(2);
        Elevator elevator3 = new Elevator(3);
        Elevator elevator4 = new Elevator(4);
        Elevator elevator5 = new Elevator(5);
        Elevator elevator6 = new Elevator(6);
        Elevator elevator7 = new Elevator(7);

        elevators.add(elevator1);
        elevators.add(elevator2);
        elevators.add(elevator3);
        elevators.add(elevator4);
        elevators.add(elevator5);
        elevators.add(elevator6);
        elevators.add(elevator7);
    }

    /**
     * Assigns a request to an elevator and sets its status to busy.
     *
     * @param request the request to assign the elevator to
     * @return the assigned elevator or null if all elevators are busy
     */
    public Elevator assignElevator(Request request) {
        for (Elevator elevator : elevators) {
            if (!elevator.isBusy()) {
                elevator.setBusy(true);
                elevator.setRequest(request);
                logger.info("Elevator " + elevator.getId() + " is now busy");
                return elevator;
            }
        }
        return null;
    }

    /**
     * Checks if there is a free elevator in queue.
     */
    public boolean allBusy() {
        for (Elevator elevator : elevators) {
            if (!elevator.isBusy()) {
                return false;
            }
        }
        return true;
    }

    public void freeElevator(Elevator elevator) {
        elevator.setBusy(false);
    }
}
