package org.semanticwb.forum.base;


public class FrmModererationCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmModererationCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmModererationCat");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmModererationCat");

    public FrmModererationCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmModererationCat getFrmModererationCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmModererationCat)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmModererationCat> listFrmModererationCats(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmModererationCat>(org.semanticwb.forum.FrmModererationCat.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmModererationCat> listFrmModererationCats()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmModererationCat>(org.semanticwb.forum.FrmModererationCat.class, it, true);
    }

    public static org.semanticwb.forum.FrmModererationCat createFrmModererationCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmModererationCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFrmModererationCat(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmModererationCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmModererationCat(id, model)!=null);
    }
}
