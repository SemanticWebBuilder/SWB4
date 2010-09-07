/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class Root extends Site implements NodeEvent
{

    public Root()
    {
        this(null);
    }
    public Root(String id)
    {
        super();
        try {
            if (id == null) {
                for (WebSiteInfo site : OfficeApplication.getOfficeApplicationProxy().getSites()) {
                    Site siteNode = new Site(site);
                    this.add(siteNode);
                    siteNode.addAddNodeListener(this);
                    for (NodeEvent event : events) {
                        event.addNode(siteNode);
                    }
                    siteNode.add(new DefaultMutableTreeNode());
                }
            }
            else {
                for (WebSiteInfo site : OfficeApplication.getOfficeApplicationProxy().getSites()) {
                    if (site.id.equals(id)) {
                        Site siteNode = new Site(site);
                        this.add(siteNode);
                        siteNode.addAddNodeListener(this);
                        for (NodeEvent event : events) {
                            event.addNode(siteNode);
                        }
                        siteNode.add(new DefaultMutableTreeNode());
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRoot()
    {
        return true;
    }

    public void selectNode(DefaultMutableTreeNode node)
    {
        for (NodeEvent event : events) {
            event.selectNode(node);
        }
    }

    public void addNode(DefaultMutableTreeNode node)
    {
        for (NodeEvent event : events) {
            event.addNode(node);
        }
    }
}
