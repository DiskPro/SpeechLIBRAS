package com.example.guilhermefrancisco.speechrcg;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView voiceI;
    private ImageButton speakB;

    private Context context = this;

    private String resultTxt = "Hello";

    private HashMap<String, String> MapLetra;

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

        MapLetra = new Gson().fromJson(getString(R.string.map), new TypeToken<HashMap<String, String>>(){}.getType());

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
        Resources resources = context.getResources();

        String word;
        int newIdx = 0;
        boolean lastword = false;
        String LetraSep = "";

        while (!lastword) {
            if (temEspaco(rsltxt)) {//Checks for existence of space character
                word = rsltxt.substring(0, rsltxt.indexOf(' ')); //Chops string down to first word before space
                newIdx = rsltxt.indexOf(' ') + 1; //Creates new index value to be used in next substring

                rsltxt = rsltxt.substring(newIdx);
            } else {
                word = rsltxt;
                lastword = true;
            }
            if (MapLetra.get(word) != null) { //Checks for existence of word in the mapaLetra map after string is divided in words
                Cell createList = new Cell();

                createList.setLetra(word);
                createList.setImg(resources.getIdentifier(MapLetra.get(word), "drawable", context.getPackageName()));
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
                        LetraSep = String.valueOf(word.toLowerCase().charAt(i));
                        Cell createList = new Cell();

                        String test = MapLetra.get(LetraSep);

                        createList.setLetra(String.valueOf(LetraSep));
                        createList.setImg(resources.getIdentifier(MapLetra.get(LetraSep), "drawable", context.getPackageName())); //Create object with the image version of the letter and text description
                        theimage.add(createList); //Add said object to list
                    }
                }
            }
        } return theimage; //Return list with images in order to display them
    }
        }
