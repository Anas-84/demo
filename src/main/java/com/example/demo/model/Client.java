package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class Client {
    private static final String MESSAGE = """
                        
            Номер телефона: %s 
            ФИО: %s %s %s
            Инн: %s
            Адрес: %s
            Номера счетов: %s
            """;

    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String inn;
    private String address;
    private final Set<String> accountNumbers = new HashSet<>();

    @Override
    public String toString() {
        return String.format(MESSAGE,
                phoneNumber,
                lastName, firstName, patronymic,
                inn,
                address,
                accountNumbers);
    }
}
