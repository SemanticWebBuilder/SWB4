/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.lib;

import java.io.OutputStream;

/**
 *
 * @author jorge.jimenez
 */
public class WBServletOutputStreamImp extends javax.servlet.ServletOutputStream 
{
    OutputStream out=null;
    
    /** Creates a new instance of WBServletOutputStreamImp */
    public WBServletOutputStreamImp(OutputStream out)
    {
        this.out=out;
    }
    
    public void write(int b) throws java.io.IOException
    {
        out.write(b);
    }
}
