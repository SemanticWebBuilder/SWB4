/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URI;

/**
 *
 * @author victor.lorenzana
 */
public class TripleElement
{

    public TripleElement(URI suj, URI pred, String obj)
    {
        this.suj = suj;
        this.pred = pred;
        this.obj = obj;
    }
    public URI suj;
    public URI pred;
    public String obj;

    @Override
    public String toString()
    {

        return suj.toString() + "pred:" + pred.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof TripleElement && obj != null)
        {
            TripleElement element = (TripleElement) obj;
            return element.suj.equals(suj) & element.pred.equals(pred);
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
}
