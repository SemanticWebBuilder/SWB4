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
import org.semanticwb.api.WBResourceToSWBResourceWrapper;
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
        if(res instanceof WBResourceToSWBResourceWrapper)
        {
            ret=((WBResourceToSWBResourceWrapper)res).getOldResource();
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
