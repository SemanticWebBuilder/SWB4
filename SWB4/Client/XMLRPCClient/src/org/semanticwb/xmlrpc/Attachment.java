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
    @Override
    public String toString()
    {
        this.hashCode();
        return this.name;        
    }
    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }
    @Override
    public boolean equals(Object obj)
    {
        boolean equals=false;
        if(obj instanceof Attachment)
        {
            equals=((Attachment)obj).name.equals(this.name);
        }
        return equals;
    }
}
