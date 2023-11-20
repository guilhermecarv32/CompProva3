package br.edu.ifba.chutes.cenario2.borda.impl;

import java.util.List;

import br.edu.ifba.chutes.cenario2.borda.atuador.Atuador;
import br.edu.ifba.chutes.cenario2.modelo.Chute;

public class DiagnosticoVelocidade implements Atuador<Chute, String>{

	private static final int MARGEM_SUP_VELOCIDADE = 80;
	private static final int MARGEM_INF_VELOCIDADE = 60;
    
        @Override
    public String atuar(List<Chute> leituras) {
        String diagnostico = "";
        double indiceChute = 0;

        for(Chute leitura : leituras){
            indiceChute += leitura.getVelocidade();
        }

        indiceChute /= leituras.size();

        if(indiceChute >= MARGEM_SUP_VELOCIDADE){
            diagnostico += "Chute com velocidade ACIMA da media!";
        } else if (indiceChute >= MARGEM_INF_VELOCIDADE && indiceChute <= MARGEM_SUP_VELOCIDADE){
            diagnostico += "Chute dentro da media.";
        } else{
            diagnostico += "Chute com velocidade ABAIXO da media!";
        }

        return diagnostico;
    }
}