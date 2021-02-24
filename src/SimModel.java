import java.util.*;
public class SimModel {

    //Sim Model Variables
    public static double seconds, minutes, hours, currentDay; // double to track decimals
    public static double I1BlockedTime, I2BlockedTime, IdleWS1, IdleWS2, IdleWS3; // double to track decimals
    private static Queue<SimEvent> FEL; //Future event list
    public static Queue<inspector> IQ; // the inspector queue when a buffer is full
    public static Queue<workstation> WQ; // the inspector queue when a buffer is full

    // Inputs
    public static double[] servinsp1, servinsp22, servinsp23, ws1, ws2, ws3; //inspection times and workstation times
    public static int servinsp1_index, servinsp22_index, servinsp23_index, ws1_index, ws2_index, ws3_index; // to keep track of the element of interest
    // Metrics, Stats, and Counters
    private static double I1BT, I2BT; // double to track decimals


    private static double[] readFile(String fileName) {
        // reads from given data files and returns an array
    }

    private static void initialization(){
        // initializes the
    }

    public static void main(String[] args) {

    }

    private static void GenerateReport(){

    }

    private static void checkMinutes(double seconds) {
        if (seconds == 60){
            minutes += 1;
            seconds = 0;
        }
    }

    private static void checkHour(double minutes) {
        if (minutes == 60){
            hours += 1;
            minutes = 0;
        }
    }

    private static void checkDay(double hours) {
        if (hours == 24){
            currentDay += 1;
            hours = 0;
        }
    }

    private static void ProcessInspector(SimEvent evt){

    }

    private static void ProcessWorkstation(SimEvent evt){

    }

    private static void ScheduleEventInspector(int index, double[] process_time_array, SimEvent.eventType eType, inspector ins){

    }

    private static void ScheduleEventWorkstation(int index, double[] process_time_array, SimEvent.eventType eType, workstation ws){

    }

    private static void ProcessSimEvent(SimEvent nxtEvent){

    }

}