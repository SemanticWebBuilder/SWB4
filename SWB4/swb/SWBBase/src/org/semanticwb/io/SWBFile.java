/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.io;

import java.io.File;
import java.net.URI;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFile extends java.io.File{

    public SWBFile(String pathName)
    {
        super(pathName);
    }

    public SWBFile(URI uri)
    {
        super(uri);
    }

    public SWBFile(File parent, String child)
    {
        super(parent, child);
    }

    public SWBFile(String parent, String child)
    {
        super(parent, child);
    }


}
