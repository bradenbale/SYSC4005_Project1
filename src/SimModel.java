import java.time.Clock;
import java.util.*;
public class SimModel {

    //Sim Model Variables
    public static double seconds, minutes, hours, currentDay, simDays, Clock, workdayHours; // double to track decimals
    public static double I1BlockedTime, I2BlockedTime, IdleWS1, IdleWS2, IdleWS3;
    public static boolean WS1busy, WS2busy, WS3busy, I1busy, I2busy;
    public static Random RNGws1, RNGws2, RNGws3, RNGi1, RNGi22, RNGi23;
    private static Queue<SimEvent> FEL; //Future event list
    public static Queue<inspector> IQ; // the inspector queue when a buffer is full
    public static Queue<workstation> WQ; // the inspector queue when a buffer is full
    public static List WS1buffer1, WS2buffer1, WS2buffer2, WS3buffer1, WS3buffer2;

    // Inputs
    public static double[][] ws1D = {{0.222, 0.09}, {0.666, 0.17}, {1.11, 0.245}, {1.554, 0.3159}, {1.998, }};
    public static double[][] ws2D = {{}, {}, {}};
    public static double[][] ws3D = {{}, {}, {}};
    public static double[][] i1D = {{}, {}, {}};
    public static double[][] i2D = {{}, {}, {}};

    public static int servinsp1_index, servinsp22_index, servinsp23_index, ws1_index, ws2_index, ws3_index; // to keep track of the element of interest
    // Metrics, Stats, and Counters
    private static double I1inBusy, I2inBusy; // holds in busy times of inspectors
    private static double WS1inUse, WS2inUse, WS3inUse; // holds in use times of workstations
    private static double UI1, UI2, UW1, UW2, UW3; // double to calculate utilization



    public static double generateInspectorTime(int insNum, int compNum){
        Random rand = new Random();
        double randTime=0;
        if(insNum==1){
            randTime= Math.log(1-rand.nextDouble())/(-0.0965);
        }
        else if(insNum==2 && compNum==2) {
            randTime= Math.log(1 - rand.nextDouble()) / (-0.0643);
        }
        else if(insNum==2 && compNum==3){
            randTime = Math.log(1-rand.nextDouble())/(-0.0485);
        }
        return randTime;
    }

    public static double generateWSTime(int wsNum){
        Random rand = new Random();
        double randTime=0;
        if(wsNum==1){
            randTime= Math.log(1-rand.nextDouble())/(-0.2171);
        }
        else if(wsNum==2) {
            randTime= Math.log(1 - rand.nextDouble()) / (-0.09015);
        }
        else if(wsNum==3){
            randTime =Math.log(1-rand.nextDouble())/(-0.1137);
        }
        return randTime;
    }

    private static void initialization(){
        Clock = 0.0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        currentDay = 1;
        I1BlockedTime = 0;
        I2BlockedTime = 0;
        I1inBusy=0;
        I2inBusy=0;
        WS1inUse=0;
        WS2inUse=0;
        WS3inUse=0;
        IdleWS1=0;
        IdleWS2=0;
        IdleWS3=0;
        UI1=0;
        UI2=0;
        UW1=0;
        UW2=0;
        UW3=0;

        simDays = 10;

        //Creating the inspectors
        inspector I1 = new inspector(1);
        inspector I2 = new inspector(2);

        //Creating the workstations
        workstation WS1 = new workstation(1);
        workstation WS2 = new workstation(2);
        workstation WS3 = new workstation(3);

        //Set inspector 1 to work on C1, inspector 2 works on either C2 or C3

        I1.setComponentNumber();

        System.out.print("\n-------------------------------\n");
        System.out.print("Initial State of the simulation\n");
        System.out.print("Inspector 1 working on C1 for WS1");
        I1busy=true;
        I1.setComponentNumber();
        SimEvent newEVT = new SimEvent(SimEvent.eventType.I_process,0, I1,null);
        ScheduleEvent(newEVT);
        System.out.println("");

        I2busy=true;
        I2.setComponentNumber();
        System.out.print("Inspector 2 working on C2 or C3 for WS2 or WS3\n");
        SimEvent newEVT2 = new SimEvent(SimEvent.eventType.I_process,0, I2,null);
        ScheduleEvent(newEVT2);
        System.out.println("");
    }

    public static void main(String[] args) {
        //long seed = Long.parseLong("12345");    // Creating a seed for the random number generators
        // Initializing the RNGs
        RNGws1 = new Random();
        RNGws2 = new Random();
        RNGws3 = new Random();
        RNGi1 = new Random();
        RNGi22 = new Random();
        RNGi23 = new Random();

        FEL = new PriorityQueue<>();            // Initializing the FEL and waiting queues
        WQ = new LinkedList<>();
        IQ = new LinkedList<>();

        initialization();

        System.out.print("\n-----------------------------------------------------------\n");
        System.out.print("Day " + currentDay +"\n");
        while ((currentDay <= simDays) && !(FEL.isEmpty())) {
            SimEvent imminentEVT = FEL.poll();
            if (imminentEVT != null) {
                Clock = imminentEVT.geteTime();
                System.out.print("Clock = " + Clock);
                ProcessSimEvent(imminentEVT);
            }
            //System.out.print("SIM: FEL isEmpty = " + FEL.isEmpty() +"\n");
        }
        GenerateReport();
    }

