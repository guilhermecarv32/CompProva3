package br.edu.ifba.chutes.cenario2.nuvem.impl;

import java.io.IOException;

import br.edu.ifba.chutes.cenario2.borda.impl.CalcularMedia;
import br.edu.ifba.chutes.cenario2.borda.impl.DiagnosticoVelocidade;
import br.edu.ifba.chutes.cenario2.borda.sensores.SensoresImpl;
import br.edu.ifba.chutes.cenario2.encriptacao.FalhaEncriptacao;
import br.edu.ifba.chutes.cenario2.encriptacao.impl.EncriptadorImpl;
import br.edu.ifba.chutes.cenario2.modelo.Chute;
import br.edu.ifba.chutes.cenario2.modelo.Partida;
import br.edu.ifba.chutes.cenario2.nuvem.executor.Executor;

public class ExecutorImpl extends Executor{

    private Partida partida = null;
    private SensoresImpl sensores = null;

    CalcularMedia calculadorMedia;
    DiagnosticoVelocidade diagnostico = new DiagnosticoVelocidade();

    public ExecutorImpl(Partida partida, SensoresImpl sensores, int totalDeLeituras){
        super(totalDeLeituras);

        this.partida = partida;
        this.sensores = sensores;
        this.calculadorMedia = new CalcularMedia();
    }

    @Override
    public void processarLeitura(int leituraAtual){
        if(sensores.temLeitura()){
            String encriptacao = sensores.getLeitura();

            try {
                String json = new EncriptadorImpl(null).desencriptar(encriptacao);

                partida.onLeitura(Chute.fromJson(json));
                Chute leitura = partida.getUltimaLeitura();

                partida.onLeitura(leitura);
                double chute = calculadorMedia.atuar(partida.getLeituras());
                String verificarChutes = diagnostico.atuar(partida.getLeituras());
                
                System.out.println("Leitura registrada no chute " + leituraAtual + " da partida " + partida.getId() + ", " + verificarChutes + " Velocidade: " + chute + " km/h");
            } catch (FalhaEncriptacao | IOException e) {
                e.printStackTrace();
            }
        
        }
    }
    
}
