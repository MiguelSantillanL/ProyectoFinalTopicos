package com.example.proyectofinaltopicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.RestrictionEntry;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class A1_CrearRuna extends AppCompatActivity {
    AutoCompleteTextView actv_lolChampions;
    Spinner spin_RuneMain,spin_runaClave, spin_ranura1,spin_ranura2,spin_ranura3,spin_subRuna,spin_subRuna1,spin_subRuna2;
    String [] lolcamp = new String[159] ; //159 campeones de lol
    String [] mainRunes =  {" ","Precisión", "Dominación", "Brujería", "Valor", "Inspiración"}; // las 5 runas principales
    String [] runaClave = new String[4]; //Dominacion y presicion
    String [] runaClave2 = new String[3]; //brujeria, valor, inspiracion
    String [] ranura1 = new String[3]; //ranura1
    String [] ranura2 = new String[3]; //ranura2
    String [] ranura3 = new String[3]; //ranura3
    String [] ranura3d = new String[4]; //ranura3 para dominacion
    String [] runaLolcito = new String[9]; //valor final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1_crear_runa);
        //***************************************************
        actv_lolChampions = findViewById(R.id.ac_tvChampions);
        spin_RuneMain =(Spinner) findViewById(R.id.spin_RuneMain);
        spin_runaClave = findViewById(R.id.spin_runaClave);
        spin_ranura1 = findViewById(R.id.spin_ranura1);
        spin_ranura2 = findViewById(R.id.spin_ranura2);
        spin_ranura3 = findViewById(R.id.spin_ranura3);
        spin_subRuna = findViewById(R.id.spin_subRuna);
        spin_subRuna1 = findViewById(R.id.spin_subRuna1);
        spin_subRuna2 = findViewById(R.id.spin_subRuna2);
        //desabilitamos las subrunas ya que es necesario saber las sunas principales
        spin_runaClave.setEnabled(false);
        spin_ranura1.setEnabled(false);
        spin_ranura2.setEnabled(false);
        spin_ranura3.setEnabled(false);
        spin_subRuna1.setEnabled(false);
        spin_subRuna2.setEnabled(false);


        //obtenemos la lista de los campeones de lol
        getChampionList();
        ArrayAdapter adapterChampList = new ArrayAdapter(this, android.R.layout.select_dialog_item,lolcamp); //strings de campeones a un ArrayAdapter
        actv_lolChampions.setThreshold(1);
        actv_lolChampions.setAdapter(adapterChampList);

        //para mostrar los valores de la runa main
        ArrayAdapter adapterRuneMainList = new ArrayAdapter(this, android.R.layout.select_dialog_item,mainRunes); //strings de campeones a un ArrayAdapter
        spin_RuneMain.setAdapter(adapterRuneMainList);
        //verificar runa principal para mostrar subrunas
        spin_RuneMain.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        runaLolcito[1] = item.toString();
                        System.out.println("valor " + pos + " : " + item.toString());     //prints the text in spinner item.
                        getActivateRanuras(pos);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        //runas secundarias
        spin_subRuna.setAdapter(adapterRuneMainList);
        spin_subRuna.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        runaLolcito[6] = item.toString();
                        getSecondRun(pos);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });



    }


    public void btn_A1Regresar(View view){
        finish();
    } // fin de btn_A1Regresar

    public void btn_A1Guardar(View view) throws IOException {


        runaLolcito[0] =  actv_lolChampions.getText().toString(); //campeon
        runaLolcito[1] = spin_RuneMain.getSelectedItem().toString(); //runa main
        runaLolcito[2] = spin_runaClave.getSelectedItem().toString(); //runa clave
        //sub runa clave
        runaLolcito[3] = spin_ranura1.getSelectedItem().toString();
        runaLolcito[4] = spin_ranura2.getSelectedItem().toString();
        runaLolcito[5] = spin_ranura3.getSelectedItem().toString();
        //runa secundaria
        runaLolcito[6] = spin_subRuna.getSelectedItem().toString();
        runaLolcito[7] = spin_subRuna1.getSelectedItem().toString(); //sub runa sec
        runaLolcito[8] = spin_subRuna2.getSelectedItem().toString(); //sub runa sec
        for (int i = 0 ; i<9 ; i++)
            System.out.println(runaLolcito[i]);

        int numElem;
        try { //Para crear un archivo de texto usamos el Metodo openFileOutput, el cual nos regresa un
            // objeto de clase FileOutputStream
            FileOutputStream ArchivoSalida = openFileOutput("runasLoL.txt", MODE_PRIVATE);
            //Para faciltar la escritura en el Archivo de Salida utilizamos otra clase llamada OutputStreamWrite
            OutputStreamWriter OutputText = new OutputStreamWriter(ArchivoSalida);



            numElem = numElemnts();
            int temp = 0;

            for (int i= 0; i<(9*numElem +9) ; i++){
                //if (numElem ==0 )
                //    OutputText.write(runaLolcito[temp] + "\n");
                if (i >= (9*numElem-1))
                    OutputText.write(runaLolcito[temp] + "\n");
                temp++;
            }

            incNumElemnt(numElem);



            //incNumElemnt(0);


            OutputText.close();
            ArchivoSalida.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void incNumElemnt(int num){
        try {
            FileOutputStream ArchivoSalida = openFileOutput("numRunas.txt", MODE_PRIVATE);
            //Para faciltar la escritura en el Archivo de Salida utilizamos otra clase llamada OutputStreamWrite
            OutputStreamWriter OutputText = new OutputStreamWriter(ArchivoSalida);
            int varor = num+1;

            //OutputText.write(0 + "\n");

            OutputText.write( varor + "\n");
            OutputText.close();
            ArchivoSalida.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int numElemnts(){
        int temp = -1;
        try { //Para crear un archivo de texto usamos el Metodo openFileOutput, el cual nos regresa un
            // objeto de clase FileOutputStream
            FileInputStream ArchivoDeLectura = openFileInput("numRunas.txt");
            InputStreamReader TextoDeEntrada = new InputStreamReader(ArchivoDeLectura);
            BufferedReader BufferEntrada = new BufferedReader(TextoDeEntrada);
            temp =  Integer.valueOf(BufferEntrada.readLine());
            BufferEntrada.close();
            TextoDeEntrada.close();
            ArchivoDeLectura.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
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

    public void getActivateRanuras(int opc){
        if (opc>0){
            //System.out.println("La opcion es: " + opc);
            spin_runaClave.setEnabled(true);
            spin_ranura1.setEnabled(true);
            spin_ranura2.setEnabled(true);
            spin_ranura3.setEnabled(true);

            if (opc == 1){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.preci);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<4 ; i++)
                        runaClave[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterMainRune = new ArrayAdapter(this, android.R.layout.select_dialog_item,runaClave);
                spin_runaClave.setAdapter(adapterMainRune);
                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_ranura1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_ranura2.setAdapter(adapterranura2);
                ArrayAdapter adapterranura3 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura3);
                spin_ranura3.setAdapter(adapterranura3);
            }

            else if (opc == 2){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.domi);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<4 ; i++)
                        runaClave[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<4 ; i++)
                        ranura3d[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterMainRune = new ArrayAdapter(this, android.R.layout.select_dialog_item,runaClave);
                spin_runaClave.setAdapter(adapterMainRune);
                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_ranura1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_ranura2.setAdapter(adapterranura2);
                ArrayAdapter adapterranura3 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura3d);
                spin_ranura3.setAdapter(adapterranura3);
            }

            else if (opc == 3){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.bruj);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<3 ; i++)
                        runaClave2[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterMainRune = new ArrayAdapter(this, android.R.layout.select_dialog_item,runaClave2);
                spin_runaClave.setAdapter(adapterMainRune);
                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_ranura1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_ranura2.setAdapter(adapterranura2);
                ArrayAdapter adapterranura3 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura3);
                spin_ranura3.setAdapter(adapterranura3);

            }

            else if (opc == 4){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.valor);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<3 ; i++)
                        runaClave2[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterMainRune = new ArrayAdapter(this, android.R.layout.select_dialog_item,runaClave2);
                spin_runaClave.setAdapter(adapterMainRune);
                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_ranura1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_ranura2.setAdapter(adapterranura2);
                ArrayAdapter adapterranura3 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura3);
                spin_ranura3.setAdapter(adapterranura3);
            }

            else if (opc == 5){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.insp);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<3 ; i++)
                        runaClave2[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterMainRune = new ArrayAdapter(this, android.R.layout.select_dialog_item,runaClave2);
                spin_runaClave.setAdapter(adapterMainRune);
                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_ranura1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_ranura2.setAdapter(adapterranura2);
                ArrayAdapter adapterranura3 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura3);
                spin_ranura3.setAdapter(adapterranura3);

            }

        }

    }//fin de getActivateRanuras

    public void getSecondRun(int opc){
        if (opc>0){
            spin_subRuna1.setEnabled(true);
            spin_subRuna2.setEnabled(true);
            if (opc == 1){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.preci);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<4 ; i++)
                        runaClave[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_subRuna1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_subRuna2.setAdapter(adapterranura2);
            }

            else if (opc == 2){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.domi);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<4 ; i++)
                        runaClave[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<4 ; i++)
                        ranura3d[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_subRuna1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_subRuna2.setAdapter(adapterranura2);
            }

            else if (opc == 3){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.bruj);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<3 ; i++)
                        runaClave2[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }


                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_subRuna1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_subRuna2.setAdapter(adapterranura2);

            }

            else if (opc == 4){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.valor);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<3 ; i++)
                        runaClave2[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_subRuna1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_subRuna2.setAdapter(adapterranura2);

            }

            else if (opc == 5){
                try {
                    InputStream campeonList = getResources().openRawResource(R.raw.insp);
                    InputStreamReader campeon = new InputStreamReader(campeonList);
                    BufferedReader BufferEntrada = new BufferedReader(campeon);

                    for (int i = 0 ; i<3 ; i++)
                        runaClave2[i]= BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura1[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura2[i] = BufferEntrada.readLine();
                    for (int i = 0 ; i<3 ; i++)
                        ranura3[i] = BufferEntrada.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapterranura1 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura1);
                spin_subRuna1.setAdapter(adapterranura1);
                ArrayAdapter adapterranura2 = new ArrayAdapter(this, android.R.layout.select_dialog_item,ranura2);
                spin_subRuna2.setAdapter(adapterranura2);
            }

        }
    }//fin de getSecondRun


}//Fin de la actividad principal