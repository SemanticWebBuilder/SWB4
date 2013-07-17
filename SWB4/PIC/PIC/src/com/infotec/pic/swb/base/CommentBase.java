package com.infotec.pic.swb.base;


public abstract class CommentBase extends org.semanticwb.model.SWBClass implements com.infotec.pic.swb.AdditionalFiles,com.infotec.pic.swb.AdditionalImages,org.semanticwb.model.Traceable,com.infotec.pic.swb.AdditionalUrl
{
    public static final org.semanticwb.platform.SemanticProperty pic_comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#comment");
    public static final org.semanticwb.platform.SemanticClass pic_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Comment");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Comment");

    public static class ClassMgr
    {
       /**
       * Returns a list of Comment for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Comment
       */

        public static java.util.Iterator<com.infotec.pic.swb.Comment> listComments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Comment for all models
       * @return Iterator of com.infotec.pic.swb.Comment
       */

        public static java.util.Iterator<com.infotec.pic.swb.Comment> listComments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.Comment
       * @param id Identifier for com.infotec.pic.swb.Comment
       * @param model Model of the com.infotec.pic.swb.Comment
       * @return A com.infotec.pic.swb.Comment
       */
        public static com.infotec.pic.swb.Comment getComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Comment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Comment
       * @param id Identifier for com.infotec.pic.swb.Comment
       * @param model Model of the com.infotec.pic.swb.Comment
       * @return A com.infotec.pic.swb.Comment
       */
        public static com.infotec.pic.swb.Comment createComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Comment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Comment
       * @param id Identifier for com.infotec.pic.swb.Comment
       * @param model Model of the com.infotec.pic.swb.Comment
       */
        public static void removeComment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Comment
       * @param id Identifier for com.infotec.pic.swb.Comment
       * @param model Model of the com.infotec.pic.swb.Comment
       * @return true if the com.infotec.pic.swb.Comment exists, false otherwise
       */

        public static boolean hasComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComment(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Comment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Comment
       * @return Iterator with all the com.infotec.pic.swb.Comment
       */

        public static java.util.Iterator<com.infotec.pic.swb.Comment> listCommentByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Comment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Comment
       */

        public static java.util.Iterator<com.infotec.pic.swb.Comment> listCommentByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Comment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Comment
       * @return Iterator with all the com.infotec.pic.swb.Comment
       */

        public static java.util.Iterator<com.infotec.pic.swb.Comment> listCommentByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Comment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Comment
       */

        public static java.util.Iterator<com.infotec.pic.swb.Comment> listCommentByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CommentBase.ClassMgr getCommentClassMgr()
    {
        return new CommentBase.ClassMgr();
    }

   /**
   * Constructs a CommentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Comment
   */
    public CommentBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Url property
* @return String with the Url
*/
    public String getUrl()
    {
        return getSemanticObject().getProperty(pic_url);
    }

/**
* Sets the Url property
* @param value long with the Url
*/
    public void setUrl(String value)
    {
        getSemanticObject().setProperty(pic_url, value);
    }

    public java.util.Iterator<String> listFiles()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasFile);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addFile(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasFile, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllFile()
    {
        getSemanticObject().removeProperty(pic_hasFile);
    }

    public void removeFile(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasFile,new org.semanticwb.platform.SemanticLiteral(value));
    }

/**
* Gets the Comment property
* @return String with the Comment
*/
    public String getComment()
    {
        return getSemanticObject().getProperty(pic_comment);
    }

/**
* Sets the Comment property
* @param value long with the Comment
*/
    public void setComment(String value)
    {
        getSemanticObject().setProperty(pic_comment, value);
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

    public java.util.Iterator<String> listImages()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasImage);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addImage(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasImage, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllImage()
    {
        getSemanticObject().removeProperty(pic_hasImage);
    }

    public void removeImage(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasImage,new org.semanticwb.platform.SemanticLiteral(value));
    }
}
