package univ.lab.problem4.model;

public abstract class Animal {
    private final String name;
    protected final int id;
    public Animal(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public abstract void run();
}
