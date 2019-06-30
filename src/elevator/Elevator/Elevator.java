package elevator.Elevator;

import elevator.Direction;
import elevator.Person.Person;
import elevator.getTimeStamp;
import exceptions.PositiveInputException;

import java.util.ArrayList;
import java.util.Iterator;
import java.text.DecimalFormat;

public class Elevator {





    private int id;
    private int currentFloor;
    private Direction.Direct direction;
    private boolean doorOpen = false;



    private boolean movingTowardsFloorRequest = false;
    private boolean movingTowardsRequestFloor = false;




    public static int timeOut;
    private int idleCount = 0;

    public static int floorTime;
    public static int doorTime;
    public static int idleTime;

    public int idleTimer;

    public static int capacity;









    public ArrayList<Integer> floorVisit = new ArrayList<>();
    private ArrayList<Person> riders = new ArrayList<>();
    public ArrayList<Person> requesters = new ArrayList<>();
    public ArrayList<Person> finished = new ArrayList<>();
    public ArrayList<Integer> floorBeginning = new ArrayList<>();






    public Elevator(int number) {
        id = number;
        currentFloor = 1;
        direction = Direction.Direct.IDLE;

    }

    public boolean isMovingTowardsFloorRequest(){
        return movingTowardsFloorRequest;
    }

    public boolean isMovingTowardsRequestFloor(){
        return movingTowardsRequestFloor;

    }

    public ArrayList<Person> getRidersCopy() {
        ArrayList<Person> ridersCopy = new ArrayList<>();
        if(!riders.isEmpty()){
            for(Person p : riders){
                ridersCopy.add(p);
            }
        }


        return ridersCopy;
    }

    public void setMaxElevator(int capacity)throws PositiveInputException {
        if(capacity < 1){
            throw new PositiveInputException("Input needs to be positive");
        }
        this.capacity = capacity;

    }

    public void setIdleTimer(int idleTimer)  {

        this.idleTimer = idleTimer;


    }

    public void setTimeOut(int timeOut) throws PositiveInputException {
        if(timeOut < 1){
            throw new PositiveInputException("Input needs to be positive");
        }
        this.timeOut = timeOut;


    }

    public void setFloorTime(int floorTime) throws PositiveInputException{
        if(floorTime < 1){
            throw new PositiveInputException("Input needs to be positive");
        }
        this.floorTime = floorTime;

    }

    public void setDoorTime(int doorTime) throws PositiveInputException{
        if(doorTime < 1){
            throw new PositiveInputException("Input needs to be positive");
        }
        this.doorTime = doorTime;


    }

    public boolean isDoorOpen(){
        return doorOpen;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }


    public int getID()  {

        return id;


    }








    public boolean isMoving(){
        if(direction == Direction.Direct.UP || direction == Direction.Direct.Down){
            return true;
        }
        return false;
    }

    public boolean isIdle(){
        if(direction == Direction.Direct.IDLE){
            return true;
        }
        return false;
    }




    public int getCurrentFloor(){
        return currentFloor;
    }


    public void setDoorOpen(int doorTime) {




            doorOpen = true;







    }

    public void setDoorClose() {



            doorOpen = false;






    }


    public void moveElevator (long increment) throws PositiveInputException {




            if (floorBeginning.contains(currentFloor)){
                addRider(increment);
                movingTowardsFloorRequest = false;
                movingTowardsRequestFloor = false;


            }


            else if(isElevatorRiderDesired(riders) && !riders.isEmpty()){
                removePerson(increment);
                movingTowardsFloorRequest = false;
                movingTowardsRequestFloor = false;


            }

            else if(doorOpen == true){
                setDoorClose();



                System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " Doors Close");
            }




