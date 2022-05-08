package com.example.themagicg.utils;

import java.util.UUID;
//Classe Criada para gerar uma identificação única para cada requisição feita pelo usuário, desde que instânciada.
public final class UniqueId {

    private static UniqueId instance;
    private final String uuid;

    private UniqueId() {
        this.uuid = UUID.randomUUID().toString();
    }

    public static synchronized UniqueId getInstance() {
        if (instance == null) {
            instance = new UniqueId();
        }
        return instance;
    }

    public String getUuid() {
        return uuid;
    }

    public static void clearInstance() {
        instance = null;
    }

}
