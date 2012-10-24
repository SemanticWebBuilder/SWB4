/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.social.Childrenable;
import org.semanticwb.social.SocialAdmin;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.TreeNodePage;
import org.semanticwb.social.components.tree.AdvancedTreeModel;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.components.tree.Element;
import org.zkoss.zk.ui.event.*;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

/*
 * Clase de utilerías para los recursos de swbsocial
 */
public class SWBSocialResourceUtils {

    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_DOUBLECLICK = "doubleClick";
    public static final String ACTION_REMOVE = "remove";
    public static final String ACTION2= "updateTree";
    /**
     * Holds a reference to a log utility.
     * <p>Mantiene una referencia a la utiler&iacute;a de generaci&oacute;n de bit&aacute;coras.</p>
     */
    private static Logger log = SWBUtils.getLogger(SWBSocialResourceUtils.class);
    /**
     * Holds a reference to an object of this class.
     * <p>Mantiene una referencia a un objeto de esta clase.</p>
     */
    static private SWBSocialResourceUtils instance;
    static ArrayList<String> aDoubles = new ArrayList();
    static HashMap<String, String> hmapChanges = new HashMap();

    /**
     * Creates a new object of this class.
     */
    private SWBSocialResourceUtils() {
        init();
    }

    /**
     * Retrieves a reference to the only one existing object of this class.
     * <p>Obtiene una referencia al &uacute;nico objeto existente de esta clase.</p>
     * @param applicationPath a string representing the path for this application
     * @return a reference to the only one existing object of this class
     */
    static public synchronized SWBSocialResourceUtils createInstance() {
        if (SWBSocialResourceUtils.instance == null) {
            init();
            SWBSocialResourceUtils.instance = new SWBSocialResourceUtils();
        }
        return SWBSocialResourceUtils.instance;
    }

    /*
     * Initializes the class variables needed to provide this object's services
     * <p>Inicializa las variables de clase necesarias para proveer los servicios de este objeto.</p>
     */
    /**
     * Inits the.
     */
    private static void init() {

    }

    public static class Components {

        public static void updateTreeNode(Map<String, Object> requestScope, SWBClass newSWBClass)
        {
            ElementTreeNode treeNode=(ElementTreeNode)requestScope.get("treeItem");
            if(treeNode!=null)
            {
                EventQueue<Event> eq = EventQueues.lookup("insertNodo2Tree", EventQueues.SESSION, true);
                TreeNodeRefresh treeNodeRefresh=new TreeNodeRefresh(treeNode, newSWBClass);
                eq.publish(new Event("onCreateNode", null, treeNodeRefresh));
            }
        }

        public static void updateTreeNode(Map<String, Object> requestScope, String title)
        {
            ElementTreeNode treeNode=(ElementTreeNode)requestScope.get("treeItem");
            if(treeNode!=null)
            {
                EventQueue<Event> eq = EventQueues.lookup("updateNodo2Tree", EventQueues.SESSION, true);
                TreeNodeRefresh treeNodeRefresh=new TreeNodeRefresh(treeNode,title);
                eq.publish(new Event("onUpdateNode", null, treeNodeRefresh));
            }
        }

        public static ElementTreeNode getComponentbyUri(HttpServletRequest request)
        {
            try
            {
                if(request.getParameter("itemUri")!=null)
                {
                    AdvancedTreeModel advTreeModel=(AdvancedTreeModel)request.getSession().getAttribute("elemenetTreeModel");
                    return advTreeModel.findNode(request.getParameter("itemUri"),request.getParameter("wsite"), advTreeModel.getRoot());
                }
            }catch(Exception e)
            {
                log.error(e);
            }
            return null;
        }

