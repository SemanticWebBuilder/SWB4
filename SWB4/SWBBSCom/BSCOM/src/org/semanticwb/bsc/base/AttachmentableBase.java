package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC al que se le puedan agregar archivos 
   */
public interface AttachmentableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Define las características de un archivo adjunto. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Attachment");
   /**
   * Almacena más de un archivo adjunto 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasAttachments");
   /**
   * Cualquier elemento BSC al que se le puedan agregar archivos 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Attachmentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Attachmentable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentses();
    public boolean hasAttachments(org.semanticwb.bsc.catalogs.Attachment value);

   /**
   * Adds the Attachments
   * @param value An instance of org.semanticwb.bsc.catalogs.Attachment
   */
    public void addAttachments(org.semanticwb.bsc.catalogs.Attachment value);

   /**
   * Remove all the values for the property Attachments
   */
    public void removeAllAttachments();

   /**
   * Remove a value from the property Attachments
   * @param value An instance of org.semanticwb.bsc.catalogs.Attachment
   */
    public void removeAttachments(org.semanticwb.bsc.catalogs.Attachment value);

/**
* Gets the Attachments
* @return a instance of org.semanticwb.bsc.catalogs.Attachment
*/
    public org.semanticwb.bsc.catalogs.Attachment getAttachments();
}
