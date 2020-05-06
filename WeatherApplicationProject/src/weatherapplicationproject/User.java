/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherapplicationproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;
import weatherapplicationproject.UI.LoginUI;
import weatherapplicationproject.UI.ClientUI;

/**
 *
 * @author Daleh
 */
public class User {
   public static LoginUI loginScreen = new LoginUI(); 
   public static ClientUI clientScreen = new ClientUI();
   
   static String username    = null;
   static String password = null;
   static String clientMode    = "User";
   static String response = "";
   //static String loginResponse = "";
   public static DataInputStream din;
   public static DataOutputStream dout; 
   private static Socket s;
   public static String mode = "";
   
   
   
    static String selectedStation = null; 
    static String selectedLattitude;
    static String selectedLongtitude; 
    static String selectedWindDirection;
    static String selectedWindSpeed;
    static String selectedTemp;
   
   
    public static void main(String[] args) throws IOException  
    {
        try
        {
            loginScreen.setVisible(true);
            
            while (true)
            {
            System.out.print(mode + "\n");
            if(mode.equals("Login"))
            {
                attemptConnection();
                attemptLogin();
            }
            if(mode.equals("Download"))
                {
                    downloadWeatherStation();            
                }
            }
            
        }
                            
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(null, "Error 2");             
        }    
    }
    
   static void attemptConnection()
    {
        try
        {               
            InetAddress ip = InetAddress.getByName("localhost");
            s = new Socket(ip, 5050);        
            din  = new DataInputStream(s.getInputStream()); 
            dout = new DataOutputStream(s.getOutputStream());                
            dout.writeUTF(clientMode);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error 3");
        }
    }
   
   static void attemptLogin()
   {
       try
       {
           //dout.writeUTF();
           dout.writeUTF("Login");
           username = loginScreen.txtUser.getText();
           password = loginScreen.txtPass.getText();
           
           dout.writeUTF(username);
           dout.writeUTF(password);
           
           response = din.readUTF(); 
           
           if (response.equals("Success"))
           {
               clientScreen.lblUser.setText(username);
               clientScreen.setVisible(true);
               loginScreen.setVisible(false);
               
               JOptionPane.showMessageDialog(null, "Win");

                 int amountOfConnectedStations = 0;
                 amountOfConnectedStations =  Integer.valueOf(din.readUTF());                                                
                 for(int i = 0; i < amountOfConnectedStations; i++)
                 {                 
                      clientScreen.cmbStationSelect.add(din.readUTF());
                 }
               
               mode = "";
               
               
           }
       }
       catch(IOException e)
       {
           
       }
       
       }
      
   static void downloadWeatherStation()
   {
       try 
       {
           dout.writeUTF("Download");
           
           selectedStation =  clientScreen.cmbStationSelect.getItem(clientScreen.cmbStationSelect.getSelectedIndex());
           dout.writeUTF(selectedStation);
           
            selectedLattitude  =  din.readUTF(); 
            selectedLongtitude =  din.readUTF(); 
            selectedWindSpeed  =  din.readUTF(); 
            selectedTemp       =  din.readUTF(); 
            selectedWindDirection   =  din.readUTF();
            
            clientScreen.areaDownload.setText("Weather Station Id : " +  selectedStation  + "\n" +
                "(Field) Lattitude    : " +  selectedLattitude  + "\n" +
                "(Field) Longtitude   : " +  selectedLongtitude + "\n" +
                "(Field) Wind Speed    : " + selectedWindSpeed  + "\n" +
                "(Weather) Temperature: " +  selectedTemp       + "\n" +                
                "(Weather) Humidity   : " +  selectedWindDirection);
            
            
            mode = "";
            selectedLattitude  =  null; 
            selectedLongtitude =  null; 
            selectedWindSpeed   =  null; 
            selectedTemp       =  null;  
            selectedWindDirection   =  null;  
            selectedStation = null; 
       }
       catch(IOException e)
       {
           
       }
       
       
   }
   
}

 
