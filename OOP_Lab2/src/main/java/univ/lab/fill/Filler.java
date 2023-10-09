package univ.lab.fill;

public interface Filler {
    <T> void fill(Object obj, String attribute, T value);
}
