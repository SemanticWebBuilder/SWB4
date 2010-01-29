/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.monitor;

import java.text.DateFormat;

/**
 *
 * @author serch
 */
public class SWBFormatUtils {
    final static long SEGUNDO = 1000;
    final static long MINUTO = 60 * SEGUNDO;
    final static long HORA   = 60 * MINUTO;
    final static long DIA    = 24 * HORA;
    private static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

    static String formatTime(long t) {
        String str;
        if (t < 1 * MINUTO) {
            String seconds = String.format("%.3f", t / (double)SEGUNDO);
            str = ""+seconds+" Segundos";
        } else {
            long remaining = t;
            long days = remaining / DIA;
            remaining %= 1 * DIA;
            long hours = remaining / HORA;
            remaining %= 1 * HORA;
            long minutes = remaining / MINUTO;

            if (t >= 1 * DIA) {
                str = ""+days+" dias, "+hours+" horas, "+minutes+" minutos";
            } else if (t >= 1 * HORA) {
                str = ""+hours+" horas, "+minutes+" minutos";
            } else {
                str = ""+minutes+" minutos";
            }
        }
        return str;
    }

    static synchronized String formatDate(long time){
        return df.format(time);
    }

    static String formatLong(long value) {
	return String.format("%,d", value);
    }
}
