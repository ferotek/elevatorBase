package elevator.Building;
import elevator.Elevator.Elevator;
import elevator.ElevatorController.ElevatorController;
import elevator.Floor.Floor;
import elevator.Person.Person;
import exceptions.ElevatorBoundException;
import exceptions.PositiveInputException;
import gui.ElevatorDisplay;
import elevator.Direction;
import elevator.getTimeStamp;


import java.util.HashMap;
import java.util.Iterator;
//Building that owns floors, elevators, elevator controller
public class Building {



    private static Building buildingSingleton;
    private  int numFloors;
    private  int numElevators;




    public HashMap<Integer, Floor> floors = new HashMap<>();
    public HashMap<Integer, Elevator> elevators = new HashMap<>();



    public ElevatorDisplay elevatorDisplay = new ElevatorDisplay().getInstance();

    public ElevatorController testController =  ElevatorController.getInstance();

    //gets the elevatordisplay direction to operate the elevator display
    public gui.ElevatorDisplay.Direction equalDirection(Direction.Direct direction){
        if(direction == Direction.Direct.UP){
            return ElevatorDisplay.Direction.UP;

        }
        else if(direction == Direction.Direct.Down ){
            return ElevatorDisplay.Direction.DOWN;

        }

        else if (direction == Direction.Direct.IDLE){
            return ElevatorDisplay.Direction.IDLE;
        }

        return ElevatorDisplay.Direction.IDLE;



    }


    public void initalizeDisplay(){

        elevatorDisplay.getInstance().initialize(numFloors);
        for(int i = 1; i <=numElevators; i++){
            elevatorDisplay.getInstance().addElevator(i,1);
        }


    }

    public void removeRiderFromFloor(long increment){
        for(int i = 1; i <= numFloors; i++) {
            for(int j = 1; j <= numElevators; j++){

                if(!elevators.get(j).getRidersCopy().isEmpty()){
                    for (Person p : elevators.get(j).getRidersCopy()) {
                        for(Iterator<Person> iterator = floors.get(i).waiting.iterator(); iterator.hasNext();){
                            Person f = iterator.next();
                            if(p.getId().equals(f.getId()) && p.getStartedTrip() == true){
                                System.out.println("Time: " +  getTimeStamp.getTimeStamp(increment)  + " " + f.getId() + " has left floor " + floors.get(i).getFloorNum());
                                floors.get(i).removeWaiting(f);
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }
    }


    public void addRiderToFloor(long increment){

            for(int i = 1; i <= numElevators; i++){
                for (Iterator<Person> iterator = elevators.get(i).finished.iterator(); iterator.hasNext();) {
                    Person p = iterator.next();
                    if(!floors.get(p.getDesiredFloor()).finished.contains(p)){
                        System.out.println("Time: " + getTimeStamp.getTimeStamp(increment)+ " " + p.getId() + " has entered floor " + floors.get(p.getDesiredFloor()).getFloorNum());
                        floors.get(p.getDesiredFloor()).addFinished(p);
                        iterator.remove();
                    }



                }

            }


    }


    public void displayElevators(){

        for(int i = 1; i <= numElevators; i++){
            elevatorDisplay.getInstance().updateElevator(i,elevators.get(i).getCurrentFloor(),elevators.get(i).getRidersCopy().size(),equalDirection(elevators.get(i).getDirect()));
            if(elevators.get(i).isDoorOpen()==true){
                elevatorDisplay.getInstance().openDoors(i);
            }
            else if (elevators.get(i).isDoorOpen()==false){
                elevatorDisplay.getInstance().closeDoors(i);

            }


        }
    }

    public void operateElevators(long time){
        testController.getInstance().getPendingRequests();
        testController.getInstance().decisionMakingProcess(elevators);
        testController.getInstance().moveElevators(time, elevators, numElevators);
        removeRiderFromFloor(time);
        addRiderToFloor(time);
    }

    public void shutDownElevator(){
        elevatorDisplay.getInstance().shutdown();
    }





    private Building(){

    }


    public static Building getInstance() {
        if(buildingSingleton == null) {

            buildingSingleton = new Building();
            System.out.println("Building initialized\n");
            return buildingSingleton;
        }


        return buildingSingleton;
    }

    public void init(int numFloors, int numElevators, int floorTime, int waitTime, int doorTime, int maxPersonFloor) throws PositiveInputException {
        if(numFloors  < 1|| numElevators < 1){
            throw new PositiveInputException("Input needs to be positive");
        }


        this.numFloors = numFloors;
        for(int i = 1; i <= numFloors; i++){
            Floor floor = new Floor(i);
            floors.put(i, new Floor(i));
        }

        this.numElevators = numElevators;

        for (int i = 1; i <= numElevators; i++) {

            elevators.put(i, new Elevator(i));
            System.out.println("Elevator " + i + " initialized.");
            try {
                elevators.get(i).setDoorTime(doorTime);
                elevators.get(i).setFloorTime(floorTime);
                elevators.get(i).setIdleTimer(waitTime);
                elevators.get(i).setMaxElevator(maxPersonFloor);
            } catch (PositiveInputException e) {

            }


        }

    }

    public int getNumElevators(){
        return numElevators;
    }

    public void addPersontoFloor(Person person) throws ElevatorBoundException {

        if((person.getStartFloor()<1 || person.getStartFloor()>numFloors)|| (person.getDesiredFloor()<1 || person.getDesiredFloor()>numFloors)){
            throw new ElevatorBoundException("Please enter floor between 1 and " + numFloors);
        }

        int floorNum = person.getStartFloor();

        testController.getInstance().newRequests.add(0,person.getDesiredFloor());
        testController.getInstance().personRequesters.add(0,person);


        floors.get(floorNum).addWaiting(person);



    }



}