         /*
         * Metodo cuya funcionalidad es la de insertar un nodo al árbol de navegación y que se refleje en el mismo
         * @param parentItem item padre del que se desea insertar
         * @param swbClass SWBClass del nuevo elemento a insertar
         * @param wpage WebPage de la categoría donde se desea insertar un nuevo nodo
         */
        public static void insertTreeNode(ElementTreeNode parentItem, SWBClass swbClass) {
            if (swbClass instanceof Descriptiveable) {
                try
                {
                    String ImgAdminPathBase = "/work/models/";
                    //Tomo un sitio de tipo SocialAdmin (Se considera que solo debe haber uno) y este debe de tener
                    //imagenes en la ruta /work/models/[iddesitio]/admin/img/
                    SocialAdmin socialAdm=SocialAdmin.ClassMgr.listSocialAdmins().next();
                    ImgAdminPathBase+=socialAdm.getId()+"/admin/img/";
                    

                    String categoryID=null;
                    Descriptiveable descripTable = (Descriptiveable) swbClass;
                    DisplayObject displayObj = (DisplayObject) swbClass.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
                    if (parentItem.getData().getUri().indexOf("#") > -1) //El parentItem no es una categoría, entonces se asume que es childrenable el nuevo nodo
                    {
                        SemanticObject semObj = SemanticObject.getSemanticObject(parentItem.getData().getUri());
                        if (semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable)) {
                            //Asumimos entonces que swbClass es de tipo Childrenable, ya que el nodo donde se esta creando es de tipo Childrenable
                            Childrenable childrenable = (Childrenable) swbClass;
                            childrenable.setParentObj((Childrenable) semObj.getGenericInstance());
                            categoryID=parentItem.getData().getCategoryID();
                        }
                    }else{
                        categoryID=parentItem.getData().getUri();
                    }
                    String modelID=swbClass.getSemanticObject().getModel().getModelObject().getId();
                    parentItem.insert(new ElementTreeNode(new Element(descripTable.getTitle(), descripTable.getURI(), null, ImgAdminPathBase + displayObj.getIconClass(), 
                            categoryID, modelID), new ElementTreeNode[0], true), parentItem.getChildCount());
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
        }

        /*
         * Metodo cuya funcionalidad es la de actualizar un nodo del árbol,
         * basicamente se actualiza solamente en su título, para que este se
         * vea reflejado en el mismo árbol
         * @param item ElementTreeNode a actualizar
         * @param title String a colocar en el item a actualizar
         */

        public static void updateTreeTitleNode(ElementTreeNode item, String title) {
            Element element = (Element) item.getData();
            element.setName(title);
            item.setData(element);
        }


        public static void updateTreeNode(HttpServletRequest request, SWBParamRequest paramRequest)
        {
            try
            {
                String action=(String)request.getAttribute("action");
                if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_REMOVE))
                {
                    String objUri=(String)request.getAttribute("objUri");
                    if(objUri!=null)
                    {
                        SemanticObject semObj=SemanticObject.createSemanticObject(objUri);
                        Semantic.removeSemObjTreeNode(semObj);
                    }
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+paramRequest.getWebPage().getWebSiteId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }

        /*
         * Metodo que crea el nodo de la nueva marca y la pasa al archivo zul cnf_GenericZulTreeUpdate, para que este levante un evento y
         * sea dicho evento manejado en el indexAdm.zul y este actualice el árbol, ya que no se puede actualizar sin que sea en un listener (Evento)
         */
        public static void createNewBrandNode(HttpServletRequest request, SWBParamRequest paramRequest, WebSite newSite)
        {
            try
            {
                WebSite adminWebSite=paramRequest.getWebPage().getWebSite();
                User user=paramRequest.getUser();

                DisplayObject displayObj=(DisplayObject)SocialSite.sclass.getDisplayObject().createGenericInstance();
                String sIconImg="off_"+displayObj.getIconClass();
                //rootTreeNode.insert(new ElementTreeNode(new Element(newSite.getTitle(), newSite.getURI(), "/work/models/"+adminWebSite.getId()+"/admin/img/"+sIconImg),getTreeCategoryNodes(newSite, adminWebSite, user),true), rootTreeNode.getChildCount());
                ElementTreeNode newBrandNode=new ElementTreeNode(new Element(newSite.getTitle(), newSite.getURI(), "/work/models/"+adminWebSite.getId()+"/admin/img/"+sIconImg),getTreeCategoryNodes(newSite, adminWebSite, user),true);
                request.setAttribute("action", "updateTree");
                request.setAttribute("treeItem", newBrandNode);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/work/models/"+adminWebSite.getId()+"/admin/zul/cnf_GenericZulTreeUpdate.zul");
                dispatcher.include(request, new SWBResponse());
            }catch(Exception e)
            {
                log.debug(e);
            }
        }

        /*
         * Metodo que sirve para crear los nodos de tipo categorías a un nuevo nodo de tipo Marca.
         */
        private static ElementTreeNode[] getTreeCategoryNodes(SWBModel model, WebSite adminWebSite, User user)
        {
            try
            {
                ArrayList<TreeNodePage> alist=new ArrayList();
                WebSite adminWSite=WebSite.ClassMgr.getWebSite(adminWebSite.getId());
                WebPage treePageHome=adminWSite.getWebPage("TreeCategoryNodes");
                int cont=0;
                Iterator <WebPage> itChilds=treePageHome.listVisibleChilds(user.getLanguage());
                while(itChilds.hasNext())
                {
                    WebPage page=itChilds.next();
                    if(page instanceof TreeNodePage)
                    {
                        TreeNodePage treeNodePage=(TreeNodePage)page;
                        alist.add(treeNodePage);
                        cont++;
                    }
                }
                ElementTreeNode[] elementTreeNode=new ElementTreeNode[cont];
                Iterator<TreeNodePage> itTreeNodes=alist.iterator();
                int cont2=0;
                while(itTreeNodes.hasNext())
                {
                    TreeNodePage treeNode=itTreeNodes.next();
                    String iconImgPath=SWBPortal.getWebWorkPath()+treeNode.getWorkPath()+"/"+treeNode.social_wpImg.getName()+"_"+treeNode.getId()+"_"+treeNode.getWpImg();
                    elementTreeNode[cont2]=new ElementTreeNode(new Element(treeNode.getTitle(), treeNode.getId(), treeNode.getZulResourcePath(), iconImgPath, null, model.getId()));
                    cont2++;
                }
                return elementTreeNode;
            }catch(Exception e)
            {
                log.error(e);
            }
            return new ElementTreeNode[0];
        }

      
        /*
         * Actualiza el árbol, este metodo se debe llamar desde un zul o un controlador de zul.
         */
        public static void removeNodeFromTree(Map<String, Object> requestScope)
        {
            try
            {
                String action=(String)requestScope.get("action");
                if(action!=null && action.equals(SWBSocialResourceUtils.ACTION_REMOVE))
                {
                    String objUri=(String)requestScope.get("objUri");
                    if(objUri!=null)
                    {
                        SemanticObject semObj=SemanticObject.createSemanticObject(objUri);
                        Semantic.removeSemObjTreeNode(semObj);
                    }
                    ElementTreeNode treeItem=(ElementTreeNode)requestScope.get("treeItem");
                    if(treeItem!=null)
                    {
                        EventQueue<Event> eq = EventQueues.lookup("removeNodo2Tree", EventQueues.SESSION, true);
                        eq.publish(new Event("onRemoveNode", null, treeItem));
                    }
                }
            }catch(Exception e)
            {
                log.debug(e);
            }
        }

        /*
         * Envía mensajes a la barra de estatus
         */
        public static void setStatusMessage(String message)
        {
            EventQueue<Event> eq = EventQueues.lookup("updateMsgWin", EventQueues.SESSION, true);
            eq.publish(new Event("onUpdateMsgWin", null, message));
        }

    }

