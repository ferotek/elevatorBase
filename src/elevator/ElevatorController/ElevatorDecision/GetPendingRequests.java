package elevator.ElevatorController.ElevatorDecision;
import elevator.Person.Person;
import java.util.ArrayList;
import java.util.HashMap;

public interface GetPendingRequests {

    void getPendingElevRequests(ArrayList<Person> pendingRequesters, ArrayList<Person> personRequesters);
}
