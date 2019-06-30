package elevator.ElevatorController;

import elevator.Direction;
import elevator.Elevator.Elevator;
import elevator.ElevatorController.ElevatorDecision.ElevatorDecision;
import elevator.ElevatorController.ElevatorDecision.ElevatorDecisionProf;
import elevator.ElevatorController.ElevatorDecision.GetPendingRequests;
import elevator.ElevatorController.ElevatorDecision.GetPendingRequestsProf;
import elevator.Person.Person;
import exceptions.PositiveInputException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class ElevatorController {

    private ElevatorDecision elevatorDecision = new ElevatorDecisionProf();
    private GetPendingRequests getPendingRequests = new GetPendingRequestsProf();

    private static ElevatorController elevInstance;


    private Person requester;




    public ArrayList<Person> personRequesters = new ArrayList<>();





    public ArrayList<Person> pendingRequesters = new ArrayList<>();


    public ArrayList<Integer> newRequests = new ArrayList<Integer>();



    //public HashMap<Integer, Elevator> elevators = new HashMap<>();





    public static ElevatorController getInstance() {
        if (elevInstance == null) {

            elevInstance = new ElevatorController();
            System.out.println("ElevatorController initialized\n");
            return elevInstance;
        }


        return elevInstance;

    }

    public Elevator getElevator(int elevNum, HashMap<Integer, Elevator> elevators) {
        return elevators.get(elevNum);

    }




    public void moveElevators(long increment, HashMap<Integer, Elevator> elevators, int numElev) {

        try {
            for (int i = 1; i <= numElev; i++) {
                elevators.get(i).moveElevator(increment);
            }
        }catch(PositiveInputException e){

        }


    }




    public void decisionMakingProcess(HashMap<Integer, Elevator> elevators){
        if(!personRequesters.isEmpty()){
            requester = personRequesters.get(0);
            personRequesters.remove(0);
            //System.out.println(requester.getId());

            if(elevatorDecision.decisionMakingProcessBoolean(requester, elevators) == true  ){

                ;
            }
            else{
                pendingRequesters.add(requester);
            }

        }


    }

    public void getPendingRequests(){
        getPendingRequests.getPendingElevRequests(pendingRequesters, personRequesters);
    }




    }





