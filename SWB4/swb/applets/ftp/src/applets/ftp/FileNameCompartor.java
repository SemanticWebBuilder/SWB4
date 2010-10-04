/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.ftp;

import java.util.Comparator;
import javax.swing.JLabel;

/**
 *
 * @author victor.lorenzana
 */
public class FileNameCompartor implements Comparator<applets.ftp.File> {

    
    
    private boolean asc=false;
    public int compare(applets.ftp.File o1, applets.ftp.File o2)
    {
        if(asc)
            return o1.getName().compareToIgnoreCase(o2.getName());
        else
            return o2.getName().compareToIgnoreCase(o1.getName());
    }
    public boolean  isAsc()
    {
        return asc;
    }
    public void toogle()
    {
        asc=!asc;        
    }

}
