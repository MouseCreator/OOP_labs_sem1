package univ.lab.parser;

import java.util.List;

public interface Parser<T> {
    List<T> parse(String filename);
    void write(String filename, List<T> instances);
}
