package org.semanticwb.model.base;


public class CodeEditorBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_codeEditorLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#codeEditorLanguage");
    public static final org.semanticwb.platform.SemanticClass swbxf_CodeEditor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#CodeEditor");

    public CodeEditorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(swbxf_codeEditorLanguage);
    }

    public void setLanguage(String codeEditorLanguage)
    {
        getSemanticObject().setProperty(swbxf_codeEditorLanguage, codeEditorLanguage);
    }
}
