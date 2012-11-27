/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.monitor;

import java.text.DateFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBFormatUtils.
 * 
 * @author serch
 */
public class SWBFormatUtils {
    
    /** The Constant SEGUNDO. */
    final static long SEGUNDO = 1000;
    
    /** The Constant MINUTO. */
    final static long MINUTO = 60 * SEGUNDO;
    
    /** The Constant HORA. */
    final static long HORA   = 60 * MINUTO;
    
    /** The Constant DIA. */
    final static long DIA    = 24 * HORA;
    
    /** The df. */
    private static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

    /**
     * Format time.
     * 
     * @param t the t
     * @return the string
     */
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

    /**
     * Format date.
     * 
     * @param time the time
     * @return the string
     */
    static synchronized String formatDate(long time){
        return df.format(time);
    }

    /**
     * Format long.
     * 
     * @param value the value
     * @return the string
     */
    static String formatLong(long value) {
	return String.format("%,d", value);
    }

    /**
     * Format millis.
     * 
     * @param ms the ms
     * @return the string
     */
    static String formatMillis(long ms)
    {
        return String.format("%.4fsec", ms / 1000d);
    }
}
