/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Persona.Persona;
import View.View;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * 
 *
 * @author davidmendozaloor
 * @param <E>
 */
public class Controller {
    private final View view;

    public Controller(View view) {
        this.view = view;
    }
   // Cuando cambias el numero de nodos
    public  ChangeListener<Number> numNodosListener() {
    return (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
        view.getModel().getPersonas().clear();
        view.borrarCirculos();
        view.getModel().setPersonas(view.getModel().inicializarPersonas((int) newValue+10, 0));
        view.inicializarCirculos();
    };
    }
    
    //Eventos al presionar Play
    public EventHandler<ActionEvent> botonPlayAction(){
        return (ActionEvent event) -> {
            try {
                if(view.getModel().getPause() ==true) view.getModel().setPause(false);
                else view.getModel().suicidios(view.getSaltosBox().getValue(),view.getDireccionChoiceBox().getValue(),(long) view.getVelocidadSlider().getValue());
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                Thread.currentThread().interrupt();
            }
        };
    }
    
    //Eventos al presionar Circulos
    public EventHandler<MouseEvent> circuloPresionado(Persona persona){
        return (MouseEvent event) -> {
            view.lowlight(view.getModel().getPersonas().getFirst().getCircle());
            view.highlight(persona.getCircle());
            view.getModel().getPersonas().setLast(persona.getIndice()-1);
            view.moverEspada(persona);
        };
    }
    public EventHandler<ActionEvent> botonStopAction(){
        return (ActionEvent event) -> {
            view.getModel().getPersonas().clear();
            view.borrarCirculos();
            view.getModel().setPersonas(view.getModel().inicializarPersonas(view.getNumeroNodos().getValue(), 0));
            Platform.runLater(()->{view.getPane().getChildren().remove(view.getSword());});         
            view.inicializarCirculos();
            view.newSword();
            //view.moverEspada(view.getModel().getPersonas().getFirst());
        };
    }
    
    public EventHandler<ActionEvent> botonPauseAction(){
        return (ActionEvent event) -> {
            view.getModel().setPause(true);
        };
    }
    
    
}

