package elevator.ElevatorController.ElevatorDecision;

import elevator.Elevator.Elevator;
import elevator.Person.Person;

import java.util.ArrayList;
import java.util.HashMap;

//Interface for implementing different elevator controller decision algorithms

public interface ElevatorDecision {

    boolean decisionMakingProcessBoolean(Person requester, HashMap<Integer, Elevator> elevators);

    boolean elevatorDesiredDirection(Person p, Elevator elevator);

    //void getPendingElevRequests(ArrayList<Person> pendingRequesters, ArrayList<Person> personRequesters);


}
