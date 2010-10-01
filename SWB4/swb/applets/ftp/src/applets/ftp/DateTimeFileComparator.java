/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.ftp;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author victor.lorenzana
 */
public class DateTimeFileComparator implements Comparator<applets.ftp.File> {

    boolean asc=false;
    private static final SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public int compare(File o1, File o2)
    {
        try
        {
            Date date1=df.parse(o1.getLastUpdate());
            Date date2=df.parse(o2.getLastUpdate());
            if(asc)
                return date1.compareTo(date2);
            else
                return date2.compareTo(date1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    public void toogle()
    {
        asc=!asc;
    }

}
