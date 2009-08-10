package org.semanticwb.portal.community.base;


public class PersonalCommBase extends org.semanticwb.portal.community.Community 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_PersonalComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PersonalComm");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PersonalComm");

    public PersonalCommBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.PersonalComm> listPersonalComms(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PersonalComm>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.PersonalComm> listPersonalComms()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PersonalComm>(it, true);
    }

    public static org.semanticwb.portal.community.PersonalComm getPersonalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.PersonalComm)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.PersonalComm createPersonalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.PersonalComm)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePersonalComm(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPersonalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPersonalComm(id, model)!=null);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
