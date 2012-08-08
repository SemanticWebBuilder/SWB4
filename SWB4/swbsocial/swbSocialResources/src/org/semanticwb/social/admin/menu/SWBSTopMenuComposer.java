/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.menu;

import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.ZulWebPage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menubar;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.impl.XulElement;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSTopMenuComposer extends GenericForwardComposer <Component>{

    public Menubar mainMenu;
    public List ls = new java.util.ArrayList();
    WebSite wsite=WebSite.ClassMgr.getWebSite("swbsocial");
    @Wire
    private Include content;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
            super.doAfterCompose(comp);
            SWBParamRequest paramRequest=(SWBParamRequest)requestScope.get("paramRequest");
            if(paramRequest!=null)
            {
                wsite=paramRequest.getWebPage().getWebSite();
                if(wsite!=null)
                {
                    WebPage menusWP=wsite.getWebPage("menus");
                    if(menusWP!=null)
                    {
                        makeMenu(menusWP, mainMenu);
                    }
                }
        }
    }

    private void makeMenu(WebPage webpage, XulElement parent)
    {
        Iterator <WebPage> itChilds=webpage.listVisibleChilds("es");
        while(itChilds.hasNext())
        {
            WebPage child=itChilds.next();
            if(child instanceof ZulWebPage)
            {
                ZulWebPage zulPage=(ZulWebPage)child;
                if(zulPage.listVisibleChilds("es").hasNext()) //Menu
                {
                    Menu menu = new Menu();
                    menu.setParent(parent);
                    menu.setLabel(zulPage.getDisplayName());
                    menu.setId(zulPage.getId());
                    if(zulPage.getWpImg()!=null)
                    {
                        menu.setImage(SWBPortal.getWebWorkPath()+child.getWorkPath()+"/"+ZulWebPage.social_wpImg.getName()+"_"+zulPage.getId()+"_"+zulPage.getWpImg());
                    }
                    if(zulPage.getWphOverImg()!=null)
                    {
                        menu.setHoverImage(SWBPortal.getWebWorkPath()+child.getWorkPath()+"/"+ZulWebPage.social_wphOverImg.getName()+"_"+zulPage.getId()+"_"+zulPage.getWphOverImg());
                    }
                    Menupopup menuPopUp = new Menupopup();
                    menuPopUp.setParent(menu);
                    makeMenu(child, menuPopUp);
                }else{  //Menuitem
                    Menuitem menuItem = new Menuitem();
                    menuItem.setParent(parent);
                    menuItem.setId(zulPage.getId());
                    menuItem.setLabel(zulPage.getDisplayName());
                    if(zulPage.getWpImg()!=null)
                    {
                        menuItem.setImage(SWBPortal.getWebWorkPath()+child.getWorkPath()+"/"+ZulWebPage.social_wpImg.getName()+"_"+zulPage.getId()+"_"+zulPage.getWpImg());
                    }
                    if(zulPage.getWphOverImg()!=null)
                    {
                        menuItem.setHoverImage(SWBPortal.getWebWorkPath()+child.getWorkPath()+"/"+ZulWebPage.social_wphOverImg.getName()+"_"+zulPage.getId()+"_"+zulPage.getWphOverImg());
                    }
                    menuItem.addEventListener("onClick", new menuEventListener());
                    menuItem.setAttribute("zulPageID", zulPage.getId());
                }
            }
        }

    }

    class menuEventListener implements org.zkoss.zk.ui.event.EventListener {
        public void onEvent(org.zkoss.zk.ui.event.Event event) {
            try {
                    String zulPageID=(String)event.getTarget().getAttribute("zulPageID");
                    ZulWebPage zulWPage=(ZulWebPage)wsite.getWebPage(zulPageID);
                    if(zulWPage.getZulResourcePath()!=null)
                    {
                        content.setSrc(zulWPage.getZulResourcePath());
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
