package br.edu.ifba.chutes.cenario2.encriptacao.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import br.edu.ifba.chutes.cenario2.encriptacao.FalhaEncriptacao;
import br.edu.ifba.chutes.cenario2.encriptacao.FalhaGeracaoDeChaves;
import br.edu.ifba.chutes.cenario2.encriptacao.encriptador.Encriptador;

public class EncriptadorImpl extends Encriptador<GeradorDeChavesImpl> {

    private static final String ALGORITIMO_DE_ENCRIPTACAO = "AES";

    public EncriptadorImpl(GeradorDeChavesImpl gerador) {
        super(gerador);
    }

    private byte[] chaveToByte(String chave) {
        int len = chave.length();

        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(chave.charAt(i), 16) << 4) +
                    Character.digit(chave.charAt(i + 1), 16));
        }

        return bytes;
    }

    @Override
    public String encriptar(String dados) throws FalhaEncriptacao {
        String encriptacao = "";

        synchronized (encriptacao) {
            try {
                String chave = gerador.gerarChave();

                Cipher cifrador = Cipher.getInstance(ALGORITIMO_DE_ENCRIPTACAO);
                cifrador.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chaveToByte(chave), ALGORITIMO_DE_ENCRIPTACAO));

                byte[] cifragem = cifrador.doFinal(dados.getBytes(StandardCharsets.UTF_8));
                encriptacao = Base64.getEncoder().encodeToString(cifragem);

                SincronizadorImpl.getInstancia().guardarChave(encriptacao, chave);
            } catch (FalhaGeracaoDeChaves e) {
                throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
            } catch (NoSuchPaddingException e) {
                throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
            } catch (InvalidKeyException e) {
                throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
            } catch (IllegalBlockSizeException e) {
                throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
            } catch (BadPaddingException e) {
                throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
            }

        }
        
        return encriptacao;
    }

    @Override
    public String desencriptar(String encriptacao) throws FalhaEncriptacao {
        String dados = null;

        try {
            String chave = SincronizadorImpl.getInstancia().recuperarChave(encriptacao);

            Cipher cifrador = Cipher.getInstance(ALGORITIMO_DE_ENCRIPTACAO);
            cifrador.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chaveToByte(chave), ALGORITIMO_DE_ENCRIPTACAO));

            byte[] bytes = Base64.getDecoder().decode(encriptacao);
            byte[] bytesDecriptados = cifrador.doFinal(bytes);

            dados = new String(bytesDecriptados, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
        } catch (BadPaddingException e) {
            throw new FalhaEncriptacao("falha encriptando dados: " + e.getMessage());
        }

        return dados;
    }

}