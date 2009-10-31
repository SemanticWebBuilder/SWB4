/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * ResourceMgr.java
 *
 * Created on 3 de junio de 2002, 11:20
 */
package com.infotec.wb.core;

import com.infotec.appfw.util.*;

import java.util.*;

import com.infotec.wb.util.*;
import com.infotec.wb.core.db.*;

import java.io.*;

import com.infotec.topicmaps.*;
import com.arthurdo.parser.*;
import com.infotec.topicmaps.Occurrence;
import com.infotec.topicmaps.Topic;
import com.infotec.wb.core.Template;
import com.infotec.wb.core.WBResourceTraceMgr;
import com.infotec.wb.core.RuleMgr;
import com.infotec.wb.core.WBUser;
import com.infotec.wb.core.db.RecResource;
import com.infotec.wb.core.db.DBResource;
import com.infotec.wb.core.db.DBCatalogs;
import com.infotec.wb.lib.*;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.appfw.lib.AFAppObject;
import com.infotec.appfw.util.AFUtils;
import com.infotec.topicmaps.bean.TopicMgr;
import com.infotec.wb.core.*;
import com.infotec.wb.lib.*;

import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;

import com.infotec.wb.lib.WBResource;
import com.infotec.wb.resources.GenericAdmResource;

import javax.servlet.http.HttpServletRequest;


