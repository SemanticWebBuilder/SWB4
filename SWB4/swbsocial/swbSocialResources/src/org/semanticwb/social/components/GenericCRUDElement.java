/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components;

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
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
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

/*
 * Clase cuya funcionalidad sera la de poder transferir el control mediante el manejo dinamico
 * la propiedad src de un iframe de un zul a un determinado lugar que funcione para la creación,
 * edición y posiblemente la eliminación de elementos del árbol de navegación de la administración
 * de la herramienta swbsocial
 */
public class GenericCRUDElement extends GenericForwardComposer <Component>
{
    private static Logger log = SWBUtils.getLogger(GenericCRUDElement.class);
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
    WebPage optionWepPage;
    SemanticObject semObject;


    /*
     * Metodo implementación de la clase padre, este metodo se ejecuta una vez cargado
     * el componente.
     */
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
           optionWepPage=(WebPage)requestScope.get("optionWepPage");
           if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
           {
                win_dobleClick.setTitle("Creación de:"+optionWepPage.getTitle());
                Tab tab=new Tab(optionWepPage.getDisplayTitle(user.getLanguage()));
                tab.setParent(tabs_dobleClick);
                if(optionWepPage instanceof TreeNodePage)
                {
                    final TreeNodePage treeNodePage=(TreeNodePage) optionWepPage;
                    if(treeNodePage.getWpImg()!=null)
                    {
                        String iconImgPath=SWBPortal.getWebWorkPath()+treeNodePage.getWorkPath()+"/"+treeNodePage.social_wpImg.getName()+"_"+treeNodePage.getId()+"_"+treeNodePage.getWpImg();
                        tab.setImage(iconImgPath);
                    }
                    System.out.println("Entra a Add de GenericCRUD,wsite:"+wsite+",action:"+action+",objUri:"+objUri+",parentItem:"+parentItem);
             
                    //
                    requestScope.put("wsite", wsite);
                    requestScope.put("action", action);
                    requestScope.put("objUri", objUri);
                    this.getPage().setAttribute("wsite",wsite);
                    this.getPage().setAttribute("action",action);
                    this.getPage().setAttribute("objUri",objUri);
                    this.getPage().setAttribute("parentItem",parentItem);
                    //Desktop
                    this.getPage().getDesktop().setAttribute("wsite",wsite);
                    this.getPage().getDesktop().setAttribute("action",action);
                    this.getPage().getDesktop().setAttribute("parentItem",parentItem);
                    
                    System.out.println("Index de parentItem:"+parentItem.getIndex(parentItem));

                    iframe_dobleClick.getPage().getDesktop().getSession().setAttribute("parentItem", parentItem);
                    iframe_dobleClick.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&itemUri="+parentItem.getData().getUri());
                    
                }
           }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT))
           {
               SemanticObject semObj=SemanticObject.getSemanticObject(objUri);
               if(semObj.getProperty(Descriptiveable.swb_title)!=null)
               {
                    win_dobleClick.setTitle(semObj.getSemanticClass().getClassCodeName()+":"+semObj.getProperty(Descriptiveable.swb_title));
               }
               if(wsiteAdm!=null && parentItem!=null)
               {
                   buildTabs();
               }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }

    /*
     * Metodo que construye los tabs de un cierto elemento del árbol, esto sucede cuando un usuario
     * da doble click en el elemento.
     */
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
                        iframe_dobleClick.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&objUri="+objUri);
                        isFirstNode=false;
                    }
                    tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            iframe_dobleClick.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&objUri="+objUri);
                        }
                     });
                 }
             }
         }
     }
   
}
