package elevator;

public class getTimeStamp {
    public static String getTimeStamp(long milTime){
        long now = milTime;

        long hours = now/3600000;
        now -= (hours * 3600000);

        long minutes = now/60000;
        now -= (minutes *60000);

        long seconds = now/1000;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);


    }
}
