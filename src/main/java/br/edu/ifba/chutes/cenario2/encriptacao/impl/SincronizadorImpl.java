package br.edu.ifba.chutes.cenario2.encriptacao.impl;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifba.chutes.cenario2.encriptacao.chaves.Sincronizador;

public class SincronizadorImpl implements Sincronizador {

    private static Sincronizador instancia = null;
    public static Sincronizador getInstancia() {
        if (instancia == null) {
            instancia = new SincronizadorImpl();
        }

        return instancia;
    }

    private SincronizadorImpl() {
    }

    private Map<String, String> chaves = new HashMap<>();

    @Override
    public void guardarChave(String id, String chave) {
        chaves.put(id, chave);
    }

    @Override
    public String recuperarChave(String id) {
        String chave = "";

        if (chaves.containsKey(id)) {
            chave = chaves.get(id);

            chaves.remove(id);
        }

        return chave;
    }
    
}
