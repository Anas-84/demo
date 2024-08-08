package com.example.demo;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static final String MESSAGE = """
            Список операций:
            0. Покинуть приложение
            1. Создавать клиента.
            2. Создавать счет клиента.
            3. Закрывать счет клиента
            4. Зачислять денежные средства (например, зарплата)
            5. Переводить денежные средства
            6. Просмотреть все счета
            7. Просмотреть всех клиентов
            """;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, OperationService> operations = getInitOperations();
        System.out.println(MESSAGE);
        while (true) {
            System.out.println();
            System.out.println("Введите операцию: ");
            String operation = scanner.next();
            if ("0".equals(operation)) {
                System.out.println("До скорых встречь!");
                return;
            }
            OperationService operationService = operations.get(operation);
            if (operationService == null) {
                System.out.println("Такой операции не существует!");
            } else {
                operationService.process(scanner);
            }
        }
    }

    private static Map<String, OperationService> getInitOperations() {
        AccountRepository accountRepository = new AccountRepository();
        ClientRepository clientRepository = new ClientRepository();
        List<OperationService> operations = List.of(new CloseOperationService(),
                new CreateClientService(clientRepository),
                new CreateAccountService(accountRepository, clientRepository),
                new CloseAccountService(accountRepository, clientRepository),
                new ChargeSalaryService(accountRepository),
                new MoneyTransferService(accountRepository),
                new ShowAllAccountsService(accountRepository),
                new ShowAllClientsService(clientRepository));
        return operations.stream()
                .collect(Collectors.toMap(OperationService::getOperation, Function.identity()));
    }
}
