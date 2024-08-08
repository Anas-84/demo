package com.example.demo.service;

import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class ShowAllAccountsService implements OperationService {
    private final AccountRepository accountRepository;

    @Override
    public String getOperation() {
        return "6";
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Все счета: " + accountRepository.getAccounts());
    }
}
