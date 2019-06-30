package elevator;

public class Direction {



    public enum Direct{
        UP, Down, IDLE

    }




    public static Direct getDirection(int startFloor, int endFloor){

        if(startFloor < endFloor) {

            return Direct.UP;
        }
        else if(startFloor > endFloor){

            return Direct.Down;

        }


        return Direct.IDLE;




    }

}
