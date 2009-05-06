/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import org.junit.Test;

/**
 *
 * @author cramos
 */
public class Gral {


    @Test
    public void replace() {
        
        String str = "Her name is Tamana and Tamana is a good girl.";
        String strreplace = "Sonia";
        String result = str.replaceAll("Tamana", "qq");
        System.out.println(result);

        str = "[ 1 , 2, 3, 4 ]";
        str = str.replaceFirst("\\[", "");
        str = str.replaceFirst("\\]", "");
        str=str.replaceAll(" ", "");
        System.out.println(str);
    }

}
