/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources.swbtree;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author javier.solis
 */
public class TreeUtils
{
    private static int nullnode=0;

    static public JSONObject getAction(String name, String value, String target) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("value", value);
        obj.put("target", target);
        return obj;
    }

    static public JSONObject getEvent(String name, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("action", action);
        return obj;
    }

    static public JSONObject getMenuItem(String title, String icon, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("title", title);
        obj.put("icon", icon);
        obj.put("action", action);
        return obj;
    }

    static public JSONObject getDNode(String id, String title, String type, String icon) throws JSONException
    {
        return getNode(id+"|"+(nullnode++), title, type, icon);
    }
    
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

    static public JSONObject getReloadAction() throws JSONException
    {
        return getAction("reload",null,null);
    }

    static public JSONObject getNewTabAction() throws JSONException
    {
        return getAction("newTab",SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp",null);
    }

    static public JSONObject getMenuSeparator() throws JSONException
    {
        return getMenuItem("_",null, null);
    }

    static public JSONObject getDummy() throws JSONException
    {
        return getNode("_NOID_" + (nullnode++), "DUMMY", "DUMMY", "DUMMY");
    }

    //018008499809

}
