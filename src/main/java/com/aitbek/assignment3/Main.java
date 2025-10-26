package com.aitbek.assignment3;

import com.aitbek.assignment3.cli.CommandLineInterface;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting MST Algorithm Benchmark System...");

        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
}