package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC al que se le puedan agregar archivos 
   */
public interface AttachmentBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_hasAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasAttachments");
   /**
   * Cualquier elemento BSC al que se le puedan agregar archivos 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Attachment");

    public java.util.Iterator<String> listAttachmentses();

    public void addAttachments(String value);
    public void removeAllAttachments();
    public void removeAttachments(String value);
}
