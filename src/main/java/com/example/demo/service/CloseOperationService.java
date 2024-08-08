package com.example.demo.service;

import java.util.Scanner;

public class CloseOperationService implements OperationService {

    @Override
    public String getOperation() {
        return "0";
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("До скорых встреч!!!");
    }
}
