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
import java.net.URLDecoder;
import java.net.URLEncoder;

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
public class GenericRedirect extends GenericForwardComposer <Component>
{
    private static Logger log = SWBUtils.getLogger(GenericRedirect.class);
    private static final long serialVersionUID = 1L;
    private WebSite wsiteAdm;
    private WebSite wsite=null;
    private User user=null;
    private String objUri=null;
    ElementTreeNode treeItem;
    ElementTreeNode item;
    Window win_genCRUD;
    Tabs tabs_genCRUD;
    Iframe iframe_genCRUD;
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
           //item=(ElementTreeNode)requestScope.get("item");
           action=(String)requestScope.get("action");
           if(action==null)
           {
               action = "edit";
           }
           optionWepPage=(WebPage)requestScope.get("optionWepPage");
           TreeNodePage treeNodePage=null;
           if(optionWepPage!=null)
           {
                win_genCRUD.setTitle("Creación de:"+optionWepPage.getTitle());
                Tab tab=new Tab(optionWepPage.getDisplayTitle(user.getLanguage()));
                tab.setParent(tabs_genCRUD);
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
                        iframe_genCRUD.setSrc(optionWepPage.getUrl(user.getLanguage())+"?wsite="+wsite!=null?wsite.getId():null+"&action="+action+"&objUri="+objUri);
                    }
                 });
           }
           
           if(action.equals(SWBSocialResourceUtils.ACTION_ADD) && treeNodePage!=null)
           {
               iframe_genCRUD.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&itemUri="+URLEncoder.encode(treeItem.getData().getUri()));
           }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT))
           {
               buildEditTab();
           }
           else if(action.equals(SWBSocialResourceUtils.ACTION_DOUBLECLICK))
           {
               SemanticObject semObj=SemanticObject.getSemanticObject(URLDecoder.decode(objUri));
               if(semObj.getProperty(Descriptiveable.swb_title)!=null)
               {
                    win_genCRUD.setTitle(semObj.getSemanticClass().getClassCodeName()+":"+semObj.getProperty(Descriptiveable.swb_title));
               }
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

    private void buildEditTab()
    {
        String wsiteId=null;
        if(wsite!=null)
        {
            wsiteId=wsite.getId();
        }
       
        //Manejo dinamico de la propiedad "src" del iframe.
        iframe_genCRUD.setScrolling("false");iframe_genCRUD.setScrolling("no");
        String itemUri=null;
        if(treeItem!=null)
        {
            itemUri=URLEncoder.encode(treeItem.getData().getUri());
        }
        iframe_genCRUD.setSrc(optionWepPage.getUrl(user.getLanguage())+"?wsite="+wsiteId+"&action="+action+"&objUri="+objUri+"&itemUri="+itemUri);
    }

    /*
     * Metodo que construye los tabs de un cierto elemento del árbol, esto sucede cuando un usuario
     * da doble click en el elemento.
     */
     private void buildTabs()
     {
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
                    tab.setParent(tabs_genCRUD);
                    if(treeNodePage.getWpImg()!=null)
                    {
                        String iconImgPath=SWBPortal.getWebWorkPath()+treeNodePage.getWorkPath()+"/"+treeNodePage.social_wpImg.getName()+"_"+treeNodePage.getId()+"_"+treeNodePage.getWpImg();
                        tab.setImage(iconImgPath);
                    }
                    if(isFirstNode) //Se ejecuta solo con el primer webpage del topico padre, en este caso por lo regular sería el topico de "Configurar".
                    {
                        iframe_genCRUD.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                        iframe_genCRUD.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&objUri="+objUri);
                        isFirstNode=false;
                    }
                    tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            iframe_genCRUD.setSrc("/work/models/swbsocial/admin/zul/clearCompose.zul");
                            iframe_genCRUD.setSrc(treeNodePage.getUrl(user.getLanguage())+"?wsite="+wsite.getId()+"&action="+action+"&objUri="+objUri);
                        }
                     });
                 }
             }
         }
     }
   
}
