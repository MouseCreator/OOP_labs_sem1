package univ.lab.parser;

import univ.lab.model.Papers;


public interface Parser {
    Papers parse(String filename);
    void write(String filename, Papers instances);
}
