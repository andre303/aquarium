/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.EventQueue;
import javax.swing.*;


public class Aquarium extends Application {


    public static void main(String[] args)
    {
             JOptionPane.showMessageDialog( null, "Вітаємо у нашій грі", "Вітання", JOptionPane.DEFAULT_OPTION );
             launch(args);
            EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                NewClass frame = new NewClass(DataClass.aglae_quantiy);


                frame.setTitle("Акваріум");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

            }
        });
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLChoseForm.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Вибір рівня");
        stage.setScene(scene);
        stage.show();
    }
}
