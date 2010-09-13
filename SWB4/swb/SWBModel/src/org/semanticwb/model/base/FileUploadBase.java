/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
    * Elemento que muestra un componente grafico para subir archivos.
    */
public abstract class FileUploadBase extends org.semanticwb.model.base.FormElementBase 
{
   
   /** Valor en kbs. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileMaxSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileMaxSize");
    
    /** The Constant swbxf_fileFilter. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileFilter");
   
   /** Elemento que muestra un componente grafico para subir archivos. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of FileUpload for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.FileUpload
        */

        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FileUpload for all models
       * @return Iterator of org.semanticwb.model.FileUpload
       */

        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       * @return A org.semanticwb.model.FileUpload
       */
        public static org.semanticwb.model.FileUpload getFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       * @return A org.semanticwb.model.FileUpload
       */
        public static org.semanticwb.model.FileUpload createFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       */
        public static void removeFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FileUpload
       * @param id Identifier for org.semanticwb.model.FileUpload
       * @param model Model of the org.semanticwb.model.FileUpload
       * @return true if the org.semanticwb.model.FileUpload exists, false otherwise
       */

        public static boolean hasFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFileUpload(id, model)!=null);
        }
    }

   /**
    * Constructs a FileUploadBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the FileUpload
    */
    public FileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the FileMaxSize property.
 * 
 * @return long with the FileMaxSize
 */
    public long getFileMaxSize()
    {
        return getSemanticObject().getLongProperty(swbxf_fileMaxSize);
    }

/**
 * Sets the FileMaxSize property.
 * 
 * @param value long with the FileMaxSize
 */
    public void setFileMaxSize(long value)
    {
        getSemanticObject().setLongProperty(swbxf_fileMaxSize, value);
    }

/**
 * Gets the FileFilter property.
 * 
 * @return String with the FileFilter
 */
    public String getFileFilter()
    {
        return getSemanticObject().getProperty(swbxf_fileFilter);
    }

/**
 * Sets the FileFilter property.
 * 
 * @param value long with the FileFilter
 */
    public void setFileFilter(String value)
    {
        getSemanticObject().setProperty(swbxf_fileFilter, value);
    }

    /**
     * Removes the.
     */
    public void remove()
    {
        getSemanticObject().remove();
    }

    /**
     * List related objects.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
