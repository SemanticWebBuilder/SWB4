package org.semanticwb.portal.resources.sem.forum.base;


public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticProperty frm_pstThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pstThread");
    public static final org.semanticwb.platform.SemanticProperty frm_pstBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pstBody");
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
    public static final org.semanticwb.platform.SemanticProperty frm_hasAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasAttachments");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_pstParentPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pstParentPost");
    public static final org.semanticwb.platform.SemanticProperty frm_haschildPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#haschildPost");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");

    public static class ClassMgr
    {
       /**
       * Returns a list of Post for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.forum.Post for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.forum.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Post.ClassMgr.createPost(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.forum.Post
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return A org.semanticwb.portal.resources.sem.forum.Post
       */
        public static org.semanticwb.portal.resources.sem.forum.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.forum.Post
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return A org.semanticwb.portal.resources.sem.forum.Post
       */
        public static org.semanticwb.portal.resources.sem.forum.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.forum.Post
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       */
        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.forum.Post
       * @param id Identifier for org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return true if the org.semanticwb.portal.resources.sem.forum.Post exists, false otherwise
       */

        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined Thread
       * @param value Thread of the type org.semanticwb.portal.resources.sem.forum.Thread
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByThread(org.semanticwb.portal.resources.sem.forum.Thread value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_pstThread, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined Thread
       * @param value Thread of the type org.semanticwb.portal.resources.sem.forum.Thread
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByThread(org.semanticwb.portal.resources.sem.forum.Thread value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_pstThread,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined Attachments
       * @param value Attachments of the type org.semanticwb.portal.resources.sem.forum.Attachment
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByAttachments(org.semanticwb.portal.resources.sem.forum.Attachment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasAttachments, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined Attachments
       * @param value Attachments of the type org.semanticwb.portal.resources.sem.forum.Attachment
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByAttachments(org.semanticwb.portal.resources.sem.forum.Attachment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_hasAttachments,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined ParentPost
       * @param value ParentPost of the type org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByParentPost(org.semanticwb.portal.resources.sem.forum.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_pstParentPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined ParentPost
       * @param value ParentPost of the type org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByParentPost(org.semanticwb.portal.resources.sem.forum.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_pstParentPost,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined ChildPost
       * @param value ChildPost of the type org.semanticwb.portal.resources.sem.forum.Post
       * @param model Model of the org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByChildPost(org.semanticwb.portal.resources.sem.forum.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_haschildPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.forum.Post with a determined ChildPost
       * @param value ChildPost of the type org.semanticwb.portal.resources.sem.forum.Post
       * @return Iterator with all the org.semanticwb.portal.resources.sem.forum.Post
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByChildPost(org.semanticwb.portal.resources.sem.forum.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(frm_haschildPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostBase.ClassMgr getPostClassMgr()
    {
        return new PostBase.ClassMgr();
    }

   /**
   * Constructs a PostBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Post
   */
    public PostBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property Thread
   * @param value Thread to set
   */

    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_pstThread, value.getSemanticObject());
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
        getSemanticObject().removeProperty(frm_pstThread);
    }

   /**
   * Gets the Thread
   * @return a org.semanticwb.portal.resources.sem.forum.Thread
   */
    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_pstThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Body property
* @return String with the Body
*/
    public String getBody()
    {
        return getSemanticObject().getProperty(frm_pstBody);
    }

/**
* Sets the Body property
* @param value long with the Body
*/
    public void setBody(String value)
    {
        getSemanticObject().setProperty(frm_pstBody, value);
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.forum.Attachment
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.forum.Attachment
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(getSemanticObject().listObjectProperties(frm_hasAttachments));
    }

   /**
   * Gets true if has a Attachments
   * @param value org.semanticwb.portal.resources.sem.forum.Attachment to verify
   * @return true if the org.semanticwb.portal.resources.sem.forum.Attachment exists, false otherwise
   */
    public boolean hasAttachments(org.semanticwb.portal.resources.sem.forum.Attachment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasAttachments,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Attachments
   * @return a org.semanticwb.portal.resources.sem.forum.Attachment
   */
    public org.semanticwb.portal.resources.sem.forum.Attachment getAttachments()
    {
         org.semanticwb.portal.resources.sem.forum.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Attachment)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   /**
   * Sets the value for the property ParentPost
   * @param value ParentPost to set
   */

    public void setParentPost(org.semanticwb.portal.resources.sem.forum.Post value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(frm_pstParentPost, value.getSemanticObject());
        }else
        {
            removeParentPost();
        }
    }
   /**
   * Remove the value for ParentPost property
   */

    public void removeParentPost()
    {
        getSemanticObject().removeProperty(frm_pstParentPost);
    }

   /**
   * Gets the ParentPost
   * @return a org.semanticwb.portal.resources.sem.forum.Post
   */
    public org.semanticwb.portal.resources.sem.forum.Post getParentPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_pstParentPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.portal.resources.sem.forum.Post
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.forum.Post
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listchildPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(getSemanticObject().listObjectProperties(frm_haschildPost));
    }

   /**
   * Gets true if has a childPost
   * @param value org.semanticwb.portal.resources.sem.forum.Post to verify
   * @return true if the org.semanticwb.portal.resources.sem.forum.Post exists, false otherwise
   */
    public boolean haschildPost(org.semanticwb.portal.resources.sem.forum.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_haschildPost,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the childPost
   * @return a org.semanticwb.portal.resources.sem.forum.Post
   */
    public org.semanticwb.portal.resources.sem.forum.Post getchildPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_haschildPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
