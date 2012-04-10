/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
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

    
    public static final Set<URL> visited = Collections.synchronizedSet(new HashSet<URL>());
    private static final Timer timer = new Timer("Spiders");

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
    private static final ConcurrentLinkedQueue<Spider> spiders = new ConcurrentLinkedQueue<Spider>();
    private static final HashSet<Spider> SetSpiders = new HashSet<Spider>();

    public static void addSpider(Spider spider)
    {
        if (!SetSpiders.contains(spider) && !visited.contains(spider.getURL()))
        {
            spiders.add(spider);
        }
        SetSpiders.add(spider);
    }
    private static final Set<Spider> spidersRunning = Collections.synchronizedSet(new HashSet<Spider>());

    public static void addURL(URL url)
    {
        addSpider(new Spider(url));      
    }
}
