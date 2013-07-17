package com.infotec.pic.swb.base;


public abstract class InfoRequestBase extends org.semanticwb.model.SWBClass implements com.infotec.pic.swb.AdditionalFiles,com.infotec.pic.swb.AdditionalImages,org.semanticwb.model.Traceable,com.infotec.pic.swb.AdditionalUrl
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty pic_fromUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#fromUser");
    public static final org.semanticwb.platform.SemanticClass pic_AnswerFiles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AnswerFiles");
    public static final org.semanticwb.platform.SemanticProperty pic_answerFiles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#answerFiles");
    public static final org.semanticwb.platform.SemanticClass pic_Company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");
    public static final org.semanticwb.platform.SemanticProperty pic_toCompany=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#toCompany");
    public static final org.semanticwb.platform.SemanticClass pic_StatusType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#StatusType");
    public static final org.semanticwb.platform.SemanticProperty pic_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#status");
    public static final org.semanticwb.platform.SemanticProperty pic_answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#answer");
    public static final org.semanticwb.platform.SemanticProperty pic_infoComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#infoComment");
    public static final org.semanticwb.platform.SemanticProperty pic_infoSubject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#infoSubject");
    public static final org.semanticwb.platform.SemanticProperty pic_infoContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#infoContent");
    public static final org.semanticwb.platform.SemanticClass pic_InfoRequest=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#InfoRequest");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#InfoRequest");

    public static class ClassMgr
    {
       /**
       * Returns a list of InfoRequest for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequests(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.InfoRequest for all models
       * @return Iterator of com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequests()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest>(it, true);
        }

        public static com.infotec.pic.swb.InfoRequest createInfoRequest(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.InfoRequest.ClassMgr.createInfoRequest(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.InfoRequest
       * @param id Identifier for com.infotec.pic.swb.InfoRequest
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return A com.infotec.pic.swb.InfoRequest
       */
        public static com.infotec.pic.swb.InfoRequest getInfoRequest(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.InfoRequest)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.InfoRequest
       * @param id Identifier for com.infotec.pic.swb.InfoRequest
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return A com.infotec.pic.swb.InfoRequest
       */
        public static com.infotec.pic.swb.InfoRequest createInfoRequest(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.InfoRequest)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.InfoRequest
       * @param id Identifier for com.infotec.pic.swb.InfoRequest
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       */
        public static void removeInfoRequest(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.InfoRequest
       * @param id Identifier for com.infotec.pic.swb.InfoRequest
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return true if the com.infotec.pic.swb.InfoRequest exists, false otherwise
       */

        public static boolean hasInfoRequest(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInfoRequest(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined FromUser
       * @param value FromUser of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByFromUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_fromUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined FromUser
       * @param value FromUser of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByFromUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_fromUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined AnswerFiles
       * @param value AnswerFiles of the type com.infotec.pic.swb.AnswerFiles
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByAnswerFiles(com.infotec.pic.swb.AnswerFiles value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_answerFiles, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined AnswerFiles
       * @param value AnswerFiles of the type com.infotec.pic.swb.AnswerFiles
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByAnswerFiles(com.infotec.pic.swb.AnswerFiles value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_answerFiles,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined ToCompany
       * @param value ToCompany of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByToCompany(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_toCompany, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined ToCompany
       * @param value ToCompany of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByToCompany(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_toCompany,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined Status
       * @param value Status of the type com.infotec.pic.swb.StatusType
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByStatus(com.infotec.pic.swb.StatusType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_status, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined Status
       * @param value Status of the type com.infotec.pic.swb.StatusType
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByStatus(com.infotec.pic.swb.StatusType value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_status,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.InfoRequest
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.InfoRequest with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.InfoRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.InfoRequest> listInfoRequestByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.InfoRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static InfoRequestBase.ClassMgr getInfoRequestClassMgr()
    {
        return new InfoRequestBase.ClassMgr();
    }

   /**
   * Constructs a InfoRequestBase with a SemanticObject
   * @param base The SemanticObject with the properties for the InfoRequest
   */
    public InfoRequestBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property FromUser
   * @param value FromUser to set
   */

    public void setFromUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_fromUser, value.getSemanticObject());
        }else
        {
            removeFromUser();
        }
    }
   /**
   * Remove the value for FromUser property
   */

    public void removeFromUser()
    {
        getSemanticObject().removeProperty(pic_fromUser);
    }

   /**
   * Gets the FromUser
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getFromUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_fromUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property AnswerFiles
   * @param value AnswerFiles to set
   */

    public void setAnswerFiles(com.infotec.pic.swb.AnswerFiles value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_answerFiles, value.getSemanticObject());
        }else
        {
            removeAnswerFiles();
        }
    }
   /**
   * Remove the value for AnswerFiles property
   */

    public void removeAnswerFiles()
    {
        getSemanticObject().removeProperty(pic_answerFiles);
    }

   /**
   * Gets the AnswerFiles
   * @return a com.infotec.pic.swb.AnswerFiles
   */
    public com.infotec.pic.swb.AnswerFiles getAnswerFiles()
    {
         com.infotec.pic.swb.AnswerFiles ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_answerFiles);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.AnswerFiles)obj.createGenericInstance();
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
   * Sets the value for the property ToCompany
   * @param value ToCompany to set
   */

    public void setToCompany(com.infotec.pic.swb.Company value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_toCompany, value.getSemanticObject());
        }else
        {
            removeToCompany();
        }
    }
   /**
   * Remove the value for ToCompany property
   */

    public void removeToCompany()
    {
        getSemanticObject().removeProperty(pic_toCompany);
    }

   /**
   * Gets the ToCompany
   * @return a com.infotec.pic.swb.Company
   */
    public com.infotec.pic.swb.Company getToCompany()
    {
         com.infotec.pic.swb.Company ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_toCompany);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Company)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Status
   * @param value Status to set
   */

    public void setStatus(com.infotec.pic.swb.StatusType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_status, value.getSemanticObject());
        }else
        {
            removeStatus();
        }
    }
   /**
   * Remove the value for Status property
   */

    public void removeStatus()
    {
        getSemanticObject().removeProperty(pic_status);
    }

   /**
   * Gets the Status
   * @return a com.infotec.pic.swb.StatusType
   */
    public com.infotec.pic.swb.StatusType getStatus()
    {
         com.infotec.pic.swb.StatusType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_status);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.StatusType)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Answer property
