/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integraci�n,
 * colaboraci�n y conocimiento, que gracias al uso de tecnolog�a sem�ntica puede generar contextos de
 * informaci�n alrededor de alg�n tema de inter�s o bien integrar informaci�n y aplicaciones de diferentes
 * fuentes, donde a la informaci�n se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creaci�n original del Fondo de Informaci�n y Documentaci�n
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en tr�mite.
 *
 * INFOTEC pone a su disposici�n la herramienta SemanticWebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garant�a sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class ImageUploadBase.
 */
public abstract class ImageUploadBase extends org.semanticwb.model.FileUpload 
{
    
    /** The Constant swbxf_imgThumbnailWidth. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailWidth");
    
    /** The Constant swbxf_imgMaxHeight. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgMaxHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgMaxHeight");
    
    /** The Constant swbxf_imgMaxWidth. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgMaxWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgMaxWidth");
    
    /** The Constant swbxf_imgCrop. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgCrop=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgCrop");
    
    /** The Constant swbxf_imgThumbnail. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnail");
    
    /** The Constant swbxf_imgThumbnailHeight. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_imgThumbnailHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#imgThumbnailHeight");
    
    /** The Constant swb_ImageUpload. */
    public static final org.semanticwb.platform.SemanticClass swb_ImageUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ImageUpload");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ImageUpload");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List image uploads.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ImageUpload> listImageUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ImageUpload>(it, true);
        }

        /**
         * List image uploads.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.ImageUpload> listImageUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ImageUpload>(it, true);
        }

        /**
         * Gets the image upload.
         * 
         * @param id the id
         * @param model the model
         * @return the image upload
         */
        public static org.semanticwb.model.ImageUpload getImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ImageUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the image upload.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. image upload
         */
        public static org.semanticwb.model.ImageUpload createImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ImageUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the image upload.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for image upload.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getImageUpload(id, model)!=null);
        }
    }

    /**
     * Instantiates a new image upload base.
     * 
     * @param base the base
     */
    public ImageUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the img thumbnail width.
     * 
     * @return the img thumbnail width
     */
    public int getImgThumbnailWidth()
    {
        return getSemanticObject().getIntProperty(swbxf_imgThumbnailWidth);
    }

    /**
     * Sets the img thumbnail width.
     * 
     * @param value the new img thumbnail width
     */
    public void setImgThumbnailWidth(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgThumbnailWidth, value);
    }

    /**
     * Gets the img max height.
     * 
     * @return the img max height
     */
    public int getImgMaxHeight()
    {
        return getSemanticObject().getIntProperty(swbxf_imgMaxHeight);
    }

    /**
     * Sets the img max height.
     * 
     * @param value the new img max height
     */
    public void setImgMaxHeight(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgMaxHeight, value);
    }

    /**
     * Gets the img max width.
     * 
     * @return the img max width
     */
    public int getImgMaxWidth()
    {
        return getSemanticObject().getIntProperty(swbxf_imgMaxWidth);
    }

    /**
     * Sets the img max width.
     * 
     * @param value the new img max width
     */
    public void setImgMaxWidth(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgMaxWidth, value);
    }

    /**
     * Checks if is img crop.
     * 
     * @return true, if is img crop
     */
    public boolean isImgCrop()
    {
        return getSemanticObject().getBooleanProperty(swbxf_imgCrop);
    }

    /**
     * Sets the img crop.
     * 
     * @param value the new img crop
     */
    public void setImgCrop(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_imgCrop, value);
    }

    /**
     * Checks if is img thumbnail.
     * 
     * @return true, if is img thumbnail
     */
    public boolean isImgThumbnail()
    {
        return getSemanticObject().getBooleanProperty(swbxf_imgThumbnail);
    }

    /**
     * Sets the img thumbnail.
     * 
     * @param value the new img thumbnail
     */
    public void setImgThumbnail(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_imgThumbnail, value);
    }

    /**
     * Gets the img thumbnail height.
     * 
     * @return the img thumbnail height
     */
    public int getImgThumbnailHeight()
    {
        return getSemanticObject().getIntProperty(swbxf_imgThumbnailHeight);
    }

    /**
     * Sets the img thumbnail height.
     * 
     * @param value the new img thumbnail height
     */
    public void setImgThumbnailHeight(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgThumbnailHeight, value);
    }
}
