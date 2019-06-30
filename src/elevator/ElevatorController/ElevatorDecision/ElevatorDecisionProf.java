package elevator.ElevatorController.ElevatorDecision;

import elevator.Elevator.Elevator;
import elevator.Person.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import elevator.Direction;

//Class that implements Professor Hield's algorithm

public class ElevatorDecisionProf implements ElevatorDecision {




    public boolean decisionMakingProcessBoolean(Person requester, HashMap<Integer, Elevator> elevators) {
        int numElev = elevators.size();
            //int floorRequestBegin = requester.getStartFloor();



            for (int i = 1; i <= numElev; i++) {
                Direction.Direct desired = Direction.getDirection(elevators.get(i).getCurrentFloor(), requester.getDesiredFloor());
                if (elevators.get(i).getCurrentFloor() == requester.getStartFloor()) {
                    if (elevators.get(i).getDirectionElevator() == Direction.Direct.IDLE || (elevators.get(i).getDirectionElevator() == desired)) {


                        addPersonElevator(elevators.get(i), requester);

                        return true;


                    }
                } else if (elevators.get(i).isMoving()) {
                    if (elevatorDesiredDirection(requester, elevators.get(i))){
                        addPersonElevator(elevators.get(i), requester);

                        return true;



                    }


                } else if (elevators.get(i).isIdle()) {
                    addPersonElevator(elevators.get(i), requester);

                    return true;


                }





            }




        return false;


    }

    public void addPersonElevator(Elevator elevator, Person p) {
        elevator.floorVisit.add(p.getDesiredFloor());
        elevator.requesters.add(p);
        elevator.floorBeginning.add(p.getStartFloor());
        //System.out.println("Person " + p.getId() + " added to elevator request " + elevator.getID());

    }





    public boolean elevatorDesiredDirection(Person person, Elevator elevator) {
        if (elevator.isMovingTowardsFloorRequest() == true) {
            if(Direction.getDirection(elevator.getCurrentFloor(), person.getStartFloor()) == elevator.getDirect()) {
                if (person.getDirection() == elevator.getDirect()) {
                    //System.out.println("mtfr " + elevator.getID());

                    return true;

                }
            }


        }
        else if(elevator.isMovingTowardsRequestFloor() == true) {
            if (elevator.getDirect() == person.getDirection() ){
                if (elevator.requesters.get(0).getDirection() == person.getDirection() ) {
                    if(elevator.getDirect() == Direction.getDirection(elevator.getCurrentFloor(),person.getStartFloor())){

                        return true;
                    }


                }
            }

        }




        return false;
    }

    /*public void getPendingElevRequests(ArrayList<Person> pendingRequesters, ArrayList<Person> personRequesters){
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
    }*/

}
