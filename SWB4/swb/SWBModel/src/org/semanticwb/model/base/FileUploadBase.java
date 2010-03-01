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
 * The Class FileUploadBase.
 */
public abstract class FileUploadBase extends org.semanticwb.model.base.FormElementBase 
{
    
    /** The Constant swbxf_fileMaxSize. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileMaxSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileMaxSize");
    
    /** The Constant swbxf_fileFilter. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_fileFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#fileFilter");
    
    /** The Constant swbxf_FileUpload. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FileUpload");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List file uploads.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }

        /**
         * List file uploads.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FileUpload> listFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FileUpload>(it, true);
        }

        /**
         * Gets the file upload.
         * 
         * @param id the id
         * @param model the model
         * @return the file upload
         */
        public static org.semanticwb.model.FileUpload getFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the file upload.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. file upload
         */
        public static org.semanticwb.model.FileUpload createFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the file upload.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for file upload.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFileUpload(id, model)!=null);
        }
    }

    /**
     * Instantiates a new file upload base.
     * 
     * @param base the base
     */
    public FileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the file max size.
     * 
     * @return the file max size
     */
    public long getFileMaxSize()
    {
        return getSemanticObject().getLongProperty(swbxf_fileMaxSize);
    }

    /**
     * Sets the file max size.
     * 
     * @param value the new file max size
     */
    public void setFileMaxSize(long value)
    {
        getSemanticObject().setLongProperty(swbxf_fileMaxSize, value);
    }

    /**
     * Gets the file filter.
     * 
     * @return the file filter
     */
    public String getFileFilter()
    {
        return getSemanticObject().getProperty(swbxf_fileFilter);
    }

    /**
     * Sets the file filter.
     * 
     * @param value the new file filter
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
