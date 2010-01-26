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
 * Resource.java
 *
 * Created on 24 de junio de 2002, 16:38
 */

package com.infotec.wb.core;

import javax.servlet.http.*;

import com.infotec.wb.core.db.*;
import com.infotec.topicmaps.*;
import com.infotec.appfw.exception.*;
import org.w3c.dom.*;

import java.util.*;

import com.infotec.wb.core.db.RecResource;
import org.semanticwb.SWBException;

/**
 * Objeto: el objeto Resource es un objeto que se encuentra en la capa de l�gica y
 * de interacci�n dentro del core, este objeto es el encargado de proporcionar a
 * los WBResources (recursos de usuario)la informaci�n por defecto que requiere
 * WebBuilder para administrar el recurso.
 *
 * Object: the Resource object is an object that is in the layer of logic and
 * interaction within Core, this object is the one in charge to provide to the
 * WBResources (user resources) the information by defect that requires WebBuilder
 * to administer the resource.
 * @author Javier Solis Gonzalez
 * @version 1.2
 */
public class Resource
{
    org.semanticwb.model.Resource res = null;

    public Resource(org.semanticwb.model.Resource resource)
    {
        res = resource;
    }

    public org.semanticwb.model.Resource getNative()
    {
        return res;
    }

    public static final String Mode_EDIT="edit";
    public static final String Mode_VIEW="view";
    public static final String Mode_HELP="help";
    public static final String Mode_ADMIN="admin";
    public static final String Mode_ADMHLP="admhlp";
    
    public static final int UsrLevel_NONE=0;
    public static final int UsrLevel_SEE=1;
    public static final int UsrLevel_CREATE=2;
    public static final int UsrLevel_MODIFY=3;
    public static final int UsrLevel_REMOVE=4;
    public static final int UsrLevel_ADMIN=5;
    
    public static final int ResType_CONTENT=1;
    public static final int ResType_STRATEGY=2;
    public static final int ResType_SYSTEM=3;
    public static final int ResType_APPLICATION=4;
    public static final int ResType_INTERN=9;
    
    public static final String WinState_NORMAL="normal";
    public static final String WinState_MAXIMIZED="maximized";
    public static final String WinState_MINIMIZED="minimized";
    
    /** Crea un nuevo objeto Resource
     */
    public Resource(String topicmap)
    {
        //TODO:

    }

    /** Crea un nuevo objeto Resource con la informacion del registro de base de datos RecResource.
     * @param recResource Objeto de referencia a un registro de la tabla wbresource de la base de datos.
     */
    public Resource(RecResource recResource)
    {
        setRecResource(recResource);
    }

    /**
     * @param recResource  */
    public void setRecResource(RecResource recResource)
    {
        res=recResource.getNative();
    }

    /** regresa id del recurso.
     * @return Value of property id.
     */
    public long getId()
    {
        long ret=0;
        try
        {
            ret=Long.parseLong(res.getId());
        }catch(Exception noe){
            //No es posible obtener el id del recurso
        }
        return ret;
    }
    
    /** regresa id del recurso.
     * @return Value of property String topicmapid.
     */
    public String getTopicMapId()
    {
        return res.getWebSiteId();
    }    
    
    /** regresa id del recurso.
     * @return Value of property TopicMap.
     */
    public TopicMap getTopicMap()
    {
        return new TopicMap(res.getWebSite());
    }    
    

    /** Compara dos Recursos
     * @param obj
     * @return
     */
    public boolean equals(Object obj)
    {
        return ((Resource) obj).getNative().hashCode() == res.hashCode();
    }

    /** regresa string con el nombre de la clase del recurso.
     * @return Value of property type.
     */
    public int getType()
    {
        return res.getResourceType().getId().hashCode();
        //return recResource.getType();
    }
    
    /** regresa string con el nombre de la clase del recurso.
     * @return Value of property type.
     */
    public String getTypeMap()
    {
        return res.getResourceType().getWebSite().getId();
    }
    

    /** regresa contador de versiones.
     * @return Value of property lastversion.
     */
    public int getLastversion()
    {
        //TODO
        return 0;
        //return recResource.getLastversion();
    }

