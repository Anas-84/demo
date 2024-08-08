package com.example.demo.repository;

import com.example.demo.model.Client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientRepository {
    private final Map<String, Client> clients = new HashMap<>();

    public Client getClient(String phoneNumber) {
        return clients.get(phoneNumber);
    }

    public void addClient(Client client) {
        clients.put(client.getPhoneNumber(), client);
    }

    public Collection<Client> getAllClients() {
        return clients.values();
    }
}