/** Objeto: Manejador de Recursos en memoria
 *
 * Object: Manager of Resources in memory.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class ResourceMgr implements AFAppObject, AFObserver
{
    private HashMap resources;                  //WBResource
    private HashMap resourcesbase;              //Resources by type
    private HashMap resourceLoaders;            //Resources ClassLoaders
    static private ResourceMgr instance;        //The single instance
    private WBIntervalEvaluation intereval;
    private WBResourceCachedMgr cache;
    private boolean resReloader = false;
    private WBResourceTraceMgr tracer;

    private HashMap oldresources;               //WBResource

    /** Creates new DBUser */
    private ResourceMgr()
    {
        AFUtils.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_ResourceMgr_ResourceMgr"), true);
        resources = new HashMap();
        resourcesbase = new HashMap();
        resourceLoaders = new HashMap();
        oldresources = new HashMap();
        int time = 100;
        try
        {
            time = Integer.parseInt((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/resourceCached"));
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_ResourceMgr_maxCacheRes"), true);
        }
        cache = new WBResourceCachedMgr(time);
        tracer = new WBResourceTraceMgr();
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
        intereval = new WBIntervalEvaluation();
        DBResource dbt = DBResource.getInstance();
        
        try
        {
            resReloader = Boolean.valueOf(com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/resReloader", "false")).booleanValue();
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_init_resReloader"), true);
            resReloader = false;
        }        

        Enumeration en = dbt.getResourceMaps();
        while (en.hasMoreElements())
        {
            String smap=(String)en.nextElement();
           
            try
            {
                Enumeration en2 = dbt.getResources(smap);
                while (en2.hasMoreElements())
                {
                    RecResource rec = (RecResource) en2.nextElement();
                    createWBResource(rec);
                }
            }catch(Exception e){AFUtils.log(e,"Error loadin resources:"+smap);}
        }

    }

    /**
     * @return HashMap WBResources */
    public HashMap getResources(String topicmap)
    {
        HashMap map=(HashMap)resources.get(topicmap);
        if(map==null)map=new HashMap();
        return map;
    }

    /**
     * @param type
     * @return  HashMap Resources*/
    public HashMap getResourcesBaseOfType(String topicmap, int type)
    {
        HashMap map=(HashMap)resourcesbase.get(topicmap);
        if(map==null)return new HashMap();
        map=(HashMap)map.get(""+type);
        if(map==null)return new HashMap();
        return map;
    }
    
    /**
     * @param type
     * @return  HashMap Resources*/
    public HashMap getResourcesBaseOfType(String topicmap, int type, String typeMap)
    {
        HashMap map=(HashMap)resourcesbase.get(topicmap);
        if(map==null)return new HashMap();
        String typekey=""+type;
        if(!topicmap.equals(typeMap))typekey+="|"+typeMap;
        map=(HashMap)map.get(typekey);
        if(map==null)return new HashMap();
        return map;
    }
    
    public WBResource getResourceCached(WBResource res, HttpServletRequest request, WBParamRequest paramsRequest)
    {
        cache.incResourceHits();
        if (paramsRequest.getResourceBase().isCached())
        {
            return cache.getResource(res, request, paramsRequest);
        }else
        {
            return res;
        }
    }
    
    

    /**
     * @param user
     * @param topic
     * @param params
     * @param tpl
     * @return  */
    public Iterator getContents(WBUser user, com.infotec.topicmaps.Topic topic, HashMap params, Template tpl)
    {
        Date today = new Date();
        //today = new Date(today.getYear(), today.getMonth(), today.getDate());
        TreeSet ret = new TreeSet(new WBPriorityComparator(true));
        Iterator it = topic.getContents();
        while (it.hasNext())
        {
            com.infotec.topicmaps.Occurrence occ = (com.infotec.topicmaps.Occurrence) it.next();
            //System.out.println("Occ:"+occ.getResourceData());
            long recid = Long.parseLong(occ.getResourceData());
            WBResource res = getResource(topic.getMap().getId(), recid);
            if (res != null)
            {
                Resource base = res.getResourceBase();
                if (checkResource(base, user, 0, null, 0, today, topic))
                {
                    //if (base.isCached())
                    //    ret.add(cache.getResource(res));
                    //else
                    ret.add(res);
                }
            } else
            {
                AFUtils.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_getContents_msg1") + " " + occ.getId() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_getContents_msg2") + " " + topic.getId() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_getContents_msg3") + " " + recid + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_getContents_msg4"), true);
            }
        }
        return ret.iterator();
    }


    /**
     * @param type
     * @param user
     * @param topic
     * @param params
     * @param tpl
     * @return  */
    public Iterator getResources(String type, WBUser user, com.infotec.topicmaps.Topic topic, HashMap params, Template tpl)
    {
        //System.out.print("getResource:");
        //System.out.print(" topic:"+topic.getDisplayName());
        //System.out.print(" name:"+params.get("name"));
        //System.out.print(" template:"+tpl.getId());
        //System.out.print(" templateMap:"+tpl.getTopicMapId());
        //System.out.print(" type:"+type);
        //System.out.print(" type:"+params.get("type"));
        //System.out.println(" stype:"+params.get("stype"));

        Date today = new Date();
        //today = new Date(today.getYear(), today.getMonth(), today.getDate());

        
        //separar tipo de recurso
        int itype=0;
        String typemap=tpl.getTopicMapId();;
        if(type != null)
        {
            try
            {
                if(type.indexOf("|")>-1)
                {
                    itype=Integer.parseInt(type.substring(0,type.indexOf('|')));
                    typemap=type.substring(type.indexOf('|')+1);
                }else
                {
                    itype=Integer.parseInt(type);
                }
            }catch(Exception e){AFUtils.log(e);}
        }        
        //System.out.println("itype:"+itype+" typemap:"+typemap);
        
        //separar subtypo de recurso
        int stype=0;
        String stypemap=tpl.getTopicMapId();
        String sstype = (String)params.get("stype");
        if(sstype != null)
        {
            try
            {
                if(sstype.indexOf("|")>-1)
                {
                    stype=Integer.parseInt(sstype.substring(0,sstype.indexOf('|')));
                    stypemap=sstype.substring(sstype.indexOf('|')+1);
                }else
                {
                    stype=Integer.parseInt(sstype);
                }
            }catch(Exception e){AFUtils.log(e);}
        }
        
        

        String name = (String) params.get("name");

        int camp = 0;
        if (name != null)
        {
            camp = CampMgr.getInstance().getCamp(topic, tpl, name.toLowerCase());
        }
//        System.out.println("camp-->"+name+":"+camp);
        //OK_TODO: revisar recursos de global
        TreeSet ret = new TreeSet(new WBPriorityComparator());
        
        ArrayList tp=null;
        if(topic.getMap().getId().equals(tpl.getTopicMapId()))
        {
            HashMap map=(HashMap)resourcesbase.get(topic.getMap().getId());
            if(map!=null)
            {
                HashMap aux=((HashMap) map.get(type));
                if(aux!=null)tp=new ArrayList(aux.values());
            }
        }else
        {
            //System.out.println("map:"+map);
            HashMap map=(HashMap)resourcesbase.get(topic.getMap().getId());
            if(map!=null)
            {
                HashMap aux=((HashMap) map.get(""+itype+"|"+tpl.getTopicMapId()));
                if(aux!=null)tp=new ArrayList(aux.values());
            }
            
            map=(HashMap)resourcesbase.get(tpl.getTopicMapId());
            if(map!=null)
            {
                HashMap aux=((HashMap) map.get(""+itype));
                if(aux!=null)
                {
                    if(tp==null)tp=new ArrayList(aux.values());
                    else tp.addAll(aux.values());
                }
            }            
        }
        
        if(type.endsWith(TopicMgr.TM_GLOBAL))
        {
            HashMap mapg=(HashMap)resourcesbase.get(TopicMgr.TM_GLOBAL);
            if(mapg!=null)
            {
                HashMap aux=((HashMap)mapg.get(type.substring(0,type.indexOf('|'))));
                if(aux!=null)
                {
                    if(tp==null)tp=new ArrayList(aux.values());
                    else tp.addAll(aux.values());
                }
            }
        }

        if (tp==null || tp.size()==0) return ret.iterator();
        Iterator en = tp.iterator();
        while (en.hasNext())
        {
            com.infotec.wb.core.Resource base = (Resource) en.next();
            //System.out.println("rec:"+base.getId()+" topicmap="+base.getTopicMapId() +" stype="+stype+" stypemap="+stypemap);
            if (checkResource(base, user, stype, stypemap, camp, today, topic))
            {
                WBResource wbr=getResource(base.getTopicMapId(),base.getId());
                //System.out.println("checkResource ok:"+wbr.getResourceBase().getId());
                if(wbr!=null)
                {
                    //if (base.isCached())
                    //{
                    //    ret.add(cache.getResource(wbr));
                    //}
                    //else
                    //{
                        ret.add(wbr);
                    //}
                }
            }
        }
//        System.out.println("size:"+ret.size());
        return ret.iterator();
    }

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
        //System.out.println("checkResource:"+base.getId()+" tmid:"+base.getTopicMapId()+" stype:"+stype+" stypemap:"+stypemap+" camp:"+camp+" topic:"+topic.getDisplayName());
        RuleMgr ruleMgr = RuleMgr.getInstance();
        if(stypemap==null)stypemap=base.getTopicMapId();
        boolean passrules = true;
        
        //System.out.println(""+base.getActive()+" == 1 && "+base.getDeleted()+" == 0");
        //System.out.println("&& (("+base.getSubType()+" == "+stype+" && ("+base.getSubType()+"==0 ||"+base.getSubTypeMap()+".equals("+stypemap+"))))");
        //System.out.println("&& ("+camp+" < 3 || "+base.getCamp()+" == "+camp+")");
        //System.out.println("&& ("+base.getMaxViews()+" == -1 || "+base.getMaxViews()+" > "+base.getViews()+")");
        //System.out.println("&& ("+base.getCamp()+" == 0 || "+DBCatalogs.getInstance().getCamp(base.getTopicMapId(),base.getCamp()).getActive()+" == 1)");
        
        if (base.getActive() == 1 && base.getDeleted() == 0
                //&& (stype == 0 || (base.getSubType() == stype && base.getSubTypeMap().equals(stypemap)))
                && ((base.getSubType() == stype && (base.getSubType()==0 ||base.getSubTypeMap().equals(stypemap))))
                && (camp < 3 || base.getCamp() == camp)
                && (base.getMaxViews() == -1 || base.getMaxViews() > base.getViews())
                && (base.getCamp() == 0 || DBCatalogs.getInstance().getCamp(base.getTopicMapId(),base.getCamp()).getActive() == 1)
        )
        {

            if (!base.evalFilterMap(topic)) return false;

            passrules=base.haveAccess(user);
            
            if (passrules == true && !intereval.eval(today, base)) passrules = false;
            //System.out.println("passrules:"+passrules);
            if (passrules)
            {
                base.refreshRandPriority();
                //System.out.println("priority:"+base.getRandPriority());
            }
        } else
            passrules = false;
        //System.out.println("checkResource:"+passrules);
        return passrules;
    }

    /**
     * @param id
     * @throws WBException
     * @return  */
    public WBResource getResource(String topicmap, long id)
    {
        HashMap map=(HashMap)resources.get(topicmap);
        if(map==null) return null;
        WBResource rec = (WBResource) map.get(new Long(id));
        return rec;
    }
    
    /**
     * @param id
     * @throws WBException
     * @return  */
    public WBResource getOldResource(long id)
    {
        WBResource rec = (WBResource) oldresources.get(new Long(id));
        return rec;
    }
    

    public void destroy()
    {
        instance=null;
        AFUtils.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_ResourceMgr_destroy"));
    }

    public void refresh()
    {
    }

    /** Avisa al observador de que se ha producido un cambio.
     * @param s
     * @param recresource  */
    public void sendDBNotify(String s, Object recresource)
    {
        RecResource rec = (RecResource) recresource;
        if(rec==null)return;
        
        String typekey=""+rec.getType();
        if(!rec.getTypeMap().equals(rec.getTopicMapId()))typekey+="|"+rec.getTypeMap();
        
        HashMap basemap=(HashMap)resourcesbase.get(rec.getTopicMapId());
        HashMap map=(HashMap)resources.get(rec.getTopicMapId());
        if(map==null)
        {
            map=new HashMap();
            resources.put(rec.getTopicMapId(),map);
            basemap=new HashMap();
            resourcesbase.put(rec.getTopicMapId(),basemap);
        }
        if (s.equals("remove"))
        {
            map.remove(new Long(rec.getId()));
            
            ((HashMap) basemap.get(typekey)).remove(new Long(rec.getId()));
            cache.removeResource(rec);
        } else if (s.equals("create"))
        {
            createWBResource(rec);
        } else if (s.equals("update") || s.equals("load"))
        {
            cache.removeResource(rec);
            WBResource res = (WBResource) map.get(new Long(rec.getId()));
            if (res != null)
            {
                res.getResourceBase().setRecResource(rec);
                try
                {
                    res.setResourceBase(res.getResourceBase());
                }catch(Exception e){AFUtils.log(e);}
            } else
            {
                createWBResource(rec);
            }
        }
    }
    
    /** Valida carga de Recursos de versiones anteriore
     *
     */
    public Object convertOldWBResource(Object obj)
    {
        return convertOldWBResource(obj, null);
    }

    /** Valida carga de Recursos de versiones anteriore
     *  Si el recursos es de una version anterior 
     *  asigna setWb2Resource(true) del recursos
     */
    public Object convertOldWBResource(Object obj, Resource base)
    {
        Object aux = null;
        if (obj instanceof WBResource)
        {
            aux = obj;
        } else
        {
            try
            {
                Class wbresource = Class.forName("infotec.wb2.lib.WBResource");
                //System.out.println("convert:"+wbresource+" -> "+wbresource.isInstance(obj));
                if (wbresource.isInstance(obj))
                {
                    if(base!=null)base.setWb2Resource(true);
                    Class wbreswrapper = Class.forName("infotec.wb2.lib.WBResourceWrapperNew");
                    Constructor cons = wbreswrapper.getConstructor(new Class[]{wbresource});
                    aux = cons.newInstance(new Object[]{obj});
                }
            } catch (Exception e)
            {
                AFUtils.log(e, "");
            }
        }
        return aux;
    }

    public Class createWBResourceClass(String clsname) throws ClassNotFoundException
    {
        return createWBResourceClass(clsname, false);
    }

    public Class createWBResourceClass(String clsname, boolean replaceLoader) throws ClassNotFoundException
    {
        Class cls = null;
        if (!ResourceMgr.getInstance().isResurceReloader())
        {
            cls = Class.forName(clsname);
        } else
        {
            ClassLoader cl = null;
            if (replaceLoader)
            {
                resourceLoaders.remove(clsname);
                //recarga bundle (XML) del recurso (si existe).
                GenericAdmResource.reload(clsname);
            } else
            {
                cl = (ClassLoader) resourceLoaders.get(clsname);
            }
            if (cl == null)
            {
                cl = new WBClassLoader(this.getClass().getClassLoader());
                ((WBClassLoader)cl).setFilterClass(getClassBase(clsname));
                resourceLoaders.put(clsname, cl);
            }
            cls = cl.loadClass(clsname);
        }
        //System.out.println("createWBResourceClass:"+clsname+"->"+cls);
        return cls;
    }
    
    private String getClassBase(String classname)
    {
        String ret=null;
        int i=classname.lastIndexOf('.');
        if(i>0)
        {
            ret=classname.substring(0,i);
        }
        return ret;
    }


    public WBResource createWBResource(RecResource rec)
    {
        WBResource obj = null;
        try
        {
            com.infotec.appfw.util.AFUtils.getInstance().debug("Loading Res:" + rec.getId(), 3);
            Resource base = new Resource(rec);
            String clsname = base.getResourceType().getObjclass();
            Class cls = createWBResourceClass(clsname);

            obj = (WBResource) convertOldWBResource(cls.newInstance(),base);
            if (obj != null)
            {
                obj.setResourceBase(base);
                obj.init();
                
                HashMap basemap=(HashMap)resourcesbase.get(rec.getTopicMapId());
                HashMap map=(HashMap)resources.get(rec.getTopicMapId());
                if(map==null)
                {
                    map=new HashMap();
                    resources.put(rec.getTopicMapId(), map);
                    
                    basemap=new HashMap();
                    resourcesbase.put(rec.getTopicMapId(), basemap);
                }
                map.put(new Long(base.getId()), obj);
                if(base.isWb2Resource())oldresources.put(new Long(base.getId()), obj);
                
                String typekey=""+rec.getType();
                if(!rec.getTypeMap().equals(rec.getTopicMapId()))typekey+="|"+rec.getTypeMap();
                HashMap tp = (HashMap) basemap.get(typekey);
                if (tp == null)
                {
                    tp = new HashMap();
                    basemap.put(typekey, tp);
                }
                tp.put(new Long(base.getId()), base);
//                System.out.println("base.getId():"+base.getId()+" tmid:"+base.getTopicMapId()+" typekey:"+typekey);
            }
        } catch (Throwable e)
        {
            if(rec!=null)AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_createWBResource")+" "+rec.getId()+"-"+rec.getTopicMapId(), true);
            else AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_createWBResource")+" (rec==null)", true);
        }
        //System.out.println("createWBResource:"+obj);
        return obj;
    }

    public boolean reloadResourceClass(String tmap,int type)
    {
        //System.out.println("reloadResourceClass smap:"+smap+" type:"+type);
        try
        {
            String clsname = DBResourceType.getInstance().getResourceType(tmap,type).getObjclass();
            Class cls = createWBResourceClass(clsname, true);

            Iterator itm=com.infotec.topicmaps.bean.TopicMgr.getInstance().getTopicMaps();
            while(itm.hasNext())
            {
                TopicMap tm=(TopicMap)itm.next();
                String smap=tm.getId();
                HashMap bmap = (HashMap)resourcesbase.get(smap);
                HashMap map=(HashMap)resources.get(smap);
                if(map==null)
                {
                    map=new HashMap();
                    resources.put(smap, map);

                    bmap=new HashMap();
                    resourcesbase.put(smap, bmap);
                }

                HashMap resmap = new HashMap(map);
                Iterator it = resmap.values().iterator();
                while (it.hasNext())
                {
                    WBResource res = (WBResource) it.next();
                    if (res.getResourceBase().getType() == type && res.getResourceBase().getTypeMap().equals(tmap)) it.remove();
                }

                String typekey=""+type;
                if(!tmap.equals(smap))typekey+="|"+tmap;
                HashMap basemap = (HashMap) bmap.get(typekey);
                if (basemap!=null)
                {
                    it = basemap.values().iterator();
                    while (it.hasNext())
                    {
                        Resource base = (Resource) it.next();

                        WBResource obj = (WBResource) convertOldWBResource(cls.newInstance(),base);
                        if (obj != null)
                        {
                            obj.setResourceBase(base);
                            obj.init();
                            resmap.put(new Long(base.getId()), obj);
                            if(base.isWb2Resource())oldresources.put(new Long(base.getId()), obj);
                        }
                    }
                }
                resources.put(smap,resmap);
            }
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_ResourceMgr_reloadResourceClass") + type, true);
            return false;
        }
        ;
        return true;
    }

    /** Getter for property resReloader.
     * @return Value of property resReloader.
     *
     */
    public boolean isResurceReloader()
    {
        return resReloader;
    }

    /** Setter for property resReloader.
     * @param resReloader New value of property resReloader.
     *
     */
    public void setResourceReloader(boolean resReloader)
    {
        this.resReloader = resReloader;
    }

    /** Getter for property timeLock.
     * @return Value of property timeLock.
     *
     */
    public WBResourceTraceMgr getResourceTraceMgr()
    {
        return tracer;
    }
    
    /** Getter for property timeLock.
     * @return Value of property timeLock.
     *
     */
    public WBResourceCachedMgr getResourceCacheMgr()
    {
        return cache;
    }    

    /** Getter for property resourceLoaders.
     * @return Value of property resourceLoaders.
     *
     */
    public java.util.HashMap getResourceLoaders()
    {
        return resourceLoaders;
    }

    /** Setter for property resourceLoaders.
     * @param resourceLoaders New value of property resourceLoaders.
     *
     */
    public void setResourceLoaders(java.util.HashMap resourceLoaders)
    {
        this.resourceLoaders = resourceLoaders;
    }
    
    
//    public void invokeResourceRender(String itdm, long rid)
//    {
//        //TODO:Falta envio de log de accesos
//        WBResource currResource = getResource(idtm,rid);
//        //WBResponse resp=new WBResponse(response);
//        WBParamRequestImp resParams = new WBParamRequestImp(request,currResource.getResourceBase(),topic,user);
//        //resParams.setArguments(args);
//        if(act!=null)resParams.setAction(act);
//        resParams.setCallMethod(mto);
//        if(mdo!=null)resParams.setMode(mdo);
//        if(wst!=null)resParams.setWindowState(wst);
//        if(vtopic!=null)
//        {
//            resParams.setVirtualTopic(vtopic);
//        }
//        ResourceMgr.getInstance().getResourceTraceMgr().renderTraced(currResource, request, response, resParams);
//        return;    
//    }

}
