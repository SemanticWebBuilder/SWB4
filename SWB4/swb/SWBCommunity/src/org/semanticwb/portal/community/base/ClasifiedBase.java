package org.semanticwb.portal.community.base;


public class ClasifiedBase extends org.semanticwb.portal.community.DirectoryObject implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Contactable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_clasifiedOperationType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#clasifiedOperationType");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Clasified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Clasified");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Clasified");

    public ClasifiedBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Clasified> listClasifieds(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Clasified>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Clasified> listClasifieds()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Clasified>(it, true);
    }

    public static org.semanticwb.portal.community.Clasified createClasified(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Clasified.createClasified(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Clasified getClasified(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Clasified)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Clasified createClasified(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Clasified)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeClasified(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasClasified(String id, org.semanticwb.model.SWBModel model)
    {
        return (getClasified(id, model)!=null);
    }

    public String getPhoneNumber()
    {
        return getSemanticObject().getProperty(swbcomm_phoneNumber);
    }

    public void setPhoneNumber(String value)
    {
        getSemanticObject().setProperty(swbcomm_phoneNumber, value);
    }

    public String getOperationType()
    {
        return getSemanticObject().getProperty(swbcomm_clasifiedOperationType);
    }

    public void setOperationType(String value)
    {
        getSemanticObject().setProperty(swbcomm_clasifiedOperationType, value);
    }

    public String getName()
    {
        return getSemanticObject().getProperty(swbcomm_name);
    }

    public void setName(String value)
    {
        getSemanticObject().setProperty(swbcomm_name, value);
    }

    public String getEmail()
    {
        return getSemanticObject().getProperty(swbcomm_email);
    }

    public void setEmail(String value)
    {
        getSemanticObject().setProperty(swbcomm_email, value);
    }
}
