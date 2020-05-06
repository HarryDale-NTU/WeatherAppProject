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
import java.util.Random;
import static weatherapplicationproject.User.clientMode;
import static weatherapplicationproject.User.din;
import static weatherapplicationproject.User.dout;

/**
 *
 * @author Daleh
 */
public class WeatherStation {
    
    static String Id = null;
    static String clientMode = "Weather";
    static float posLattitude   = 0;
    static float posLongtitude  = 0;
    static float airTemp = 0;
    static float windSpeed   = 0;
    static float testSpeed   = 0;
    static String windDir  = null;
    static String[] windDirList = {"North","East","South","West","North East","South East","South West","North West"};
    
    public static void main(String[] args) throws IOException, InterruptedException
    {

        try{
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 5050);        
            din  = new DataInputStream(s.getInputStream()); 
            dout = new DataOutputStream(s.getOutputStream());                
            dout.writeUTF(clientMode);
            GetID(din);
            
            while (true)
            {
                CreateValues();
                SendValues(dout);
                Thread.sleep(5000);
            }  
        }
        catch(IOException e)
        {
                    
        }
    }
    
    
    
    static void GetID(DataInputStream din)
    {
        try{
            Id = din.readUTF();
        }
        catch(IOException e)
        {
                    
        }
    }
    static void CreateValues() 
    {
        Random random = new Random();
        posLattitude   = random.nextFloat()- 1.14f ;
        posLongtitude  = random.nextFloat()- 1.14f;
        airTemp = random.nextFloat()- 1.14f;
        windSpeed = random.nextFloat()- 1.14f;
        testSpeed = random.nextFloat()-1.4f;
        //windDir = windDirList[random.nextInt(7)];
    }
        
    static void SendValues(DataOutputStream dout) throws IOException
     {
        dout.writeUTF(Id);
         
        dout.writeUTF(Float.toString(posLattitude));
        dout.writeUTF(Float.toString(posLongtitude));
        dout.writeUTF(Float.toString(airTemp));
        dout.writeUTF(Float.toString(windSpeed));
        dout.writeUTF(Float.toString(testSpeed));                
     }   
    
}
