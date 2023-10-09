package univ.lab.fill;

public interface Filler {
    <T> void fill(Object toInitialize, String attribute, T value);
}
