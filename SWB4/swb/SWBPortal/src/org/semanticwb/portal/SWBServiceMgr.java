/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Template;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.indexer.SWBIndexer;

/**
 *
 * @author javier.solis
 */
public class SWBServiceMgr implements SemanticObserver {

    private static Logger log = SWBUtils.getLogger(SWBServiceMgr.class);

    private int lastobj;              //ultimo objeto modificado
    private long lastthread;          //ultimo thread utilizado

    public void notify(SemanticObject obj, Object prop, String action)
    {
        User usr = SWBPortal.getSessionUser();
        log.trace("obj:" + obj + " prop:" + prop + " action:" + action + " " + usr);
        //System.out.println("obj:" + obj + " prop:" + prop + " action:" + action + " " + usr);
        if(obj.getModel().isTraceable())SWBPortal.getDBAdmLog().saveAdmLog(usr, obj, prop, action);

        SemanticClass cls = obj.getSemanticClass();
        if(cls.isSWB())
        {
            if (prop == null) //se modifico un objeto CREATE o REMOVE
            {
                if (action.equals("CREATE")) //CREATE
                {
                    updateTraceable(obj,usr);
                    if(obj.instanceOf(SWBModel.sclass))
                    {
                        java.io.File dir=new java.io.File(SWBPlatform.getWorkPath() + "/models/"+ obj.getId());
                        dir.mkdirs();
                    }
                    if(obj.instanceOf(WebSite.sclass))
                    {
//                        WebSite website=(WebSite)obj.createGenericInstance();
//                        //Crea repositorio x defecto
//                        website.setUserRepository(SWBContext.getDefaultRepository());
//                        WebPage wp=website.createWebPage("home");
//                        wp.setTitle("Home");
//                        wp.set
//                        website.setHomePage(wp);
//                        //Crea lenguages x defecto
//                        Iterator <Language> itLangs=SWBContext.getGlobalWebSite().listLanguages();
//                        while(itLangs.hasNext()){
//                            Language langNext=itLangs.next();
//                            Language lang=website.createLanguage(langNext.getId());
//                            lang.setTitle(langNext.getTitle());
//                            lang.setDescription(langNext.getDescription());
//                        }
                        java.io.File dir=new java.io.File(SWBPlatform.getWorkPath() + "/models/"+ obj.getId() + "/Template");
                        dir.mkdirs();
                        dir=new java.io.File(SWBPlatform.getWorkPath() + "/models/" + obj.getId() + "/Resource");
                        dir.mkdirs();
                        //
                    }
                    if(obj.instanceOf(Template.sclass))
                    {
                        String ctx=SWBPlatform.getContextPath();
                        Template tpl=(Template)obj.createGenericInstance();
                        VersionInfo vi=VersionInfo.createVersionInfo(tpl.getWebSite());
                        vi.setVersionNumber(1);
                        vi.setVersionFile("template.html");
                        tpl.setActualVersion(vi);
                        tpl.setLastVersion(vi);
                        String txt=Template.DEFAUL_HTML;
                        try
                        {
                            SWBPlatform.writeFileToWorkPath(tpl.getWorkPath()+"/1/"+"template.html", SWBUtils.IO.getStreamFromString(txt), usr.getURI());
                        }catch(Exception e){log.error(e);}
                    }
                    if(obj.instanceOf(Dns.sclass))
                    {
                        Dns.refresh();
                    }
                } else //REMOVES
                {
                    if (obj.instanceOf(SWBModel.sclass)) //Removes website
                    {
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + "/models/"+obj.getId());
                    } else if (obj.instanceOf(Template.sclass)) // Removes Template
                    {
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + obj.getWorkPath());
                    } else if (obj.instanceOf(Resource.sclass)) // Removes Resource
                    {
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + obj.getWorkPath());
                    }
                    if(obj.instanceOf(Dns.sclass))
                    {
                        Dns.refresh();
                    }
                    if(obj.instanceOf(WebPage.sclass))
                    {
                        SWBPortal.getIndexMgr().getDefaultIndexer().removeTopic(obj.getModel().getName(), obj.getId());
                    }
                    if(obj.instanceOf(WebSite.sclass))
                    {
                        SWBPortal.getIndexMgr().getDefaultIndexer().removeWebSite(obj.getId());
                    }
                    if(obj.instanceOf(ResourceType.sclass))
                    {
                        try
                        {
                            Class cls2=SWBPortal.getResourceMgr().createSWBResourceClass(obj.getProperty(ResourceType.swb_resourceClassName));
                            ((SWBResource)cls2.newInstance()).uninstall((ResourceType)obj.createGenericInstance());
                        }catch(Exception e){log.error(e);}
                    }

                }
            } else if (prop instanceof SemanticProperty)
            {
                //System.out.println("obj2:"+obj+" "+Resource.sclass+"="+Resource.sclass+" prop:"+prop+"="+Resource.swb_resourceSubType);
                if(obj.instanceOf(ResourceType.sclass) && prop.equals(ResourceType.swb_resourceClassName))
                {
                    try
                    {
                        Class cls2=SWBPortal.getResourceMgr().createSWBResourceClass(obj.getProperty(ResourceType.swb_resourceClassName));
                        ((SWBResource)cls2.newInstance()).install((ResourceType)obj.createGenericInstance());
                    }catch(Exception e){log.error(e);}
                }

                if(obj.instanceOf(Resource.sclass) && prop.equals(Resource.swb_resourceSubType))
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
                if(obj.instanceOf(Resource.sclass))
                {
                    SWBResource res=SWBPortal.getResourceMgr().getResource(obj.getURI());
                    //System.out.println("Instanceof SWBResource:"+res);
                    try
                    {
                        if(res!=null)res.setResourceBase(res.getResourceBase());
                    }catch(Exception e){log.error(e);}
                }
                if(obj.instanceOf(Dns.sclass)&& prop.equals(Dns.swb_dns))
                {
                    Dns.refresh();
                }
                if(obj.instanceOf(Template.sclass)&& prop.equals(Template.swb_active))
                {
                    Template aux=(Template)obj.createGenericInstance();
                    Template tpl=SWBPortal.getTemplateMgr().getTemplateImp(aux);
                    if(tpl!=null)tpl.reload();
                }
                if(obj.getModel().isTraceable())updateObject(obj,usr);
            }else
            {
                //TODO: SemanticClass
            }
        }
    }
    public void updateObject(SemanticObject obj, User usr)
    {
        try
        {
            //TODO:Una rapida aproximacion para no estar actualizando al modificar cada propiedad
            //if(obj.hashCode()!=lastobj || Thread.currentThread().getId()!=lastthread)
            {
                //System.out.println("updateObject:"+obj+" user:"+usr);
                lastobj=obj.hashCode();
                lastthread=Thread.currentThread().getId();
                updateTraceable(obj,usr);

                if(obj.instanceOf(WebPage.sclass))
                {
                    SWBIndexer indexer=SWBPortal.getIndexMgr().getDefaultIndexer();
                    indexer.removeTopic(obj.getModel().getName(),obj.getId());
                    indexer.indexWebPage((WebPage)obj.createGenericInstance());
                }
                if(obj.instanceOf(Resource.sclass))
                {
                    //System.out.println("Resource modificado");
                    Resource res=(Resource)obj.createGenericInstance();
                    if(res.getResourceType()!=null && res.getResourceType().getResourceMode()==ResourceType.MODE_CONTENT)
                    {
                        //System.out.println("Resource tipo contenido");
                        SWBIndexer indexer=SWBPortal.getIndexMgr().getDefaultIndexer();
                        indexer.removeContent(obj.getId(),obj.getModel().getName());
                        Iterator<Resourceable> it=res.listResourceables();
                        while(it.hasNext())
                        {
                            Resourceable ob=it.next();
                            if(ob instanceof WebPage)
                            {
                                //System.out.println("Contenido en la pagina ob");
                                indexer.indexContent(res,(WebPage)ob);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){log.error(e);}
    }

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

    public void init() {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
    }
}
