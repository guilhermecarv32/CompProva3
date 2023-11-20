package br.edu.ifba.chutes.cenario2.encriptacao.encriptador;

import br.edu.ifba.chutes.cenario2.encriptacao.FalhaEncriptacao;
import br.edu.ifba.chutes.cenario2.encriptacao.chaves.GeradorDeChaves;

public abstract class Encriptador<Gerador extends GeradorDeChaves> {
    
    protected Gerador gerador = null;

    public Encriptador(Gerador gerador) {
        this.gerador = gerador;
    }

    public abstract String encriptar(String dados) throws FalhaEncriptacao;

    public abstract String desencriptar(String encriptacao) throws FalhaEncriptacao;

}