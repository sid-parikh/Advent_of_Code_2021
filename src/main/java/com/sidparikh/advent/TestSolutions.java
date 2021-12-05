package com.sidparikh.advent;

import com.sidparikh.advent.solutions.*;

import java.io.FileNotFoundException;

/**
 * This class serves as a Main class and runs each day's {@link Solution}.
 */
public class TestSolutions {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println(new Day01().getSolution());
        System.out.println(new Day02().getSolution());
        System.out.println(new Day03().getSolution());
        System.out.println(new Day04().getSolution());
        System.out.println(new Day05().getSolution());

    }

}