    /** regresa 0 si el recurso no esta activo y 1 si lo esta.
     * @return Value of property active.
     */
    public int getActive()
    {
        if(res.isActive())return 1;
        else return 0;
    }
    
    /** regresa 0 si el recurso no es monitoreado con sus hits y 1 si lo esta.
     * @return Value of property hitlog.
     */
    public int getHitLog()
    {
        //TODO
        return 0;
        //return recResource.getHitLog();
    }

    /** regresa xmlconf del recurso.
     * @return Value of property xmlconf.
     */
    public java.lang.String getXmlConf()
    {
        return res.getXmlConf();
    }

    /** regresa xml del recurso.
     * @return Value of property xml.
     */
    public java.lang.String getXml()
    {
        return res.getXml();
    }

    /** regresa descriGetter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription()
    {
        return res.getDescription();
    }

    /** regresa el numero de version al que el recurso esta apuntando.
     * @return Value of property actualversion.
     */
    public int getActualversion()
    {
        //TODO:
        return 0;
        //return recResource.getActualversion();
    }

    /** regresa titulo del recurso.
     * @return Value of property title.
     */
    public java.lang.String getTitle()
    {
        return res.getTitle();
    }

    /** regresa fecha de ultima actualizacion.
     * @return Value of property lastupdate.
     */
    public java.sql.Timestamp getLastupdate()
    {
        return new java.sql.Timestamp(res.getUpdated().getTime());
    }

    /** regresa fecha de creacion.
     * @return Value of property created.
     */
    public java.sql.Timestamp getCreated()
    {
        return new java.sql.Timestamp(res.getCreated().getTime());
    }

    /** regresa 0 si el recurso fue deleted y 1 si esta disponible.
     * @return Value of property deleted.
     */
    public int getDeleted()
    {
        if(res.isDeleted())return 1;
        else return 0;
    }

    /** regresa un arreglo de las reglas asociadas con este recurso.
     * @return aray con el numero de cada regla asociada.
     */
    public Iterator getRules()
    {
        ArrayList ret=new ArrayList();
        //TODO:
        return ret.iterator();
        //return rules.iterator();
    }
    
     /** regresa un arreglo de los roles asociadas con este recurso.
     * @return aray con el numero de cada role asociado.
     */
    public Iterator getRoles()
    {
        ArrayList ret=new ArrayList();
        //TODO:
        return ret.iterator();
        //return roles.iterator();
    }
    
     /** 
     * @return un boolean indicando si existe el rol o no en el recurso.
     */
    public boolean haveRole(int role)
    {
        //TODO:
        return false;
        //return roles.contains(new Integer(role));
    }
    
    /** 
     * @return un boolean indicando si existe la regla o no en el recurso.
     */
    public boolean haveRule(int rule)
    {
        //TODO:
        return false;
        //return rules.contains(new Integer(rule));
    }
    

    /** regresa la version del recurso en objeto DOM.
     * @return Value of property domconf.
     */
    public Document getDomConf()
    {
        return res.getDomConf();
        //return domconf;
    }

    /** regresa la version del recurso en objeto DOM.
     * @return Value of property dom.
     */
    public Document getDom()
    {
        return res.getDom();
        //return dom;
    }

    /**
     * @return  */
    public int getSubType()
    {
        return res.getResourceSubType().getId().hashCode();
        //return recResource.getIdSubType();
    }
    
    /**
     * @return  */
    public String getSubTypeMap()
    {
        return res.getResourceSubType().getWebSite().getId();
    }    

    /**
     * @return  */
    public int getCamp()
    {
        //TODO
        return 0;
        //return recResource.getIdCamp();
    }

    /**
     * @return  */
    public int getPriority()
    {
        return res.getPriority();
    }

    /**
     * @return  */
    public String getIdAdm()
    {
        return res.getCreator().getURI();
    }

    public void refreshRandPriority()
    {
        res.refreshRandPriority();
    }

    /**
     * @return  */
    public int getRandPriority()
    {
        return res.getRandPriority();
    }

    /** Getter for property interval.
     * @return Value of property interval.
     */
    public org.w3c.dom.NodeList getInterval()
    {
        //TODO:
        return null;
        //return interval;
    }

