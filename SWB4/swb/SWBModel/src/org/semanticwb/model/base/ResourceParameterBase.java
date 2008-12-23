package org.semanticwb.model.base;


public class ResourceParameterBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_resParamValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#resParamValue");
    public static final org.semanticwb.platform.SemanticProperty swbxf_resParamName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#resParamName");
    public static final org.semanticwb.platform.SemanticClass swbxf_ResourceParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ResourceParameter");

    public ResourceParameterBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setValue(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(swbxf_resParamValue, semanticobject);
    }

    public void removeValue()
    {
        getSemanticObject().removeProperty(swbxf_resParamValue);
    }

    public org.semanticwb.platform.SemanticObject getValue()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_resParamValue);
         return ret;
    }

    public String getName()
    {
        return getSemanticObject().getProperty(swbxf_resParamName);
    }

    public void setName(String resParamName)
    {
        getSemanticObject().setProperty(swbxf_resParamName, resParamName);
    }
}
