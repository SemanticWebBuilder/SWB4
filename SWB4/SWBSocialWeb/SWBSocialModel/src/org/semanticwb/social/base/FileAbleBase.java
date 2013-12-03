package org.semanticwb.social.base;

   /**
   * Interface en la que se define un elemento de tipo file, que funciona para subir archivos al servidor. 
   */
public interface FileAbleBase extends org.semanticwb.social.PostDataable
{
   /**
   * Propiedad en donde se almacenan archivos en el servidor 
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasFile");
   /**
   * Interface en la que se define un elemento de tipo file, que funciona para subir archivos al servidor. 
   */
    public static final org.semanticwb.platform.SemanticClass social_FileAble=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FileAble");

    public java.util.Iterator<String> listFiles();

    public void addFile(String value);
    public void removeAllFile();
    public void removeFile(String value);
}
