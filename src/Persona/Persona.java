package Persona;


import Model.Model;
import View.View;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bolic
 */
public class Persona   implements Comparable<Persona>{
    private int indice;
    private Circle circle;
    private boolean isAlive;
    private double posX;
    private double posY;

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
    
    

    public Persona(int indice) {
        this.indice = indice;
        this.circle= new Circle();
        circle.setFill(Color.GREEN);
        isAlive= true;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    public void morir(){
        this.isAlive=false;
        this.circle.setFill(javafx.scene.paint.Color.GRAY);
    }

    @Override
    public int compareTo(Persona o) {
        if(this.getIndice()== o.getIndice()) return 0;
        else return -1;
    }
    
    public void matar( Persona persona, View view, long velocidad){
       
        Platform.runLater(()->{
            view.moverEspada(persona);
            persona.morir();  
        });
        try {
            Thread.sleep(1000/velocidad);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pasarEspada (Persona nuevoAsesino, View view, long velocidad){
        Platform.runLater(()->{
            view.lowlight(this.getCircle());
            view.moverEspada(nuevoAsesino);
            view.highlight(nuevoAsesino.getCircle());
        });
        try {
            Thread.sleep(1000/velocidad);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
  
    
    

}
