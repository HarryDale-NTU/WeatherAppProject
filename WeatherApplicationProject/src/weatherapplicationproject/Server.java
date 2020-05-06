/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherapplicationproject;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JOptionPane;
import static weatherapplicationproject.Server.serverScreen;
import weatherapplicationproject.UI.ServerUI;

/**
 *
 * @author Daleh
 */
public class Server {
    
    public static ServerUI serverScreen = new ServerUI();
    static Vector<Vector> listStation = new Vector(0);
    static Vector<Vector> listUser = new Vector(0);
    
    
    public static void main(String[] args) throws IOException
    {
       serverScreen.setVisible(true); 
              
       ServerSocket ss = new ServerSocket(5050);
         
       while(true)
       {
        createConnections(ss);

       }
    }
    
    static void createConnections(ServerSocket ss)throws IOException
    {   
        Socket s = null;
         try
         {
            s = ss.accept();// now server will accept the connection
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            clientHandler newClient = new clientHandler(s,din,dout);
            Thread t = new Thread(newClient);
            t.start();
            
         }
         catch(IOException e)
         {
          s.close();
            JOptionPane.showMessageDialog(null, "Error4"); //error handling
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
    
    public static Vector<Float> stationData = new Vector(5);
    static int   Id = 0;
    static float lattitude   = 0;
    static float longtitude  = 0;
    static float elevation   = 0;
    static float temperature = 0;
    static float humidity    = 0;
    
    
    
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
        
        while ("User".equals(clientMode))
            {
                try{
                userLoginVerify(this.din, this.dout);
                }
                catch (IOException e)
                {
                    
                }
            }
        while (clientMode.equals("Weather"))
            {
                clientWeatherStation(this.din, this.dout);
  
            }
//        while("User".equals(clientMode))
//        {
//            userLoginVerify(this.s, this.din, this.dout);
//        }
    }
    
    static void clientCheck(DataInputStream din) throws IOException 
     {                              
                                      
     }
            
    static void userLoginVerify(DataInputStream din, DataOutputStream dout)throws IOException 
    {
        try
        {
        

        String mode = din.readUTF();
        
        if (mode.equals("Login"))
        {
            
            boolean isLocated = false; //Set check for located file as false
            String userTemp = "";//Declared empty Variable for password
            String passTemp = "";//Declared empty Variable for password
            String userFile = "Users.txt";
            String response     = "Failed";
            

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
                        response = "Success";
                        serverScreen.areaUsers.setText(serverScreen.areaUsers.getText().trim()+username.trim()+"\n");
                    }
                }
                   dout.writeUTF(response);
                   
                   
                   if(response.equals("Success"))
                {
                    // Calc the amount of connected stations and send the num to client. (taken from the textArea)
                    int connectedStationsAmount = 0;
                    for (String currentLine : serverScreen.areaStations.getText().split("\\n")) 
                    {              
                      connectedStationsAmount++;
                    }             
                    dout.writeUTF(Integer.toString(connectedStationsAmount));

                    // Send each connected weather station Id to the client GUI. 
                    for(String currentLine : serverScreen.areaStations.getText().split("\\n"))
                    {
                      dout.writeUTF(currentLine);
                    }
                    
                }  
                mode = "";
            }
        
            
            if(mode.equals("Download"))
            {
              // Read which is selected from client.
              int selectedId = Integer.valueOf(din.readUTF());  
              
              // Send all data of selected Ws to the client.                         
              dout.writeUTF(Server.listStation.get(selectedId).get(0).toString());
              dout.writeUTF(Server.listStation.get(selectedId).get(1).toString());
              dout.writeUTF(Server.listStation.get(selectedId).get(2).toString());
              dout.writeUTF(Server.listStation.get(selectedId).get(3).toString());
              dout.writeUTF(Server.listStation.get(selectedId).get(4).toString());
                
               mode = "";
            }
        }
        catch (IOException e)
        {
            
        }
             
}
    

    
   
    static void clientUser()
    {
        
    }
    
    static void clientWeatherStation(DataInputStream din, DataOutputStream dout)
    {   
        try{
          Id = Server.listStation.size();
          dout.writeUTF(Integer.toString(Id));
          Id = Integer.valueOf(din.readUTF());
          
          String currentWeatherStations =  serverScreen.areaStations.getText();          
            if(currentWeatherStations.contains(Integer.toString(Id)) == false)
            {             
               serverScreen.areaStations.append(Integer.toString(Id) + "\n");            
            } 
            
            lattitude   = Float.valueOf(din.readUTF()); 
            longtitude  = Float.valueOf(din.readUTF()); 
            elevation   = Float.valueOf(din.readUTF()); 
            temperature = Float.valueOf(din.readUTF()); 
            humidity    = Float.valueOf(din.readUTF());
            
            stationData.insertElementAt(lattitude  , 0);
            stationData.insertElementAt(longtitude , 1);
            stationData.insertElementAt(elevation  , 2);
            stationData.insertElementAt(temperature, 3);
            stationData.insertElementAt(humidity   , 4);
            
            Server.listStation.insertElementAt(stationData, Id);
//          serverScreen.areaUsers.setText(serverScreen.areaUsers.getText().trim() +"\n"+username.trim());
//          serverScreen.areaStations.setText(serverScreen.areaStations.getText().trim() +"\n"+Id);
        }
        catch (IOException e)
        {
            
        }
        
  
    }
    
   
    

}
            
    
            



