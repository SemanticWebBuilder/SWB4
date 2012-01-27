/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform.io;

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
}
