/*
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
 */
package org.semanticwb.opensocial.model.data.base;


public abstract class MediaItemBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty data_appId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#appId");
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty data_mime_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#mime_type");
    public static final org.semanticwb.platform.SemanticClass data_MediaItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#MediaItem");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#MediaItem");

    public static class ClassMgr
    {
       /**
       * Returns a list of MediaItem for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItems(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.MediaItem for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItems()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem>(it, true);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return A org.semanticwb.opensocial.model.data.MediaItem
       */
        public static org.semanticwb.opensocial.model.data.MediaItem getMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.MediaItem)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return A org.semanticwb.opensocial.model.data.MediaItem
       */
        public static org.semanticwb.opensocial.model.data.MediaItem createMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.MediaItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       */
        public static void removeMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return true if the org.semanticwb.opensocial.model.data.MediaItem exists, false otherwise
       */

        public static boolean hasMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMediaItem(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.MediaItem with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return Iterator with all the org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItemByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.MediaItem with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItemByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.MediaItem with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return Iterator with all the org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItemByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.data.MediaItem with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItemByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a MediaItemBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MediaItem
   */
    public MediaItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AppId property
* @return String with the AppId
*/
    public String getAppId()
    {
        return getSemanticObject().getProperty(data_appId);
    }

/**
* Sets the AppId property
* @param value long with the AppId
*/
    public void setAppId(String value)
    {
        getSemanticObject().setProperty(data_appId, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in MediaItem object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in MediaItem object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

/**
* Gets the Mime_type property
* @return String with the Mime_type
*/
    public String getMime_type()
    {
        return getSemanticObject().getProperty(data_mime_type);
    }

/**
* Sets the Mime_type property
* @param value long with the Mime_type
*/
    public void setMime_type(String value)
    {
        getSemanticObject().setProperty(data_mime_type, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
