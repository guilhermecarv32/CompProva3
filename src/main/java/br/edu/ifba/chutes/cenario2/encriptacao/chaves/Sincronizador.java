package br.edu.ifba.chutes.cenario2.encriptacao.chaves;

public interface Sincronizador {
    
    public void guardarChave(String id, String chave);

    public String recuperarChave(String id);

}
