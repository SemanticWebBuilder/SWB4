/*
 * WBServletOutputStreamImp.java
 *
 * Created on 10 de marzo de 2006, 10:24 AM
 */

package org.semanticwb.servlet;

import java.io.OutputStream;

/**
 *
 * @author Javier Solis Gonzalez
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
