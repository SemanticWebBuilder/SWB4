package org.semanticwb.process.model.base;

public interface CallableBase extends org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.IOSpecificable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_InputOutputBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputBinding");
    public static final org.semanticwb.platform.SemanticProperty swp_hasIOBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasIOBinding");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInterface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInterface");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSupportedInterfaceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasSupportedInterfaceRef");
    public static final org.semanticwb.platform.SemanticClass swp_Callable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Callable");
    public static final org.semanticwb.platform.SemanticProperty swp_calledElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#calledElement");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> listIOBindings();
    public boolean hasIOBinding(org.semanticwb.process.model.InputOutputBinding value);

    public void addIOBinding(org.semanticwb.process.model.InputOutputBinding value);

    public void removeAllIOBinding();

    public void removeIOBinding(org.semanticwb.process.model.InputOutputBinding value);

    public org.semanticwb.process.model.InputOutputBinding getIOBinding();

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> listSupportedInterfaceRefs();
    public boolean hasSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value);

    public void addSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value);

    public void removeAllSupportedInterfaceRef();

    public void removeSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value);

    public org.semanticwb.process.model.ProcessInterface getSupportedInterfaceRef();

    public void setCalledElement(org.semanticwb.process.model.Callable callable);

    public void removeCalledElement();

    public org.semanticwb.process.model.Callable getCalledElement();
}
