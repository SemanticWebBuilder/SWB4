package org.semanticwb.forum.base;


public class FrmAttachmentsBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty frm_attDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#attDescription");
    public static final org.semanticwb.platform.SemanticProperty frm_attFileName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#attFileName");
    public static final org.semanticwb.platform.SemanticProperty frm_attFileSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#attFileSize");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPost");
    public static final org.semanticwb.platform.SemanticProperty frm_attPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#attPost");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty frm_attMimeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#attMimeType");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticClass frm_FrmAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmAttachments");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmAttachments");

    public FrmAttachmentsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmAttachments getFrmAttachments(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmAttachments)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmAttachments> listFrmAttachmentss(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmAttachments>(org.semanticwb.forum.FrmAttachments.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmAttachments> listFrmAttachmentss()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmAttachments>(org.semanticwb.forum.FrmAttachments.class, it, true);
    }

    public static org.semanticwb.forum.FrmAttachments createFrmAttachments(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmAttachments)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.forum.FrmAttachments createFrmAttachments(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.forum.FrmAttachments.createFrmAttachments(String.valueOf(id), model);
    }

    public static void removeFrmAttachments(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmAttachments(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmAttachments(id, model)!=null);
    }

    public String getAttDescription()
    {
        return getSemanticObject().getProperty(frm_attDescription);
    }

    public void setAttDescription(String attDescription)
    {
        getSemanticObject().setProperty(frm_attDescription, attDescription);
    }

    public String getFileName()
    {
        return getSemanticObject().getProperty(frm_attFileName);
    }

    public void setFileName(String attFileName)
    {
        getSemanticObject().setProperty(frm_attFileName, attFileName);
    }

    public int getFileSize()
    {
        return getSemanticObject().getIntProperty(frm_attFileSize);
    }

    public void setFileSize(int attFileSize)
    {
        getSemanticObject().setLongProperty(frm_attFileSize, attFileSize);
    }

    public void setPost(org.semanticwb.forum.FrmPost frmpost)
    {
        getSemanticObject().setObjectProperty(frm_attPost, frmpost.getSemanticObject());
    }

    public void removePost()
    {
        getSemanticObject().removeProperty(frm_attPost);
    }

    public org.semanticwb.forum.FrmPost getPost()
    {
         org.semanticwb.forum.FrmPost ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_attPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmPost)obj.getSemanticClass().newGenericInstance(obj);
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
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public String getMimeType()
    {
        return getSemanticObject().getProperty(frm_attMimeType);
    }

    public void setMimeType(String attMimeType)
    {
        getSemanticObject().setProperty(frm_attMimeType, attMimeType);
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
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