    private static void GenerateReport(){
        UI1 = (I1inBusy/Clock)*100;
        UI2 = (I2inBusy/Clock)*100;
        UW1 = (WS1inUse/Clock)*100;
        UW2 = (WS2inUse/Clock)*100;
        UW3 = (WS3inUse/Clock)*100;

        System.out.print("\n---------------------------------------------------------\n");
        System.out.print("Statistics\n");
        System.out.print("Inspector 1 Utilization = " + UI1 + "\n");
        System.out.print("Inspector 1 Utilization = " + UI2 + "\n");
        System.out.print("Workstation 1 Utilization = " + UW1 + "\n");
        System.out.print("Workstation 2 Utilization = " + UW2 + "\n");
        System.out.print("Workstation 3 Utilization = " + UW3 + "\n");
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

    private static void checkSimDay(double clockVal) {
        if (((Clock/(workdayHours*60)) + 1) > currentDay) {
            //    currentDay += 1;
            //Clock = 0;
            currentDay = (Clock/(workdayHours*60)) + 1;
            System.out.print("\n-----------------------------------------------------------\n");
            System.out.print("Day " + currentDay +"\n");
        }

    }

    private static void ProcessInspector(SimEvent evt) {
        System.out.print(" event = inspector has finished inspecting " + evt.getInspectorID().getID());
        if (!I1busy && evt.getInspectorID().getID() == 1) {
            if (WS1buffer1.size() < 2) {
                WS1buffer1.add(1);
                I1busy = true;
                evt.getInspectorID().setComponentNumber();
                I1inBusy += Clock - I1BlockedTime;
                ScheduleEvent(evt);
                //ScheduleEvent(new SimEvent(SimEvent.eventType.WS_process, 0, null, 1));
            } else if (WS2buffer1.size() < 2) {
                WS2buffer1.add(1);
                I1busy = true;
                evt.getInspectorID().setComponentNumber();
                I1inBusy += Clock - I1BlockedTime;
                ScheduleEvent(evt);
            } else if (WS3buffer1.size() < 2) {
                WS3buffer1.add(1);
                I1busy = true;
                evt.getInspectorID().setComponentNumber();
                I1inBusy += Clock - I1BlockedTime;
                ScheduleEvent(evt);
            } else {
                I1busy = false;
                I1BlockedTime = Clock;
            }
        } else if (!I2busy && evt.getInspectorID().getID() == 2) {
            if (evt.getInspectorID().getComponentNumber() == 2) {
                if (WS2buffer2.size() < 2) {
                    WS2buffer2.add(2);
                    evt.getInspectorID().setComponentNumber();
                    I2busy = true;
                    I2inBusy += Clock - I2BlockedTime;
                    ScheduleEvent(evt);
                } else {
                    I2busy = false;
                    I2BlockedTime = Clock;
                }
            } else if (evt.getInspectorID().getComponentNumber() == 3) {
                if (WS3buffer2.size() < 2) {
                    WS3buffer2.add(3);
                    evt.getInspectorID().setComponentNumber();
                    I2busy = true;
                    I2inBusy = Clock;
                    ScheduleEvent(evt);
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
                WS1inUse += Clock - IdleWS1;
                WS1buffer1.remove(0);
                ScheduleEvent(evt);
            }
        }else if(!WS2busy && evt.getWorkstationID().getID() == 2){
            if(!WS2buffer1.isEmpty() && !WS2buffer2.isEmpty()){
                WS2busy = true;
                WS2inUse += Clock - IdleWS2;
                WS2buffer1.remove(0);
                WS2buffer2.remove(0);
                ScheduleEvent(evt);
            }else{
                IdleWS2 = Clock;
                WS2busy = false;
            }
        }else if(!WS3busy && evt.getWorkstationID().getID() == 3){
            if(!WS3buffer1.isEmpty() && !WS3buffer2.isEmpty()){
                WS3busy = true;
                WS3inUse += Clock - IdleWS3;
                WS3buffer1.remove(0);
                WS3buffer2.remove(0);
                ScheduleEvent(evt);
            }else{
                IdleWS3 = Clock;
                WS3busy = false;
            }
        }
    }

    private static void ScheduleEvent(SimEvent incomingEvent){
        double newRN = -1.0;
        switch(incomingEvent.geteType()){
            case I_process:
                newRN = generateInspectorTime(incomingEvent.getInspectorID().getID(), incomingEvent.getInspectorID().getComponentNumber());
                break;
            case WS_process:
                newRN = generateWSTime(incomingEvent.getWorkstationID().getID());
                break;
            case ES:
        }
        checkSimDay(newRN);
        SimEvent newEVT = new SimEvent(incomingEvent.geteType(), Clock + newRN, incomingEvent.getInspectorID(), incomingEvent.getWorkstationID());
        if(newEVT.geteType().equals(SimEvent.eventType.I_process)){
            System.out.print(" => new event = " + newEVT.geteType() + " time " + newEVT.geteTime() + " inspector " + newEVT.getInspectorID().getID());
        }else{
            System.out.print(" => new event = " + newEVT.geteType() + " time " + newEVT.geteTime() + " workstation " + newEVT.getWorkstationID().getID());
        }
        FEL.offer(newEVT);
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