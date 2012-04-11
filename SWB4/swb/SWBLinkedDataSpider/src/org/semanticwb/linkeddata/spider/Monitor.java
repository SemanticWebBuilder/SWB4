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
    
    public Monitor(SpiderDomain domain)
    {
        this.domain = domain;
        
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
        int max = 15;
        int dif = max - domain.spidersRunning.size();
        for (int i = 0; i < dif; i++)
        {
            Spider spider = domain.spiders.poll();
            if (spider != null)
            {
                Thread t = new Thread(spider);
                t.start();
                domain.spidersRunning.add(spider);
            }
        }       
    }
}
