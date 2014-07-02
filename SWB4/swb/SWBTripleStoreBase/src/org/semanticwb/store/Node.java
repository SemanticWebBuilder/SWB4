/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

/**
 *
 * @author javier.solis.g
 */
public class Node
{
    protected Node()
    {
        
    }
    
    public boolean isBlank()
    {
        return isResource() && asResource().hasId();
    }
    
    public boolean isResource()
    {
        return this instanceof Resource;
    }
    
    public boolean isLiteral()
    {
        return this instanceof Literal;
    }
    
    public Resource asResource()
    {
        return (Resource)this;
    }
    
    public Literal asLiteral()
    {
        return (Literal)this;
    }
    
    public static Node parseNode(String ntstr)
    {
        if(ntstr==null)return null;
        char ch=ntstr.charAt(0);
        if(ch=='\"')
        {
            int i=ntstr.lastIndexOf('\"');
            int j=ntstr.indexOf('@',i);
            int k=ntstr.indexOf("^^",i);
            String value=ntstr.substring(1,i);
            String lang=null;
            String type=null;
            if(j>-1)
            {
                if(k>-1)lang=ntstr.substring(j+1, k);
                else lang=ntstr.substring(j+1,ntstr.length());
            }
            if(k>-1)type=ntstr.substring(k+2,ntstr.length());
            return new Literal(value,lang,type);
        }
        return new Resource(ntstr);
    }
    
    
    public String getValue()
    {
        return null;
    }    
    
}
