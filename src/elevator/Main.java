//David Guo SE 450
package elevator;

import elevator.Building.Building;
import elevator.Person.Person;
import exceptions.ElevatorBoundException;
import exceptions.PositiveInputException;
import gui.ElevatorDisplay;
import static gui.ElevatorDisplay.Direction.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;


public class Main{



    //Checks to see if the simulation has finished by checking to see if everybody has finished riding
    public static boolean finishedRunning(ArrayList<Person> People){
        for(Person p: People){
            if(!p.getFinishedTrip()){
                return false;
            }
        }
        return true;

    }







    public static void test(){




        ArrayList<Person> peopleElevators = new ArrayList<>();
        FileReader reader;
        try {
            // Create a FileReader object using your filename
            reader = new FileReader("input.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jObj;

        try {
            // Create a JSONParser using the FileReader
            jObj = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }

        // How to parse individual values
        int numFloors = (int)((long) jObj.get("NumFloors"));
        int numElevators = (int) ((long)jObj.get("NumElevators"));
        int maxPersonFloor = (int) ((long)jObj.get("MaxPerson"));
        int floorTime = (int) ((long)jObj.get("TimePerFloor"));
        int doorTime = (int)((long) jObj.get("DoorOpenTime"));
        int waitTime = (int) ((long)jObj.get("TimeOut"));
        int durationTime = (int) ((long)jObj.get("DurationTime"));
        int creationRate = (int) ((long)jObj.get("CreationRate"));


        Random rand = new Random();

        Building testBuilding = Building.getInstance();

        try{
            testBuilding.getInstance().init(numFloors,numElevators, floorTime,waitTime, doorTime, maxPersonFloor);

        }catch ( PositiveInputException ex){

        }

        int personCounter = 0;

        testBuilding.getInstance().initalizeDisplay();
        long initTime = System.currentTimeMillis();
        int i = 0;
        for (; i <= durationTime/1000; i++) {
            long currentTime = System.currentTimeMillis() - initTime;
            if (i%(creationRate)==0) {
                try{

                    personCounter++;
                    String ID = "P" + personCounter;
                    int startfloor = rand.nextInt(20)+1;
                    int endfloor;
                    do{
                        endfloor = rand.nextInt(20)+1;;

                    }while(endfloor == startfloor);
                    Person p = new Person(ID, startfloor, endfloor);

                    testBuilding.addPersontoFloor(p);
                    peopleElevators.add(p);
                    System.out.println("Time: " + getTimeStamp.getTimeStamp(currentTime) + " Person P" +  personCounter + " created on floor " + p.getStartFloor() + " wants to go to floor " + p.getDesiredFloor());
                    System.out.println("Time: " + getTimeStamp.getTimeStamp(currentTime) + " Person P" +  personCounter + " presses " + p.getDirection() + " on floor " + p.getStartFloor());
                    p.setWaitStart(currentTime);
                }catch (PositiveInputException ex){


                }
                catch (ElevatorBoundException ex){

                }






            }



            testBuilding.getInstance().operateElevators(currentTime);





            testBuilding.getInstance().displayElevators();


            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){

            }








        }

        do{
            long currentTime = System.currentTimeMillis() - initTime;
            testBuilding.getInstance().operateElevators(currentTime);







            testBuilding.getInstance().displayElevators();


            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){

            }
            i++;

        }while(!finishedRunning(peopleElevators));
        ArrayList<Long> waitTimes = new ArrayList<Long>();
        HashMap<Long, String> waitTimesMap = new HashMap();
        ArrayList<Long> rideTimes = new ArrayList<Long>();
        HashMap<Long,String> rideTimesMap = new HashMap();

        System.out.format("Person   Start Floor   End Floor    Direction    Wait Time    Ride Time    Total Time\n");
        for(Person p : peopleElevators){


                rideTimes.add(p.getRideTime());
                rideTimesMap.put(p.getRideTime(), p.getId());




                waitTimes.add(p.getWaitTime());
                waitTimesMap.put(p.getWaitTime(), p.getId());




            String dir = (p.getDirection().toString());
            System.out.format("%-10s %-11d %-15d %-13s %-13d %-13d %-13d\n", p.getId(), p.getStartFloor(), p.getDesiredFloor(), dir, p.getWaitTime(), p.getRideTime(), p.getTotalTime());

        }
        Collections.sort(waitTimes);
        Collections.sort(rideTimes);
        double avgWaitTime =0;
        double avgRideTime =0;
        for(long w: waitTimes){
            avgWaitTime +=w;

        }
        for(long w: rideTimes){
            avgRideTime +=w;

        }
        long minWaitTime = waitTimes.get(0);
        long maxWaitTime = waitTimes.get(waitTimes.size()-1);
        long minRideTime = rideTimes.get(0);
        long maxRideTime = rideTimes.get(rideTimes.size()-1);
        System.out.format("Avg Wait Time: %.02f seconds\n", avgWaitTime/waitTimes.size());
        System.out.format("Avg Ride Time: %.02f seconds\n", avgRideTime/rideTimes.size());
        System.out.format("Shortest wait time: " + waitTimes.get(0) + " seconds ");
        for(Map.Entry<Long,String> entry : waitTimesMap.entrySet()){
            if(minWaitTime == entry.getKey())
            System.out.format(" %s ", entry.getValue());
        }
        System.out.println();
        System.out.format("Longest wait time: " + waitTimes.get(waitTimes.size()-1) + " seconds ");
        for(Map.Entry<Long,String> entry : waitTimesMap.entrySet()){
            if(maxWaitTime == entry.getKey())
                System.out.format(" %s ", entry.getValue());
        }
        System.out.println();
        System.out.format("Shortest ride time: " + rideTimes.get(0)+ " ");
        for(Map.Entry<Long,String> entry : rideTimesMap.entrySet()){
            if(minRideTime == entry.getKey())
                System.out.format(" %s ", entry.getValue());
        }
        System.out.println();
        System.out.format("Longest ride time: " + rideTimes.get(rideTimes.size()-1) + " seconds ");
        for(Map.Entry<Long,String> entry : rideTimesMap.entrySet()){
            if(maxRideTime == entry.getKey())
                System.out.format(" %s ", entry.getValue());
        }
        System.out.println();

        testBuilding.shutDownElevator();





    }









    public static void main(String[] args) throws InterruptedException {

        //test1();
        //test2();
        //test3();
        test();

    }

    private static void moveElevator(int elevNum, int fromFloor, int toFloor) throws InterruptedException {
        int numRiders = (int) (11.0 * Math.random()) + 1;

        ElevatorDisplay.getInstance().closeDoors(elevNum);
        if (fromFloor < toFloor) {
            for (int i = fromFloor; i <= toFloor; i++) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, i, numRiders, UP);
                Thread.sleep(0);
            }
        } else {
            for (int i = fromFloor; i >= toFloor; i--) {
                ElevatorDisplay.getInstance().updateElevator(elevNum, i, numRiders, DOWN);
                Thread.sleep(0);
            }
        }
        ElevatorDisplay.getInstance().openDoors(elevNum);


    }


    }
