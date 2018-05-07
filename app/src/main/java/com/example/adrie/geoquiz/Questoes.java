package com.example.adrie.geoquiz;

public class Questoes {

    private int perguntas;
    private boolean respostas;


    public Questoes(int perguntas, boolean respostas) {
        this.perguntas = perguntas;
        this.respostas = respostas;
    }

    public int getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(int perguntas) {
        this.perguntas = perguntas;
    }

    public boolean isRespostas() {
        return respostas;
    }

    public void setRespostas(boolean respostas) {
        this.respostas = respostas;
    }

}

