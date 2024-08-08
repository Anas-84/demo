package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Client;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateAccountService implements OperationService {
    private static final Set<String> SUCCESS_CURRENCY = Set.of("RUB", "EUR", "USD");
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public String getOperation() {
        return "2";
    }

    @Override
    public void process(Scanner scanner) {
        Collection<Client> clients = clientRepository.getAllClients();
        try {
            if (clients.isEmpty()) {
                throw new IllegalArgumentException("На данный момент клиентов нет, необходимо завести!");
            }
            System.out.println("Клиенты: \n" + clients);
            System.out.println();
            System.out.println("Вудите номер телефона которуму клиенту хотите открыть счет");
            System.out.println("Номер телефона: ");
            String phoneNumber = scanner.next();
            Set<String> phoneNumbers = clients.stream()
                    .map(Client::getPhoneNumber)
                    .collect(Collectors.toSet());
            if (!phoneNumbers.contains(phoneNumber)) {
                throw new IllegalArgumentException("Клиента с таким номером телефона нет");
            }
            Client client = clientRepository.getClient(phoneNumber);
            Set<String> accountNumbers = client.getAccountNumbers();
            String accountNumber = null;
            do {
                System.out.println("Введите номер счета");
                accountNumber = scanner.next();
                if (accountNumbers.contains(accountNumber)) {
                    System.out.println("Такой счет уже существует");
                }
            } while (accountNumbers.contains(accountNumber));
            System.out.println("Введите БИК");
            String bic = scanner.next();
            System.out.println("Введите валюту");
            String currency = scanner.next();
            if (!SUCCESS_CURRENCY.contains(currency)) {
                throw new IllegalArgumentException("Не корректная валюта. Допустимые значения: " + SUCCESS_CURRENCY);
            }
            Account account = Account.builder()
                    .number(accountNumber)
                    .sum(BigDecimal.ZERO)
                    .active(true)
                    .bic(bic)
                    .currency(currency)
                    .build();
            accountRepository.addAccount(account);
            accountNumbers.add(accountNumber);
            clientRepository.addClient(client);
            System.out.println("Счет для клиента успешно создан");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
