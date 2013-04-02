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

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Date;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.Template;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticTSObserver;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.repository.Unstructured;
import org.semanticwb.repository.Workspace;
import org.semanticwb.triplestore.SWBTSUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBServiceMgr.
 * 
 * @author javier.solis
 */
public class SWBServiceMgr implements SemanticObserver, SemanticTSObserver, SWBObserver {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBServiceMgr.class);

    /** The lastobj. */
    private int lastobj;              //ultimo objeto modificado
    
    /** The lastthread. */
    private long lastthread;          //ultimo thread utilizado
    
    /** The lasttime. */
    private long lasttime;            //ultimo time utilizado

    /** The instanceid. */
    private String instanceid=null;

    /* (non-Javadoc)
     * @see org.semanticwb.platform.SemanticObserver#notify(org.semanticwb.platform.SemanticObject, java.lang.Object, java.lang.String)
     */
    /**
     * Notify.
     * 
     * @param obj the obj
     * @param prop the prop
     * @param lang the lang
     * @param action the action
     */
    public void notify(SemanticObject obj, Object prop, String lang, String action)
    {
        User usr = SWBContext.getSessionUser();
        if(obj!=null && obj.getModel().isDataModel())return;
        
        //log.trace("obj:" + obj + " prop:" + prop + " action:" + action + " " + usr);
        try
        {
            //System.out.println("obj:" + obj + " prop:" + prop + " action:" + action + " " + usr);

            if(obj.getModel().isTraceable())SWBPortal.getDBAdmLog().saveAdmLog(usr, obj, prop, action);
            
            //boolean iswebsite=false;
            //if(obj!=null && obj.getModel().getModelObject().instanceOf(WebSite.sclass))iswebsite=true;            

            SemanticClass cls = obj.getSemanticClass();
            if(cls!=null && cls.isSWB())
            {
                if (prop == null) //se modifico un objeto CREATE o REMOVE
                {
                    if (action.equals("CREATE")) //CREATE
                    {
                        updateTraceable(obj,usr);
                        if(obj.instanceOf(SWBModel.sclass))
                        {
                            java.io.File dir=new java.io.File(SWBPortal.getWorkPath() + "/models/"+ obj.getId());
                            dir.mkdirs();
                        }
                        if(obj.instanceOf(Workspace.sclass))
                        {
                            Workspace ws=(Workspace)obj.createGenericInstance();
                            if (ws.getRoot() == null)
                            {
                                Unstructured root = Unstructured.ClassMgr.createUnstructured(ws);
                                root.setName("jcr:root");
                                root.setPath("/");
                                ws.setRoot(root);
                            }
                        }
                        if(obj.instanceOf(WebSite.sclass))
                        {
                            java.io.File dir=new java.io.File(SWBPortal.getWorkPath() + "/models/"+ obj.getId() + "/Template");
                            dir.mkdirs();
                            dir=new java.io.File(SWBPortal.getWorkPath() + "/models/" + obj.getId() + "/Resource");
                            dir.mkdirs();
                        }
                        if(obj.instanceOf(Template.sclass))
                        {
                            String ctx=SWBPlatform.getContextPath();
                            Template tpl=(Template)obj.createGenericInstance();
                            VersionInfo vi=VersionInfo.ClassMgr.createVersionInfo(tpl.getWebSite());
                            vi.setVersionNumber(1);
                            vi.setVersionFile("template.html");
                            tpl.setActualVersion(vi);
                            tpl.setLastVersion(vi);
                            String txt=Template.DEFAUL_HTML;
                            try
                            {
                                SWBPortal.writeFileToWorkPath(tpl.getWorkPath()+"/1/"+"template.html", SWBUtils.IO.getStreamFromString(txt), usr);
                            }catch(Exception e){log.error(e);}
                        }
                    } else //REMOVES
                    {
                        if (obj.instanceOf(SWBModel.sclass)) //Removes website
                        {
                            SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + "/models/"+obj.getId());
                        } else
                        {
                            SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + obj.getWorkPath());
                        }

                        if(obj.instanceOf(ResourceType.sclass))
                        {
                            try
                            {
                                Class cls2=SWBPortal.getResourceMgr().createSWBResourceClass(obj.getProperty(ResourceType.swb_resourceClassName));
                                ((SWBResource)cls2.newInstance()).uninstall((ResourceType)obj.createGenericInstance());
                            }catch(Exception e){log.error(e);}
                        }
                        if(obj.instanceOf(Searchable.swb_Searchable))
                        {
                            SWBIndexer index=SWBPortal.getIndexMgr().getDefaultIndexer();
                            if(index!=null)
                            {
                                index.removeSearchable(obj.getURI());
                            }
                        }
                    }
                } else if (prop instanceof SemanticProperty)
                {
                    //System.out.println("obj2:"+obj+" "+Resource.sclass+"="+Resource.sclass+" prop:"+prop+"="+Resource.swb_resourceSubType);
                    if(prop.equals(ResourceType.swb_resourceClassName) && obj.instanceOf(ResourceType.sclass))
                    {
                        try
                        {
                            String clsname=obj.getProperty(ResourceType.swb_resourceClassName);
                            if(clsname!=null)
                            {
                                Class cls2=SWBPortal.getResourceMgr().createSWBResourceClass(clsname);
                                if(cls2!=null)
                                {
                                    SWBResource res=((SWBResource)SWBPortal.getResourceMgr().convertOldWBResource(cls2.newInstance()));
                                    if(res!=null)
                                    {
                                        res.install((ResourceType)obj.createGenericInstance());
                                    }
                                }
                            }
                        }catch(Exception e){log.error(e);}
                    }

                    if(prop.equals(Resource.swb_resourceSubType) && obj.instanceOf(Resource.sclass))
                    {
                        Resource res=(Resource)obj.createGenericInstance();
                        if(res.getResourceType()==null)
                        {
                            ResourceSubType sub=res.getResourceSubType();
                            //System.out.println("sub:"+sub);
                            if(sub!=null)
                            {
                                res.setResourceType(sub.getType());
                            }
                        }
                    }                    
                    if(obj.getModel().isTraceable())updateObject(obj,usr);
                }else
                {
                    //TODO: SemanticClass
                }
            }
        }finally
        {
            //no se envian a otras maquinas
//                if(!SWBPortal.isStandAlone())
//                {
//                    StringBuffer msg=new StringBuffer();
//                    msg.append("tri");
//                    msg.append("|");
//                    msg.append(instanceid);
//                    msg.append("|");
//                    msg.append(obj.getURI());
//                    msg.append("|");
//                    if(prop!=null)msg.append(prop);
//                    else msg.append("_");
//                    msg.append("|");
//                    if(lang!=null)msg.append(lang);
//                    else msg.append("_");
//                    msg.append("|");
//                    msg.append(action);
//                    SWBPortal.getMessageCenter().sendMessage(msg.toString());
//                }
        }
    }
    
    
    /**
     * Update object.
     * 
     * @param obj the obj
     * @param usr the usr
     */
    public void updateObject(SemanticObject obj, User usr)
    {
        try
        {
            //TODO:Una rapida aproximacion para no estar actualizando al modificar cada propiedad
            if(obj.hashCode()!=lastobj || Thread.currentThread().getId()!=lastthread || System.currentTimeMillis()>(lasttime+100))
            {
                //System.out.println("updateObject:"+obj+" user:"+usr);
                lastobj=obj.hashCode();
                lastthread=Thread.currentThread().getId();
                lasttime=System.currentTimeMillis();
                updateTraceable(obj,usr);

                if(!SWBPortal.isClient() && obj.instanceOf(Searchable.swb_Searchable))
                {
                    Searchable searchable=(Searchable)obj.createGenericInstance();
                    SWBIndexer indexer=SWBPortal.getIndexMgr().getDefaultIndexer();
                    if(indexer!=null && searchable!=null)
                    {
                        indexer.removeSearchable(searchable.getURI());
                        indexer.indexSerchable(searchable);
                    }
                }
            }
        }catch(Exception e){log.error(e);}
    }

    /**
     * Update traceable.
     * 
     * @param obj the obj
     * @param usr the usr
     */
    public void updateTraceable(SemanticObject obj, User usr)
    {
        //System.out.println("obj:" + obj + " " + usr.getName()+" "+usr.isRegistered());
        if (obj.instanceOf(Traceable.swb_Traceable))
        {
            Date date = new Date();
            if (obj.getDateProperty(Traceable.swb_created) == null)
            {
                obj.setDateProperty(Traceable.swb_created, date);
            }
            obj.setDateProperty(Traceable.swb_updated, date);
            if (usr != null && usr.isRegistered())
            {
                if (obj.getObjectProperty(Traceable.swb_creator) == null)
                {
                    obj.setObjectProperty(Traceable.swb_creator, usr.getSemanticObject());
                }
                obj.setObjectProperty(Traceable.swb_modifiedBy, usr.getSemanticObject());
            }
        }
    }

    /**
     * Inits the.
     */
    public void init() {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
        SWBPlatform.getSemanticMgr().registerTSObserver(this);
        if(!SWBPortal.isStandAlone())
        {
            instanceid=SWBPortal.getMessageCenter().getAddress()+"."+System.currentTimeMillis();
            //SWBPortal.getMessageCenter().registerObserver("tri", this);
            SWBPortal.getMessageCenter().registerObserver("rdf", this);
        }
    }

    /**
     * Send db notify.
     * 
     * @param s the s
     * @param obj the obj
     */
    public void sendDBNotify(String s, Object obj) 
    {
        //Remove cache
        StringTokenizer st=new StringTokenizer(obj.toString(),"|");
        String key=st.nextToken();
        String date=st.nextToken();
        String sid=st.nextToken();        
        //
        {
//            if(key.equals("tri"))
//            {
//                //System.out.println(obj);
//                String uri=st.nextToken();
//                String puri=st.nextToken();
//                if(puri.equals("_"))puri=null;
//                String lang=st.nextToken();
//                if(lang.equals("_"))lang=null;
//                String action=st.nextToken();
//                SWBPlatform.getSemanticMgr().processExternalChange(uri, puri, lang, action);
//            }else 
            if(key.equals("rdf"))
            {
                try
                {
                    String uri=st.nextToken();
                    String puri=st.nextToken();
                    if(puri.equals("_"))puri=null;
                    String node=st.nextToken();
                    if(node.equals("_"))
                    {
                        node=null;
                    }else
                    {
                        node=node.replace("{!}", "|");
                    }
                    String action=st.nextToken();
                    Node n=SWBTSUtil.string2Node(node,null);
                    if(!sid.equals(instanceid))
                    {
                        SWBPlatform.getSemanticMgr().processExternalChange(uri, puri, n, action);
                    }
                }catch(Exception e)
                {
                    log.error("Error processing:"+obj,e);
                }
            }
        }               
    }

    @Override
    public void notify(SemanticObject obj, Statement stmt, String action, boolean remote) 
    {
        if(!SWBPortal.isStandAlone() && !remote)
        {
            StringBuffer msg=new StringBuffer();
            msg.append("rdf");
            msg.append("|");
            msg.append(instanceid);
            msg.append("|");
            msg.append(obj.getURI());
            msg.append("|");
            if(stmt!=null)
            {
                Property p=stmt.getPredicate();
                if(p!=null)msg.append(p.getURI());
                else msg.append("_");
                msg.append("|");
                RDFNode n=stmt.getObject();
                if(n!=null)msg.append(SWBTSUtil.node2String(n.asNode()).replace("|", "{!}"));
                else msg.append("_");
            }
            else msg.append("_|_");
            msg.append("|");
            msg.append(action);
            SWBPortal.getMessageCenter().sendMessage(msg.toString());
        }
        if(obj!=null && !obj.getModel().isDataModel())
        {
            processCacheService(obj, stmt, action);
        }
    }
    
    
     /* (non-Javadoc)
     * @see org.semanticwb.platform.SemanticObserver#notify(org.semanticwb.platform.SemanticObject, java.lang.Object, java.lang.String)
     */
    /**
     * Notify.
     * 
     * @param uri the resource uri
     * @param prop the property uri
     * @param node the RDF node
     * @param action the action
     */
    public void processCacheService(SemanticObject obj, Statement stmt, String action)            
    {
        //log.trace("processCacheService: obj:" + obj + " stmt:" + stmt +" "+action);  
        //System.out.println("processCacheService:"+obj+" stmt:" + stmt + " action:" + action);  
        
        if(obj!=null)
        {            
            Property prop=null;
            if(stmt!=null)prop=stmt.getPredicate();
            
            if(prop==null && obj.instanceOf(IPFilter.sclass))
            {
                WebSite site=SWBContext.getWebSite(obj.getModel().getName());
                if(site!=null)
                {
                    site.clearCache();
                }
            }             
            
            if(prop!=null && prop.equals(IPFilter.swb_ipFilterNumber.getRDFProperty()) && obj.instanceOf(IPFilter.sclass))
            {
                WebSite site=SWBContext.getWebSite(obj.getModel().getName());
                if(site!=null)
                {
                    site.clearCache();
                }
            }                            
            
            if(action.equals(SemanticObject.ACT_ADD))
            {
                if(prop!=null)
                {
                    if(prop.equals(ResourceType.swb_resourceOWL.getRDFProperty()) && obj.instanceOf(ResourceType.sclass))
                    {
                        try
                        {
                            SWBPortal.getResourceMgr().loadResourceTypeModel((ResourceType)obj.createGenericInstance());
                            SWBPlatform.getSemanticMgr().loadBaseVocabulary();
                        }catch(Exception e){log.error(e);}
                    }

                    if(prop.equals(Resource.swb_updated.getRDFProperty()) && obj.instanceOf(Resource.sclass))
                    {
                        SWBResource res=SWBPortal.getResourceMgr().getResource(obj.getURI());
                        //System.out.println("Instanceof SWBResource:"+res);
                        try
                        {
                            if(res!=null)
                            {
                                res.setResourceBase(res.getResourceBase());
                                SWBPortal.getResourceMgr().getResourceCacheMgr().removeResource(res.getResourceBase());
                            }
                        }catch(Exception e){log.error(e);}
                    }
                    if(((prop.equals(Template.swb_active.getRDFProperty())) || prop.equals(Template.swb_actualVersion.getRDFProperty())) && obj.instanceOf(Template.sclass))
                    {
                        Template aux=(Template)obj.createGenericInstance();
                        Template tpl=SWBPortal.getTemplateMgr().getTemplateImp(aux);
                        if(tpl!=null)tpl.reload();
                    }
                }
            }else if(action.equals(SemanticObject.ACT_REMOVE))
            {
                //Eliminar cache se resource
                if(prop==null && obj.instanceOf(Resource.sclass))
                {
                    SWBPortal.getResourceMgr().removeResource(obj.getURI());
                }                               
            }
        }
    }    
    
}
