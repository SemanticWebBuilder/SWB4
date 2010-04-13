package org.semanticwb.process.model.base;

public interface OperationReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Operation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Operation");
    public static final org.semanticwb.platform.SemanticProperty swp_operationRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#operationRef");
    public static final org.semanticwb.platform.SemanticClass swp_OperationReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OperationReferensable");

    public void setOperationRef(org.semanticwb.process.model.Operation operation);

    public void removeOperationRef();

    public org.semanticwb.process.model.Operation getOperationRef();
}
