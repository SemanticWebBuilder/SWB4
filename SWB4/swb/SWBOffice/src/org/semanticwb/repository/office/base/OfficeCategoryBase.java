package org.semanticwb.repository.office.base;


public class OfficeCategoryBase extends org.semanticwb.repository.Folder implements org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty office_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#title");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty office_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#user");
    public static final org.semanticwb.platform.SemanticProperty office_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#description");
    public static final org.semanticwb.platform.SemanticClass office_OfficeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeCategory");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeCategory");

    public OfficeCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategorys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategorys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(it, true);
    }

    public static org.semanticwb.repository.office.OfficeCategory getOfficeCategory(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
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
        return getSemanticObject().getProperty(office_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(office_title, title);
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
        return getSemanticObject().getProperty(office_user);
    }

    public void setUser(String user)
    {
        getSemanticObject().setProperty(office_user, user);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(office_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(office_description, description);
    }
}
