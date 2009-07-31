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
    public PageInfo()
    {
        active=false;
    }
    public String id;
    public String title;
    public String description;
    public SiteInfo site;
    public String url;
    public boolean active;
}
