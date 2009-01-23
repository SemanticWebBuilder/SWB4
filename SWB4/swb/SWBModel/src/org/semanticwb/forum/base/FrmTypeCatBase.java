package org.semanticwb.forum.base;


public class FrmTypeCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmTypeCat");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmTypeCat");

    public FrmTypeCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmTypeCat getFrmTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmTypeCat)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmTypeCat> listFrmTypeCats(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmTypeCat>(org.semanticwb.forum.FrmTypeCat.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmTypeCat> listFrmTypeCats()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmTypeCat>(org.semanticwb.forum.FrmTypeCat.class, it, true);
    }

    public static org.semanticwb.forum.FrmTypeCat createFrmTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmTypeCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFrmTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmTypeCat(id, model)!=null);
    }
}
