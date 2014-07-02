/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

/**
 *
 * @author javier.solis.g
 */
public class Triple
{
    private Node s;
    private Node p;
    private Node o;   
    private Integer hash;
    
    public Triple(String line)
    {
        int j1 = line.indexOf(' ');
        int j2 = line.indexOf(' ', j1 + 1);

        String subj = line.substring(0, j1);
        String prop = line.substring(j1 + 1, j2);
        String obj = line.substring(j2 + 1, line.length() - 2);       
        
        s=(Resource)Node.parseNode(subj);
        p=(Resource)Node.parseNode(prop);
        o=Node.parseNode(obj);
    }

    public Triple(String ss, String sp, String so)
    {
        s=Node.parseNode(ss);
        p=Node.parseNode(sp);
        o=Node.parseNode(so);
    }
    
    public Triple(Node s, Node p, Node o)
    {
        this.s=s;
        this.p=p;
        this.o=o;
    }

    public Node getSubject()
    {
        return s;
    }

    public Node getProperty()
    {
        return p;
    }
    
    public Node getObject()
    {
        return o;
    }   
    
    @Override
    public String toString()
    {
        return s+" "+p+" "+o+" .";
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean ret=true;
        if(obj instanceof Triple)
        {
            Triple l=(Triple)obj;
            if(s!=null && !s.equals(l.s))ret=false;
            if(ret && p!=null && !p.equals(l.p))ret=false;
            if(ret && o!=null && !o.equals(l.o))ret=false;
        }else ret=false;
        return ret;
    }

    @Override
    public int hashCode()
    {
        if(hash==null)hash=toString().hashCode();
        return hash;
    }
    
    
    
}

