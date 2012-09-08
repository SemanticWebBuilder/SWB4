/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.utils;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.DisplayObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.admin.tree.ElementTreeNode;
import org.semanticwb.social.admin.tree.Element;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialResourceUtils {

    public static final String ACTION_ADD="add";
    public static final String ACTION_EDIT="edit";
    public static final String ACTION_REMOVE="remove";

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
    static ArrayList<String> aDoubles=new ArrayList();
    static HashMap<String, String> hmapChanges=new HashMap();

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

    private static void init()
    {

    }

     public static class Components {

         public static void insertTreeNode(ElementTreeNode parentItem, SWBClass swbClass)
         {
             if(swbClass instanceof Descriptiveable)
             {
                //Tomando en cuenta que solo exista un solo sitio de admin de swbsocial y que exista con identificador "swbsocial"
                //Todo:Ver si puede mejorar la sig. línea.
                String ImgAdminPathBase="/work/models/swbsocial/admin/img/";
                Descriptiveable descripTable=(Descriptiveable)swbClass;
                DisplayObject displayObj=(DisplayObject)swbClass.getSemanticObject().getSemanticClass().getDisplayObject().createGenericInstance();
                parentItem.insert(new ElementTreeNode(new Element(descripTable.getTitle(), descripTable.getURI(), ImgAdminPathBase+displayObj.getIconClass())), parentItem.getChildCount());
             }
         }

         public static void updateTreeNode(ElementTreeNode item, String title)
         {
            Element element=(Element)item.getData();
            element.setName(title);
            item.setData(element);
         }

         
     }


}
