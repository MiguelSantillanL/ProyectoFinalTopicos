package com.example.proyectofinaltopicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class A2_ListaDeRunas extends AppCompatActivity {
    AutoCompleteTextView ac_tvA2Champions;
    Spinner spin_calif, spin_valores;
    TextView tv_valores;
    RecyclerView rv_runas;
    String [] Categorias = {"", "Campeón", "Runa Principal", "Tipo de runa", "Línea  para jugar", "Calificación"};
    String [] lolcamp = new String[159] ; //159 campeones de lol
    String [] mainRunes =  {" ","Precisión", "Dominación", "Brujería", "Valor", "Inspiración"}; // las 5 runas principales
    String [] runaType = { " " , "AD", "AP", "Support", "Tanque"};
    String [] lineaPlayer = { " ", "TOP", "JG", "MID", "BOT" };
    String [] calif = {" ", " 1  ✡" ," 2  ✡" ," 3  ✡" ," 4  ✡" ," 5  ✡" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2_lista_de_runas);
        spin_calif = findViewById(R.id.spin_calif);
        spin_valores = findViewById(R.id.spin_valores);
        rv_runas = findViewById(R.id.rv_runas);
        ac_tvA2Champions = findViewById(R.id.ac_tvA2Champions);
        tv_valores = findViewById(R.id.tv_valores);


        spin_valores.setEnabled(false);
        rv_runas.setEnabled(false);
        ac_tvA2Champions.setEnabled(false);
        ac_tvA2Champions.setVisibility(View.INVISIBLE);

        ArrayAdapter adapterCat = new ArrayAdapter(this, android.R.layout.select_dialog_item,Categorias); //strings de campeones a un ArrayAdapter
        spin_calif.setAdapter(adapterCat);

        spin_calif.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);

                        //System.out.println("valor " + pos + " : " + item.toString());     //prints the text in spinner item.
                        getCalif(pos);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


    }

    public void getCalif(int opc){
        if (opc>0){
            //System.out.println("La opcion es: " + opc);

            if (opc == 1){ //campeon
                tv_valores.setText("Campeón");
                ArrayAdapter adapterChampList = new ArrayAdapter(this, android.R.layout.select_dialog_item,lolcamp); //strings de campeones a un ArrayAdapter
                //spin_valores.setAdapter(adapterChampList);
                spin_valores.setEnabled(false);
                ac_tvA2Champions.setEnabled(true);
                spin_valores.setVisibility(View.INVISIBLE);
                ac_tvA2Champions.setVisibility(View.VISIBLE);
                ac_tvA2Champions.setThreshold(1);
                ac_tvA2Champions.setAdapter(adapterChampList);

            }
            else if (opc == 2){ //runa principal
                spin_valores.setEnabled(true);
                ac_tvA2Champions.setEnabled(false);
                ac_tvA2Champions.setVisibility(View.INVISIBLE);
                spin_valores.setVisibility(View.VISIBLE);
                tv_valores.setText("Runa Principal");
                ArrayAdapter adaptermainRunes = new ArrayAdapter(this, android.R.layout.select_dialog_item,mainRunes); //strings de campeones a un ArrayAdapter
                spin_valores.setAdapter(adaptermainRunes);
            }
            else if (opc == 3){ //Tipo de
                spin_valores.setEnabled(true);
                ac_tvA2Champions.setEnabled(false);
                ac_tvA2Champions.setVisibility(View.INVISIBLE);
                spin_valores.setVisibility(View.VISIBLE);
                tv_valores.setText("Campeón");
                ArrayAdapter adapterrunaType = new ArrayAdapter(this, android.R.layout.select_dialog_item,runaType); //strings de campeones a un ArrayAdapter
                spin_valores.setAdapter(adapterrunaType);
            }
            else if (opc == 4){ //Línea  para
                spin_valores.setEnabled(true);
                ac_tvA2Champions.setEnabled(false);
                ac_tvA2Champions.setVisibility(View.INVISIBLE);
                spin_valores.setVisibility(View.VISIBLE);
                tv_valores.setText("Tipo de runar");
                ArrayAdapter adapterlineaPlayer = new ArrayAdapter(this, android.R.layout.select_dialog_item,lineaPlayer); //strings de campeones a un ArrayAdapter
                spin_valores.setAdapter(adapterlineaPlayer);
            }
            else if (opc == 5){ //Calificación
                spin_valores.setEnabled(true);
                ac_tvA2Champions.setEnabled(false);
                ac_tvA2Champions.setVisibility(View.INVISIBLE);
                spin_valores.setVisibility(View.VISIBLE);
                tv_valores.setText("Calificación");
                ArrayAdapter adapterCalificación = new ArrayAdapter(this, android.R.layout.select_dialog_item,calif); //strings de campeones a un ArrayAdapter
                spin_valores.setAdapter(adapterCalificación);
            }

            }
    }

        public void getChampionList(){
            try {
                InputStream campeonList = getResources().openRawResource(R.raw.campeonlist);
                InputStreamReader campeon = new InputStreamReader(campeonList);
                BufferedReader BufferEntrada = new BufferedReader(campeon);
                for (int i = 0 ; i<159 ; i++)
                    lolcamp[i] = BufferEntrada.readLine();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



}//fin de la actividad principal
