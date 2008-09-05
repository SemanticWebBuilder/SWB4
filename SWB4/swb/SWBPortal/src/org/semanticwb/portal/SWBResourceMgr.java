/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletRef;
import org.semanticwb.model.PortletSubType;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceCachedMgr;
import org.semanticwb.portal.api.SWBResourceTraceMgr;
import org.semanticwb.portal.util.SWBPriorityComparator;

/**
 *
 * @author Jei
 */
public class SWBResourceMgr 
{
    private static Logger log = SWBUtils.getLogger(SWBResourceMgr.class);
    
    private HashMap<String,HashMap> resources;                  //WBResource
    private HashMap<String,ClassLoader> resourceLoaders;                                //Resources ClassLoaders
    private boolean resReloader = false;

//    private SWBIntervalEvaluation intereval;
    private SWBResourceCachedMgr cache;
    private SWBResourceTraceMgr tracer;
    
    public SWBResourceMgr()
    {
        log.event("Initializing SWBResourceMgr...");
    }
    
    
    public void init()
    {
        resources=new HashMap();
        resourceLoaders = new HashMap();     
        int time = 100;
        try
        {
            time = Integer.parseInt((String) SWBPlatform.getEnv("swb/resourceCached","100"));
        } catch (Exception e)
        {
            log.error("Error getting swb/resourceCached variable...",e);
        }        
        cache = new SWBResourceCachedMgr(time);
        tracer = new SWBResourceTraceMgr();        
    }
    
    public SWBResource getResource(String model, String id)
    {
        HashMap<String,SWBResource> map=(HashMap)resources.get(model);
        if(map==null)
        {
            map=new HashMap<String,SWBResource>();
            resources.put(model, map);
        }
        SWBResource res=map.get(id);
        if(res==null)
        {
            Portlet portlet=SWBContext.getWebSite(model).getPortlet(id);
            if(portlet!=null)
            {
                res=createSWBResource(portlet);
                map.put(id, res);
            }
        }
        return res;
    }
    
