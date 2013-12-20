/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.dimensiondata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author yutzin.wong
 */
public class TestChkStatus {

  public static double getCurrentUsage(String ip) throws IOException{
//   public static void main(String[] args) throws Exception {
//        System.out.println(ip);
        Socket statusSocket = new Socket("10.192.103.12", 666);
        PrintWriter out =
                new PrintWriter(statusSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                new InputStreamReader(statusSocket.getInputStream()));
        String userInput = ""; 
        String infos;
                
        while ((infos=in.readLine()) != null) {
            userInput += infos + ("\n");
            System.out.println(userInput);
        }


        out.close();
        in.close();
        statusSocket.close();
        System.out.println("user input  " + userInput);
        
        String del = "[\\n]+"; 
        String [] info; 

        info = userInput.split(del); 
        System.out.println(info.length);

    double CpuAverage = getCPUAverage(info); 
        System.out.println(CpuAverage);
      return CpuAverage; 
    
    }

    public static double getCPUAverage(String[] info) {
       
       double val= 0.0;
       int den = info.length; 
       String [] each; 
       for (int i =0; i < info.length; i++ ){    
            each = info[i].split("\\s+"); 
            val += (Double.parseDouble(each[1]));
            System.out.println(val);
//            for (int j = 0 ; j< each.length; j ++ )
//                System.out.println(each[j]);
            }
        System.out.println(den);
       double averag = val/den;
       
       return (averag);
    }
}
