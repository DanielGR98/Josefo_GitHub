/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author bolic
 */
public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        View view=new View();
        primaryStage.setScene(view.iniciarEscena());
        primaryStage.setTitle("Proyecto Josefo");
        primaryStage.show();
    }
    
}
