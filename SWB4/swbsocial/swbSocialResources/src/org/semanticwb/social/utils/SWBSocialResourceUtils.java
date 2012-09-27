/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.utils;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.Childrenable;
import org.semanticwb.social.admin.tree.ElementTreeNode;
import org.semanticwb.social.admin.tree.Element;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialResourceUtils {

    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_REMOVE = "remove";
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

        public static void insertTreeNode(ElementTreeNode parentItem, SWBClass swbClass, WebPage wpage) {
            if (swbClass instanceof Descriptiveable) {
                try
                {
                    //Tomando en cuenta que solo exista un solo sitio de admin de swbsocial y que exista con identificador "swbsocial"
                    //Todo:Ver si puede mejorar la sig. línea.
                    String ImgAdminPathBase = "/work/models/swbsocial/admin/img/";
                    /*
                    WebSite adminWSite=wpage.getWebSite();
                    if(adminWSite!=null)
                    {
                    ImgAdminPathBase="/work/models/"+adminWSite+"/admin/img/";
                    }*/
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
                    parentItem.insert(new ElementTreeNode(new Element(descripTable.getTitle(), descripTable.getURI(), null, ImgAdminPathBase + displayObj.getIconClass(), categoryID), new ElementTreeNode[0], true), parentItem.getChildCount());
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
        }

        public static void updateTreeNode(ElementTreeNode item, String title) {
            Element element = (Element) item.getData();
            element.setName(title);
            item.setData(element);
        }
    }

    public static class Semantic
    {
        /**
         * Metodo para eliminar los todos los hijos de un elementos que sea de tipo Childrenale
         * @param semObj
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
