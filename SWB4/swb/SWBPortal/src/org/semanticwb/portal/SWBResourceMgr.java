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
package org.semanticwb.portal;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceCachedMgr;
import org.semanticwb.portal.api.SWBResourceTraceMgr;
import org.semanticwb.portal.util.SWBPriorityComparator;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBResourceMgr.
 * 
 * @author Jei
 */
public class SWBResourceMgr
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBResourceMgr.class);

    /** The resources. */
    private HashMap<String,SWBResource> resources;                  //WBResource
    
    /** The resource loaders. */
    private HashMap<String,ClassLoader> resourceLoaders;            //Resources ClassLoaders
    
    /** The res reloader. */
    private boolean resReloader = false;

//    private SWBIntervalEvaluation intereval;
    /** The cache. */
private SWBResourceCachedMgr cache;
    
    /** The tracer. */
    private SWBResourceTraceMgr tracer;

    /** The resource owls. */
    private Set resourceOWLS;               //hash de ontologias de recursos

    /**
     * Instantiates a new sWB resource mgr.
     */
    public SWBResourceMgr()
    {
        log.event("Initializing SWBResourceMgr...");
        resourceOWLS=new HashSet();
    }


    /**
     * Inits the.
     */
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
        try
        {
            resReloader = Boolean.parseBoolean(SWBPlatform.getEnv("swb/resReloader","false"));
        } catch (Exception e)
        {
            log.error("Error getting swb/resReloader variable...",e);
        }
        cache = new SWBResourceCachedMgr(time);
        tracer = new SWBResourceTraceMgr();
        loadResourceModels();
    }

    /**
     * Load resource models.
     */
    public void loadResourceModels()
    {
        Iterator<WebSite> it=SWBContext.listWebSites();
        while(it.hasNext())
        {
            WebSite site=it.next();
            Iterator<ResourceType> it2=site.listResourceTypes();
            while(it2.hasNext())
            {
                ResourceType type=it2.next();
                loadResourceTypeModel(type);
            }
        }
        SWBPlatform.getSemanticMgr().rebind();
    }

    /**
     * Load resource type model.
     * 
     * @param type the type
     */
    public void loadResourceTypeModel(ResourceType type)
    {
        String cls=type.getResourceOWL();
        if(cls!=null)
        {
//            SemanticModel model=SWBPlatform.getSemanticMgr().getModel(cls);
//            if(model==null)
            {
                try
                {
                    String path="/"+SWBUtils.TEXT.replaceAll(cls, ".", "/")+".owl";
                    
                    if(!resourceOWLS.contains(path))
                    {
                        resourceOWLS.add(path);
                        //System.out.println("path:"+path);
                        InputStream in=getClass().getResourceAsStream(path);
                        if(in!=null)
                        {
                            log.debug("Reading:"+path);
                            Model m=ModelFactory.createDefaultModel();
                            m.read(in, null);
                            SemanticModel model=new SemanticModel(cls,m);
                            if(SWBPlatform.getSemanticMgr().getSchema().addOWLModel(path,model,false))
                            {
                                log.event("ResourceOntology:"+path);
                            }
                            //SWBPlatform.getSemanticMgr().loadBaseVocabulary();
                            //System.out.println(cls);
                        }
                    }
                }catch(Exception e){log.error("Error loading OWL File:"+cls,e);}
            }
        }
    }

    /**
     * Gets the resource.
     * 
     * @param model the model
     * @param id the id
     * @return the resource
     */
    public SWBResource getResource(String model, String id)
    {
        //System.out.println("model:"+model+" id:"+id);
        Resource resource=SWBContext.getWebSite(model).getResource(id);
        return getResource(resource);
    }

    /**
     * Elimina el recurso del cache de recursos.
     * @param uri
     */
    public void removeResource(String uri)
    {
        resources.remove(uri);
    }

    /**
     * Gets the resource.
     * 
     * @param uri the uri
     * @return the resource
     */
    public SWBResource getResource(String uri)
    {
        SWBResource res=resources.get(uri);
        if(res==null)
        {
            synchronized(this)
            {
                res=resources.get(uri);
                if(res==null)
                {
                    Resource resource=(Resource)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uri);
                    res=getResource(resource);
                }
            }
        }
        return res;
    }

    /**
     * Gets the resource.
     * 
     * @param resource the resource
     * @return the resource
     */
    public SWBResource getResource(Resource resource)
    {
        SWBResource res=null;
        if(resource!=null)
        {
            String uri=resource.getURI();
            res=resources.get(uri);
            if(res==null)
            {
                synchronized(this)
                {
                    res=resources.get(uri);
                    if(res==null)
                    {
                        res=createSWBResource(resource);
                        if(res!=null)
                        {
                            resources.put(uri, res);
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * Gets the resource cached.
     * 
     * @param res the res
     * @param request the request
     * @param paramsRequest the params request
     * @return the resource cached
     */
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
     * Gets the contents.
     * 
     * @param user the user
     * @param topic the topic
     * @param params the params
     * @param tpl the tpl
     * @return the contents
     * @return
     */
    public Iterator<SWBResource> getContents(User user, WebPage topic, HashMap params, Template tpl)
    {
        Date today = new Date();
        //today = new Date(today.getYear(), today.getMonth(), today.getDate());
        TreeSet ret = new TreeSet(new SWBPriorityComparator(true));
        //Iterator<ResourceRef> it = topic.listResourceRefs();
        Iterator<Resource> it = topic.listResources();
        while (it.hasNext())
        {
            //ResourceRef ref = it.next();
            //System.out.println("Occ:"+occ.getResourceData());
            //Resource resource=ref.getResource();
            Resource resource=it.next();
            //System.out.println("Resource:"+resource);
            //SWBResource res = getResource(resource.getWebSiteId(), resource.getSId());
            SWBResource res = getResource(resource);
            if (res != null)
            {
                Resource base = res.getResourceBase();
                //System.out.println("base:"+base);
                //if(user.haveAccess(base))
                {
                    if(checkResource(base, user, 0, today, topic))
                    {
                        //System.out.println("Add:"+res);
                        ret.add(res);
                    }
                }
            } else
            {
                log.warn("Error getContents:"+topic.getURI()+" user:"+user.getId());
            }
        }
        return ret.iterator();
    }

    /**
     * Gets the resources.
     * 
     * @param type the type
     * @param stype the stype
     * @param user the user
     * @param topic the topic
     * @param params the params
     * @param tpl the tpl
     * @return the resources
     * @return
     */
    public Iterator getResources(ResourceType type, ResourceSubType stype, User user, WebPage topic, HashMap params, Template tpl)
    {
        Date today = new Date();
        TreeSet ret = new TreeSet(new SWBPriorityComparator());

        log.debug("getResource:");
        log.debug(" -->topic:"+topic.getTitle());
        log.debug("  -->name:"+params.get("name"));
        if(tpl!=null)
        {
            log.debug("  -->template:"+tpl.getId());
            log.debug("  -->templateMap:"+tpl.getWebSiteId());
        }
        log.debug("  -->type:"+type);
        log.debug("  -->stype:"+stype);
        log.debug("  -->params:"+params);

        if(type!=null)
        {
            Iterator<Resource> it;
            //TODO:check type
            if(stype!=null)
            {
                it=stype.listResources();
            }else
            {
                it=type.listResources();
            }
            while(it.hasNext())
            {
                Resource base=it.next();
                if(base!=null)
                {
                    int camp=0;
                    if(stype==null && base.getResourceSubType()!=null)continue; //verifica recursos sin subtipo
                    if(checkResource(base, user, camp, today, topic))
                    {
                        SWBResource wbr=getResource(base);
                        //System.out.println("checkResource ok:"+wbr.getResourceBase().getId());
                        if(wbr!=null)
                        {
                            ret.add(wbr);
                        }
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

    /**
     * Check resource.
     * 
     * @param base the base
     * @param user the user
     * @param camp the camp
     * @param today the today
     * @param topic the topic
     * @return true, if successful
     * @return
     */
    public boolean checkResource(Resource base, User user, int camp, Date today, WebPage topic)
    {
        boolean passrules = true;
        //System.out.println("checkResource:"+base.getId()+" tmid:"+base.getTopicMapId()+" stype:"+stype+" stypemap:"+stypemap+" camp:"+camp+" topic:"+topic.getDisplayName());
//        RuleMgr ruleMgr = RuleMgr.getInstance();

        //System.out.println(""+base.getActive()+" == 1 && "+base.getDeleted()+" == 0");
        //System.out.println("&& (("+base.getSubType()+" == "+stype+" && ("+base.getSubType()+"==0 ||"+base.getSubTypeMap()+".equals("+stypemap+"))))");
        //System.out.println("&& ("+camp+" < 3 || "+base.getCamp()+" == "+camp+")");
        //System.out.println("&& ("+base.getMaxViews()+" == -1 || "+base.getMaxViews()+" > "+base.getViews()+")");
        //System.out.println("&& ("+base.getCamp()+" == 0 || "+DBCatalogs.getInstance().getCamp(base.getTopicMapId(),base.getCamp()).getActive()+" == 1)");

        if (base.isValid()
                //&& base.getResourceSubType() == stype
                //&& (camp < 3 || base.getCamp() == camp)
                //&& (base.getMaxViews() == -1 || base.getMaxViews() > base.getViews())
                //&& (base.getCamp() == 0 || DBCatalogs.getInstance().getCamp(base.getTopicMapId(),base.getCamp()).getActive() == 1)
        )
        {
            //validar lenguage del recurso
            Language l=base.getLanguage();
            if(l!=null && !l.getId().equals(user.getLanguage()))return false;

            //validar dispositivo del recurso
            Device d=base.getDevice();
            if(d!=null)
            {
                if(!user.hasDevice(d))return false;
            }

            //Filter
            if(!base.evalFilterMap(topic)) return false;

            passrules=user.haveAccess(base);

            //TODO:calendar
            //if (passrules == true && !intereval.eval(today, base)) passrules = false;
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
     * Valida carga de Recursos de versiones anteriore
     * Si el recursos es de una version anterior
     * asigna setWb2Resource(true) del recursos.
     * 
     * @param obj the obj
     * @return the object
     */
    public Object convertOldWBResource(Object obj)  //Resource base
    {
        Object aux = null;
        if (obj instanceof SWBResource)
        {
            aux = obj;
        } else
        {
            if(SWBPlatform.getEnv("swb/oldResourcesSupport", "false").equals("true"))
            {
                try
                {
                    //Class wbresource = Class.forName("infotec.wb2.lib.WBResource");
                    Class wbresource = Class.forName("com.infotec.wb.lib.WBResource");
                    //System.out.println("convert:"+wbresource+" -> "+wbresource.isInstance(obj));
                    if (wbresource!=null && wbresource.isInstance(obj))
                    {
                        //if(base!=null)base.setWb2Resource(true);
                        Class wbreswrapper = Class.forName("org.semanticwb.api.WBResourceToSWBResourceWrapper");
                        Constructor cons = wbreswrapper.getConstructor(new Class[]{wbresource});
                        aux = cons.newInstance(new Object[]{obj});
                    }else
                    {
                        //version 2
                        wbresource = Class.forName("infotec.wb2.lib.WBResource");
                        if (wbresource!=null && wbresource.isInstance(obj))
                        {
                            //if(base!=null)base.setWb2Resource(true);
                            Class wbreswrapper = Class.forName("infotec.wb2.lib.WBResourceWrapperNew");
                            Constructor cons = wbreswrapper.getConstructor(new Class[]{wbresource});
                            aux = cons.newInstance(new Object[]{obj});

                            wbresource = Class.forName("com.infotec.wb.lib.WBResource");
                            wbreswrapper = Class.forName("org.semanticwb.api.WBResourceToSWBResourceWrapper");
                            cons = wbreswrapper.getConstructor(new Class[]{wbresource});
                            aux = cons.newInstance(new Object[]{aux});
                        }
                    }
                } catch (Exception e)
                {
                    log.error(e);
                }
            }else
            {
                log.warn("Old resource versions is not supported, for configure go to web.properties...");
            }
        }
        return aux;
    }

    /**
     * Creates the swb resource class.
     * 
     * @param clsname the clsname
     * @return the class
     * @throws ClassNotFoundException the class not found exception
     */
    public Class createSWBResourceClass(String clsname) throws ClassNotFoundException
    {
        return createSWBResourceClass(clsname, false);
    }

    /**
     * Creates the swb resource class.
     * 
     * @param clsname the clsname
     * @param replaceLoader the replace loader
     * @return the class
     * @throws ClassNotFoundException the class not found exception
     */
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
                GenericAdmResource.reload(clsname);
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

    /**
     * Creates the swb resource.
     * 
     * @param resource the resource
     * @return the sWB resource
     */
    public SWBResource createSWBResource(Resource resource)
    {
        SWBResource obj = null;
        try
        {
            log.debug("Loading Resource:" + resource.getURI());
            ResourceType type=resource.getResourceType();
            if(type!=null)
            {
                String clsname = type.getResourceClassName();
                Class cls = createSWBResourceClass(clsname);
                obj = (SWBResource) convertOldWBResource(cls.newInstance());
                //obj=(SWBResource)cls.newInstance();
                if (obj != null)
                {
                    obj.setResourceBase(resource);
                    if(obj.getResourceBase()==null)throw new SWBException(clsname+": if you override method setResourceBase, you have to invoke super.setResourceBase(base);");
                    obj.init();

                    //HashMap basemap=(HashMap)resourcesbase.get(resource.getTopicMapId());
                    //resources.put(resource.getURI(), obj);
                    //if(base.isWb2Resource())oldresources.put(new Long(base.getId()), obj);

    //                String typekey=""+resource.getType();
    //                if(!resource.getTypeMap().equals(resource.getTopicMapId()))typekey+="|"+resource.getTypeMap();
    //                HashMap tp = (HashMap) basemap.get(typekey);
    //                if (tp == null)
    //                {
    //                    tp = new HashMap();
    //                    basemap.put(typekey, tp);
    //                }
    //                tp.put(new Long(base.getId()), base);
    //                System.out.println("base.getId():"+base.getId()+" tmid:"+base.getTopicMapId()+" typekey:"+typekey);
                }
            }
        } catch (Throwable e)
        {
            if(resource!=null)log.error("Error Creating SWBResource:"+" "+resource.getWebSiteId()+"-"+resource.getId(),e);
            else log.error("Error Creating SWBResource: resource==null"+e);
        }
        //System.out.println("createWBResource:"+obj);
        return obj;
    }

    /** Getter for property resReloader.
     * @return Value of property resReloader.
     *
     */
    public boolean isResourceReloader()
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

    /**
     * Regresa el ClassLoader utilizado para cargar el tipo de recurso.
     * 
     * @param className nombre de la clase del recurso
     * @return ClassLoader del recurso
     */
    public ClassLoader getResourceLoader(String className)
    {
        return resourceLoaders.get(className);
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

    /**
     * Gets the class base.
     * 
     * @param classname the classname
     * @return the class base
     */
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
    
