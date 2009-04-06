package org.semanticwb.repository.office.base;


public class OfficeCategoryBase extends org.semanticwb.repository.Folder implements org.semanticwb.repository.Referenceable,org.semanticwb.content.Descriptiveable,org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty cm_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#title");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty cm_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#user");
    public static final org.semanticwb.platform.SemanticProperty cm_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#description");
    public static final org.semanticwb.platform.SemanticClass cm_OfficeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeCategory");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeCategory");

    public OfficeCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.office.OfficeCategory getOfficeCategory(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategorys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(org.semanticwb.repository.office.OfficeCategory.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategorys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(org.semanticwb.repository.office.OfficeCategory.class, it, true);
    }

    public static org.semanticwb.repository.office.OfficeCategory createOfficeCategory(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeOfficeCategory(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasOfficeCategory(String id, org.semanticwb.model.SWBModel model)
    {
        return (getOfficeCategory(id, model)!=null);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(cm_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(cm_title, title);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public String getUser()
    {
        return getSemanticObject().getProperty(cm_user);
    }

    public void setUser(String user)
    {
        getSemanticObject().setProperty(cm_user, user);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(cm_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(cm_description, description);
    }
}
