package elevator.Person;


import elevator.Direction;
import exceptions.ElevatorBoundException;
import exceptions.NegativeInputException;
import exceptions.PositiveInputException;

public class Person {


    private String Id;
    private  int desiredFloor;
    private  int startFloor;

    public int currentfloor;
    private boolean finishedTrip;
    private boolean startedTrip;

    private long waitStart;
    private long waitEnd;
    private long rideStart;
    private long rideEnd;



    private long waitTime;
    private long rideTime;

    private long totalTime;


    private Direction.Direct direction;







    public Person (String IdIn,  int floorNumStart, int floorNumEnd) throws PositiveInputException {
        if(floorNumStart < 1 || floorNumEnd < 1){
            throw new PositiveInputException("Input needs to be positive");
        }

        this.Id = IdIn;
        startFloor = floorNumStart;
        desiredFloor = floorNumEnd;
        finishedTrip = false;
        startedTrip = false;
        currentfloor = startFloor;
        direction = Direction.getDirection(startFloor,desiredFloor);
    }

    public String getId(){
        return Id;
    }



    public int getStartFloor(){
        return startFloor;
    }

    public Direction.Direct getDirection() {

        return direction;
    }

    public void setWaitStart(long increment){
        waitStart = increment/1000;


    }



    public void setWaitEnd(long increment){
        waitEnd =increment;


    }

    public void setRideStart(long increment){
        rideStart = increment/1000;


    }

    public void setRideEnd(long increment){
        rideEnd = increment/1000;


    }



    public int getDesiredFloor(){
        return desiredFloor;
    }

    public void setFinishedTrip(){

            finishedTrip = true;


    }

    public boolean getFinishedTrip(){

        return finishedTrip;

    }

    public void setStartedTrip(){

            startedTrip = true;


    }



    public boolean getStartedTrip(){
        return startedTrip;
    }

    public long getWaitTime(){
        waitTime = rideStart - waitStart;
        return waitTime;
    }

    public void setWaitTime(){
        waitTime = rideStart - waitStart;

    }

    public long getRideTime(){

        rideTime =  rideEnd - rideStart;
        return rideTime;

    }



    public long getTotalTime(){

        totalTime  =  rideTime + waitTime;
        return totalTime;

    }

    public void setTotalTime(){

        totalTime  =  rideTime + waitTime;


    }


















}
