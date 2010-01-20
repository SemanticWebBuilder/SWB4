/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * ResourceMgr.java
 *
 * Created on 3 de junio de 2002, 11:20
 */
package com.infotec.wb.core;

import java.util.*;

import com.infotec.topicmaps.*;
import com.infotec.wb.core.db.RecResource;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.appfw.lib.AFAppObject;
import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.lib.*;

import java.lang.reflect.*;

import com.infotec.wb.lib.WBResource;
import com.infotec.wb.resources.GenericAdmResource;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPortal;
import org.semanticwb.api.SWBResourceToWBResourceWrapper;
import org.semanticwb.portal.api.SWBResource;


/** Objeto: Manejador de Recursos en memoria
 *
 * Object: Manager of Resources in memory.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class ResourceMgr implements AFAppObject, AFObserver
{
    static private ResourceMgr instance;        //The single instance

    /** Creates new DBUser */
    private ResourceMgr()
    {
    }

    /**
     * @return  */
    static public ResourceMgr getInstance()
    {
        if (instance == null)
        {
            makeInstance();
        }
        return instance;
    }
    
    static private synchronized void makeInstance()
    {
        if (instance == null)
        {
            instance = new ResourceMgr();
            instance.init();
        }
    }      

    public void init()
    {
    }

    /**
     * @return HashMap WBResources */
    public HashMap getResources(String topicmap)
    {
        HashMap map=new HashMap();
        //TODO
        return map;
    }

    /**
     * @param type
     * @return  HashMap Resources*/
    public HashMap getResourcesBaseOfType(String topicmap, int type)
    {
        HashMap map=new HashMap();
        //TODO
//        (HashMap)resourcesbase.get(topicmap);
//        if(map==null)return new HashMap();
//        map=(HashMap)map.get(""+type);
//        if(map==null)return new HashMap();
        return map;
    }
    
    /**
     * @param type
     * @return  HashMap Resources*/
    public HashMap getResourcesBaseOfType(String topicmap, int type, String typeMap)
    {
        HashMap map=new HashMap();
        //TODO
//        HashMap map=(HashMap)resourcesbase.get(topicmap);
//        if(map==null)return new HashMap();
//        String typekey=""+type;
//        if(!topicmap.equals(typeMap))typekey+="|"+typeMap;
//        map=(HashMap)map.get(typekey);
//        if(map==null)return new HashMap();
        return map;
    }
    
    public WBResource getResourceCached(WBResource res, HttpServletRequest request, WBParamRequest paramsRequest)
    {
//        cache.incResourceHits();
//        if (paramsRequest.getResourceBase().isCached())
//        {
//            return cache.getResource(res, request, paramsRequest);
//        }else
//        {
//            return res;
//        }
        return res;
    }
    
    

//    /**
//     * @param user
//     * @param topic
//     * @param params
//     * @param tpl
//     * @return  */
//    public Iterator getContents(WBUser user, com.infotec.topicmaps.Topic topic, HashMap params, Template tpl)
//    {
//        ArrayList ret=new ArrayList();
//          //TODO
//        SWBPortal.getResourceMgr().getContents(user.getNative(),  topic.getNative(), params, tpl)
//        return ret.iterator();
//    }


//    /**
//     * @param type
//     * @param user
//     * @param topic
//     * @param params
//     * @param tpl
//     * @return  */
//    public Iterator getResources(String type, WBUser user, com.infotec.topicmaps.Topic topic, HashMap params, Template tpl)
//    {
//        ArrayList ret=new ArrayList();
//        //TODO
//        return ret.iterator();
//    }

    /**
     * @param base
     * @param user
     * @param stype
     * @param camp
     * @param today
     * @param topic
     * @return  */
    public boolean checkResource(Resource base, WBUser user, int stype,String stypemap, int camp, Date today, com.infotec.topicmaps.Topic topic)
    {
        return SWBPortal.getResourceMgr().checkResource(base.getNative(), user.getNative(), camp, today, topic.getNative());
    }

    public WBResource asWBResource(SWBResource res)
    {
        WBResource ret=null;
        if(res instanceof SWBResourceToWBResourceWrapper)
        {
            ret=((SWBResourceToWBResourceWrapper)res).getOldResource();
        }
        return ret;
    }

    /**
     * @param id
     * @throws WBException
     * @return  */
    public WBResource getResource(String topicmap, long id)
    {
        SWBResource res=SWBPortal.getResourceMgr().getResource(topicmap, ""+id);
        return asWBResource(res);
    }
    
    public void refresh()
    {
    }

    /** Avisa al observador de que se ha producido un cambio.
     * @param s
     * @param recresource  */
    public void sendDBNotify(String s, Object recresource)
    {
    }
    
    public boolean reloadResourceClass(String tmap,int type)
    {
        return true;
    }

    /** Getter for property resReloader.
     * @return Value of property resReloader.
     *
     */
    public boolean isResurceReloader()
    {
        return SWBPortal.getResourceMgr().isResourceReloader();
    }

    /** Setter for property resReloader.
     * @param resReloader New value of property resReloader.
     *
     */
    public void setResourceReloader(boolean resReloader)
    {
        SWBPortal.getResourceMgr().setResourceReloader(resReloader);
    }

    public void destroy()
    {
    }

//    /** Getter for property timeLock.
//     * @return Value of property timeLock.
//     *
//     */
//    public WBResourceTraceMgr getResourceTraceMgr()
//    {
//        return null;
//    }
//
//    /** Getter for property timeLock.
//     * @return Value of property timeLock.
//     *
//     */
//    public WBResourceCachedMgr getResourceCacheMgr()
//    {
//        return cache;
//    }

//    /** Getter for property resourceLoaders.
//     * @return Value of property resourceLoaders.
//     *
//     */
//    public java.util.HashMap getResourceLoaders()
//    {
//        return resourceLoaders;
//    }

//    /** Setter for property resourceLoaders.
//     * @param resourceLoaders New value of property resourceLoaders.
//     *
//     */
//    public void setResourceLoaders(java.util.HashMap resourceLoaders)
//    {
//        this.resourceLoaders = resourceLoaders;
//    }
    
}
