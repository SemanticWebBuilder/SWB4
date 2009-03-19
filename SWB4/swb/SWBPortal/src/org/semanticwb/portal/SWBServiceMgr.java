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
import org.semanticwb.model.Language;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
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

/**
 *
 * @author javier.solis
 */
public class SWBServiceMgr implements SemanticObserver {

    private static Logger log = SWBUtils.getLogger(SWBServiceMgr.class);

    public void notify(SemanticObject obj, Object prop, String action)
    {
        User usr = SWBPortal.getSessionUser();
        log.trace("obj:" + obj + " prop:" + prop + " action:" + action + " " + usr);
        SWBPortal.getDBAdmLog().saveAdmLog(usr, obj, prop, action);

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
                        WebSite website=(WebSite)obj.createGenericInstance();
                        //Crea repositorio x defecto
                        website.setUserRepository(SWBContext.getDefaultRepository());
                        WebPage wp=website.createWebPage("home");
                        wp.setTitle("home");
                        website.setHomePage(wp);
                        //Crea lenguages x defecto
                        Iterator <Language> itLangs=SWBContext.getGlobalWebSite().listLanguages();
                        while(itLangs.hasNext()){
                            Language langNext=itLangs.next();
                            Language lang=website.createLanguage(langNext.getId());
                            lang.setTitle(langNext.getTitle());
                            lang.setDescription(langNext.getDescription());
                        }
                        java.io.File dir=new java.io.File(SWBPlatform.getWorkPath() + "/models/"+ obj.getId() + "/Template");
                        dir.mkdirs();
                        dir=new java.io.File(SWBPlatform.getWorkPath() + "/models/" + obj.getId() + "/Resource");
                        dir.mkdirs();
                        //
                    }
                    if(obj.instanceOf(Template.sclass))
                    {
                        Template tpl=(Template)obj.createGenericInstance();
                        VersionInfo vi=new VersionInfo(tpl.getSemanticObject());
                        vi.setVersionNumber(1);
                        tpl.setActualVersion(vi);
                        tpl.setLastVersion(vi);
                        if(tpl.getActualVersion()==null || tpl.getActualVersion().getVersionFile()==null)
                        {
                            vi.setVersionFile("template.html");
                        }
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
                }
            } else if (prop instanceof SemanticProperty)
            {
                updateTraceable(obj,usr);
            }else
            {
                //TODO: SemanticClass
            }
        }
    }

    public void updateTraceable(SemanticObject obj, User usr)
    {
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
