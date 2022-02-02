package elevator;

import java.util.Objects;

/**
 * Represents an elevator.
 *
 * @author Johannes Hufnagl
 */
public class Elevator {
    private final int id;
    private int position;
    private Request request;
    private boolean busy;

    /**
     * Creates an elevator on the first floor.
     *
     * @param id number representation of elevator
     */
    public Elevator(int id) {
        this.id = id;
        position = 0;
    }

    public int getId() {
        return id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elevator elevator = (Elevator) o;
        return id == elevator.id && position == elevator.position && busy == elevator.busy && Objects.equals(request, elevator.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, request, busy);
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", position=" + position +
                ", request=" + request.getSource() + " -> " + request.getDestination() +
                ", busy=" + busy +
                '}';
    }
}
