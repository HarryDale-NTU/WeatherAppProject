/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherapplicationproject;

import java.io.*;
import java.net.*;
import weatherapplicationproject.UI.ServerUI;

/**
 *
 * @author Daleh
 */
public class Server {
    
    static ServerUI serverScreen = new ServerUI(); 
    
    public class createConnections{
        try 
            {                    
                // Make Socket Object. 
                s = _ss.accept();                   
                System.out.println("New Connection recognised. Socket details:  " + s);                   
                // obtain input and out streams.
                DataInputStream dis  = new DataInputStream( s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());                   
                System.out.println("New Connection data I/O streams defined.");  
                // Create a new server thread to handle client updates using the ClientHandler. 
                clientHandler t = new clientHandler(s, dis, dos);  
               // allWeatherStations.add(t);
                System.out.println("New Connection assigned new server thread and added to vector.");                 
                // Start this new threads execution.                   
                t.start();        
                
                
                try{
            ss = new ServerSocket(1201); //server start at 1201 port
            
            
            while(true)
            {
                s = ss.accept();// now server will accept the connection
                //serverScreen.areaServer.setText(areaServer.getText().trim() +"Server open on port 1201".trim());
                //areaServer.setText(areaServer.getText().trim() +"\nNew client ("+(ID+1)+") request received : "+ s);
                
                DataInputStream din  = new DataInputStream( s.getInputStream()); 
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                clientHandler newClient = new clientHandler(s,ID);
                Thread t = new Thread(newClient);
                
                //areaServer.setText(areaServer.getText().trim() +"\nAdding new client to list\n");
                
                //clientList.add(newClient);
                t.start();
            }
        //display a Server confirmation effort
        }catch(IOException e){
        }   

        try{
            DatagramSocket server = new DatagramSocket(1201);
            byte[] data = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(data,data.length);
            server.receive (receivedPacket);
            data = receivedPacket.getData();
            
            
        }
        catch (Exception e){       
        }
            } 
    }
    public class clientHandler{
        
    }
    public class weatherStationHandler{
        
    }
            
            
    
    
}


