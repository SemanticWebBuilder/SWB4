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
package org.semanticwb.portal;

import java.text.SimpleDateFormat;
import org.junit.Test;
import java.util.GregorianCalendar;

public class Calendario {

    //@Test
    public void Dates() {
        GregorianCalendar gc1 = new GregorianCalendar(2008, 11, 1);
        GregorianCalendar gc2 = new GregorianCalendar(2009, 1, 2);

        while(!gc1.after(gc2)) {
            System.out.println("fecha=" + gc1.get(gc1.DAY_OF_MONTH) + "/" + gc1.get(gc1.MONTH) + "/" + gc1.get(gc1.YEAR));
            gc1.add(gc1.DATE, 1);            
        }
    }

    @Test
    public void seahoy() {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         String date = sdf.format(cal.getTime());
         System.out.println("hoy="+date);

         System.out.println("dia del mes="+cal.get(cal.DAY_OF_MONTH));
         System.out.println("ultimo dia del mes="+cal.getActualMaximum(cal.DAY_OF_MONTH));
         System.out.println("dias para terminar el mes="+(cal.getActualMaximum(cal.DAY_OF_MONTH)-cal.get(cal.DAY_OF_MONTH)));
         cal.add(cal.DATE, cal.getActualMaximum(cal.DAY_OF_MONTH)-cal.get(cal.DAY_OF_MONTH));

         date = sdf.format(cal.getTime());
         System.out.println("fin de mes="+date);
    }

}
