package br.edu.ifba.chutes.cenario2.borda.sensores;

public interface Sensores<Leitura>{

    public boolean temLeitura();

    public String getLeitura();
}
