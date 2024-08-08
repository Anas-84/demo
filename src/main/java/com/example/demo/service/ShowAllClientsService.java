package com.example.demo.service;

import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class ShowAllClientsService implements OperationService {
    private final ClientRepository clientRepository;

    @Override
    public String getOperation() {
        return "7";
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Все клиенты: " + clientRepository.getAllClients());
    }
}
