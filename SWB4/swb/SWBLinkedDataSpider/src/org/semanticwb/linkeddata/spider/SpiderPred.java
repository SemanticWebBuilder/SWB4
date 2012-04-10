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
public class SpiderPred extends Spider
{

    public SpiderPred(URL seedURL)
    {
        super(seedURL);

    }

    @Override
    public synchronized  void onTriple(URI suj, URI pred, String obj, Spider source, String lang)
    {
        TripleElement element = new TripleElement(suj, pred, obj);
        predicates.add(element);
        super.onTriple(suj, pred, obj, source, lang);
    }

}
