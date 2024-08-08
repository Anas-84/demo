package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

@RequiredArgsConstructor
public class MoneyTransferService implements OperationService {
    private final AccountRepository accountRepository;

    @Override
    public String getOperation() {
        return "5";
    }

    @Override
    public void process(Scanner scanner) {
        Set<String> activeAccountNumbers = accountRepository.getActiveAccountNumbers();
        System.out.println("Список активных счетов: " + activeAccountNumbers);
        System.out.println("Введите счет списания:");
        String accountNumberFrom = scanner.next();
        System.out.println("Введите счет зачисления:");
        String accountNumberTo = scanner.next();
        System.out.println("Введите сумму перевода: ");
        try {
            BigDecimal sum = scanner.nextBigDecimal();
            if (sum.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Отрицательная сумма");
            }
            Account accountFrom = accountRepository.getAccount(accountNumberFrom);
            Account accountTo = accountRepository.getAccount(accountNumberTo);
            String currencyFrom = accountFrom.getCurrency();
            if (!currencyFrom.equalsIgnoreCase(accountTo.getCurrency())) {
                throw new IllegalArgumentException("У счетов разные валюты");
            }
            if (accountFrom.getSum().compareTo(sum) < 0) {
                throw new IllegalArgumentException("Недостаточно средств для перевода " +
                        sum + " " + currencyFrom + " со счета: " + accountNumberFrom);
            }
            accountFrom.setSum(accountFrom.getSum().subtract(sum));
            accountTo.setSum(accountTo.getSum().add(sum));
            accountRepository.addAccount(accountFrom);
            accountRepository.addAccount(accountTo);
            System.out.println("Перевод успешно проведун со счета: " + accountNumberFrom +
                    " на счет: " + accountNumberTo + " на сумму: " + sum + " " + currencyFrom);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Не верный формат суммы");
        }
    }
}
