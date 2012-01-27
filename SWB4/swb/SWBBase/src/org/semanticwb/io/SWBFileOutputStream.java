/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFileOutputStream extends FileOutputStream {

    public SWBFileOutputStream(String name) throws FileNotFoundException {
        super(name);
    }

    public SWBFileOutputStream(File file) throws FileNotFoundException {
        super(file);
    }

    public SWBFileOutputStream(FileDescriptor fdObj) throws FileNotFoundException {
        super(fdObj);
    }

    public SWBFileOutputStream(File file, boolean append) throws FileNotFoundException {
        super(file, append);
    }

    public SWBFileOutputStream(String name, boolean append) throws FileNotFoundException {
        super(name, append);
    }
}
