package univ.lab.parser;

import univ.lab.model.Paper;

import java.util.List;

public interface Parser {
    List<Paper> parse(String filename);
    void write(String filename, List<Paper> instances);
}
