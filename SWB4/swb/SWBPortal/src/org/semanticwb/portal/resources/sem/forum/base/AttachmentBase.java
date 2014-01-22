package org.semanticwb.portal.resources.sem.forum.base;


public abstract class AttachmentBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty frm_atMimeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atMimeType");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_atPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atPost");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticProperty frm_atThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atThread");
    public static final org.semanticwb.platform.SemanticProperty frm_atFileName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atFileName");
    public static final org.semanticwb.platform.SemanticProperty frm_atFileSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atFileSize");
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");

    public static class ClassMgr
    {
       /**
       * Returns a list of Attachment for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.forum.Attachment for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.forum.Attachment createAttachment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Attachment.ClassMgr.createAttachment(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.forum.Attachment
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Attachment
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return A org.semanticwb.portal.resources.sem.forum.Attachment
       */
        public static org.semanticwb.portal.resources.sem.forum.Attachment getAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Attachment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.forum.Attachment
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Attachment
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return A org.semanticwb.portal.resources.sem.forum.Attachment
       */
        public static org.semanticwb.portal.resources.sem.forum.Attachment createAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Attachment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.forum.Attachment
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Attachment
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       */
        public static void removeAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.forum.Attachment
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Attachment
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return true if the org.semanticwb.portal.resources.sem.forum.Attachment exists, false otherwise
       */

        public static boolean hasAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAttachment(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined Post
       * @param value Post of the type org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByPost(org.semanticwb.portal.resources.sem.forum.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_atPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined Post
       * @param value Post of the type org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByPost(org.semanticwb.portal.resources.sem.forum.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_atPost,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined Thread
       * @param value Thread of the type org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Attachment
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByThread(org.semanticwb.portal.resources.sem.forum.Thread value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_atThread, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Attachment with a determined Thread
       * @param value Thread of the type org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByThread(org.semanticwb.portal.resources.sem.forum.Thread value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_atThread,value.getSemanticObject(),sclass));
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
* Gets the MimeType property
* @return String with the MimeType
*/
    public String getMimeType()
    {
        return getSemanticObject().getProperty(frm_atMimeType);
    }

/**
* Sets the MimeType property
* @param value long with the MimeType
*/
    public void setMimeType(String value)
    {
        getSemanticObject().setProperty(frm_atMimeType, value);
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
   * Sets the value for the property Post
   * @param value Post to set
   */

    public void setPost(org.semanticwb.portal.resources.sem.forum.Post value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_atPost, value.getSemanticObject());
        }else
        {
            removePost();
        }
    }
   /**
   * Remove the value for Post property
   */

    public void removePost()
    {
        getSemanticObject().removeProperty(frm_atPost);
    }

   /**
   * Gets the Post
   * @return a org.semanticwb.portal.resources.sem.forum.Post
   */
    public org.semanticwb.portal.resources.sem.forum.Post getPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_atPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
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
   /**
   * Sets the value for the property Thread
   * @param value Thread to set
   */

    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_atThread, value.getSemanticObject());
        }else
        {
            removeThread();
        }
    }
   /**
   * Remove the value for Thread property
   */

    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_atThread);
    }

   /**
   * Gets the Thread
   * @return a org.semanticwb.portal.resources.sem.forum.Thread
   */
    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_atThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the FileName property
* @return String with the FileName
*/
    public String getFileName()
    {
        return getSemanticObject().getProperty(frm_atFileName);
    }

/**
* Sets the FileName property
* @param value long with the FileName
*/
    public void setFileName(String value)
    {
        getSemanticObject().setProperty(frm_atFileName, value);
    }

/**
* Gets the FileSize property
* @return int with the FileSize
*/
    public int getFileSize()
    {
        return getSemanticObject().getIntProperty(frm_atFileSize);
    }

/**
* Sets the FileSize property
* @param value long with the FileSize
*/
    public void setFileSize(int value)
    {
        getSemanticObject().setIntProperty(frm_atFileSize, value);
    }
}
