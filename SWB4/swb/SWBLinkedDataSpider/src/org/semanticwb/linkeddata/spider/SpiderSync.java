/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URI;
import java.net.URL;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderSync extends Spider
{

    public SpiderSync(URL seedURL, SpiderDomain domain)
    {
        super(seedURL, domain);

    }

    @Override
    public synchronized void onTriple(URI suj, URI pred, String obj, Spider source, String lang)
    {
        TripleElement element = new TripleElement(suj, pred, obj);
        SpiderManager.addPredicate(element);
        
    }

    @Override
    public synchronized void onTriple(URI suj, URI pred, URI obj, Spider source, String lang)
    {
        TripleElement element = new TripleElement(suj, pred, obj.toString());
        SpiderManager.addPredicate(element);
        
    }

    @Override
    public void fireEventnewTriple(URI suj, URI pred, String obj, Spider spider, String lang)
    {
        for (SpiderEventListener listener : this.getListeners())
        {
            listener.onTriple(suj, pred, obj, spider, lang);
        }
    }

    @Override
    public void fireEventnewTriple(URI suj, URI pred, URI obj, Spider spider, String lang)
    {
        for (SpiderEventListener listener : this.getListeners())
        {
            listener.onTriple(suj, pred, obj, spider, lang);
        }
    }
}
