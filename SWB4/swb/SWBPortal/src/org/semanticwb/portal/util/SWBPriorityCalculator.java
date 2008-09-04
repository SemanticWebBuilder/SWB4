/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

/**
 *
 * @author Jei
 */
public class SWBPriorityCalculator 
{
    /**
     * @param p
     * @return  */
    public static int calcPriority(int p) {
        if (p == 0)
            return 0;
        else if (p == 1)
            return 1;
        else if (p == 5)
            return 50;
        else if (p > 5)
            return 60;
        else {
            return (int) (Math.random() * 10 * p) + 2;
        }
    }    
}
