/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package registration;
import java.sql.ResultSet;
import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.Statement;


/**
 *
 * @author hamza
 */
public class Registration extends JFrame implements ActionListener{
    
    private JTextField tf;
    private JButton submit;
    private JButton exit;
    private JTextArea detailsArea;
    private JRadioButton female;
    private JRadioButton male;
    private JCheckBox swimming;
    private JCheckBox music;
    private JComboBox favoritePlaceOptions;
    
    
    
    
    Registration(){
        
        
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        
        this.setLayout(layout);
        
        JLabel nameLbl = new JLabel("Name: ");
        JLabel genderLbl = new JLabel("Gender: ");
        JLabel interestLbl = new JLabel("Interest: ");
        JLabel favoritePlaceLbl = new JLabel("Favorite Place: ");
        JLabel detailsLbl = new JLabel("Details: ");
        tf = new JTextField(15);
        submit = new JButton("Submit");
        exit = new JButton("Exit");
        music = new JCheckBox("Music");
        swimming = new JCheckBox("Swimming");
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        String[] fpo = {"Bangladish","Lebanon","Syrian","Kuwait","France"};
        favoritePlaceOptions = new JComboBox(fpo);
        detailsArea = new JTextArea(5,10);
        
        
        ButtonGroup grp = new ButtonGroup();
        grp.add(male);
        grp.add(female);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(nameLbl,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(tf,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(genderLbl,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(male,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(female,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(interestLbl,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(music,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(swimming,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(favoritePlaceLbl,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(favoritePlaceOptions,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(detailsLbl,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(detailsArea,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(submit,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 5;
        this.add(exit,gbc);
        
        submit.addActionListener(this);
        exit.addActionListener(this);
        
        setSize(1000,500);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Registration();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(submit)){
            String name=tf.getText();
            String favoritePlace= (String) favoritePlaceOptions.getSelectedItem();
            String details=detailsArea.getText();
            String gender = null;
            
            
//            Gender Value
            if(male.isSelected()){
                    gender = "M";
            }else if(female.isSelected()){
                    gender = "F";
            }
            
//            Interest Value
            StringBuilder  interestValues = new StringBuilder ();
            
            if(music.isSelected()){
                interestValues.append("Music");
            }
            if(swimming.isSelected()){
                interestValues.append("Swimming");
            }
            
            String interest = interestValues.toString().trim();
     
            try{
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl = "jdbc:mysql://localhost:3306/registration";
                
                Connection connect = DriverManager.getConnection(myUrl, "root", "");
                Statement s = connect.createStatement();
                
                
                
                int sql = s.executeUpdate("INSERT INTO users(name,gender,interest,favorite_place,details) VALUES('"+name+"','"+gender+"','"+interest+"','"+favoritePlace+"','"+details+"')");
            
                ResultSet rs = s.executeQuery("SELECT * FROM users");
                
                File f = new File("C://OOPfiles/backup.txt");
                
                PrintWriter pw = new PrintWriter(f);
                while(rs.next()){
                    int id = rs.getInt(1);
                    name = rs.getString(2);
                    gender = rs.getString(3);
                    interest = rs.getString(4);
                    favoritePlace = rs.getString(5);
                    details = rs.getString(6);
                    
                    pw.write("id: " + id + " " + "|" +"name: " + name + " " + "|"  + "gender: " + gender + " " + "|" + "interest: " + interest + " " + "|" + "favoritePlace: "+ favoritePlace + " " + "|" + "details: " + details + "\n");
                }
                
                if(sql<1){
                    System.out.println("insert failed");
                }else{
                    System.out.println("insert success");
                }
                s.close();
                connect.close();
                pw.close();
            }   catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
        }else if (e.getSource().equals(exit)) {
            System.exit(0);
        }

    }
    

    
}