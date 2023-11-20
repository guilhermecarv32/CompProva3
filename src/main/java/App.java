import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.github.javafaker.Faker;

import br.edu.ifba.chutes.cenario2.borda.sensores.SensoresImpl;
import br.edu.ifba.chutes.cenario2.encriptacao.impl.EncriptadorImpl;
import br.edu.ifba.chutes.cenario2.encriptacao.impl.GeradorDeChavesImpl;
import br.edu.ifba.chutes.cenario2.modelo.Partida;
import br.edu.ifba.chutes.cenario2.nuvem.impl.ExecutorImpl;

public class App {
    private static final String CAMINHO_DO_VIDEO = "D:\\Users\\User\\Desktop\\Guilherme\\java_progs_vscode\\avaliacao3\\avaliacao3\\ondas\\videoplayback.webm";
    
    private static final int TOTAL_DE_PARTIDAS = 10;
    private static final int TOTAL_DE_LEITURAS = 3;

    private static List<Thread> executores = new ArrayList<>();

    public static void iniciarProcessamentoDeLeituras(Map<Partida, SensoresImpl> partidas){
        for(Entry<Partida, SensoresImpl> item : partidas.entrySet()){
            Partida partida = item.getKey();
            SensoresImpl sensores = item.getValue();

            Thread executor = new Thread(new ExecutorImpl(partida, sensores, TOTAL_DE_LEITURAS));
            executores.add(executor);

            executor.start();
        }
    }

    public static void esperarFinalizacao() throws InterruptedException{
        for(Thread executor : executores){
            executor.join();
        }
    }

    public static Map<Partida, SensoresImpl> gerarPartidas(EncriptadorImpl encriptador){
        Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

        Map<Partida, SensoresImpl> partidas = new TreeMap<>();
        for (int i = 0; i <= TOTAL_DE_PARTIDAS; i++){
            int idPartida = faker.number().numberBetween(1, 10);
            Partida partida = new Partida("Partida #" + idPartida);

            partidas.put(partida, new SensoresImpl(encriptador));
        }

        return partidas;
    }
    
    public static void main(String[] args) throws Exception {
        GeradorDeChavesImpl gerador = new GeradorDeChavesImpl();
        gerador.iniciar(CAMINHO_DO_VIDEO);
        
        Map<Partida, SensoresImpl> partidas = gerarPartidas(new EncriptadorImpl(gerador));

        System.out.println("Iniciando Processamento...");
        iniciarProcessamentoDeLeituras(partidas);
        esperarFinalizacao();

        System.out.println("Processamento Finalizado.");
        gerador.finalizar();
    }
}
