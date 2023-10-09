package univ.lab.parser;

import java.util.List;

public interface Parser<T> {
    List<T> parse();
    void write(List<T> instances);
}
