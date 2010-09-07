/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import java.util.HashSet;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class Site extends DefaultMutableTreeNode implements ToolTipTreeNode, NodeEvent
{

    protected HashSet<NodeEvent> events = new HashSet<NodeEvent>();
    private WebSiteInfo webSiteInfo;
    private JLabel component = new JLabel();

    @Override
    public boolean isRoot()
    {
        return false;
    }
    private String id;
    private String title;

    public void addAddNodeListener(NodeEvent event)
    {
        events.add(event);
    }

    public Site()
    {
        this.id = "";
        this.title = "";
        component.setText(title);
        component.setOpaque(true);
    }

    public void loadHome()
    {
        try {
            this.removeAllChildren();
            WebPageInfo home = OfficeApplication.getOfficeApplicationProxy().getHomePage(webSiteInfo);
            WebPage homeNode = new WebPage(home);
            homeNode.addAddNodeListener(this);
            this.add(homeNode);
            for (NodeEvent event : events) {
                event.addNode(homeNode);
            }
            if (home.childs > 0) {
                homeNode.add(new DefaultMutableTreeNode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Site(WebSiteInfo webSiteInfo)
    {
        this.webSiteInfo = webSiteInfo;
        this.id = webSiteInfo.id;
        this.title = webSiteInfo.title;
        component.setText(title);
        component.setOpaque(true);
    }

    public WebSiteInfo getWebSiteInfo()
    {
        return webSiteInfo;
    }

    public String getToolTipText()
    {
        return this.title;
    }

    @Override
    public String toString()
    {
        return title;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof Site) {
            equals = ((Site) obj).id.equals(this.id);
        }
        return equals;
    }

    public JLabel getComponent()
    {
        return component;
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
