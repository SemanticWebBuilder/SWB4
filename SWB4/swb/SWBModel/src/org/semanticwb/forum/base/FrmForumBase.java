package org.semanticwb.forum.base;


public class FrmForumBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty frm_userPostRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#userPostRole");
    public static final org.semanticwb.platform.SemanticClass frm_FrmModererationCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmModererationCat");
    public static final org.semanticwb.platform.SemanticProperty frm_moderationMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#moderationMode");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticProperty frm_threadcount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#threadcount");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_lastpostusr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#lastpostusr");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticClass frm_FrmThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasthread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasthread");
    public static final org.semanticwb.platform.SemanticProperty frm_postcount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postcount");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty frm_userThreadRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#userThreadRole");
    public static final org.semanticwb.platform.SemanticProperty frm_lastpostdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#lastpostdate");
    public static final org.semanticwb.platform.SemanticClass frm_FrmTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmTypeCat");
    public static final org.semanticwb.platform.SemanticProperty frm_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#type");
    public static final org.semanticwb.platform.SemanticClass frm_FrmCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmCategory");
    public static final org.semanticwb.platform.SemanticProperty frm_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#category");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass frm_FrmForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmForum");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmForum");

    public FrmForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmForum getFrmForum(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmForum)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmForum> listFrmForums(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmForum>(org.semanticwb.forum.FrmForum.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmForum> listFrmForums()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmForum>(org.semanticwb.forum.FrmForum.class, it, true);
    }

    public static org.semanticwb.forum.FrmForum createFrmForum(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmForum)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.forum.FrmForum createFrmForum(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.forum.FrmForum.createFrmForum(String.valueOf(id), model);
    }

    public static void removeFrmForum(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmForum(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmForum(id, model)!=null);
    }

    public void setPostRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().setObjectProperty(frm_userPostRole, role.getSemanticObject());
    }

    public void removePostRole()
    {
        getSemanticObject().removeProperty(frm_userPostRole);
    }

    public org.semanticwb.model.Role getPostRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_userPostRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public void setModerationMode(org.semanticwb.forum.FrmModererationCat frmmodererationcat)
    {
        getSemanticObject().setObjectProperty(frm_moderationMode, frmmodererationcat.getSemanticObject());
    }

    public void removeModerationMode()
    {
        getSemanticObject().removeProperty(frm_moderationMode);
    }

    public org.semanticwb.forum.FrmModererationCat getModerationMode()
    {
         org.semanticwb.forum.FrmModererationCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_moderationMode);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmModererationCat)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public int getThreadcount()
    {
        return getSemanticObject().getIntProperty(frm_threadcount);
    }

    public void setThreadcount(int threadcount)
    {
        getSemanticObject().setLongProperty(frm_threadcount, threadcount);
    }

    public void setLastpostusr(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(frm_lastpostusr, user.getSemanticObject());
    }

    public void removeLastpostusr()
    {
        getSemanticObject().removeProperty(frm_lastpostusr);
    }

    public org.semanticwb.model.User getLastpostusr()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_lastpostusr);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmThread> listthreads()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmThread>(org.semanticwb.forum.FrmThread.class, getSemanticObject().listObjectProperties(frm_hasthread));
    }

    public boolean hasthread(org.semanticwb.forum.FrmThread frmthread)
    {
        if(frmthread==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasthread,frmthread.getSemanticObject());
    }

    public org.semanticwb.forum.FrmThread getthread()
    {
         org.semanticwb.forum.FrmThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasthread);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmThread)obj.createGenericInstance();
         }
         return ret;
    }

    public int getPostcount()
    {
        return getSemanticObject().getIntProperty(frm_postcount);
    }

    public void setPostcount(int postcount)
    {
        getSemanticObject().setLongProperty(frm_postcount, postcount);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public void setThreadRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().setObjectProperty(frm_userThreadRole, role.getSemanticObject());
    }

    public void removeThreadRole()
    {
        getSemanticObject().removeProperty(frm_userThreadRole);
    }

    public org.semanticwb.model.Role getThreadRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_userThreadRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getLastpostdate()
    {
        return getSemanticObject().getDateProperty(frm_lastpostdate);
    }

    public void setLastpostdate(java.util.Date lastpostdate)
    {
        getSemanticObject().setDateProperty(frm_lastpostdate, lastpostdate);
    }

    public void setType(org.semanticwb.forum.FrmTypeCat frmtypecat)
    {
        getSemanticObject().setObjectProperty(frm_type, frmtypecat.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(frm_type);
    }

    public org.semanticwb.forum.FrmTypeCat getType()
    {
         org.semanticwb.forum.FrmTypeCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_type);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmTypeCat)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCategory(org.semanticwb.forum.FrmCategory frmcategory)
    {
        getSemanticObject().setObjectProperty(frm_category, frmcategory.getSemanticObject());
    }

    public void removeCategory()
    {
        getSemanticObject().removeProperty(frm_category);
    }

    public org.semanticwb.forum.FrmCategory getCategory()
    {
         org.semanticwb.forum.FrmCategory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmCategory)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
