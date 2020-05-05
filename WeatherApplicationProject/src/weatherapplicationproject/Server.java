/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherapplicationproject;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import static weatherapplicationproject.Server.serverScreen;
import weatherapplicationproject.UI.ServerUI;

/**
 *
 * @author Daleh
 */
public class Server {
    
    static Socket s = null;
    public static ServerUI serverScreen = new ServerUI(); 
    
    public static void main(String[] args) throws IOException
    {
       serverScreen.setVisible(true); 
              
       ServerSocket ss = new ServerSocket(5050);
         
       createConnections(ss);

    }
    
    static void createConnections(ServerSocket ss)throws IOException
    {   
         try
         {
            s = ss.accept();// now server will accept the connection
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            clientHandler newClient = new clientHandler(s,din,dout);
            Thread t = new Thread(newClient);
            t.start();
            
            }catch(IOException e)
            {
               JOptionPane.showMessageDialog(null, "Error"); //error handling
            }                       
        }
}

class clientHandler extends Thread
{
    static String clientMode = ""; 
    final DataInputStream din; 
    final DataOutputStream dout; 
    final Socket s;
    private static Scanner x;
    
    static String username = "";
    static String password  = "";
    
    
    
    public clientHandler(Socket s, DataInputStream din, DataOutputStream dout)
    {
        this.s = s; 
        this.din = din; 
        this.dout = dout;
    }
    
    @Override
    public void run()
    {
        do
        {
            try
            {
                clientMode = din.readUTF();
            }
            catch (IOException e)
            {
                
            }
        }while(clientMode.equals(""));  
        
        while (clientMode.equals("User"))
            {
                try{
                userLoginVerify(this.s, this.din, this.dout);
                }
                catch (IOException e)
                {
                    
                }
            }
//        while("User".equals(clientMode))
//        {
//            userLoginVerify(this.s, this.din, this.dout);
//        }
    }
    
    static void clientCheck(DataInputStream din) throws IOException 
     {                              
                                      
     }
            
    static void userLoginVerify(Socket s, DataInputStream din, DataOutputStream dout)throws IOException 
    {
        boolean isLocated = false; //Set check for located file as false
        String userTemp = "";//Declared empty Variable for password
        String passTemp = "";//Declared empty Variable for password
        String userFile = "Users.txt";
        String connection     = "Failed";

        String mode = din.readUTF();
        
        if (mode.equals("Login"))
        {
            try
            {
                username = din.readUTF();
                password = din.readUTF();

                x = new Scanner(new File(userFile));
                x.useDelimiter("[,\n]");//remove , from the string inside file
                while (x.hasNext() && !isLocated) 
                {
                    userTemp = x.next();
                    passTemp = x.next();

                    if (userTemp.trim().equals(username.trim()) && passTemp.trim().equals(password.trim())) 
                    {
                        isLocated = true;
                        connection = "Success";
                        serverScreen.areaUsers.setText(serverScreen.areaUsers.getText().trim() +username.trim());
                    }
                }
                   dout.writeUTF(connection);
                   mode = "";
            } 
            catch (Exception e) 
            {       
                JOptionPane.showMessageDialog(null, "Error 1");//Catch any error and display to user
            }
        }
    }
   
    static void clientUser()
    {
        
        
        
        
        
    }
    
    static void clientWeatherStation(DataInputStream din, DataOutputStream dout)
    {   
        
        
        
        
        
        
    }
    
   
    

}
            
    
            



