package org.semanticwb.repository.office.base;


public class OfficeCategoryBase extends org.semanticwb.repository.Folder implements org.semanticwb.repository.Referenceable,org.semanticwb.content.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty cm_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#title");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty cm_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#user");
    public static final org.semanticwb.platform.SemanticProperty cm_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#description");
    public static final org.semanticwb.platform.SemanticClass cm_OfficeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeCategory");


    public static org.semanticwb.repository.office.OfficeCategory createOfficeCategory(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, cm_OfficeCategory), cm_OfficeCategory);
    }

    public OfficeCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
