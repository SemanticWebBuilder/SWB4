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
public class SizeFileComparator implements Comparator<applets.ftp.File> {

   
    boolean asc=false;
    public int compare(File o1, File o2)
    {
       
        try
        {
            int size1=Integer.parseInt(o1.size);
            int size2=Integer.parseInt(o2.size);
            if(asc)
                return size1>size2?1:-1;
            else
                return size2>size1?1:-1;
        }
        catch(NumberFormatException nfe)
        {
            nfe.printStackTrace();
        }
        return 1;
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