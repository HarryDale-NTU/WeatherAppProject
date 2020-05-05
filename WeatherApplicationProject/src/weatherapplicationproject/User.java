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
   
    public static void main(String[] args) throws IOException  
    {
        try
        {
            
            loginScreen.setVisible(true);

            

            while(true)
            {
                if(mode.equals("Login"))
                {
                    attemptConnection();
                    attemptLogin();
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
           
           //response = din.readUTF();
           clientScreen.setVisible(true);
           loginScreen.setVisible(false);
           mode = "";
           
           
           
           if (response.equals("Success"))
           {
               JOptionPane.showMessageDialog(null, "Win");
           }
       }
       catch(IOException e)
       {
           
       }
       
       }
      
//   static void downloadWeatherStation()
//   {
//       try 
//       {
//           
//       }
//       catch(IOException e)
//       {
//           
//       }
//       
//       
//   }
   
}

 
