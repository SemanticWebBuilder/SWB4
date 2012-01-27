/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFileInputStream extends FileInputStream
{
    public SWBFileInputStream(String name) throws FileNotFoundException {
        super(name);
    }

    public SWBFileInputStream(FileDescriptor fdObj) throws FileNotFoundException {
        super(fdObj);
    }

    public SWBFileInputStream(File file) throws FileNotFoundException {
        super(file);
    }

}
