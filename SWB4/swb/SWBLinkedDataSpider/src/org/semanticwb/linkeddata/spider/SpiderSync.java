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
    public void get()
    {
        try
        {
            if (!SpiderManager.predicates.hasPred(url.toURI()))
            {
                super.get();
            }
        }
        catch (Exception e)
        {
        }
    }

    @Override
    public synchronized void onTriple(URI suj, URI pred, String obj, Spider source, String lang)
    {
        TripleElement element = new TripleElement(suj, pred, obj);
        SpiderManager.addPredicate(element);
        //super.onTriple(suj, pred, obj, source, lang);
    }

    @Override
    public void onTriple(URI suj, URI pred, URI obj, Spider source, String lang)
    {
        TripleElement element = new TripleElement(suj, pred, obj.toString());
        SpiderManager.addPredicate(element);
        //super.onTriple(suj, pred, obj, source, lang);
    }

    @Override
    public void onNewSubject(URI suj,TYPE type,Spider spider)
    {
    }

    @Override
    protected void onPred(URI pred)
    {
    }
}