    /** Getter for property filterMap.
     * @return Value of property filterMap.
     */
    public org.w3c.dom.NodeList getFilterMap()
    {
        //TODO:
        return null;
        //return filterMap;
    }

    /** Getter for property recResource.
     * @return Value of property recResource.
     */
    public RecResource getRecResource()
    {
        return new RecResource(res);
    }

    /**
     * @param id
     * @param user
     * @return  */
    public boolean addRule(int id, String user)
    {
        //TODO:
        return false;
    }

    /**
     * @param id
     * @param user
     * @return  */
    public boolean removeRule(int id, String user)
    {
        //TODO:
        return false;
    }
    
    /**
     * @param id
     * @param user
     * @return  */
    public boolean addRole(int id, String repository, String user)
    {
        //TODO:
        return false;
    }

    /**
     * @param id
     * @param user
     * @return  */
    public boolean removeRole(int id, String repository, String user)
    {
        //TODO:
        return false;
    }
    
    
    /** Getter for property maxviews.
     * @return Value of property maxviews.
     */
    public long getMaxViews()
    {
        return res.getMaxViews();
    }
    
    /**
     * @return  */
    public long getCache()
    {
        return 0;
        //return recResource.getCache();
    }
    

    /**
     * @return  */
    public long getViews()
    {
        return res.getViews();
    }

    /**
     * @return  */
    public long getHits()
    {
        return res.getHits();
    }

    /**
     * @param request
     * @param user
     * @param topic  */
    public void addHit(HttpServletRequest request, WBUser user, Topic topic)
    {
        //TODO
        res.addHit(request, user.getNative(), topic.getNative());
    }

    /**
     * @param topic
     * @return  */
    public boolean evalFilterMap(Topic topic)
    {
        //TODO
        return false;
    }

    /**
     * @param tp
     * @return  */
    public boolean setFlowOfContent2Init(Topic tp)
    {
        //TODO
        return false;
    }

