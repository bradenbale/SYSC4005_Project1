import java.util.*;
public class inspector {

    public inspector(int ID){
        this.ID = ID; // ID for the inspector
        this.inpsComponent = 0; // which component the inspector is currently working on
    }

    //initialize variables

    private int ID;
    private int inpsComponent;
    private Random random = new Random();
    private double comparator = 0.5;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getComponentNumber() {
        return inpsComponent;
    }

    // for ID one the component is always 1, for two, the component is either 2 or 3

    public void setComponentNumber() {
        if(ID == 1) this.inpsComponent = 1;
        else {
            if(random.nextGaussian() > comparator){
                this.inpsComponent = 2;
            } else {
                this.inpsComponent = 3;
            }
        }
    }

}
