package View;

import Controller.Controller;
import Model.Model;
import Persona.Persona;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bolic
 */
public class View {
    private Pane pane;
    private final Controller controller;
    private Model model;
    private ImageView sword;
    private ChoiceBox<String> direccionChoiceBox;
    private ChoiceBox<Integer> numeroNodos;
    private Slider velocidadSlider;
    private ChoiceBox<Integer> saltosBox;
    
    

    public Slider getVelocidadSlider() {
        return velocidadSlider;
    }

    public ChoiceBox<Integer> getSaltosBox() {
        return saltosBox;
    }
    
    

    public ChoiceBox<Integer> getNumeroNodos() {
        return numeroNodos;
    }
    
    
    

    public ChoiceBox<String> getDireccionChoiceBox() {
        return direccionChoiceBox;
    }
    

    public ImageView getSword() {
        return sword;
    }
    

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
    
    public View(){
        pane=new Pane();
        controller=new Controller(this);
        model= new Model(this);
 
    }

 
    
    public Scene iniciarEscena(){
        
        // Hbox contenedor princial
        HBox root= new HBox();
        root.setAlignment(Pos.CENTER_RIGHT);
        root.setPrefWidth(1500);
        root.setPrefHeight(1000);
        pane.setPrefWidth(600);
        pane.setPrefHeight(1000);
        
        // Funcion que dibuja los circulos en el pane inicialmente son 30
        inicializarCirculos();
        
        //Contenedor que contendra al pane y al resto de botones y ChoiceBoxes
        VBox vbox= new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        
        //ChoiceBox para elegir numero de Nodos
        HBox numeroNodosHbox= new HBox();
        Label numeroNodosLabel= new Label("Numero de Nodos:");
        numeroNodos= new ChoiceBox();
        ArrayList<Integer> list= new ArrayList();
        for(int i=10;i<=100;i++){
            list.add(i);
        }
        numeroNodos.getItems().addAll(list);
        numeroNodos.setValue(30);
        numeroNodos.getSelectionModel().selectedIndexProperty().addListener(controller.numNodosListener());
        numeroNodosHbox.getChildren().addAll(numeroNodosLabel,numeroNodos);
        numeroNodosHbox.setAlignment(Pos.CENTER);
        
        //ChoiceBox para elegir la direccion de giro
        HBox direccionHBox = new HBox();
        Label direccionLabel= new Label("Direccion:");
        direccionChoiceBox= new ChoiceBox();
        direccionChoiceBox.getItems().addAll("Derecha","Izquierda");
        direccionChoiceBox.setValue("Derecha");
        direccionHBox.getChildren().addAll(direccionLabel,direccionChoiceBox);
        direccionHBox.setAlignment(Pos.CENTER);
        
        //Slider para controlar la velocidad de giro
        velocidadSlider= new Slider();
        velocidadSlider.setMin(0.5);
        velocidadSlider.setMax(1.5);
        velocidadSlider.setValue(1);
        velocidadSlider.setShowTickLabels(true);
        velocidadSlider.setShowTickMarks(true);
        velocidadSlider.setBlockIncrement(0.1);
        velocidadSlider.setMajorTickUnit(0.5);
        
        //ChoiceBox para controlar numero de saltos
        HBox saltosHBox = new HBox();
        Label saltosLabel= new Label("Saltos:");
        saltosBox= new ChoiceBox<>();
        ArrayList<Integer> listSaltos= new ArrayList<>();
        for (int i=0; i<20;i++) listSaltos.add(i);
        saltosBox.getItems().addAll(listSaltos);
        saltosBox.setValue(2);
        saltosHBox.getChildren().addAll(saltosLabel,saltosBox);
        saltosHBox.setAlignment(Pos.CENTER);
        //Boton de Play
        Button playButton= new Button("Play");
        playButton.setOnAction(controller.botonPlayAction());
        
        //Boton de Pausa
        Button pauseButton= new Button("Pause");
        pauseButton.setOnAction(controller.botonPauseAction());
        
        //Boton de Stop
        Button stopButton= new Button("Stop");
        stopButton.setOnAction(controller.botonStopAction());
        
        vbox.getChildren().addAll(numeroNodosHbox,direccionHBox,velocidadSlider,saltosHBox,playButton,pauseButton,stopButton);
        root.getChildren().addAll(pane,vbox);
        return new Scene(root,800,600);
    }
    
   public void inicializarCirculos(){
       Iterator<Persona> ite=model.getPersonas().iterator();
       double tamano= (double) model.getPersonas().size();
       double radioCircunferencia= 7*tamano;
      
       double separacionAngulos=360/tamano;
       int cont=0;
       while(ite.hasNext()){
           Persona persona= ite.next();
           Circle cirPersona=persona.getCircle();
           //Ubicar cada circulo completando un circulo grande
           double posX= 300+radioCircunferencia*Math.cos(Math.toRadians(separacionAngulos*cont));
           double posY= 300+radioCircunferencia*Math.sin(Math.toRadians(separacionAngulos*cont));
           persona.setPosX(posX);
           persona.setPosY(posY);
           cirPersona.setRadius(20);
           cirPersona.setLayoutX(posX);
           cirPersona.setLayoutY(posY);
           if(cont ==0) highlight(cirPersona);
           else lowlight(cirPersona);
           //Accion de volver primero a un nodo cuando se presiona un circulo
           cirPersona.setOnMouseClicked(controller.circuloPresionado(persona));
           Label label= new Label(Integer.toString(persona.getIndice()));
           label.setLayoutX(posX);
           label.setLayoutY(posY);
           label.setAlignment(Pos.CENTER_LEFT);
           pane.getChildren().addAll(cirPersona,label);
           cont++;
       }
       
       //Espada cargando imagen y subiendo arhcivo
       sword= new ImageView("image/files/sword.png");
       moverEspada(model.getPersonas().getFirst());
       sword.setFitHeight(50);
       sword.setFitWidth(50);
       pane.getChildren().add(sword);
    }
   
   //Borra los circulos del pane
   public void borrarCirculos(){
       pane.getChildren().clear();
   }

    public void setSword(ImageView sword) {
        this.sword = sword;
    }
   
   
   
   //Mueve la espada sin animacion hacian la pos de una persona
   public void moverEspada(Persona persona){
       sword.setLayoutX(persona.getPosX());
       sword.setLayoutY(persona.getPosY());
   }
   
    //Subraya el circulo
   public void highlight(Circle circle){
       circle.setStrokeWidth(5);
       circle.setStroke(Color.YELLOW);
   }
   
   //Desubraya el ciruclo
   public void lowlight(Circle circle){
       circle.setStrokeWidth(1);
       circle.setStroke(Color.BLACK);
   }
   
   public void moverEspada(double x,double y){
       sword.setLayoutX(x);
       sword.setLayoutY(y);
   }
   
   public void newSword(){
       sword = new ImageView("image/files/sword.png");
       pane.getChildren().add(sword);
   }

}
