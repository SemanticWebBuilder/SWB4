/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.components;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.TreeNodePage;
import org.semanticwb.social.admin.tree.ElementTreeNode;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Tab;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

/**
 *
 * @author jorge.jimenez
 */
public class DobleClick extends GenericForwardComposer <Component>
{
    private static Logger log = SWBUtils.getLogger(DobleClick.class);
    private static final long serialVersionUID = 1L;
    private WebSite wsiteAdm;
    private WebSite wsite=null;
    private User user=null;
    private String objUri=null;
    ElementTreeNode parentItem;
    ElementTreeNode item;
    Window win_dobleClick;
    Tabs tabs_dobleClick;
    Iframe iframe_dobleClick;
    String action;
    SemanticObject semObject;



     @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        try
        {
           wsiteAdm=(WebSite)requestScope.get("wsiteAdm");
           wsite=(WebSite)requestScope.get("wsite");
           user=(User)requestScope.get("user");
           objUri=(String)requestScope.get("objUri");
           parentItem=(ElementTreeNode)requestScope.get("parentItem");
           item=(ElementTreeNode)requestScope.get("item");
           action=(String)requestScope.get("action");

           SemanticObject semObj=SemanticObject.getSemanticObject(objUri);

           if(semObj.getProperty(Descriptiveable.swb_title)!=null)
           {
                win_dobleClick.setTitle(semObj.getSemanticClass().getClassCodeName()+":"+semObj.getProperty(Descriptiveable.swb_title));
           }
           if(wsiteAdm!=null && parentItem!=null)
           {
               buildTabs();
           }
        }catch(Exception e)
        {
            log.error(e);
        }
    }


     private void buildTabs()
     {
         WebPage admItemPage=wsiteAdm.getWebPage(parentItem.getData().getUri());
         if(admItemPage!=null)
         {
             boolean isFirstNode=true;
             Iterator<WebPage> itChildPages=admItemPage.listVisibleChilds(user.getLanguage());
             while(itChildPages.hasNext())
             {
                 WebPage wPageChild=itChildPages.next();
                 if(wPageChild instanceof TreeNodePage)
                 {
                    final TreeNodePage treeNodePage=(TreeNodePage) wPageChild;
                    Tab tab=new Tab(wPageChild.getDisplayTitle(user.getLanguage()));
                    tab.setParent(tabs_dobleClick);
                    if(treeNodePage.getWpImg()!=null)
                    {
                        String iconImgPath=SWBPortal.getWebWorkPath()+treeNodePage.getWorkPath()+"/"+treeNodePage.social_wpImg.getName()+"_"+treeNodePage.getId()+"_"+treeNodePage.getWpImg();
                        tab.setImage(iconImgPath);
                    }
                    if(isFirstNode) //Se ejecuta solo con el primer webpage del topico padre, en este caso por lo regular sería el topico de "Configurar".
                    {
                        iframe_dobleClick.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+treeNodePage.getAction()+"&objUri="+objUri);
                        isFirstNode=false;
                    }
                    tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            iframe_dobleClick.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+treeNodePage.getAction()+"&objUri="+objUri);
                        }
                     });
                 }
             }
         }
     }
   
}
