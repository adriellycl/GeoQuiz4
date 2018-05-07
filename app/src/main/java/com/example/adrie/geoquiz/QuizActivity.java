package com.example.adrie.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int REQUEST_CHEAT_CODE = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mBtnCheat;
    private ImageButton mAvancar;
    private ImageButton mVoltar;
    private TextView mPerguntas;
    private TextView mPontuacao;

    private Questoes[] questoes = new Questoes[] {
            new Questoes(R.string.questao1, false),
            new Questoes(R.string.questao2, false),
            new Questoes(R.string.questao3, true),
            new Questoes(R.string.questao4, true),
            new Questoes(R.string.questao5, true) };

    private boolean mCheater;
    private int mIndexArray = 0;
    private int mPontuacaoValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Collections.shuffle(Arrays.asList(questoes));

        mPontuacao = (TextView) findViewById(R.id.pontuacao);

        mPerguntas = (TextView) findViewById(R.id.pergunta);
        mPerguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questoes.length != 0) {
                    mIndexArray = (mIndexArray + 1) % questoes.length;
                    atualizarQuestoes();
                }
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarQuestao(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarQuestao(false);
            }
        });

        mAvancar = (ImageButton) findViewById(R.id.next_button);
        mAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndexArray = (mIndexArray + 1) % questoes.length;
                mCheater = false;
                atualizarQuestoes();
            }
        });

        mVoltar = (ImageButton) findViewById(R.id.prev_button);
        mVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIndexArray == 0) {
                    mIndexArray = questoes.length - 1;
                } else {
                    mIndexArray = mIndexArray - 1;
                }
                mCheater = false;
                atualizarQuestoes();
            }
        });

        mBtnCheat = (Button) findViewById(R.id.botao_trapacear_1);
        mBtnCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean resposta = questoes[mIndexArray].isRespostas();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, resposta);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

    }

    private void atualizarQuestoes() {
        int questao =  questoes[mIndexArray].getPerguntas();
        mPerguntas.setText(questao);
    }

    private void checarQuestao(boolean respostaUsuario) {
        boolean respostaCorreta = questoes[mIndexArray].isRespostas();
        int idPergunta;

        if(mCheater) {
            idPergunta = R.string.trapacear_errado;
            mPontuacaoValue++;
        } else {
            if(respostaCorreta == respostaUsuario) {
                idPergunta = R.string.correto;
                mPontuacaoValue++;
                mPontuacao.setText(String.valueOf(mPontuacaoValue));
            } else {
                idPergunta = R.string.incorreto;
            }
        }

        Toast.makeText(this, idPergunta, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CHEAT_CODE) {
            if(dados == null) {
                return;
            }
            mCheater = CheatActivity.eRespostaMostrada(dados);
        }
    }

}