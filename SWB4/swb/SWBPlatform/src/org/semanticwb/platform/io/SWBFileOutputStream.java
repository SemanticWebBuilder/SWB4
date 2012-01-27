/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFileOutputStream extends FileOutputStream{

 public SWBFileOutputStream(String name) throws FileNotFoundException {
        super(name);
 }

}
