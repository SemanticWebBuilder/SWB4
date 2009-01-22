package org.semanticwb.forum.base;


public class FrmPriorityCatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmPriorityCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPriorityCat");


    public static org.semanticwb.forum.FrmPriorityCat createFrmPriorityCat(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmPriorityCat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, frm_FrmPriorityCat), frm_FrmPriorityCat);
    }

    public FrmPriorityCatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
