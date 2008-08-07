/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.io.File;

/**
 *
 * @author victor.lorenzana
 */
public class Attachment {
    private final File file;
    private final String name;
    public Attachment(File file,String name)
    {
        this.file=file;
        this.name=name;
    }
    public Attachment(File file)
    {
        this.file=file;
        this.name=file.getName();
    }
    public File getFile()
    {
        return file;
    }
    public String getName()
    {
        return name;    
    }
}
