package org.semanticwb.model.base;


public class SWBClassBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass swb_SWBClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");

    public SWBClassBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isValid()
    {
        //Implement this method in SWBClass object
        throw new org.semanticwb.SWBMethodImplementationRequiredException();
    }

    public void setValid(boolean valid)
    {
        //Implement this method in SWBClass object
        throw new org.semanticwb.SWBMethodImplementationRequiredException();
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
