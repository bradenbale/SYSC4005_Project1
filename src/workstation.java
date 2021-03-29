import java.util.*;
public class workstation {
    public workstation(int ID) {
        this.ID = ID;
        this.buffer1 = new LinkedList<>(); // buffer for one component
        this.buffer2 = new LinkedList<>(); // buffer for one component
        this.full1 = full1; // full flag for buffer 1
        this.empty1 = empty1; // empty flag for buffer 1
        this.full2 = full2; // full flag for buffer 2
        this.empty2 = empty2; // empty flag for buffer 2
    }

    //initialize variables

    private int ID;
    private List buffer1 = new LinkedList<>();
    private List buffer2 = new LinkedList<>();
    private int full1;
    private int empty1;
    private int full2;
    private int empty2;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // use to determine is buffer is full

    public boolean getBuffer1Full() {
        if (buffer1.size() == 2) {
            this.full1 = 1;
            return true;
        } else {
            this.full1 = 0;
            return false;
        }
    }

    // use to determine is buffer is empty

    public boolean getBuffer1Empty() {
        if (buffer1.isEmpty()) {
            this.empty1 = 1;
            return true;
        } else {
            this.empty1 = 0;
            return false;
        }
    }

    // use to determine is buffer is full

    public boolean getBuffer2Full() {
        if (buffer2.size() == 2) {
            this.full2 = 1;
            return true;
        } else {
            this.full1 = 0;
            return false;
        }
    }

    // use to determine is buffer is empty

    public boolean getBuffer2Empty() {
        if (buffer2.isEmpty()) {
            this.empty2 = 1;
            return true;
        } else {
            this.empty2 = 0;
            return false;
        }
    }

    // add a component to buffer 1

    public void addBuffer1(int component) {
        if (this.getBuffer1Full()) { }
        else {
            this.buffer1.add(component);
        }
    }

    // add a component to buffer 2

    public void addBuffer2(int component) {
        if (this.getBuffer2Full()) { }
        else {
            this.buffer2.add(component);
        }
    }

    // remove a component from buffer 1

    public void removeBuffer1() {
        if (this.getBuffer1Empty()) { }
        else this.buffer1.remove(0);
    }

    // remove a component from buffer 2

    public void removeBuffer2() {
        if (this.getBuffer2Empty()) {
        } else this.buffer2.remove(0);
    }

}
