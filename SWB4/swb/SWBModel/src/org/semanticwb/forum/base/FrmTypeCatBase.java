package org.semanticwb.forum.base;


public class FrmTypeCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmTypeCat");


    public static org.semanticwb.forum.FrmTypeCat createFrmTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmTypeCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, frm_FrmTypeCat), frm_FrmTypeCat);
    }

    public FrmTypeCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
