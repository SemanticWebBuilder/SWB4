/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
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
import java.net.URLEncoder;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

/*
 * Clase cuya funcionalidad sera la de poder transferir el control mediante el manejo dinamico
 * de la propiedad src de un iframe de un zul a un determinado lugar que funcione para la creación,
 * edición y posiblemente la eliminación de elementos del árbol de navegación de la administración
 * de la herramienta swbsocial
 */
public final class GenericRedirect extends GenericForwardComposer <Component>
{
    private static Logger log = SWBUtils.getLogger(GenericRedirect.class);
    private static final long serialVersionUID = 1L;
    private WebSite wsiteAdm;
    private WebSite wsite=null;
    private User user=null;
    private String objUri=null;
    ElementTreeNode treeItem;
    ElementTreeNode item;
    Window win_genRedirect;
    Tabs tabs_genRedirect;
    Iframe iframe_genRedirect;
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
           treeItem=(ElementTreeNode)requestScope.get("treeItem");
           action=(String)requestScope.get("action");
           iframe_genRedirect.setSrc(null);
           if(action==null)
           {
               action = SWBSocialResourceUtils.ACTION_EDIT;
           }
           TreeNodePage treeNodePage=null;
           if(requestScope.get("optionWepPage")!=null)
           {
                optionWepPage=(WebPage)requestScope.get("optionWepPage");
                //win_genRedirect.setTitle("Creación de:"+optionWepPage.getTitle());
                Tab tab=new Tab(optionWepPage.getDisplayTitle(user.getLanguage()));
                tab.setParent(tabs_genRedirect);
                if(optionWepPage instanceof TreeNodePage)
                {
                    treeNodePage=(TreeNodePage) optionWepPage;
                    if(treeNodePage.getWpImg()!=null)
                    {
                        String iconImgPath=SWBPortal.getWebWorkPath()+treeNodePage.getWorkPath()+"/"+treeNodePage.social_wpImg.getName()+"_"+treeNodePage.getId()+"_"+treeNodePage.getWpImg();
                        tab.setImage(iconImgPath);
                    }
                }
                //Para cuando le den click de nuevo, se refresque el contenido del tab
                tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        iframe_genRedirect.setSrc(optionWepPage.getUrl(user.getLanguage())+"?wsite="+wsite!=null?wsite.getId():null+"&action="+action+"&objUri="+objUri);
                    }
                 });
           }
           if(action.equals(SWBSocialResourceUtils.ACTION_ADD) && treeNodePage!=null)
           {
               iframe_genRedirect.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&itemUri="+URLEncoder.encode(treeItem.getData().getUri()));
           }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT))
           {
               buildEditTab();
           }else if(action.equals(SWBSocialResourceUtils.ACTION_REMOVE) && treeItem!=null && treeNodePage!=null)
           {
               iframe_genRedirect.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&itemUri="+URLEncoder.encode(treeItem.getData().getUri())+"&objUri="+objUri);
           }
           else if(action.equals(SWBSocialResourceUtils.ACTION_DOUBLECLICK))
           {
               /*
               SemanticObject semObj=SemanticObject.getSemanticObject(URLDecoder.decode(objUri));
               if(semObj.getProperty(Descriptiveable.swb_title)!=null)
               {
                    win_genRedirect.setTitle(semObj.getSemanticClass().getClassCodeName()+":"+semObj.getProperty(Descriptiveable.swb_title));
               }*/
               if(wsiteAdm!=null && treeItem!=null)
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
     * Metodo que crear el tab de edición para un cierto elemento del árbol
     */
    private void buildEditTab()
    {
        String wsiteId=null;
        if(wsite!=null)
        {
            wsiteId=wsite.getId();
        }
       
        //Manejo dinamico de la propiedad "src" del iframe.
        iframe_genRedirect.setScrolling("false");iframe_genRedirect.setScrolling("no");
        String itemUri=null;
        if(treeItem!=null)
        {
            itemUri=URLEncoder.encode(treeItem.getData().getUri());
        }
        iframe_genRedirect.setSrc(optionWepPage.getUrl(user.getLanguage())+"?wsite="+wsiteId+"&action="+action+"&objUri="+objUri+"&itemUri="+itemUri);
    }

    /*
     * Metodo que construye los tabs de un cierto elemento del árbol, esto sucede cuando un usuario
     * da doble click en el elemento.
     */
     private void buildTabs()
     {
         iframe_genRedirect.setScrolling("false");iframe_genRedirect.setScrolling("no");
         WebPage admItemPage=wsiteAdm.getWebPage(treeItem.getData().getCategoryID());
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
                    tab.setParent(tabs_genRedirect);
                    if(treeNodePage.getWpImg()!=null)
                    {
                        String iconImgPath=SWBPortal.getWebWorkPath()+treeNodePage.getWorkPath()+"/"+treeNodePage.social_wpImg.getName()+"_"+treeNodePage.getId()+"_"+treeNodePage.getWpImg();
                        tab.setImage(iconImgPath);
                    }
                    if(isFirstNode) //Se ejecuta solo con el primer webpage del topico padre, en este caso por lo regular sería el topico de "Configurar".
                    {
                        iframe_genRedirect.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&objUri="+objUri+"&itemUri="+URLEncoder.encode(treeItem.getData().getUri()));
                        isFirstNode=false;
                    }
                    tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            iframe_genRedirect.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&objUri="+objUri+"&itemUri="+URLEncoder.encode(treeItem.getData().getUri()));
                        }
                     });
                 }
             }
         }
     }
   
}