    public static class Semantic
    {
        
        public static void removeSemObjTreeNode(SemanticObject semObj)
        {
           if(semObj!=null)
           {
               if(semObj.getSemanticClass().isSubClass(Childrenable.social_Childrenable))
               {
                   if(SWBSocialResourceUtils.Semantic.removeObjChildrenable(semObj))
                   {
                        semObj.remove();
                   }
               }else
               {
                    semObj.remove();
               }
           }
        }

        /**
         * Metodo para eliminar los todos los hijos de un elementos que sea de tipo Childrenale, de manera recursiva.
         * @param semObj SemanticObject en el cual buscara nodos hijos, si los tiene los elimina
         * @return true: Si eliminó satisfactoriamente, false: si no lo hizo.
         */
        public static boolean removeObjChildrenable(SemanticObject semObj) {
            try {
                Childrenable childrenable = (Childrenable) semObj.getGenericInstance();
                Iterator<Childrenable> itChildren = childrenable.listChildrenObjInvs();
                while (itChildren.hasNext()) {
                    Childrenable children = itChildren.next();
                    if (children.listChildrenObjInvs().hasNext()) {
                        if(removeObjChildrenable(children.getSemanticObject()))
                        {
                            //System.out.println("Hijo que elimina-0:"+children.getSemanticObject());
                            children.getSemanticObject().remove();
                        }
                    } else {
                        //System.out.println("Hijo que elimina-1:"+children.getSemanticObject());
                        children.getSemanticObject().remove();
                    }
                }
                return true;
            } catch (Exception e) {
                log.error(e);
            }
            return false;
        }

    }
}
