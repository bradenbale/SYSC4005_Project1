import java.time.Clock;
import java.util.*;
public class SimModel {

    //Sim Model Variables
    public static double seconds, minutes, hours, currentDay, simDays, Clock; // double to track decimals
    public static double I1BlockedTime, I2BlockedTime, IdleWS1, IdleWS2, IdleWS3, WS1inUse, WS2inUse, WS3inUse, I1inBusy, I2inBusy; // double to track decimals
    public static boolean WS1busy, WS2busy, WS3busy, I1busy, I2busy;
    private static Queue<SimEvent> FEL; //Future event list
    public static Queue<inspector> IQ; // the inspector queue when a buffer is full
    public static Queue<workstation> WQ; // the inspector queue when a buffer is full
    public static List WS1buffer1, WS2buffer1, WS2buffer2, WS3buffer1, WS3buffer2;

    // Inputs
    public static double[] servinsp1, servinsp22, servinsp23, ws1, ws2, ws3; //inspection times and workstation times
    public static int servinsp1_index, servinsp22_index, servinsp23_index, ws1_index, ws2_index, ws3_index; // to keep track of the element of interest
    // Metrics, Stats, and Counters
    private static double I1BT, I2BT; // double to track decimals


    private static void initialization(){
        // initializes the
        seconds = 0;
        minutes = 0;
        hours = 0;
        currentDay = 1;
        I1BT = 0;
        I2BT = 0;
        simDays = 5;
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

    private static void ProcessInspector(SimEvent evt) {
        System.out.print(" event = inspector has finished inspecting " + evt.getInspectorID().getID());
        if (!I1busy && evt.getInspectorID().getID() == 1) {
            if (WS1buffer1.size() < 2) {
                WS1buffer1.add(1);
                I1busy = true;
                I1inBusy = Clock;
            } else if (WS2buffer1.size() < 2) {
                WS2buffer1.add(1);
                I1busy = true;
                I1inBusy = Clock;
            } else if (WS3buffer1.size() < 2) {
                WS3buffer1.add(1);
                I1busy = true;
                I1inBusy = Clock;
            } else {
                I1busy = false;
                I1BlockedTime = Clock;
            }
        } else if (!I2busy && evt.getInspectorID().getID() == 2) {
            if (evt.getInspectorID().getComponentNumber() == 2) {
                if (WS2buffer2.size() < 2) {
                    WS2buffer2.add(2);
                    I2busy = true;
                    I2inBusy = Clock;
                } else {
                    I2busy = false;
                    I2BlockedTime = Clock;
                }
            } else if (evt.getInspectorID().getComponentNumber() == 3) {
                if (WS3buffer2.size() < 2) {
                    WS2buffer2.add(3);
                    I2busy = true;
                    I2inBusy = Clock;
                } else {
                    I2busy = false;
                    I2BlockedTime = Clock;
                }
            }
        }
    }

    private static void ProcessWorkstation(SimEvent evt){
        // find out which workstation completed their process and take the required components from the required queues
        System.out.print(" event = workstation has completed production " + evt.getWorkstationID().getID());
        if(!WS1busy && evt.getWorkstationID().getID() == 1){
            if(WS1buffer1.isEmpty()){
                IdleWS1 = Clock;
                WS1busy = false;
            }else{
                WS1busy = true;
                WS1inUse = Clock;
                evt.getWorkstationID().removeBuffer1();
                WS1buffer1.remove(0);
            }
        }else if(!WS2busy && evt.getWorkstationID().getID() == 2){
            if(!WS2buffer1.isEmpty() && !WS2buffer2.isEmpty()){
                WS2busy = true;
                WS2inUse = Clock;
                WS2buffer1.remove(0);
                WS2buffer2.remove(0);
            }else{
                IdleWS2 = Clock;
                WS2busy = false;
            }
        }else if(!WS3busy && evt.getWorkstationID().getID() == 3){
            if(!WS3buffer1.isEmpty() && !WS3buffer2.isEmpty()){
                WS3busy = true;
                WS3inUse = Clock;
                WS3buffer1.remove(0);
                WS3buffer2.remove(0);
            }else{
                IdleWS3 = Clock;
                WS3busy = false;
            }
        }
    }

    private static void ScheduleEvent(int index, double[] process_time_array, SimEvent incomingEvent, inspector ins, workstation work){
        double newRN = -1.0;
        switch(incomingEvent.geteType()){
            case I_process:
                //newRN = getRandomTime();
                break;
            case WS_process:
                //newRN = getRandomTime();
                break;
            case ES:
        }
    }

    private static void ProcessSimEvent(SimEvent nxtEvent){
        switch(nxtEvent.geteType()){
            case I_process:
                ProcessInspector(nxtEvent);
                break;
            case WS_process:
                ProcessWorkstation(nxtEvent);
                break;
            case ES:
        }
        System.out.print("\n");
    }
}