package org.semanticwb.bsc.catalogs.base;


   /**
   * Define las características de un archivo adjunto. 
   */
public abstract class AttachmentBase extends org.semanticwb.bsc.catalogs.Catalog implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable
{
   /**
   * Almacena el nombre del archivo adjunto
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#file");
   /**
   * Define las características de un archivo adjunto.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Attachment");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Attachment");

    public static class ClassMgr
    {
       /**
       * Returns a list of Attachment for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Attachment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Attachment> listAttachments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Attachment for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Attachment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Attachment> listAttachments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Attachment createAttachment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Attachment.ClassMgr.createAttachment(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Attachment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Attachment
       * @param model Model of the org.semanticwb.bsc.catalogs.Attachment
       * @return A org.semanticwb.bsc.catalogs.Attachment
       */
        public static org.semanticwb.bsc.catalogs.Attachment getAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Attachment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Attachment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Attachment
       * @param model Model of the org.semanticwb.bsc.catalogs.Attachment
       * @return A org.semanticwb.bsc.catalogs.Attachment
       */
        public static org.semanticwb.bsc.catalogs.Attachment createAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Attachment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Attachment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Attachment
       * @param model Model of the org.semanticwb.bsc.catalogs.Attachment
       */
        public static void removeAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Attachment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Attachment
       * @param model Model of the org.semanticwb.bsc.catalogs.Attachment
       * @return true if the org.semanticwb.bsc.catalogs.Attachment exists, false otherwise
       */

        public static boolean hasAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAttachment(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Attachment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.catalogs.Attachment
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Attachment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Attachment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Attachment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Attachment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.catalogs.Attachment
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Attachment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Attachment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Attachment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AttachmentBase.ClassMgr getAttachmentClassMgr()
    {
        return new AttachmentBase.ClassMgr();
    }

   /**
   * Constructs a AttachmentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Attachment
   */
    public AttachmentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
* Gets the File property
* @return String with the File
*/
    public String getFile()
    {
        return getSemanticObject().getProperty(bsc_file);
    }

/**
* Sets the File property
* @param value long with the File
*/
    public void setFile(String value)
    {
        getSemanticObject().setProperty(bsc_file, value);
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
}
