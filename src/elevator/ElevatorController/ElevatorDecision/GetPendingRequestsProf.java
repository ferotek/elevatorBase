package elevator.ElevatorController.ElevatorDecision;

import elevator.Person.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import elevator.Direction;

public class GetPendingRequestsProf implements GetPendingRequests {

    public void getPendingElevRequests(ArrayList<Person> pendingRequesters, ArrayList<Person> personRequesters){
        Iterator<Person> itr = pendingRequesters.iterator();



        if(!pendingRequesters.isEmpty()){
            Person initialRequester = pendingRequesters.get(0);
            pendingRequesters.remove(0);
            personRequesters.add(initialRequester);
            if(!pendingRequesters.isEmpty()){
                for (Iterator<Person> iterator = pendingRequesters.iterator(); iterator.hasNext(); ) {



                    Person nextRequester = iterator.next();
                    if (initialRequester.getDirection() == nextRequester.getDirection()) {
                        if (initialRequester.getDirection() == Direction.Direct.UP) {
                            if (Direction.getDirection(initialRequester.getStartFloor(), nextRequester.getStartFloor()) == Direction.Direct.UP) {
                                personRequesters.add(nextRequester);
                                iterator.remove();

                            }


                        } else if (initialRequester.getDirection() == Direction.Direct.Down) {
                            if (Direction.getDirection(initialRequester.getStartFloor(), nextRequester.getStartFloor()) == Direction.Direct.Down) {
                                personRequesters.add(nextRequester);
                                iterator.remove();

                            }


                        }
                    }



                }
            }


        }
    }


}
