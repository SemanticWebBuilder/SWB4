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
public class NameDirectoryCompartor implements Comparator<Directory> {

    public int compare(Directory o1, Directory o2)
    {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }

}
