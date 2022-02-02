package elevator;

import exceptions.InvalidFloorException;

import java.util.Objects;

/**
 * Represents a request.
 *
 * @author Johannes Hufnagl
 */
public class Request {
    private final int id;
    private int source;
    private int destination;

    /**
     * Creates a request on the first floor
     *
     * @param id          number representation of the request
     * @param source      start floor of the trip
     * @param destination end floor of the trip
     * @throws InvalidFloorException if the source or destination floor is not available in the DC-Tower (<0 || >55)
     */
    public Request(int id, int source, int destination) throws InvalidFloorException {
        this.id = id;
        setSource(source);
        setDestination(destination);
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) throws InvalidFloorException {
        if (source >= 0 && source <= 55) {
            this.source = source;
        } else throw new InvalidFloorException("Invalid floor source: " + source);
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) throws InvalidFloorException {
        if (destination >= 0 && destination <= 55) {
            this.destination = destination;
        } else throw new InvalidFloorException("Invalid floor destination: " + destination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id && source == request.source && destination == request.destination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, destination);
    }

    @Override
    public String toString() {
        return "Request{" +
                "number=" + id +
                ", source=" + source +
                ", destination=" + destination +
                '}';
    }
}
