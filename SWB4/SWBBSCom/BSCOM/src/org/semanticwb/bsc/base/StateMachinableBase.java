package org.semanticwb.bsc.base;

public interface StateMachinableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_StateMachinable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#StateMachinable");
    public static final org.semanticwb.platform.SemanticProperty bsc_next=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#next");
    public static final org.semanticwb.platform.SemanticProperty bsc_previus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#previus");

   /**
   * Sets a value from the property Next
   * @param value An instance of org.semanticwb.bsc.StateMachinable
   */
    public void setNext(org.semanticwb.bsc.StateMachinable value);

   /**
   * Remove the value from the property Next
   */
    public void removeNext();

    public org.semanticwb.bsc.StateMachinable getNext();

   /**
   * Sets a value from the property Previus
   * @param value An instance of org.semanticwb.bsc.StateMachinable
   */
    public void setPrevius(org.semanticwb.bsc.StateMachinable value);

   /**
   * Remove the value from the property Previus
   */
    public void removePrevius();

    public org.semanticwb.bsc.StateMachinable getPrevius();
}
