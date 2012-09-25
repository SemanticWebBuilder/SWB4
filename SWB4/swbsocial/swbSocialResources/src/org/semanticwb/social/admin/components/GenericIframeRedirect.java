/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.components;

import org.semanticwb.SWBPortal;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.zkoss.zk.ui.Component;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.TreeNodePage;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabs;

/**
 *
 * @author jorge.jimenez
 */
public class GenericIframeRedirect extends GenericForwardComposer
{
    private WebSite wsite=null;
    String action=null;
    String objUri=null;
    WebPage optionWepPage;
    SemanticObject semObject;
    User user;
    SocialNetwork socialNet;
    Iframe genIframeRed;
    Window win_genIframeRed;
    Tabs tabs_genIframeRed;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        wsite=(WebSite)requestScope.get("wsite");
        action=(String)requestScope.get("action");
        objUri=(String)requestScope.get("objUri");
        user=(User)requestScope.get("user");
        optionWepPage=(WebPage)requestScope.get("optionWepPage");

        win_genIframeRed.setTitle(optionWepPage.getDisplayDescription(user.getLanguage()));

        Tab tab=new Tab(optionWepPage.getDisplayTitle(user.getLanguage()));
        if(optionWepPage instanceof TreeNodePage)
        {
            TreeNodePage treeNodePage=(TreeNodePage) optionWepPage;
            if(treeNodePage.getWpImg()!=null)
            {
                String iconImgPath=SWBPortal.getWebWorkPath()+treeNodePage.getWorkPath()+"/"+treeNodePage.social_wpImg.getName()+"_"+treeNodePage.getId()+"_"+treeNodePage.getWpImg();
                tab.setImage(iconImgPath);
            }
        }
        String wsiteId=null;
        if(wsite!=null)
        {
            wsiteId=wsite.getId();
        }
        tab.setParent(tabs_genIframeRed);
        //Manejo dinamico de la propiedad "src" del iframe.
        genIframeRed.setScrolling("false");genIframeRed.setScrolling("no");
        genIframeRed.setSrc(optionWepPage.getUrl(user.getLanguage())+"?wsite="+wsiteId+"&action=edit&objUri="+objUri);
        //Para cuando le den click de nuevo, se refresque el contenido del tab
        tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                genIframeRed.setSrc(optionWepPage.getUrl(user.getLanguage())+"?wsite="+wsite!=null?wsite.getId():null+"&action=edit&objUri="+objUri);
            }
         });
    }


}
