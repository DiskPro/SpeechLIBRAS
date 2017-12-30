package com.example.guilhermefrancisco.speechrcg;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView voiceI;
    private ImageButton speakB;

    private String resultTxt = "Hello!";


    //TODO: Transfer this to a JSON file
    private static final Map<String, Integer> mapaLetra;
    static
    {
        mapaLetra = new HashMap<String, Integer>();
        mapaLetra.put("a", 0);
        mapaLetra.put("b", 1);
        mapaLetra.put("c", 2);
        mapaLetra.put("d", 3);
        mapaLetra.put("e", 4);
        mapaLetra.put("f", 5);
        mapaLetra.put("g", 6);
        mapaLetra.put("h", 7);
        mapaLetra.put("i", 8);
        mapaLetra.put("j", 9);
        mapaLetra.put("k", 10);
        mapaLetra.put("l", 11);
        mapaLetra.put("m", 12);
        mapaLetra.put("n", 13);
        mapaLetra.put("o", 14);
        mapaLetra.put("p", 15);
        mapaLetra.put("q", 16);
        mapaLetra.put("r", 17);
        mapaLetra.put("s", 18);
        mapaLetra.put("t", 19);
        mapaLetra.put("u", 20);
        mapaLetra.put("v", 21);
        mapaLetra.put("w", 22);
        mapaLetra.put("x", 23);
        mapaLetra.put("y", 24);
        mapaLetra.put("z", 25);
        mapaLetra.put("á", 0);
        mapaLetra.put("ã", 0);
        mapaLetra.put("à", 0);
        mapaLetra.put("é", 4);
        mapaLetra.put("ó", 14);
        mapaLetra.put("í", 8);
        mapaLetra.put("ç", 2);
        mapaLetra.put("ê", 4);
        mapaLetra.put("â", 0);
        mapaLetra.put("0", 26);
        mapaLetra.put("1", 27);
        mapaLetra.put("2", 28);
        mapaLetra.put("3", 29);
        mapaLetra.put("4", 30);
        mapaLetra.put("5", 31);
        mapaLetra.put("6", 32);
        mapaLetra.put("7", 33);
        mapaLetra.put("8", 34);
        mapaLetra.put("9", 35);
        mapaLetra.put("10", 36);
    }

    private final Integer imagem_letra[] = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j,
            R.drawable.k,
            R.drawable.l,
            R.drawable.m,
            R.drawable.n,
            R.drawable.o,
            R.drawable.p,
            R.drawable.q,
            R.drawable.r,
            R.drawable.s,
            R.drawable.t,
            R.drawable.u,
            R.drawable.v,
            R.drawable.w,
            R.drawable.x,
            R.drawable.y,
            R.drawable.z,
            R.drawable.zero,
            R.drawable.um,
            R.drawable.dois,
            R.drawable.tres,
            R.drawable.quatro,
            R.drawable.cinco,
            R.drawable.seis,
            R.drawable.sete,
            R.drawable.oito,
            R.drawable.nove,
            R.drawable.dez
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceI = findViewById(R.id.voiceInput);
        speakB = findViewById(R.id.btnSpeak);
        speakB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                voice();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.imagens);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        ArrayList<Cell> createLists = prepareData(resultTxt);
        adapter adpt = new adapter(getApplicationContext(), createLists);

        recyclerView.setAdapter(adpt);
    }

    private void voice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        String spkDlg = getResources().getString(R.string.speakDialog);

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, spkDlg);

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //Creates result object which will store text after it is converted

                    resultTxt = result.get(0);

                    RecyclerView recyclerView = findViewById(R.id.imagens);


                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager); //Instantiates and sets up new layout manager

                    recyclerView.setHasFixedSize(true);

                    ArrayList<Cell> createLists = prepareData(resultTxt);
                    adapter adpt = new adapter(getApplicationContext(), createLists);

                    recyclerView.setAdapter(adpt);

                    voiceI.setText(resultTxt); //Changes text of element to the converted text
                }
                break;
            }

        }
    }

    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }


    public static boolean temEspaco(String str) { //Function for checking the existence of a space character
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    private ArrayList<Cell> prepareData(String rsltxt) { //Prepares data and creates list with both images (in LIBRAS) and letters
        ArrayList<Cell> theimage = new ArrayList<>();

        String word;
        int newIdx = 0;
        boolean lastword = false;
        Character LetraSep = ' ';

        while (!lastword) {
            if (temEspaco(rsltxt)) {//Checks for existence of space character
                word = rsltxt.substring(0, rsltxt.indexOf(' ')); //Chops string down to first word before space
                newIdx = rsltxt.indexOf(' ') + 1; //Creates new index value to be used in next substring

                rsltxt = rsltxt.substring(newIdx);
            } else {
                word = rsltxt;
                lastword = true;
            }
            if (mapaLetra.get(word) != null) { //Checks for existence of word in the mapaLetra map after string is divided in words
                Cell createList = new Cell();

                createList.setLetra(word);
                createList.setImg(imagem_letra[mapaLetra.get(word)]);
                theimage.add(createList);
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (word.toLowerCase().charAt(i) != ' ' ||
                            word.toLowerCase().charAt(i) != ',' ||
                            word.toLowerCase().charAt(i) != '.' ||
                            word.toLowerCase().charAt(i) != ';' ||    //Really "ghetto" fallback measures for unsupported character usage
                            word.toLowerCase().charAt(i) != '\'' ||
                            word.toLowerCase().charAt(i) != '\"' ||
                            word.toLowerCase().charAt(i) != '!'){
                        LetraSep = word.toLowerCase().charAt(i);
                        Cell createList = new Cell();

                        createList.setLetra(String.valueOf(LetraSep));
                        createList.setImg(imagem_letra[mapaLetra.get(String.valueOf(LetraSep))]); //Create object with the image version of the letter and text description
                        theimage.add(createList); //Add said object to list
                    }
                }
            }
        } return theimage; //Return list with images in order to display them
    }
        }
