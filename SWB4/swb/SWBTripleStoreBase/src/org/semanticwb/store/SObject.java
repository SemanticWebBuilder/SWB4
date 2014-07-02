/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

import static org.semanticwb.store.Graph.separator;

/**
 *
 * @author javier.solis.g
 */
public class SObject
{
    public String s;
    public String p;
    public String o;
    public String e;

    public SObject(String key, String val, int shift)
    {
        String t[]=key.split(""+separator);
        
        if(shift==0)
        {
            s=t[0];
            p=t[1];
            o=t[2];
        }else if(shift==1)
        {
            s=t[2];
            p=t[0];
            o=t[1];
        }else if(shift==2)
        {
            s=t[1];
            p=t[2];
            o=t[0];
        }   
        e=val;
    }
    
    public SObject(String t[])
    {
        this.s=t[0];
        this.p=t[1];
        this.o=t[2];
        this.e=t[3];
    }
    
    public SObject(String s, String p, String o, String e)
    {
        this.s=s;
        this.p=p;
        this.o=o;
        this.e=e;
    }

    @Override
    public String toString()
    {
        return s+" "+p+" "+o+" "+e;
    }
    
    
    
}
