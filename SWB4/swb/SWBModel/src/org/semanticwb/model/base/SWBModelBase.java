package org.semanticwb.model.base;


public class SWBModelBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");

    public SWBModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
