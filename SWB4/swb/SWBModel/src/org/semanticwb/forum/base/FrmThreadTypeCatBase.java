package org.semanticwb.forum.base;


public class FrmThreadTypeCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmThreadTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThreadTypeCat");


    public static org.semanticwb.forum.FrmThreadTypeCat createFrmThreadTypeCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmThreadTypeCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, frm_FrmThreadTypeCat), frm_FrmThreadTypeCat);
    }

    public FrmThreadTypeCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
