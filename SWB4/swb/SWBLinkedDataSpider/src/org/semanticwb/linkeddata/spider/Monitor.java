/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.util.HashSet;
import java.util.TimerTask;

/**
 *
 * @author victor.lorenzana
 */
public class Monitor extends TimerTask
{

    private final SpiderDomain domain;
    private int MAX = 15;
    public Monitor(SpiderDomain domain,int max)
    {
        this.domain = domain;
        this.MAX=max;
        
    }
    public int getMax()
    {
        return MAX;
    }

    @Override
    public void run()
    {
        HashSet<Spider> _spiderstoDelete = new HashSet<Spider>();
        for (Spider spider : domain.spidersRunning)
        {
            if (!spider.isRunning())
            {
                _spiderstoDelete.add(spider);
            }
        }
        for (Spider spider : _spiderstoDelete)
        {
            domain.spidersRunning.remove(spider);
        }
        
        int dif = MAX - domain.spidersRunning.size();
        for (int i = 0; i < dif; i++)
        {
            Spider spider = domain.spiders.poll();
            if (spider != null)
            {
                ThreadSpider t = new ThreadSpider(spider);
                t.start();
                domain.spidersRunning.add(spider);
            }
        }       
    }
}
