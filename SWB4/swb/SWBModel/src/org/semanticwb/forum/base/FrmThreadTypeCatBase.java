package org.semanticwb.forum.base;


public class FrmThreadTypeCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmThreadTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThreadTypeCat");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThreadTypeCat");

    public FrmThreadTypeCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmThreadTypeCat getFrmThreadTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmThreadTypeCat)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmThreadTypeCat> listFrmThreadTypeCats(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmThreadTypeCat>(org.semanticwb.forum.FrmThreadTypeCat.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmThreadTypeCat> listFrmThreadTypeCats()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmThreadTypeCat>(org.semanticwb.forum.FrmThreadTypeCat.class, it, true);
    }

    public static org.semanticwb.forum.FrmThreadTypeCat createFrmThreadTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmThreadTypeCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFrmThreadTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmThreadTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmThreadTypeCat(id, model)!=null);
    }
}
