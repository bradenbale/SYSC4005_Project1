import java.util.*;
public class SimEvent implements Comparable<SimEvent>{

    public static enum eventType {I_process, WS_process, ES};    // ALQ=Arrival at Loader Queue, EL=End of Loading, EW=End of Weighing, ES=End of Simulation
    private eventType eType;        // Type of the event
    private Integer eTime;          // Event Time
    private inspector InspectorID;      // Which truck is this event for.
    private workstation WorkstationID;

    public SimEvent(eventType eType, int eTime, inspector InspectorID, workstation WorkstationID) {
        this.eType = eType;
        this.eTime = eTime;
        this.InspectorID = InspectorID;
        this.WorkstationID = WorkstationID;
    }

    public int compareTo(SimEvent ev) {
        return this.geteTime().compareTo(ev.geteTime());
    }

    public eventType geteType() {
        return eType;
    }

    public void seteType(eventType eType) {
        this.eType = eType;
    }

    public Integer geteTime() {
        return eTime;
    }

    public void seteTime(int eTime) {
        this.eTime = eTime;
    }

    public inspector getInspectorID() {
        return InspectorID;
    }

    public void setInspectorID(inspector InspectorID) {
        this.InspectorID = InspectorID;
    }

    public workstation getWorkstationID() {
        return WorkstationID;
    }

    public void setWorkstationID(workstation WorkstationID) {
        this.WorkstationID = WorkstationID;
    }
}