package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class CreateClientService implements OperationService {
    private final ClientRepository clientRepository;

    @Override
    public String getOperation() {
        return "1";
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Введите дынные клиента");

        String phoneNumber = null;
        Client client = null;
        do {
            System.out.println("Номер телефона: ");
            phoneNumber = scanner.next();
            client = clientRepository.getClient(phoneNumber);
            if (client != null) {
                System.out.println("Клиетн с таким номером уже существует");
            }
        } while (client != null);

        System.out.println("Имя: ");
        String firstName = scanner.next();
        System.out.println("Фамилию: ");
        String lastName = scanner.next();
        System.out.println("Отчество: ");
        String patronymic = scanner.next();
        System.out.println("ИНН: ");
        String inn = scanner.next();
        System.out.println("Адрес: ");
        String address = scanner.next();

        clientRepository.addClient(Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patronymic(patronymic)
                .phoneNumber(phoneNumber)
                .inn(inn)
                .address(address)
                .build());
        System.out.println("Клиент: " + lastName + " успешно добавлен");
    }
}