    /**
     * @throws AFException
     * @return  */
    public String getData() throws AFException
    {
        try
        {
            return res.getData();
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param data
     * @throws AFException  */
    public void setData(String data) throws AFException
    {
        try
        {
            res.setData(data);
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @throws AFException
     * @return  */
    public String getData(WBUser usr) throws AFException
    {
        try
        {
            return res.getData(usr.getNative());
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @param data
     * @throws AFException  */
    public void setData(WBUser usr, String data) throws AFException
    {
        try
        {
            res.setData(usr.getNative(),data);
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @param topic
     * @throws AFException
     * @return  */
    public String getData(WBUser usr, Topic topic) throws AFException
    {
        try
        {
            return res.getData(usr.getNative(),topic.getNative());
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @param topic
     * @param data
     * @throws AFException  */
    public void setData(WBUser usr, Topic topic, String data) throws AFException
    {
        try
        {
            res.setData(usr.getNative(),topic.getNative(),data);
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param topic
     * @throws AFException
     * @return  */
    public String getData(Topic topic) throws AFException
    {
        try
        {
            return res.getData(topic.getNative());
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param topic
     * @param data
     * @throws AFException  */
    public void setData(Topic topic, String data) throws AFException
    {
        try
        {
            res.setData(topic.getNative(),data);
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /** Getter for property cached.
     * @return Value of property cached.
     */
    public boolean isCached()
    {
        return res.isCached();
    }

    /** Regresa la ruta de trabajo relativa al directorio work del recurso.
     */
    public String getResourceWorkPath()
    {
        return res.getWorkPath();
    }
    
    /** Regresa la ruta de trabajo relativa al directorio work del recurso.
     */    
    public static String getResourceWorkPath(RecResource rec)
    {
        return rec.getNative().getWorkPath();
    }

    /** Crea el directorio de trabajo del recurso.
     */
    public boolean makeResourceWorkPath()
    {
        boolean ret = true;
        return ret;
    }

//    /**
//     *
//     * @return
//     * @deprecated No usar...
//     * Se mantiene por compatibilidad
//     * usar getRenderUrl()
//     */
//    public String getAdminUrl()
//    {
//        //return WBUtils.getInstance().getWebPath() + com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/admresource") + "/" + getId() + "/_tm/"+getTopicMapId();
//    }

//    /**
//     *
//     * @param topic
//     * @return
//     * @deprecated No usar...
//     * Se mantiene por compatibilidad
//     * usar getRenderUrl()
//     */
//    public String getUrl(Topic topic)
//    {
//        WBResourceURL url=new WBResourceURLImp(null, this,topic,WBResourceURL.UrlType_RENDER);
//        url.setMode(url.Mode_VIEW);
//        url.setCallMethod(url.Call_DIRECT);
//        return url.toString();
//        //return WBUtils.getInstance().getWebPath() + com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/response") + "/" + topic.getMap().getId() + "/" + topic.getId() + "/" + getId();
//    }

    /** Asigna un atributo al DOM del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setAttribute(String name, String value)
    {
        res.setAttribute(name, value);
    }
    
    /** Asigna un atributo al DOM de configuraci�n del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setConfAttribute(String name, String value)
    {
        //TODO:
    }

    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getAttribute(String name, String defvalue)
    {
        return res.getAttribute(name, defvalue);
    }


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public String getAttribute(String name)
    {
        return res.getAttribute(name);
    }
    
    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public Enumeration getAttributeNames()
    {
        Vector vec=new Vector();
        Iterator it=res.getAttributeNames();
        while (it.hasNext())
        {
            Object object = it.next();
            vec.add(object);
        }
        return vec.elements();
    }
    
    
    
     /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getConfAttribute(String name, String defvalue)
    {
        //TODO
        return null;
    }


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public String getConfAttribute(String name)
    {
        //TODO
        return null;
    }
    
    /** Borra un atributo del DOM del Recurso
     */
    public void removeAttribute(String name)
    {
        res.removeAttribute(name);
    }
    
      /** Borra un atributo del DOM del Recurso
     */
    public void removeConfAttribute(String name)
    {
        //TODO
    }

    /** Actualiza los atributos del DOM a base de datos. */
    public void updateAttributesToDB() throws AFException
    {
        try
        {
            res.updateAttributesToDB();
        }catch(SWBException e)
        {
            throw new AFException(e);
        }
    }
    
     /** Actualiza los atributos del DOM a base de datos. */
    public void updateConfAttributesToDB() throws AFException
    {
        //TODO
    }

    /** Actualiza los atributos del DOM a base de datos. */
    public void updateAttributesToDB(String userid, String comment) throws AFException
    {
        //TODO
        try
        {
            res.updateAttributesToDB();
        }catch(SWBException e)
        {
            throw new AFException(e);
        }
    }
    
    /** Actualiza los atributos del DOM a base de datos. */
    public void updateConfAttributesToDB(String userid, String comment) throws AFException
    {
        //TODO
    }    
    
    /**
     *
     * @return
     */    
    public RecResourceType getResourceType()
    {
        return new RecResourceType(res.getResourceType());
    }
    
    /** Verifica si el usuario tiene permisos de acceso al recurso.
     * @return boolean
     */
    public boolean haveAccess(WBUser user)
    {
        return user.getNative().haveAccess(res);
    }    
    
    /**
     *
     * @param user
     * @return
     */    
    public boolean checkRules(WBUser user)
    {
        return user.getNative().haveAccess(res);
    }
    
    /**
     *
     * @param user
     * @return
     */    
    public boolean checkRoles(WBUser user)
    {
        return user.getNative().haveAccess(res);
    }    
    
//    /**
//     * Getter for property wb2Resource.
//     * @return Value of property wb2Resource.
//     */
//    public boolean isWb2Resource()
//    {
//        return wb2Resource;
//    }
//
//    /**
//     * Setter for property wb2Resource.
//     * @param wb2Resource New value of property wb2Resource.
//     */
//    void setWb2Resource(boolean wb2Resource)
//    {
//        this.wb2Resource = wb2Resource;
//    }
    
    public void evalDate4Views()
    {
        //TODO
    }
    
    public boolean hasWarningOfViews()
    {
        //TODO:
        return false;
        //return time4ViewsWarning;
    }
    
}
