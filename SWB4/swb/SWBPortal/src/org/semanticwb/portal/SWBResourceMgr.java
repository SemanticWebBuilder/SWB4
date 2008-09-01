/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.SWBClassLoader;
import org.semanticwb.portal.resources.SWBParamRequest;
import org.semanticwb.portal.resources.SWBResource;
import org.semanticwb.portal.resources.SWBResourceCachedMgr;
import org.semanticwb.portal.resources.SWBResourceTraceMgr;

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
            String clsname = portlet.getPortletClass().getPortletClassName();
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
