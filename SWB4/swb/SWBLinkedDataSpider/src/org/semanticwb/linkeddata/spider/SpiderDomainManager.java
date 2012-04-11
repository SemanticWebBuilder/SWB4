/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URL;
import java.util.HashMap;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderDomainManager extends HashMap<String, SpiderDomain>
{

    public boolean containsKey(URL url)
    {
        String host=url.getHost();
        return this.containsKey(host);
    }
    public SpiderDomain get(URL url)
    {
        String host=url.getHost();
        return super.get(host);
    }
    public SpiderDomain put(URL url,SpiderDomain domain)
    {
        String host=url.getHost();
        return super.put(host, domain);
    }
    public synchronized String getNames()
    {
        StringBuilder sb=new StringBuilder();
        for(String host : keySet())
        {
            sb.append(host);
            sb.append(",");
        }
        return sb.toString();
    }
}
