/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URL;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderDomain
{

    private String host;

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

    public void addSpider(Spider spider)
    {
        
    }
}
