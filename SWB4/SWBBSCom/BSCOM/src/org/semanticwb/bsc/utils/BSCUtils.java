/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bsc.utils;

import java.math.BigDecimal;

/**
 *
 * @author carlos.ramos
 */
public class BSCUtils {
    public static class Formats {
        public static BigDecimal round(float d, int decimalPlace) {
            BigDecimal bd = new BigDecimal(Float.toString(d));
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
            return bd;
        }
    }
}
