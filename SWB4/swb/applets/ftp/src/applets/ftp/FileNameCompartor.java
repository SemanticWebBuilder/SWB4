/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.ftp;

import java.util.Comparator;

/**
 *
 * @author victor.lorenzana
 */
public class FileNameCompartor implements Comparator<applets.ftp.File> {

    boolean asc=false;
    public int compare(applets.ftp.File o1, applets.ftp.File o2)
    {
        if(asc)
            return o1.getName().compareToIgnoreCase(o2.getName());
        else
            return o2.getName().compareToIgnoreCase(o1.getName());
    }
    public void toogle()
    {
        asc=!asc;
    }

}
