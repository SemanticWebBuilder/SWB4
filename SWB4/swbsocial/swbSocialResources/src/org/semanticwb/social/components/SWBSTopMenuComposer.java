/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components;

import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.ZulWebPage;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.impl.XulElement;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase Controladora del menú superior de la herramienta swbsocial
 */
public class SWBSTopMenuComposer extends GenericForwardComposer <Component>{

    private static Logger log = SWBUtils.getLogger(SWBSTopMenuComposer.class);
    public Menubar mainMenu;
    public List ls = new java.util.ArrayList();
    private WebSite wsite=null;
    private SWBParamRequest paramRequest;
    private Include content;
    private User user;

    /*
    * Metodo implementación de la clase padre, este metodo se ejecuta una vez cargado
    * el componente.
    */
    @Override
    public void doAfterCompose(Component comp) throws Exception {
            super.doAfterCompose(comp);
            try
            {
                paramRequest=(SWBParamRequest)requestScope.get("paramRequest");
                if(paramRequest!=null)
                {
                    wsite=paramRequest.getWebPage().getWebSite();
                    if(wsite!=null)
                    {
                        user=paramRequest.getUser();
                        WebPage menusWP=wsite.getWebPage("menus");
                        if(menusWP!=null)
                        {
                            makeMenu(menusWP, mainMenu);
                        }
                    }
                }
        }catch(Exception e)
        {
            log.error(e);
        }
    }

    /*
     * Metodo que genera el menú de la herramienta swbsocial
     * @param webpage página de la cual se leen todos los hijos y los cuales son representados como menus en la herramienta
     * @param parent elemento zul de tipo MenuBar del cual se van a colgar todos los hijos de webpage
     */
    private void makeMenu(WebPage webpage, XulElement parent)
    {
        try
        {
            Iterator <WebPage> itChilds=webpage.listVisibleChilds(user.getLanguage());
            while(itChilds.hasNext())
            {
                WebPage child=itChilds.next();
                if(child instanceof ZulWebPage)
                {
                    ZulWebPage zulPage=(ZulWebPage)child;
                    if(zulPage.listVisibleChilds(user.getLanguage()).hasNext()) //Menu
                    {
                        Menu menu = new Menu();
                        menu.setSclass("menuStyle");
                        menu.setHflex("true");
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
                        menuItem.setSclass("menuitemStyle");
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
                        //menuItem.setAttribute("optionWepPage", zulPage.getUrl());
                    }
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }


    /*
     * Clase que maneja el evento onClick de los elementos del menú de la herramienta
     * swbsocial
     */
    class menuEventListener implements org.zkoss.zk.ui.event.EventListener {
        public void onEvent(org.zkoss.zk.ui.event.Event event) {
            try {
                    String zulPageID=(String)event.getTarget().getAttribute("zulPageID");
                    //String optionWepPage=(String)event.getTarget().getAttribute("optionWepPage");
                    ZulWebPage zulWPage=(ZulWebPage)wsite.getWebPage(zulPageID);
                    if(zulWPage.getZulResourcePath()!=null)
                    {
                        //content.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                        content.setSrc(null);
                        content.setSrc(zulWPage.getZulResourcePath());
                        content.setDynamicProperty("optionWepPage", zulWPage);
                        content.setDynamicProperty("user", user);
                        content.setDynamicProperty("action", SWBSocialResourceUtils.ACTION_EDIT);
                        /*
                        WebSite wsite=(WebSite)SemanticObject.getSemanticObject(itemValue.getData().getUri()).getModel().getModelObject().createGenericInstance();
                        content.setDynamicProperty("wsite", wsite);
                         *
                         */
                    }
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }

}
