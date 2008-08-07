/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;
/**
 *
 * @author victor.lorenzana
 */
public class Part
{   
    private byte[] content;
    private String name;
    private String fileName;
    public Part(byte[] content,String name,String fileName)
    {
        this.content=content;
        this.name=name;
        this.fileName=fileName;
    }
    public String getFileName()
    {
        return fileName;
    }
    public String getName()
    {
        return name;
    }
    public byte[] getContent()
    {
       return content;
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
        if(obj instanceof Part)
        {
            return ((Part)obj).name.equals(this.name);
        }
        return false;
    }
    
}
