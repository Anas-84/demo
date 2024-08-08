package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

@RequiredArgsConstructor
public class ChargeSalaryService implements OperationService {
    private final AccountRepository accountRepository;

    @Override
    public String getOperation() {
        return "4";
    }

    @Override
    public void process(Scanner scanner) {
        Set<String> activeAccountNumbers = accountRepository.getActiveAccountNumbers();
        System.out.println("Список активных счетов: " + activeAccountNumbers);
        System.out.println("Введите счет зачисления:");
        String accountNumber = scanner.next();
        try {
            if (!activeAccountNumbers.contains(accountNumber)) {
                throw new IllegalArgumentException("Такого счета нет или не активен");
            }
            Account account = accountRepository.getAccount(accountNumber);
            System.out.println("Введите валюту: ");
            String currencyRequest = scanner.next();
            String currency = account.getCurrency();
            if (!currency.equalsIgnoreCase(currencyRequest)) {
                throw new IllegalArgumentException("Не верная валюта!");
            }
            System.out.println("Введите сумму зачисления:");
            BigDecimal sum = null;
            sum = scanner.nextBigDecimal();
            if (sum.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Нельзя ввести отрицательную сумму");
            }
            account.setSum(account.getSum().add(sum));
            accountRepository.addAccount(account);
            System.out.println("Счет успешно пополнен");
            System.out.println(account);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Не верный формат суммы");
        }
    }
}