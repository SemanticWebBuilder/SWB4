/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.menu.data;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.ZulWebPage;
import org.semanticwb.social.admin.menu.menuEvents;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *
 * @author jorge.jimenez
 */
public class MenuModel {
    
    public MenuModel()
    {
        
    }
    
    public void writeMenu(Menubar mainMenu, Include include)
    {
        System.out.println("Entra a writeMenu-1");


        WebSite wsite=WebSite.ClassMgr.getWebSite("swbsocial");
        if(wsite!=null)
        {
            WebPage menusWP=wsite.getWebPage("menus");
            if(menusWP!=null)
            {
                Iterator <WebPage> itMenus=menusWP.listVisibleChilds("es");
                while(itMenus.hasNext())
                {
                    WebPage menuwp=itMenus.next();
                    if(menuwp instanceof ZulWebPage)
                    {
                        ZulWebPage zulmenuWP=(ZulWebPage)menuwp;
                        Menu menu = new Menu();
                        menu.setParent(mainMenu);
                        menu.setLabel(zulmenuWP.getDisplayName());
                        //Imagenes
                        String baseImgPath=SWBPortal.getWebWorkPath()+zulmenuWP.getWorkPath()+"/";
                        if(zulmenuWP.getWpImg()!=null)
                        {
                            menu.setImage(baseImgPath+ZulWebPage.social_wpImg.getName()+"_"+zulmenuWP.getId()+"_"+zulmenuWP.getWpImg());
                        }
                        if(zulmenuWP.getWphOverImg()!=null)
                        {
                            menu.setHoverImage(baseImgPath+ZulWebPage.social_wphOverImg.getName()+"_"+zulmenuWP.getId()+"_"+zulmenuWP.getWphOverImg());
                        }
                        menu.setId(zulmenuWP.getId());
                        //pubMenu.addEventListener("onClick", new menuEvents(include));
                        if(zulmenuWP.listChilds().hasNext())
                        {
                            Menupopup childPopUp = new Menupopup();
                            childPopUp.setParent(menu);
                            Iterator <WebPage> itChids=zulmenuWP.listVisibleChilds("es");
                            while(itChids.hasNext())
                            {
                                WebPage childwp=itChids.next();
                                if(childwp instanceof ZulWebPage)
                                {
                                    ZulWebPage zulPage=(ZulWebPage)childwp;
                                    Menuitem childItem = new Menuitem();
                                    childItem.setLabel(zulPage.getDisplayName());
                                    //Imagenes
                                    baseImgPath=SWBPortal.getWebWorkPath()+zulPage.getWorkPath()+"/";
                                    if(zulPage.getWpImg()!=null)
                                    {
                                        childItem.setImage(baseImgPath+zulPage.social_wpImg.getName()+"_"+zulPage.getId()+"_"+zulPage.getWpImg());
                                    }
                                    if(zulPage.getWphOverImg()!=null)
                                    {
                                        childItem.setHoverImage(baseImgPath+zulPage.social_wphOverImg.getName()+"_"+zulPage.getId()+"_"+zulPage.getWphOverImg());
                                    }
                                    childItem.setParent(childPopUp);
                                    childItem.setId(zulPage.getId());
                                    childItem.setAttribute("URL", "http://www.google.com");
                                    childItem.addEventListener("onClick", new menuEvents(include));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
