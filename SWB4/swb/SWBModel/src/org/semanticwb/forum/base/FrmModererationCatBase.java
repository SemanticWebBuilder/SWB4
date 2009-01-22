package org.semanticwb.forum.base;


public class FrmModererationCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmModererationCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmModererationCat");


    public static org.semanticwb.forum.FrmModererationCat createFrmModererationCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmModererationCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, frm_FrmModererationCat), frm_FrmModererationCat);
    }

    public FrmModererationCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
