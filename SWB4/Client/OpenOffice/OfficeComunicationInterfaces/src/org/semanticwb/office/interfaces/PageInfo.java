/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class PageInfo {
    public boolean active;
    public PageInfo()
    {
        
    }
    public PageInfo(String id)
    {
        this.id=id;
        active=false;
    }
    public String id;
    public String title;
    public String description;
    public SiteInfo site;
    public String url;
    
}
