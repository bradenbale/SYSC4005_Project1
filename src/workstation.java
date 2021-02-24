import java.util.*;
public class workstation {
    public workstation(int ID) {
        this.ID = ID;
        this.buffer1 = new LinkedList<>();
        this.buffer2 = new LinkedList<>();
        this.full1 = full1;
        this.empty1 = empty1;
        this.full2 = full2;
        this.empty2 = empty2;
    }

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

    public boolean getBuffer1Full() {
        if (buffer1.size() == 2) {
            this.full1 = 1;
            return true;
        } else {
            this.full1 = 0;
            return false;
        }
    }

    public boolean getBuffer1Empty() {
        if (buffer1.isEmpty()) {
            this.empty1 = 1;
            return true;
        } else {
            this.empty1 = 0;
            return false;
        }
    }

    public boolean getBuffer2Full() {
        if (buffer2.size() == 2) {
            this.full2 = 1;
            return true;
        } else {
            this.full1 = 0;
            return false;
        }
    }

    public boolean getBuffer2Empty() {
        if (buffer2.isEmpty()) {
            this.empty2 = 1;
            return true;
        } else {
            this.empty2 = 0;
            return false;
        }
    }

    public void addBuffer1(int component) {
        if (this.getBuffer1Full()) { }
        else {
            this.buffer1.add(component);
        }
    }

    public void addBuffer2(int component) {
        if (this.getBuffer2Full()) { }
        else {
            this.buffer2.add(component);
        }
    }

    public void removeBuffer1() {
        if (this.getBuffer1Empty()) { }
        else this.buffer1.remove(0);
    }

    public void removeBuffer2() {
        if (this.getBuffer2Empty()) {
        } else this.buffer2.remove(0);
    }

}
