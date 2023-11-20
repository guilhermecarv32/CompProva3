package br.edu.ifba.chutes.cenario2.borda.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifba.chutes.cenario2.borda.atuador.Atuador;
import br.edu.ifba.chutes.cenario2.modelo.Chute;
import br.edu.ifba.chutes.cenario2.modelo.Partida;

public class CalcularMedia implements Atuador<Chute, Double>{
    
    @Override
    public Double atuar(List<Chute> leituras) {
        Map<Partida, List<Chute>> leiturasPartidas = new HashMap<>();
        
        // Agrupa as leituras por partida
        for (Chute chute : leituras) {
            leiturasPartidas.computeIfAbsent(chute.getPartida(), k -> new ArrayList<>()).add(chute);
        }

        double somaVelocidades = 0;
        int contador = 0;

        for (List<Chute> leiturasPartida : leiturasPartidas.values()) {
            for (Chute chute : leiturasPartida) {
                for (Chute outroChute : leiturasPartida) {
                    somaVelocidades += (chute.getVelocidade() + outroChute.getVelocidade()) / 2;
                    contador++;
                }
            }
        }

        if (contador == 0) {
            return 0.0;
        }

        return somaVelocidades / contador;
    }

}

