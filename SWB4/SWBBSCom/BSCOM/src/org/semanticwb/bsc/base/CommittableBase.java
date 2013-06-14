package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC que se pueda confirmar y no permitir editarlo 
   */
public interface CommittableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_commited=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#commited");
   /**
   * Cualquier elemento BSC que se pueda confirmar y no permitir editarlo 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Committable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Committable");

    public boolean isCommited();

    public void setCommited(boolean value);
}
