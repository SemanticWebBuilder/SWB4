package org.semanticwb.model.base;


public class RichTextBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_RichText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");


    public static org.semanticwb.model.RichText createRichText(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbxf_RichText), swbxf_RichText);
    }

    public RichTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
