package org.semanticwb.forum.base;


public class FrmPriorityCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmPriorityCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPriorityCat");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPriorityCat");

    public FrmPriorityCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmPriorityCat getFrmPriorityCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmPriorityCat)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmPriorityCat> listFrmPriorityCats(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPriorityCat>(org.semanticwb.forum.FrmPriorityCat.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmPriorityCat> listFrmPriorityCats()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPriorityCat>(org.semanticwb.forum.FrmPriorityCat.class, it, true);
    }

    public static org.semanticwb.forum.FrmPriorityCat createFrmPriorityCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmPriorityCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFrmPriorityCat(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmPriorityCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmPriorityCat(id, model)!=null);
    }
}
