package univ.lab.inject;

public class GetIntegerImplementation implements GetIntegerInterface {
    private final int value;
    public GetIntegerImplementation() {
        this.value = 20;
    }
    public GetIntegerImplementation(int value) {
        this.value = value;
    }

    @Override
    public int get() {
        return value;
    }
}
