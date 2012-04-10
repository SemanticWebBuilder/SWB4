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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderManager
{
    public static final Predicates predicates = new Predicates();
    private static SpiderDomainManager domainManager = new SpiderDomainManager();
    private static final Set<SpiderEventListener> listeners = Collections.synchronizedSet(new HashSet<SpiderEventListener>());
    public static final Set<URL> visited = Collections.synchronizedSet(new HashSet<URL>());
    private static final Timer timer = new Timer("Spiders");
    private static final ConcurrentLinkedQueue<Spider> spiders = new ConcurrentLinkedQueue<Spider>();
    private static final HashSet<Spider> SetSpiders = new HashSet<Spider>();
    

    static
    {
        timer.schedule(new TimerTask()
        {

            @Override
            public void run()
            {
                HashSet<Spider> _spiderstoDelete = new HashSet<Spider>();
                for (Spider spider : spidersRunning)
                {
                    if (!spider.isRunning())
                    {
                        _spiderstoDelete.add(spider);
                    }
                }
                for (Spider spider : _spiderstoDelete)
                {
                    spidersRunning.remove(spider);
                }
                int max = 15;
                int dif = max - spidersRunning.size();
                for (int i = 0; i < dif; i++)
                {
                    Spider spider = spiders.poll();
                    if (spider != null)
                    {
                        Thread t = new Thread(spider);
                        t.start();
                        spidersRunning.add(spider);
                    }
                }
            }
        }, 1000 * 30, 1000 * 30);

    }

    public static void addSpider(Spider spider)
    {

        if (!SetSpiders.contains(spider) && !visited.contains(spider.getURL()))
        {
            spiders.add(spider);
        }
        SetSpiders.add(spider);
    }
    private static final Set<Spider> spidersRunning = Collections.synchronizedSet(new HashSet<Spider>());

    public static Spider createSpider(URL url)
    {
        SpiderDomain domain = new SpiderDomain(url);
        if (domainManager.containsKey(url))
        {
            domain = domainManager.get(url);
        }
        else
        {
            domainManager.put(url, domain);
        }

        Spider spider = new Spider(url, domain);
        for (SpiderEventListener listener : listeners)
        {
            spider.addSpiderListener(listener);
        }
        addSpider(spider);
        domain.addSpider(spider);
        return spider;

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
        for (SpiderEventListener listener : listeners)
        {
            spider.addSpiderListener(listener);
        }
        addSpider(spider);
        domain.addSpider(spider);
        return spider;

    }

    public static void addSpiderEventListener(SpiderEventListener listener)
    {
        listeners.add(listener);
    }

    public static synchronized void onPred(URI pred,Spider spider)
    {
        // carga las propiedades de un predicado
        try
        {
            SpiderSync _spider=new SpiderSync(pred.toURL(), null);
            spider.addSpiderListener(_spider);
            _spider.run();
            spider.fireVisit(pred);            
        }
        catch(Exception e)
        {
            spider.fireError(e);
        }

    }
    public static void addPredicate(TripleElement element)
    {
        predicates.add(element);        
    }
}