* @return String with the Answer
*/
    public String getAnswer()
    {
        return getSemanticObject().getProperty(pic_answer);
    }

/**
* Sets the Answer property
* @param value long with the Answer
*/
    public void setAnswer(String value)
    {
        getSemanticObject().setProperty(pic_answer, value);
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

/**
* Gets the InfoComment property
* @return String with the InfoComment
*/
    public String getInfoComment()
    {
        return getSemanticObject().getProperty(pic_infoComment);
    }

/**
* Sets the InfoComment property
* @param value long with the InfoComment
*/
    public void setInfoComment(String value)
    {
        getSemanticObject().setProperty(pic_infoComment, value);
    }

/**
* Gets the InfoSubject property
* @return String with the InfoSubject
*/
    public String getInfoSubject()
    {
        return getSemanticObject().getProperty(pic_infoSubject);
    }

/**
* Sets the InfoSubject property
* @param value long with the InfoSubject
*/
    public void setInfoSubject(String value)
    {
        getSemanticObject().setProperty(pic_infoSubject, value);
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
* Gets the InfoContent property
* @return String with the InfoContent
*/
    public String getInfoContent()
    {
        return getSemanticObject().getProperty(pic_infoContent);
    }

/**
* Sets the InfoContent property
* @param value long with the InfoContent
*/
    public void setInfoContent(String value)
    {
        getSemanticObject().setProperty(pic_infoContent, value);
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
