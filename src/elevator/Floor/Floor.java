package elevator.Floor;
import elevator.Person.Person;

import java.util.ArrayList;


public class Floor {

    public ArrayList<Person> waiting;
    public ArrayList<Person> finished;

    private ArrayList<Person> floorPeople;
    private boolean hasRequest = false;

    private int floor;


    public Floor(int floor) {
        waiting = new ArrayList();
        finished = new ArrayList();
        floorPeople = new ArrayList<>();
        this.floor = floor;
    }

    public int getFloorNum(){
        return floor;
    }


    public void addWaiting(Person person){
        waiting.add(person);
        floorPeople.add(person);
        hasRequest = true;


    }

    public void removeWaiting(Person person){
        floorPeople.remove(person);
        if(waiting.size() == 0){
            hasRequest = false;
        }

    }



    public void addFinished(Person person){
        finished.add(person);
        floorPeople.add(person);



    }

    public int getLocation(){
        return floor;
    }

    public void setHasRequest(){
        hasRequest = true;
    }



}
