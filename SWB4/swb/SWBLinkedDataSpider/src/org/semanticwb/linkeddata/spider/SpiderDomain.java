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
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderDomain
{

    private static final ConcurrentLinkedQueue<Spider> spiders = new ConcurrentLinkedQueue<Spider>();
    private static final HashSet<Spider> SetSpiders = new HashSet<Spider>();
    private static final Set<Spider> spidersRunning = Collections.synchronizedSet(new HashSet<Spider>());
    private static final Timer timer = new Timer("Spiders");
    private String host;
    private static Logger log = SWBUtils.getLogger(SpiderDomain.class);

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

    public SpiderDomain(URL url)
    {
        this.host = url.getHost();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof SpiderDomain)
        {
            SpiderDomain domain = (SpiderDomain) obj;
            return domain.host.equals(host);
        }
        else
        {
            return false;
        }

    }

    @Override
    public int hashCode()
    {
        return host.hashCode();
    }

    @Override
    public String toString()
    {
        return host;
    }

    public void fireError(final Throwable e, final URL url)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onError(url, e);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);

                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireEventnewTriple(final URI suj, final URI pred, final URI obj, final Spider spider, final String lang)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        //listener.onTriple(suj, pred, obj,spider);
                        listener.onTriple(suj, pred, obj, spider, lang);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireVisit(final URI suj)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onNewSubject(suj);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void onNewSubject(final URI suj)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onNewSubject(suj);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);

                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireOnEnd(final URL url)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onEnd(url);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);

                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }

    }

    public void fireOnStart(final URL url)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onStart(url);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireEventnewTriple(final URI suj, final URI pred, final String obj, final Spider spider, final String lang)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        //listener.onTriple(suj, pred, obj,spider);
                        listener.onTriple(suj, pred, obj, spider, lang);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireEventNtFormat(final String row, final Spider spider, final URL url)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        //listener.onTriple(suj, pred, obj,spider);
                        listener.onNTFormat(row);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireError(final int code, final URL url)
    {
        for (final SpiderEventListener listener : SpiderManager.getListeners())
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onError(url, code);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    public void addSpider(Spider spider)
    {
        SetSpiders.add(spider);
        spiders.add(spider);
        synchronized (spidersRunning)
        {
            if (spidersRunning.isEmpty())
            {
                Thread t = new Thread(spider);
                t.start();
                spidersRunning.add(spider);
            }
        }
    }

    public void onDone(Spider spider)
    {
        synchronized (SetSpiders)
        {
            SetSpiders.remove(spider);
        }
        synchronized (spidersRunning)
        {
            spidersRunning.remove(spider);

        }
    }
}
