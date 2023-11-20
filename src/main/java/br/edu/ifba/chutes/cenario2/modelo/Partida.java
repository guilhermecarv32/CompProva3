package br.edu.ifba.chutes.cenario2.modelo;

import java.util.ArrayList;
import java.util.List;

public class Partida implements Comparable<Partida>{
	
	private String id = "";
	
	private List<Chute> leituras = new ArrayList<>();
	private Chute ultimaLeitura = null;
	
	public Partida(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Chute> getLeituras(){
		return leituras;
	}
	
	public void setLeituras(List<Chute> leituras) {
		this.leituras = leituras;
	}
	
	public Chute getUltimaLeitura() {
		return ultimaLeitura;
	}
	
	public void setUltimaLeitura(Chute ultimaLeitura) {
        this.ultimaLeitura = ultimaLeitura;
    }
	
	public void onLeitura(Chute leitura) {
        this.leituras.add(leitura);
        this.ultimaLeitura = leitura;
    }
	
	@Override
    public int compareTo(Partida outraPartida) {
        return id.compareTo(outraPartida.getId());
    }
}
