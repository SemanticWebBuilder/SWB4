package org.semanticwb.model.base;


public class SelectOneBase extends org.semanticwb.model.SWBFormElement 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_globalScope");
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_blankSuport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_blankSuport");
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectOne=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectOne");

    public SelectOneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_globalScope);
    }

    public void setGlobalScope(boolean so_globalScope)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_globalScope, so_globalScope);
    }

    public boolean isBlankSuport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_blankSuport);
    }

    public void setBlankSuport(boolean so_blankSuport)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_blankSuport, so_blankSuport);
    }
}