            else{

                direction = getDirectionElevator();
                //this.direction = direction;
                int nextfloor;



                if (direction == Direction.Direct.UP) {
                    nextfloor= currentFloor + 1;
                    System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " is moving from Floor: " + currentFloor + " to Floor " + nextfloor + " [Current Floor Requests:" + requesters.size() + "][Current rider requests: " + riders.size() + "] " + " in direction " + direction);

                    currentFloor++;
                    for (Person p : riders) {
                        p.currentfloor++;
                    }
                } else if (direction == Direction.Direct.Down) {
                    nextfloor= currentFloor - 1;
                    System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " is moving from Floor: " + currentFloor + " to Floor " + nextfloor + " [Current Floor Requests:" + requesters.size() + "][Current rider requests: " + riders.size() + "] " + " in direction " + direction);
                    currentFloor--;
                    for (Person p : riders) {
                        p.currentfloor--;
                    }
                }
                else if(direction == Direction.Direct.IDLE){
                    idleCount++;
                    System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " current Floor: " + currentFloor + " direction " + direction + " " + idleCount);
                    movingTowardsFloorRequest = false;
                    movingTowardsRequestFloor = false;

                }


            }















    }

    public boolean isElevatorRiderDesired(ArrayList<Person> people){
        if(!people.isEmpty()){
            for(Person p: people){
                if(p.getDesiredFloor() == currentFloor){
                    return true;
                }

            }
        }


        return false;

    }

    public boolean isElevatorRequesterDesired(ArrayList<Person> people){
        if(!people.isEmpty()){
            for(Person p: people){
                if(p.getDesiredFloor() == currentFloor){
                    return true;
                }

            }
        }


        return false;

    }

    public void removePerson(long increment) {

        if (isElevatorRiderDesired(riders) && !riders.isEmpty()) {


            System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " has arrived at Floor: " + currentFloor + " for Rider Request " + "[Current Floor Requests:" + requesters.size() + "][Current rider requests: " + riders.size() + "] " + direction + " " + idleCount);
            System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " Doors Open");

            setDoorOpen(doorTime/2);


            floorVisit.remove(floorVisit.indexOf(currentFloor));
            for (Iterator<Person> iterator = riders.iterator(); iterator.hasNext();) {
                Person p = iterator.next();

                if(p.getDesiredFloor() == currentFloor && p.currentfloor == currentFloor){
                    System.out.format("Time: " + getTimeStamp.getTimeStamp(increment) + " Person " + p.getId() + " has left elevator " + id + " Riders: ");
                    finished.add(p);
                    for(Person f: finished){
                        if(f.getId().equals(p.getId())){
                            f.setRideEnd(increment-1000);
                            f.setTotalTime();
                            f.setFinishedTrip();

                        }
                    }



                    iterator.remove();
                    if(!riders.isEmpty()){
                        for(Person r : riders){
                            System.out.format(r.getId()+ " ");
                        }

                    }
                    System.out.println();

                }
            }








        }


    }

    public void addRider(long increment) {
        if (riders.size() >= capacity) {
            System.out.println("Elevator is at capacity \n");
        } else if (floorBeginning.contains(currentFloor)) {



            System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " has arrived at Floor: " + currentFloor + " for Floor Request " + "[Current Floor Requests:" + requesters.size() + "][Current rider requests: " + riders.size() + "] " + direction + " " + idleCount);
            System.out.println("Time: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " Doors Open");
            setDoorOpen(doorTime/2);



            for (Iterator<Person> iterator = requesters.iterator(); iterator.hasNext(); ) {
                Person p = iterator.next();
                if (p.getStartFloor() == currentFloor) {

                    riders.add(p);
                    System.out.format("Time: " + getTimeStamp.getTimeStamp(increment) + " Person " + p.getId() + " entered elevator " + id + " Riders: ");

                    for(Person r: riders){
                        if(r.getId().equals(p.getId()) ){
                            r.setRideStart(increment+1000);
                            r.setStartedTrip();
                            r.setWaitTime();
                        }
                        System.out.format(r.getId() + " ");

                    }
                    System.out.println("\nTime: " + getTimeStamp.getTimeStamp(increment) + " Elevator " + id + " Rider Request made for: " + p.getDesiredFloor() + "[Current Floor Requests:" + requesters.size() + "][Current rider requests: " + riders.size() + "] ");
                    iterator.remove();





                }




            }
            floorBeginning.remove(floorBeginning.indexOf(currentFloor));








        }

    }




    public int getFloor(){
        return currentFloor;

    }

    public Direction.Direct getDirectionElevator() {


        if(!riders.isEmpty()){
            direction = Direction.getDirection(currentFloor,riders.get(0).getDesiredFloor()  );
            movingTowardsFloorRequest = true;
            idleCount = 0;
            return direction;
        }

        else if(!requesters.isEmpty()){
            if (direction == Direction.Direct.IDLE || riders.isEmpty()) {
                direction = Direction.getDirection(currentFloor,requesters.get(0).getStartFloor());
                movingTowardsRequestFloor = true;
                idleCount = 0;
                return direction;
            }


        }
        else if(currentFloor == 1 && (direction == Direction.Direct.Down || direction == Direction.Direct.IDLE)){
            movingTowardsFloorRequest = false;
            movingTowardsRequestFloor = false;
            return Direction.Direct.IDLE;
        }
        else if(idleCount > 10 && currentFloor > 1){
            movingTowardsFloorRequest = false;
            movingTowardsRequestFloor = false;
            return Direction.Direct.Down;
        }




        return Direction.Direct.IDLE;
    }

    public Direction.Direct getDirect(){
        return direction;
    }

    /*public void stopElevator(){
        if(requesters.get(requesters.size()-1).desiredFloor == currentFloor ){
            direction = Direction.Direct.IDLE;
        }

    }*/
}
