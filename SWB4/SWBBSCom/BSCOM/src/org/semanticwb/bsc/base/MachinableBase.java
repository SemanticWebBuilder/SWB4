package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento que se comporte como una máquina de estados. Estado inicial, estados de transición y estado final. 
   */
public interface MachinableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Cualquier elemento que se comporte como una máquina de estados. Estado inicial, estados de transición y estado final. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Machinable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Machinable");
    public static final org.semanticwb.platform.SemanticProperty bsc_next=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#next");
    public static final org.semanticwb.platform.SemanticProperty bsc_orden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#orden");
    public static final org.semanticwb.platform.SemanticProperty bsc_previus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#previus");

   /**
   * Sets a value from the property Next
   * @param value An instance of org.semanticwb.bsc.Machinable
   */
    public void setNext(org.semanticwb.bsc.Machinable value);

   /**
   * Remove the value from the property Next
   */
    public void removeNext();

    public org.semanticwb.bsc.Machinable getNext();

    public int getOrden();

    public void setOrden(int value);

   /**
   * Sets a value from the property Previus
   * @param value An instance of org.semanticwb.bsc.Machinable
   */
    public void setPrevius(org.semanticwb.bsc.Machinable value);

   /**
   * Remove the value from the property Previus
   */
    public void removePrevius();

    public org.semanticwb.bsc.Machinable getPrevius();
}
