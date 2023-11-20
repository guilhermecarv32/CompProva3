package br.edu.ifba.chutes.cenario2.encriptacao.chaves;

import br.edu.ifba.chutes.cenario2.encriptacao.FalhaGeracaoDeChaves;

public interface GeradorDeChaves {
    
    public void iniciar(String caminhoVideo)  throws FalhaGeracaoDeChaves;

    public String gerarChave()  throws FalhaGeracaoDeChaves ;

    public String getUltimaChave();

    public void finalizar()  throws FalhaGeracaoDeChaves;

}