package org.semanticwb.model.base;


public class TextBase extends org.semanticwb.model.SWBFormElement 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");

    public TextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