    public SWBResource getResourceCached(SWBResource res, HttpServletRequest request, SWBParamRequest paramsRequest)
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
    public Iterator getContents(User user, WebPage topic, HashMap params, Template tpl)
    {
        Date today = new Date();
        //today = new Date(today.getYear(), today.getMonth(), today.getDate());
        TreeSet ret = new TreeSet(new SWBPriorityComparator(true));
        Iterator<PortletRef> it = topic.listPortletRefs();
        while (it.hasNext())
        {
            PortletRef ref = it.next();
            //System.out.println("Occ:"+occ.getResourceData());
            Portlet portlet=ref.getPortlet();
            SWBResource res = getResource(portlet.getWebSiteId(), portlet.getId());
            if (res != null)
            {
                Portlet base = res.getResourceBase();
                //TODO:CheckResource
                //if (checkResource(base, user, 0, null, 0, today, topic))
                {
                    ret.add(res);
                }
            } else
            {
                log.warn("Error getContents:"+topic.getURI()+" user:"+user.getId());
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
    public Iterator getResources(PortletType type, PortletSubType stype, User user, WebPage topic, HashMap params, Template tpl)
    {
        TreeSet ret = new TreeSet(new SWBPriorityComparator());
        
        System.out.print("getResource:");
        System.out.print(" topic:"+topic.getTitle());
        System.out.print(" name:"+params.get("name"));
        System.out.print(" template:"+tpl.getId());
        System.out.print(" templateMap:"+tpl.getWebSiteId());
        System.out.print(" type:"+type);
        System.out.print(" stype:"+stype);
        System.out.println(" params:"+params);
        
        if(type!=null)
        {
            //TODO:check stype
            Iterator<Portlet> it=type.listPortlets();
            while(it.hasNext())
            {
                Portlet base=it.next();

                //if (checkResource(base, user, stype, stypemap, camp, today, topic))
                {
                    SWBResource wbr=getResource(base.getWebSiteId(),base.getId());
                    //System.out.println("checkResource ok:"+wbr.getResourceBase().getId());
                    if(wbr!=null)
                    {
                        ret.add(wbr);
                    }
                }
            }
        }
        
        
//        Date today = new Date();
        //today = new Date(today.getYear(), today.getMonth(), today.getDate());

        
        
        
//        //separar tipo de recurso
//        int itype=0;
//        String typemap=tpl.getWebSiteId();
//        if(type != null)
//        {
//            try
//            {
//                if(type.indexOf("|")>-1)
//                {
//                    itype=Integer.parseInt(type.substring(0,type.indexOf('|')));
//                    typemap=type.substring(type.indexOf('|')+1);
//                }else
//                {
//                    itype=Integer.parseInt(type);
//                }
//            }catch(Exception e){log.error(e);}
//        }        
        //System.out.println("itype:"+itype+" typemap:"+typemap);
        
        //separar subtypo de recurso
//        int stype=0;
//        String stypemap=tpl.getWebSiteId();
//        String sstype = (String)params.get("stype");
//        if(sstype != null)
//        {
//            try
//            {
//                if(sstype.indexOf("|")>-1)
//                {
//                    stype=Integer.parseInt(sstype.substring(0,sstype.indexOf('|')));
//                    stypemap=sstype.substring(sstype.indexOf('|')+1);
//                }else
//                {
//                    stype=Integer.parseInt(sstype);
//                }
//            }catch(Exception e){log.error(e);}
//        }
//        
//        
//
//        String name = (String) params.get("name");
//
//        int camp = 0;
        //TODO:Implementar
//        if (name != null)
//        {
//            camp = CampMgr.getInstance().getCamp(topic, tpl, name.toLowerCase());
//        }
//        System.out.println("camp-->"+name+":"+camp);
        //OK_TODO: revisar recursos de global
//        
//        ArrayList tp=null;
//        if(topic.getWebSiteId().equals(tpl.getWebSiteId()))
//        {
//            HashMap map=(HashMap)resourcesbase.get(topic.getWebSiteId());
//            if(map!=null)
//            {
//                HashMap aux=((HashMap) map.get(type));
//                if(aux!=null)tp=new ArrayList(aux.values());
//            }
//        }else
//        {
//            //System.out.println("map:"+map);
//            HashMap map=(HashMap)resourcesbase.get(topic.getMap().getId());
//            if(map!=null)
//            {
//                HashMap aux=((HashMap) map.get(""+itype+"|"+tpl.getTopicMapId()));
//                if(aux!=null)tp=new ArrayList(aux.values());
//            }
//            
//            map=(HashMap)resourcesbase.get(tpl.getTopicMapId());
//            if(map!=null)
//            {
//                HashMap aux=((HashMap) map.get(""+itype));
//                if(aux!=null)
//                {
//                    if(tp==null)tp=new ArrayList(aux.values());
//                    else tp.addAll(aux.values());
//                }
//            }            
//        }
//        
//        if(type.endsWith(TopicMgr.TM_GLOBAL))
//        {
//            HashMap mapg=(HashMap)resourcesbase.get(TopicMgr.TM_GLOBAL);
//            if(mapg!=null)
//            {
//                HashMap aux=((HashMap)mapg.get(type.substring(0,type.indexOf('|'))));
//                if(aux!=null)
//                {
//                    if(tp==null)tp=new ArrayList(aux.values());
//                    else tp.addAll(aux.values());
//                }
//            }
//        }
//
//        if (tp==null || tp.size()==0) return ret.iterator();
//        Iterator en = tp.iterator();
//        while (en.hasNext())
//        {
//            com.infotec.wb.core.Resource base = (Resource) en.next();
//            //System.out.println("rec:"+base.getId()+" topicmap="+base.getTopicMapId() +" stype="+stype+" stypemap="+stypemap);
//            if (checkResource(base, user, stype, stypemap, camp, today, topic))
//            {
//                WBResource wbr=getResource(base.getTopicMapId(),base.getId());
//                //System.out.println("checkResource ok:"+wbr.getResourceBase().getId());
//                if(wbr!=null)
//                {
//                    //if (base.isCached())
//                    //{
//                    //    ret.add(cache.getResource(wbr));
//                    //}
//                    //else
//                    //{
//                        ret.add(wbr);
//                    //}
//                }
//            }
//        }
//        System.out.println("size:"+ret.size());
        return ret.iterator();
    }

//    /**
//     * @param base
//     * @param user
//     * @param stype
//     * @param camp
//     * @param today
//     * @param topic
//     * @return  */
//    public boolean checkResource(Resource base, WBUser user, int stype,String stypemap, int camp, Date today, com.infotec.topicmaps.Topic topic)
//    {
//        //System.out.println("checkResource:"+base.getId()+" tmid:"+base.getTopicMapId()+" stype:"+stype+" stypemap:"+stypemap+" camp:"+camp+" topic:"+topic.getDisplayName());
//        RuleMgr ruleMgr = RuleMgr.getInstance();
//        if(stypemap==null)stypemap=base.getTopicMapId();
//        boolean passrules = true;
//        
//        //System.out.println(""+base.getActive()+" == 1 && "+base.getDeleted()+" == 0");
//        //System.out.println("&& (("+base.getSubType()+" == "+stype+" && ("+base.getSubType()+"==0 ||"+base.getSubTypeMap()+".equals("+stypemap+"))))");
//        //System.out.println("&& ("+camp+" < 3 || "+base.getCamp()+" == "+camp+")");
//        //System.out.println("&& ("+base.getMaxViews()+" == -1 || "+base.getMaxViews()+" > "+base.getViews()+")");
//        //System.out.println("&& ("+base.getCamp()+" == 0 || "+DBCatalogs.getInstance().getCamp(base.getTopicMapId(),base.getCamp()).getActive()+" == 1)");
//        
//        if (base.getActive() == 1 && base.getDeleted() == 0
//                //&& (stype == 0 || (base.getSubType() == stype && base.getSubTypeMap().equals(stypemap)))
//                && ((base.getSubType() == stype && (base.getSubType()==0 ||base.getSubTypeMap().equals(stypemap))))
//                && (camp < 3 || base.getCamp() == camp)
//                && (base.getMaxViews() == -1 || base.getMaxViews() > base.getViews())
//                && (base.getCamp() == 0 || DBCatalogs.getInstance().getCamp(base.getTopicMapId(),base.getCamp()).getActive() == 1)
//        )
//        {
//
//            if (!base.evalFilterMap(topic)) return false;
//
//            passrules=base.haveAccess(user);
//            
//            if (passrules == true && !intereval.eval(today, base)) passrules = false;
//            //System.out.println("passrules:"+passrules);
//            if (passrules)
//            {
//                base.refreshRandPriority();
//                //System.out.println("priority:"+base.getRandPriority());
//            }
//        } else
//            passrules = false;
//        //System.out.println("checkResource:"+passrules);
//        return passrules;
//    }    
    
    
//    /** Valida carga de Recursos de versiones anteriore
//     *
//     */
//    public Object convertOldWBResource(Object obj)
//    {
//        return convertOldWBResource(obj, null);
//    }

//    /** Valida carga de Recursos de versiones anteriore
//     *  Si el recursos es de una version anterior 
//     *  asigna setWb2Resource(true) del recursos
//     */
//    public Object convertOldWBResource(Object obj, Resource base)
//    {
//        Object aux = null;
//        if (obj instanceof WBResource)
//        {
//            aux = obj;
//        } else
//        {
//            try
//            {
//                Class wbresource = Class.forName("infotec.wb2.lib.WBResource");
//                //System.out.println("convert:"+wbresource+" -> "+wbresource.isInstance(obj));
//                if (wbresource.isInstance(obj))
//                {
//                    if(base!=null)base.setWb2Resource(true);
//                    Class wbreswrapper = Class.forName("infotec.wb2.lib.WBResourceWrapperNew");
//                    Constructor cons = wbreswrapper.getConstructor(new Class[]{wbresource});
//                    aux = cons.newInstance(new Object[]{obj});
//                }
//            } catch (Exception e)
//            {
//                AFUtils.log(e, "");
//            }
//        }
//        return aux;
//    }    
    
    public Class createSWBResourceClass(String clsname) throws ClassNotFoundException
    {
        return createSWBResourceClass(clsname, false);
    }

    public Class createSWBResourceClass(String clsname, boolean replaceLoader) throws ClassNotFoundException
    {
        Class cls = null;
        if (!resReloader)
        {
            cls = Class.forName(clsname);
        } else
        {
            ClassLoader cl = null;
            if (replaceLoader)
            {
                resourceLoaders.remove(clsname);
                //recarga bundle (XML) del recurso (si existe).
                //TODO:Revisar recarga
                //GenericAdmResource.reload(clsname);
            } else
            {
                cl = (ClassLoader) resourceLoaders.get(clsname);
            }
            if (cl == null)
            {
                cl = new SWBClassLoader(this.getClass().getClassLoader());
                ((SWBClassLoader)cl).setFilterClass(getClassBase(clsname));
                resourceLoaders.put(clsname, cl);
            }
            cls = cl.loadClass(clsname);
        }
        //System.out.println("createWBResourceClass:"+clsname+"->"+cls);
        return cls;
    }    
    
    public SWBResource createSWBResource(Portlet portlet)
    {
        SWBResource obj = null;
        try
        {
            log.debug("Loading Portlet:" + portlet.getURI());
            String clsname = portlet.getPortletType().getPortletClassName();
            Class cls = createSWBResourceClass(clsname);
//            obj = (WBResource) convertOldWBResource(cls.newInstance(),base);
            obj=(SWBResource)cls.newInstance();
            if (obj != null)
            {
                obj.setResourceBase(portlet);
                obj.init();
                
                //HashMap basemap=(HashMap)resourcesbase.get(portlet.getTopicMapId());
                HashMap<String,SWBResource> map=(HashMap)resources.get(portlet.getWebSiteId());
                if(map==null)
                {
                    map=new HashMap();
                    resources.put(portlet.getWebSiteId(), map);
                    
                    //basemap=new HashMap();
                    //resourcesbase.put(portlet.getTopicMapId(), basemap);
                }
                map.put(portlet.getId(), obj);
                //if(base.isWb2Resource())oldresources.put(new Long(base.getId()), obj);
                
//                String typekey=""+portlet.getType();
//                if(!portlet.getTypeMap().equals(portlet.getTopicMapId()))typekey+="|"+portlet.getTypeMap();
//                HashMap tp = (HashMap) basemap.get(typekey);
//                if (tp == null)
//                {
//                    tp = new HashMap();
//                    basemap.put(typekey, tp);
//                }
//                tp.put(new Long(base.getId()), base);
//                System.out.println("base.getId():"+base.getId()+" tmid:"+base.getTopicMapId()+" typekey:"+typekey);
            }
        } catch (Throwable e)
        {
            if(portlet!=null)log.error("Error Creating SWBResource:"+" "+portlet.getWebSiteId()+"-"+portlet.getId(),e);
            else log.error("Error Creating SWBResource: portlet==null"+e);
        }
        //System.out.println("createWBResource:"+obj);
        return obj;
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
    public SWBResourceTraceMgr getResourceTraceMgr()
    {
        return tracer;
    }
    
    /** Getter for property timeLock.
     * @return Value of property timeLock.
     *
     */
    public SWBResourceCachedMgr getResourceCacheMgr()
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

}
