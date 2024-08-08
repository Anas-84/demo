package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Client;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CloseAccountService implements OperationService {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public String getOperation() {
        return "3";
    }

    @Override
    public void process(Scanner scanner) {
        Set<String> activeAccountNumbers = accountRepository.getActiveAccountNumbers();
        Set<Client> clients = clientRepository.getAllClients()
                .stream()
                .filter(client -> client.getAccountNumbers().stream().anyMatch(activeAccountNumbers::contains))
                .collect(Collectors.toSet());
        try {
            if (clients.isEmpty()) {
                throw new IllegalArgumentException("Нет клиетнов с открытыми счетами");
            }
            System.out.println("Клиенты с открытыми счетами: \n" + clients);
            System.out.println("Введите нормер телеофна клиента у которого необходимо закрыть счет.");
            System.out.println("Нормер телеофна: ");
            String phoneNumber = scanner.next();
            Client client = clientRepository.getClient(phoneNumber);
            if (client == null) {
                throw new IllegalArgumentException("Введен не корректный номер телефона");
            }
            System.out.println("Для закрытия введите нормер счета: ");
            String accountNumber = scanner.next();
            Account account = accountRepository.getAccount(accountNumber);
            if (account == null) {
                throw new IllegalArgumentException("Такого счета нет!");
            }
            account.setActive(false);
            accountRepository.addAccount(account);
            System.out.println("Счет успешно закрыт");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}