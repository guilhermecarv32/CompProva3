package br.edu.ifba.chutes.cenario2.modelo;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Chute {
	
	@JsonProperty("velocidade")
    private int velocidade = 0;
    @JsonProperty("rpm")
	private int rpm = 0;
    @JsonProperty("forca")
	private int forca = 0;
    @JsonProperty("partida")
    private Partida partida;
    @JsonProperty("diagnostico")
    private String diagnostico = "";
	
    public Chute() {
    }

	public Chute(int velocidade, int rpm, int forca) {
		this.velocidade = velocidade;
		this.rpm = rpm;
		this.forca = forca;
	}
	
	public int getVelocidade() {
		return velocidade;
	}
	
	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}
	
	public int getRPM() {
		return rpm;
	}
	
	public void setRPM(int rpm) {
		this.rpm = rpm;
	}
	
	public int getForca() {
		return forca;
	}
	
	public void setForca(int forca) {
		this.forca = forca;
	}

    public Partida getPartida(){
        return partida;
    }

    public void setPartida(Partida partida){
        this.partida = partida;
    }
	
    public String getDiagnostico(){
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }

	@Override
	public String toString() {
		return "[Velocidade: " + velocidade + "RPM: " + rpm + "Forca: " + forca;
	}

    public static String toJson(Chute chute) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(chute);

        return json;
    }

    public static Chute fromJson(String json) throws IOException {
        Chute chute = new ObjectMapper().readValue(json, Chute.class);

        return chute;
    }
}