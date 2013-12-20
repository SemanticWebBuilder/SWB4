/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.dimensiondata;

/**
 *
 * @author yutzin.wong
 */
public class count {
    public static void main ( String [] args){
    String count  = "001";
   int a  = Integer.parseInt(count)+1;
   count = String.valueOf(a); 
        System.out.println(a); 
    long time =  System.currentTimeMillis(); 
        System.out.println("time"+ time);
    }
}
