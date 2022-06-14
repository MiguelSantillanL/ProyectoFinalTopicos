package com.example.proyectofinaltopicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private Lista ListaDeRegistros = new Lista();
    private int Id_Ventana2=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_nuevaRuna(View view){
        Intent runa = new Intent(this, A1_CrearRuna.class);
        startActivity(runa);
    }//fin btn_nuevaRuna

}//FIN ACTIVIDAD PRINCIPAL

class RunaDeLolcito implements Serializable{
    private int id;
    //******************************************************
    private String campeon; //nombre del campeon
    //*******************************************************
    //runa main
    private String runaPrincipal;
    private String [] ranuras = new String[3];
    //runa secundaria
    private String subRuna;
    private String []subRanura = new String[2];
    //*******************************************************
    //tipo de runa
    private String tipoRuna; //AP, AD, TANQUE, SUPPORT
    //*******************************************************
    //linea en la que jugar
    private  String linea; // TOP,JG,MID,ADC, SUPP
    //*******************************************************
    //valoracion de la runa
    private int valor;

    RunaDeLolcito(){
        id = 0;
        campeon = null;
        runaPrincipal = null;
        for (int i = 0; i<3 ; i++)
            ranuras[i] = null;

        subRuna = null;
        for (int j = 0; j<2 ; j++)
            subRanura[j] = null;

        tipoRuna = null;
        linea = null;
        valor = 0;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCampeon(String campeon) {
        this.campeon = campeon;
    }

    public void setRunaPrincipal(String runaPrincipal) {
        this.runaPrincipal = runaPrincipal;
    }

    public void setRanuras(int pos, String valor) {
        this.ranuras[pos] = valor;
    }

    public void setRanuras(String[] ranuras) {
        this.ranuras = ranuras;
    }

    public void setSubRuna(String subRuna) {
        this.subRuna = subRuna;
    }

    public void setSubRanura(int pos, String valor) {
        this.subRanura[pos] = valor;
    }

    public void setSubRanura(String[] subRanura) {
        this.subRanura = subRanura;
    }

    public void setTipoRuna(String tipoRuna) {
        this.tipoRuna = tipoRuna;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    //getters
    public int getId() {
        return id;
    }

    public int getValor() {
        return valor;
    }

    public String getCampeon() {
        return campeon;
    }

    public String getLinea() {
        return linea;
    }

    public String getRunaPrincipal() {
        return runaPrincipal;
    }

    public String getSubRuna() {
        return subRuna;
    }

    public String getTipoRuna() {
        return tipoRuna;
    }

    public String[] getRanuras() {
        return ranuras;
    }

    public void DisplayReg() {
        System.out.println("ID: " + this.id );
        System.out.println("Nombre deL campeon: " +  this.campeon );
        System.out.println("Runa Principal: " +  this.runaPrincipal );
        for (int i = 0; i<3 ; i++)
            System.out.println("Ranura No." + i + " :" +  this.ranuras[i] );

        System.out.println("Sub Runa: " +  this.subRuna );
        for (int j = 0; j<2 ; j++)
            System.out.println("Sub Ranura No." + j + " :" +  this.subRanura[j] );
/*
        System.out.println("Tipo de runa: " +  this.tipoRuna );
        System.out.println("Linea en la cual jugarlo: " +  this.linea );
        System.out.println("Calificacion: " +  this.valor + " estrellas" );*/
    }

}// FIN DE LA CLASE RunaDeLolcito

class Nodo implements Serializable{
    private RunaDeLolcito runa;
    private Nodo Ant;
    private Nodo Sig;

    Nodo(RunaDeLolcito dato, Nodo prev, Nodo next){
        runa=dato;
        Ant=prev;
        Sig=next;
    }

    public RunaDeLolcito getRuna(){
        return  runa;
    }

    public Nodo getAnterior(){
        return Ant;
    }

    public Nodo getSiguiente(){
        return Sig;
    }

    public void setRuna(RunaDeLolcito dato){
        runa=dato;
    }

    public void setAnterior(Nodo prev){
        Ant=prev;
    }

    public void setSiguiente(Nodo next){
        Sig=next;
    }
}//FIN DE LA CLASE NODO

class Lista implements Serializable {
    private Nodo Inicio;
    private int NumElem;
    private Nodo NodoEnPantalla;

    Lista(){
        Inicio=null;
        NumElem=0;
        NodoEnPantalla=null;
    }

    public Nodo getNodoInicio(){
        return Inicio;
    }

    public int getNumeroElementos(){
        return NumElem;
    }

    public Nodo getNodoEnPantalla(){
        return NodoEnPantalla;
    }

    public void setNodoEnPantalla(Nodo aux){
        NodoEnPantalla=aux;
    }

    public void IncertarNodo(RunaDeLolcito runa){
        Nodo NewNodo = new Nodo(runa, null, null);
        Nodo aux;
        //Si la lista esta vacia
        if(Inicio==null)
            Inicio=NewNodo;
        else{ //La Lista no esta vacia, entonces insertamos el nodo al final de la lista
            aux = Inicio;
            while( aux.getSiguiente()!=null )
                aux=aux.getSiguiente();
            // aux al final del ciclo contiene el acceso al último nodo
            aux.setSiguiente(NewNodo);
            NewNodo.setAnterior(aux);
        }
        NumElem++;
    }
}//FIN DE LA CLASE LISTA