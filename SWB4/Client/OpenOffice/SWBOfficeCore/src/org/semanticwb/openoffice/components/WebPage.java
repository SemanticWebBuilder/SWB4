/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import java.util.HashSet;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class WebPage extends DefaultMutableTreeNode implements ToolTipTreeNode, NodeEvent
{

    private HashSet<NodeEvent> events = new HashSet<NodeEvent>();
    private WebPageInfo webPageInfo;
    private JLabel component = new JLabel();
    private String id;
    private String title;
    private String description;
    private String webSite;
    private String url;
    private boolean active;

    public void setEnabled(boolean enabled)
    {
        this.component.setEnabled(enabled);
        this.component.updateUI();
    }

    public void addAddNodeListener(NodeEvent event)
    {
        events.add(event);
    }

    public WebPageInfo getWebPageInfo()
    {
        return webPageInfo;
    }

    public WebPage(WebPageInfo webPageInfo)
    {
        this.webPageInfo = webPageInfo;
        this.id = webPageInfo.id;
        this.title = webPageInfo.title;
        this.webSite = webPageInfo.siteID;
        this.url = webPageInfo.url;
        this.active = webPageInfo.active;
        component.setText(title);
        component.setToolTipText(description);
        component.setOpaque(true);
    }

    public void loadChilds()
    {
        if (getChildCount() == 1 && !(getChildAt(0) instanceof WebPage)) {
            reLoadChilds();
        }
    }

    public void reLoadChilds()
    {
        try {
            this.removeAllChildren();
            for (WebPageInfo page : OfficeApplication.getOfficeApplicationProxy().getPages(webPageInfo)) {
                WebPage child = new WebPage(page);
                child.addAddNodeListener(this);
                this.add(child);
                for (NodeEvent event : events) {
                    event.addNode(child);
                }
                if(page.childs>0)
                {
                    child.add(new DefaultMutableTreeNode());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isActive()
    {
        return active;
    }

    public String getURL()
    {
        return url;
    }

    public String getSite()
    {
        return this.webSite;
    }

    public String getToolTipText()
    {
        return description;
    }

    @Override
    public String toString()
    {
        this.hashCode();
        return title;
    }

    @Override
    public int hashCode()
    {
        return this.title.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof WebPage) {
            equals = ((WebPage) obj).title.equals(this.title);
        }
        return equals;
    }

    public String getID()
    {
        return id;
    }

    public String getDescription()
    {
        return this.description;
    }

    public JLabel getComponent()
    {
        return component;
    }

    @Override
    public boolean isRoot()
    {
        return false;
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
