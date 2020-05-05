/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherapplicationproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import weatherapplicationproject.UI.LoginUI;
import weatherapplicationproject.UI.ClientUI;

/**
 *
 * @author Daleh
 */
public class Client {
   public static LoginUI loginScreen = new LoginUI(); 
   public static ClientUI clientSceen = new ClientUI();
   static String loginInput    = null;
   static String passwordInput = null;
   //static String clientType    = "UserClient";
   static String loginResponse = "";
   public static DataInputStream din;
   public static DataOutputStream dout; 
   private static Socket s;
}
