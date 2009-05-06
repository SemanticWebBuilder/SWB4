

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
