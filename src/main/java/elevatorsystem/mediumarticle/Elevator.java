package elevatorsystem.mediumarticle;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Elevator {

    private static final Logger logger = LoggerFactory.getLogger(Elevator.class);
    private int currentFloor;
    private Direction direction;

    public Elevator(){
        currentFloor = 1;
        direction = Direction.NONE;
    }

    public synchronized int getCurrentFloor(){
        return currentFloor;
    }

    public synchronized Direction getDirection(){
        return direction;
    }

    public synchronized void setDirection(Direction direction){
        this.direction = direction;
    }

    //move the elevator to a specified floor
    public synchronized void moveToFloor(int destinationFloor){
        log.info("Elevator moving {} from {} to {}", direction, currentFloor, destinationFloor);
        logger.info("Elevator moving {} from {} to {}", direction, currentFloor, destinationFloor);
        currentFloor = destinationFloor;
    }
}
