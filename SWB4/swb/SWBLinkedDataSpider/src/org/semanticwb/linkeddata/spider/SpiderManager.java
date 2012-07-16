/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderManager
{

    public static final Predicates predicates = new Predicates();
    private static SpiderDomainManager domainManager = new SpiderDomainManager();
    private static final Set<SpiderEventListener> listeners = Collections.synchronizedSet(new HashSet<SpiderEventListener>());

    public static SpiderDomainManager getSpiderDomainManager()
    {
        return domainManager;
    }

    public static void createSpider(URL url)
    {
        createSpider(url, null);
    }

    public static void createSpider(URL url, Spider source)
    {

        SpiderDomain domain = domainManager.get(url);
        if (domain == null)
        {
            domain = new SpiderDomain(url);
            domainManager.put(url, domain);
        }

        Spider spider = new Spider(url, domain);
        domain.addSpider(spider);


    }

    public synchronized static Set<SpiderEventListener> getListeners()
    {
        Set<SpiderEventListener> _listeners = new HashSet<SpiderEventListener>();
        _listeners.addAll(listeners);
        return _listeners;
    }

    public static SpiderSync createSpiderPred(URL url)
    {
        SpiderDomain domain = new SpiderDomain(url);
        if (domainManager.containsKey(url))
        {
            domain = domainManager.get(url);
        }
        else
        {
            domain = domainManager.put(url, domain);
        }

        SpiderSync spider = new SpiderSync(url, domain);
        domain.addSpider(spider);
        return spider;

    }

    public static void addSpiderEventListener(SpiderEventListener listener)
    {
        listeners.add(listener);
    }

    public static synchronized void onPred(URI pred, Spider spider)
    {
        loadPredicates(pred);


    }

    public static void loadPredicates(URI pred)
    {
        String nopred = System.getProperty("org.semanticwb.linkeddata.spider.SpiderManager.NoPred");
        if (nopred != null && !nopred.equals("true"))
        {
            try
            {
                SpiderSync _spider = new SpiderSync(pred.toURL(), null);
                _spider.run();

            }
            catch (Exception e)
            {
            }
        }
    }

    public static void addPredicate(TripleElement element)
    {
        predicates.add(element);
    }
}
