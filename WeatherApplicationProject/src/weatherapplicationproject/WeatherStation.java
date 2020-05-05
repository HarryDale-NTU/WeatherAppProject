/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherapplicationproject;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Daleh
 */
public class WeatherStation {
    
    static String Id = null;
    //static String clientType = "WeatherStation";
    static float posLattitude   = 0;
    static float posLongtitude  = 0;
    static float airTemp = 0;
    static float windSpeed   = 0;
    static String windDir  = null;
    static String[] windDirList = {"North","East","South","West","North East","South East","South West","North West"};
    
    static void CreateValues() 
    {
        Random random = new Random();
        posLattitude   = random.nextFloat() ;
        posLongtitude  = random.nextFloat();
        airTemp = random.nextFloat();
        windSpeed = random.nextFloat();
        windDir = windDirList[random.nextInt(7)];
    }
    
    public static void main(String[] args) throws IOException 
    {
        
    }
    
}
