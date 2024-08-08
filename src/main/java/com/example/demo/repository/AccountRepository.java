package com.example.demo.repository;

import com.example.demo.model.Account;

import java.util.*;
import java.util.stream.Collectors;

public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void addAccount(Account account) {
        accounts.put(account.getNumber(), account);
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    public Set<String> getActiveAccountNumbers() {
        return getAccounts()
                .stream()
                .filter(Account::isActive)
                .map(Account::getNumber)
                .collect(Collectors.toSet());
    }
}
