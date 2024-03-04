package univ.lab.problem4.model;

public class Bird extends Animal implements Flyable{

    private int wingLength;
    public Bird(String name, int id) {
        super(name, id);
    }

    @Override
    public void run() {
        System.out.println("Running...");
    }

    @Override
    public void fly(int duration) {
        System.out.println("Flying for " + duration + " seconds...");
    }

    public int getWingLength() {
        return wingLength;
    }

    private void sit() {
        System.out.println("Sitting...");
    }

    public void setWingLength(int wingLength) {
        this.wingLength = wingLength;
    }

    @Override
    public String toString() {
        return "Bird{" +
                "wingLength=" + wingLength +
                '}';
    }
}
