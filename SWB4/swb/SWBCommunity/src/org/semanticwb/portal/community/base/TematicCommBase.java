package org.semanticwb.portal.community.base;


public class TematicCommBase extends org.semanticwb.portal.community.Community 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_TematicComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#TematicComm");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#TematicComm");

    public TematicCommBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.TematicComm> listTematicComms(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.TematicComm>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.TematicComm> listTematicComms()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.TematicComm>(it, true);
    }

    public static org.semanticwb.portal.community.TematicComm getTematicComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.TematicComm)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.TematicComm createTematicComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.TematicComm)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeTematicComm(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasTematicComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (getTematicComm(id, model)!=null);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
