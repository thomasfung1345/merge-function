/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.OOUtils;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.list.List;
/**
 *
 * @author Kai Chung Fung
 */
public class merge_function extends Application{
   static String filePath1 = "";
    static String filePath2 = "";
    FileChooser fileChooser = new FileChooser();
    TextDocument outputOdt;
    TextDocument outputOdt2;
    TextDocument result;
    Label name2_lab = new Label("");
    Label name1_lab = new Label("");
    Label name3_lab = new Label("");
    Label result_lab = new Label("");
    TextField nfile_tf = new TextField ();
    File file1;
    File file2;
    File file3;
    String type="0";
    public void start(Stage primaryStage) throws Exception {
primaryStage.setTitle("Merge Function");
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(30);
        pane.setVgap(30);
        pane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(pane, 600, 400);

        Label scenelabel = new Label("Merge your Document");
        scenelabel.setFont(Font.font("Arial", 35));
        pane.add(scenelabel, 0, 0, 2, 1);
        Label doc1_lab = new Label("Document 1 :");
        pane.add(doc1_lab, 0, 1);
        Button file1_btn = new Button("Choose a file");
        file1_btn.setOnAction(btnLoadEventListener1);
        pane.add(file1_btn, 1, 1);
        pane.add(name1_lab,2,1);
        
        Label doc2_lab = new Label("Document 2 ");
        pane.add(doc2_lab,0,2);
        Button file2_btn = new Button("Choose a file");
        file2_btn.setOnAction(btnLoadEventListener2);
        pane.add(file2_btn, 1, 2);
        pane.add(name2_lab,2,2);
        
        final ToggleGroup group = new ToggleGroup();
        Label rad_lab = new Label("Type:");
        pane.add(rad_lab,0,3);
        RadioButton rb1 = new RadioButton("Merge without page break");
        rb1.setToggleGroup(group);
      
        rb1.setUserData("1");
         pane.add(rb1,1,3);
        RadioButton rb2 = new RadioButton("Merge with page break");
        rb2.setToggleGroup(group);
        pane.add(rb2,2,3);
        rb2.setUserData("2");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ov,
          Toggle old_toggle, Toggle new_toggle) {
        if (group.getSelectedToggle() != null) {
            type= group.getSelectedToggle().getUserData().toString();
            System.out.println(type);
        }
      }
    });
        Label nfile_lab= new Label("New file :");
        pane.add(nfile_lab,0,4);
        //nfile_tf.setPromptText("Enter your file name.");
        //pane.add(nfile_tf, 1, 4);
        Button nfile_btn = new Button("Save In");
        nfile_btn.setOnAction(btnLoadEventListener3);
        pane.add(nfile_btn, 1, 4);
        pane.add(name3_lab,2,4);
        

        Button merge_btn = new Button("Merge");        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(merge_btn);
        merge_btn.setOnAction(btnLoadEventListener_merge);
        pane.add(hbox, 2, 5);
        pane.add(result_lab,1,5);
        primaryStage.setScene(scene);
        primaryStage.show();

    }     
      public static void main(String[] args) {
        launch(args);
    }
      
      
     EventHandler<ActionEvent> btnLoadEventListener1
    = new EventHandler<ActionEvent>(){
   
        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();
               
            //Set extension filter
            FileChooser.ExtensionFilter extFilter 
                    = new FileChooser.ExtensionFilter("ALL files (*.*)", "*.*");
            fileChooser.getExtensionFilters().add(extFilter);
                
            //Show open file dialog, return a java.io.File object
            file1 = fileChooser.showOpenDialog(null);
             
            //obtain a java.nio.file.Path object 
            Path filepath1 = file1.toPath();
            
            String name1  = " "+ filepath1.getFileName();
            name1_lab.setText(name1);
              System.out.print(filepath1+"\n");
        }
    };
          EventHandler<ActionEvent> btnLoadEventListener2
    = new EventHandler<ActionEvent>(){
   
        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();
               
            //Set extension filter
            FileChooser.ExtensionFilter extFilter 
                    = new FileChooser.ExtensionFilter("ALL files (*.*)", "*.*");
            fileChooser.getExtensionFilters().add(extFilter);
                
            //Show open file dialog, return a java.io.File object
            file2 = fileChooser.showOpenDialog(null);
             
            //obtain a java.nio.file.Path object 
            Path filepath2 = file2.toPath();
             String name2  = " "+ filepath2.getFileName();
            
             name2_lab.setText(name2);
              System.out.print(filepath2+"\n");
        }
    };
          EventHandler<ActionEvent> btnLoadEventListener3
    = new EventHandler<ActionEvent>(){
        JFrame parentFrame = new JFrame();

        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser() ;
            fileChooser.setTitle("Save as ");
            fileChooser.setInitialFileName(".odt");
            file3 = fileChooser.showSaveDialog(null);
            if (file3 != null) {
                 Path filepath3 = file3.toPath();
            
                String name3  = " "+ filepath3.getFileName();
            
             name3_lab.setText(name3);
              System.out.print(filepath3+"\n");
            }
}
               
            
        
    };
 EventHandler<ActionEvent> btnLoadEventListener_merge
    = new EventHandler<ActionEvent>(){
        String path1 = filePath1.toString();
        String path2 = filePath2.toString();
        TextDocument outputOdt;
        TextDocument outputOdt2;
        
        String nfile;
        @Override
        public void handle(ActionEvent t) {           
                
         try{
           nfile = nfile_tf.getText();
           if(file1 == null){
              // check document1 missing
            Alert alert1 = new Alert(AlertType.ERROR);
            alert1.setTitle("Error Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("Document 1 is missing!");
            alert1.showAndWait();
           
           }
           else if(file2 == null){
               //check document2 missing
            Alert alert2 = new Alert(AlertType.ERROR);
            alert2.setTitle("Error Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Document 2 is missing!");
            alert2.showAndWait();
            
           }
           if( file3==null){
              //check new file name missing
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("Error Dialog");
            alert3.setHeaderText(null);
            alert3.setContentText("Please choose the save file directory");
            alert3.showAndWait();
           
           }
           if(type=="0"){
              //check new file name missing
            Alert alert4 = new Alert(AlertType.ERROR);
            alert4.setTitle("Error Dialog");
            alert4.setHeaderText(null);
            alert4.setContentText("Please choose the merge type");
            alert4.showAndWait();
           
           }
           if (type!="0" && file3 != null && file2 != null && file1 != null){ 
               
            TextDocument result;
            result = TextDocument.newTextDocument(); 
            result.save("result.odt");
            TextDocument src = TextDocument.loadDocument(file1);
            TextDocument src2 = TextDocument.loadDocument(file2);
            if (type == "1"){
            
            result.addParagraph(" ");
            Paragraph p1 = result.getParagraphByReverseIndex(0, true);
            // insert contents before p1 and copy styles
            result.insertContentFromDocumentBefore(src, p1, false);
            p1.remove();
        ;
            result.addParagraph(" ");
            p1 = result.getParagraphByReverseIndex(0, true);
            result.insertContentFromDocumentBefore(src2, p1, true);
            p1.remove();
            // insert contents after p1 and don't copy styles
            //result.insertContentFromDocumentAfter(src, p1, false);
            result.save(file3);
          
                    //new
        }
            if(type == "2"){
            // Load 2 text documents
            //File file1 = new File("C:\\Users\\Dennis Wong\\Documents\\NetBeansProjects\\JavaApplication\\HelloWorld.odt");
            ODSingleXMLDocument p1 = ODSingleXMLDocument.createFromPackage(file1);

            //File file2 = new File("C:\\Users\\Dennis Wong\\Documents\\NetBeansProjects\\JavaApplication\\HelloWorld.odt");
            ODSingleXMLDocument p2 = ODSingleXMLDocument.createFromPackage(file2);

            // Concatenate them
            p1.add(p2);

            // Save to file and Open the document with OpenOffice.org !
            OOUtils.open(p1.saveToPackageAs(file3));
            
            }
            //cjange new file name 
            
            System.out.println(nfile);
            //src.save("HelloWorld.odt");
            
            result_lab.setText("The files merge suceefully ");
           
           }
       } catch (Exception e) {
            System.err.println("ERROR: unable to create output file.");
            
        }
            
        
          
            
        }
    };

}
