/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.admin.resources.swbtree;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.SWBPlatform;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeUtils.
 * 
 * @author javier.solis
 */
public class TreeUtils
{
    
    /** The nullnode. */
    private static int nullnode=0;

    /**
     * Gets the action.
     * 
     * @param name the name
     * @param value the value
     * @param target the target
     * @return the action
     * @throws JSONException the jSON exception
     */
    static public JSONObject getAction(String name, String value, String target) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("value", value);
        obj.put("target", target);
        return obj;
    }

    /**
     * Gets the event.
     * 
     * @param name the name
     * @param action the action
     * @return the event
     * @throws JSONException the jSON exception
     */
    static public JSONObject getEvent(String name, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("action", action);
        return obj;
    }

    /**
     * Gets the menu item.
     * 
     * @param title the title
     * @param icon the icon
     * @param action the action
     * @return the menu item
     * @throws JSONException the jSON exception
     */
    static public JSONObject getMenuItem(String title, String icon, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("title", title);
        obj.put("icon", icon);
        obj.put("action", action);
        return obj;
    }

    /**
     * Gets the d node.
     * 
     * @param id the id
     * @param title the title
     * @param type the type
     * @param icon the icon
     * @return the d node
     * @throws JSONException the jSON exception
     */
    static public JSONObject getDNode(String id, String title, String type, String icon) throws JSONException
    {
        return getNode(id+"|"+(nullnode++), title, type, icon);
    }
    
    /**
     * Gets the node.
     * 
     * @param id the id
     * @param title the title
     * @param type the type
     * @param icon the icon
     * @return the node
     * @throws JSONException the jSON exception
     */
    static public JSONObject getNode(String id, String title, String type, String icon) throws JSONException
    {
        if(title==null)title="Topic";
        JSONObject obj=new JSONObject();
        obj.put("id", id);
        obj.put("title",title);
        obj.put("type",type);
        obj.put("icon",icon);
        return obj;
    }

    /**
     * Gets the reload action.
     * 
     * @return the reload action
     * @throws JSONException the jSON exception
     */
    static public JSONObject getReloadAction() throws JSONException
    {
        return getAction("reload",null,null);
    }

    /**
     * Gets the new tab action.
     * 
     * @return the new tab action
     * @throws JSONException the jSON exception
     */
    static public JSONObject getNewTabAction() throws JSONException
    {
        return getAction("newTab",SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp",null);
    }

    /**
     * Gets the menu separator.
     * 
     * @return the menu separator
     * @throws JSONException the jSON exception
     */
    static public JSONObject getMenuSeparator() throws JSONException
    {
        return getMenuItem("_",null, null);
    }

    /**
     * Gets the dummy.
     * 
     * @return the dummy
     * @throws JSONException the jSON exception
     */
    static public JSONObject getDummy() throws JSONException
    {
        return getNode("_NOID_" + (nullnode++), "DUMMY", "DUMMY", "DUMMY");
    }

    //018008499809

}
