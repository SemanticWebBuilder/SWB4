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
 * The Class FlashImageUploadBase.
 */
public abstract class FlashImageUploadBase extends org.semanticwb.model.FlashFileUpload 
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
    
    /** The Constant swb_FlashImageUpload. */
    public static final org.semanticwb.platform.SemanticClass swb_FlashImageUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashImageUpload");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FlashImageUpload");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of FlashImageUpload for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.FlashImageUpload
        */

        public static java.util.Iterator<org.semanticwb.model.FlashImageUpload> listFlashImageUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashImageUpload>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FlashImageUpload for all models
       * @return Iterator of org.semanticwb.model.FlashImageUpload
       */

        public static java.util.Iterator<org.semanticwb.model.FlashImageUpload> listFlashImageUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FlashImageUpload>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.FlashImageUpload
       * @param id Identifier for org.semanticwb.model.FlashImageUpload
       * @param model Model of the org.semanticwb.model.FlashImageUpload
       * @return A org.semanticwb.model.FlashImageUpload
       */
        public static org.semanticwb.model.FlashImageUpload getFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashImageUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FlashImageUpload
       * @param id Identifier for org.semanticwb.model.FlashImageUpload
       * @param model Model of the org.semanticwb.model.FlashImageUpload
       * @return A org.semanticwb.model.FlashImageUpload
       */
        public static org.semanticwb.model.FlashImageUpload createFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FlashImageUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FlashImageUpload
       * @param id Identifier for org.semanticwb.model.FlashImageUpload
       * @param model Model of the org.semanticwb.model.FlashImageUpload
       */
        public static void removeFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FlashImageUpload
       * @param id Identifier for org.semanticwb.model.FlashImageUpload
       * @param model Model of the org.semanticwb.model.FlashImageUpload
       * @return true if the org.semanticwb.model.FlashImageUpload exists, false otherwise
       */

        public static boolean hasFlashImageUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlashImageUpload(id, model)!=null);
        }
    }

   /**
    * Constructs a FlashImageUploadBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the FlashImageUpload
    */
    public FlashImageUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the ImgThumbnailWidth property.
 * 
 * @return int with the ImgThumbnailWidth
 */
    public int getImgThumbnailWidth()
    {
        return getSemanticObject().getIntProperty(swbxf_imgThumbnailWidth);
    }

/**
 * Sets the ImgThumbnailWidth property.
 * 
 * @param value long with the ImgThumbnailWidth
 */
    public void setImgThumbnailWidth(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgThumbnailWidth, value);
    }

/**
 * Gets the ImgMaxHeight property.
 * 
 * @return int with the ImgMaxHeight
 */
    public int getImgMaxHeight()
    {
        return getSemanticObject().getIntProperty(swbxf_imgMaxHeight);
    }

/**
 * Sets the ImgMaxHeight property.
 * 
 * @param value long with the ImgMaxHeight
 */
    public void setImgMaxHeight(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgMaxHeight, value);
    }

/**
 * Gets the ImgMaxWidth property.
 * 
 * @return int with the ImgMaxWidth
 */
    public int getImgMaxWidth()
    {
        return getSemanticObject().getIntProperty(swbxf_imgMaxWidth);
    }

/**
 * Sets the ImgMaxWidth property.
 * 
 * @param value long with the ImgMaxWidth
 */
    public void setImgMaxWidth(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgMaxWidth, value);
    }

/**
 * Gets the ImgCrop property.
 * 
 * @return boolean with the ImgCrop
 */
    public boolean isImgCrop()
    {
        return getSemanticObject().getBooleanProperty(swbxf_imgCrop);
    }

/**
 * Sets the ImgCrop property.
 * 
 * @param value long with the ImgCrop
 */
    public void setImgCrop(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_imgCrop, value);
    }

/**
 * Gets the ImgThumbnail property.
 * 
 * @return boolean with the ImgThumbnail
 */
    public boolean isImgThumbnail()
    {
        return getSemanticObject().getBooleanProperty(swbxf_imgThumbnail);
    }

/**
 * Sets the ImgThumbnail property.
 * 
 * @param value long with the ImgThumbnail
 */
    public void setImgThumbnail(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_imgThumbnail, value);
    }

/**
 * Gets the ImgThumbnailHeight property.
 * 
 * @return int with the ImgThumbnailHeight
 */
    public int getImgThumbnailHeight()
    {
        return getSemanticObject().getIntProperty(swbxf_imgThumbnailHeight);
    }

/**
 * Sets the ImgThumbnailHeight property.
 * 
 * @param value long with the ImgThumbnailHeight
 */
    public void setImgThumbnailHeight(int value)
    {
        getSemanticObject().setIntProperty(swbxf_imgThumbnailHeight, value);
    }
}
