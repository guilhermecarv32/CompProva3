package br.edu.ifba.chutes.cenario2.encriptacao.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import br.edu.ifba.chutes.cenario2.encriptacao.FalhaGeracaoDeChaves;
import br.edu.ifba.chutes.cenario2.encriptacao.chaves.GeradorDeChaves;

public class GeradorDeChavesImpl implements GeradorDeChaves {

    private static int TENTATIVAS_CAPTURA_DE_QUADRO = 20;

    private FFmpegFrameGrabber grabber;
    private String ultimaChave;

    @Override
    public void iniciar(String caminhoVideo) throws FalhaGeracaoDeChaves {
        Loader.load(org.bytedeco.opencv.global.opencv_core.class);

        grabber = new FFmpegFrameGrabber(caminhoVideo);
        try {
            grabber.start();
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("falha de inicialização: " + e.getMessage());
        }
    }

    public Frame proximoQuadro() throws FalhaGeracaoDeChaves {
        Frame quadro = null;

        try {
            quadro = grabber.grab();
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("falha de inicialização: " + e.getMessage());
        }

        return quadro;
    }

    public BufferedImage proximaImage() throws FalhaGeracaoDeChaves {
        BufferedImage imagem = null;

        Java2DFrameConverter conversor = new Java2DFrameConverter();
        int tentativas = 0;
        do {
            tentativas++;

            Frame quadro = proximoQuadro();
            imagem = conversor.convert(quadro);
        } while ((imagem == null) && (tentativas < TENTATIVAS_CAPTURA_DE_QUADRO));

        conversor.close();

        return imagem;
    }

    @Override
    public String gerarChave() throws FalhaGeracaoDeChaves {
        ultimaChave = "";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(proximaImage(), "jpg", stream);
            byte[] bytes = stream.toByteArray();

            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(bytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            ultimaChave = hexString.toString();

        } catch (IOException | FalhaGeracaoDeChaves e) {
            throw new FalhaGeracaoDeChaves("falha gerando chave: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new FalhaGeracaoDeChaves("falha gerando chave: " + e.getMessage());
        }

        return ultimaChave;
    }

    @Override
    public String getUltimaChave() {
        return ultimaChave;
    }

    @Override
    public void finalizar() throws FalhaGeracaoDeChaves {
        try {
            grabber.stop();
            grabber.release();
        } catch (Exception e) {
            throw new FalhaGeracaoDeChaves("falha finalizando: " + e.getMessage());
        }
    }

}
