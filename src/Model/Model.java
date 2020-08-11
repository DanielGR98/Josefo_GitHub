/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Persona.Persona;
import tda.circulardobles.CircularDoublyLinkedList;
import View.View;
import java.util.ListIterator;
import javafx.application.Platform;

/**
 *
 * @author bolic
 */
public class Model {
    private CircularDoublyLinkedList<Persona> personas ;
    private final View view;
    SuicidiosGUI animacionSuicidios;
    private boolean pause;


    public SuicidiosGUI getAnimacionSuicidios() {
        return animacionSuicidios;
    }
    
    
    
    public Model(View view){
        this.personas= new CircularDoublyLinkedList();
        personas= inicializarPersonas(30, 0);
        this.view= view;
        pause=false;
    }

    public boolean getPause() {
        return pause;
    }

    
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
    
    public CircularDoublyLinkedList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(CircularDoublyLinkedList<Persona> personas) {
        this.personas = personas;
    }
      
    // Inicializa la lista circular personas con la cantidad dada en argumento
    public CircularDoublyLinkedList<Persona> inicializarPersonas(int cantidad, int indexPrimero){

        for(int i=0; i<cantidad;i++){
            personas.addLast(new Persona(i));
        }
        personas.setLast(cantidad-indexPrimero);
        return personas;
    }
    
    // Inicia los suicidios
    public void suicidios(int saltos, String direccion, long velocidad) throws InterruptedException{
        animacionSuicidios= new SuicidiosGUI(direccion,velocidad,saltos);
        animacionSuicidios.start();
   }
    public void detenerSuicidios(){
        
    }
    
    private class SuicidiosGUI extends Thread{
        private Persona asesino;
        private Persona persona;
        private Persona nuevoAsesino;
        private String direccion;
        private long velocidad;
        private int saltos;

        public SuicidiosGUI(String direccion, long velocidad,int saltos) {
            this.direccion = direccion;
            this.asesino= null;
            this.persona= null;
            this.nuevoAsesino=null;
            this.velocidad=velocidad;
            this.saltos= saltos;
        }
        
        @Override
        public void run(){
         int nodosRestantes= personas.size();
        ListIterator<Persona> personsIte= personas.listIterator();
        if("Derecha".equals(direccion)) {
            asesino=personsIte.next();
        }
        else asesino= personsIte.previous();
                while(nodosRestantes>2 && !pause){                
                    int cont=0;
                    while (cont <=saltos) {
                        if ("Derecha".equals(direccion)) persona= personsIte.next();
                        else persona= personsIte.previous();
                        if(persona.getIsAlive() == true && asesino.compareTo(persona)!= 0) cont++;
                    }
                    asesino.matar(persona, view,velocidad);
                    if("Derecha".equals(direccion)) nuevoAsesino=personsIte.next();
                    else nuevoAsesino= personsIte.previous();
                    while (nuevoAsesino.getIsAlive()==false) {
                        if("Derecha".equals(direccion)) nuevoAsesino=personsIte.next();
                        else nuevoAsesino= personsIte.previous();    
                    }

                    asesino.pasarEspada(nuevoAsesino, view,velocidad);
             
                    nodosRestantes--;
                    asesino=nuevoAsesino;
                    while( pause== true);
                    }
                    if("Derecha".equals(direccion)) persona=personsIte.next();
                    else persona= personsIte.previous();
                    while (persona.getIsAlive()==false) {
                        if("Derecha".equals(direccion)) persona=personsIte.next();
                        else persona= personsIte.previous();           
                    }
                    Platform.runLater(()->{
                       view.moverEspada(persona);
                       persona.morir(); 
                    });
                
               
        }
        }
}
    
    

