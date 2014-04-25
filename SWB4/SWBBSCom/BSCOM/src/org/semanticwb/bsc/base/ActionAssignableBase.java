package org.semanticwb.bsc.base;

   /**
   * Define que puede tener acciones asignadas 
   */
public interface ActionAssignableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Las acciones darán especial atención a los casos en que el o los controles se hayan determinado Deficientes o Inexistentes, y por tanto el Riesgo NO este Controlado Suficientemente. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MitigationAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MitigationAction");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasMitigationAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasMitigationAction");
   /**
   * Define que puede tener acciones asignadas 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_ActionAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ActionAssignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActions();
    public boolean hasMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value);

   /**
   * Adds the MitigationAction
   * @param value An instance of org.semanticwb.bsc.tracing.MitigationAction
   */
    public void addMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value);

   /**
   * Remove all the values for the property MitigationAction
   */
    public void removeAllMitigationAction();

   /**
   * Remove a value from the property MitigationAction
   * @param value An instance of org.semanticwb.bsc.tracing.MitigationAction
   */
    public void removeMitigationAction(org.semanticwb.bsc.tracing.MitigationAction value);

/**
* Gets the MitigationAction
* @return a instance of org.semanticwb.bsc.tracing.MitigationAction
*/
    public org.semanticwb.bsc.tracing.MitigationAction getMitigationAction();
}
