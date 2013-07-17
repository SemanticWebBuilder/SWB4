package com.infotec.pic.swb.base;


public abstract class QuoteRequestBase extends org.semanticwb.model.SWBClass implements com.infotec.pic.swb.AdditionalFiles,com.infotec.pic.swb.AdditionalImages,org.semanticwb.model.Traceable,com.infotec.pic.swb.AdditionalUrl
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty pic_fromUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#fromUser");
    public static final org.semanticwb.platform.SemanticClass pic_Company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");
    public static final org.semanticwb.platform.SemanticProperty pic_toCompany=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#toCompany");
    public static final org.semanticwb.platform.SemanticProperty pic_payMethod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#payMethod");
    public static final org.semanticwb.platform.SemanticClass pic_INCOTERMS=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#INCOTERMS");
    public static final org.semanticwb.platform.SemanticProperty pic_incoterms=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#incoterms");
    public static final org.semanticwb.platform.SemanticProperty pic_conditions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#conditions");
    public static final org.semanticwb.platform.SemanticProperty pic_insurance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#insurance");
    public static final org.semanticwb.platform.SemanticProperty pic_itemNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#itemNumber");
    public static final org.semanticwb.platform.SemanticProperty pic_additionalNotes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#additionalNotes");
    public static final org.semanticwb.platform.SemanticClass pic_QuoteRequest=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#QuoteRequest");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#QuoteRequest");

    public static class ClassMgr
    {
       /**
       * Returns a list of QuoteRequest for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequests(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.QuoteRequest for all models
       * @return Iterator of com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequests()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.QuoteRequest
       * @param id Identifier for com.infotec.pic.swb.QuoteRequest
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return A com.infotec.pic.swb.QuoteRequest
       */
        public static com.infotec.pic.swb.QuoteRequest getQuoteRequest(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.QuoteRequest)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.QuoteRequest
       * @param id Identifier for com.infotec.pic.swb.QuoteRequest
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return A com.infotec.pic.swb.QuoteRequest
       */
        public static com.infotec.pic.swb.QuoteRequest createQuoteRequest(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.QuoteRequest)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.QuoteRequest
       * @param id Identifier for com.infotec.pic.swb.QuoteRequest
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       */
        public static void removeQuoteRequest(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.QuoteRequest
       * @param id Identifier for com.infotec.pic.swb.QuoteRequest
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return true if the com.infotec.pic.swb.QuoteRequest exists, false otherwise
       */

        public static boolean hasQuoteRequest(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuoteRequest(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined FromUser
       * @param value FromUser of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByFromUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_fromUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined FromUser
       * @param value FromUser of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByFromUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_fromUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined ToCompany
       * @param value ToCompany of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByToCompany(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_toCompany, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined ToCompany
       * @param value ToCompany of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByToCompany(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_toCompany,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined Incoterms
       * @param value Incoterms of the type com.infotec.pic.swb.INCOTERMS
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByIncoterms(com.infotec.pic.swb.INCOTERMS value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_incoterms, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined Incoterms
       * @param value Incoterms of the type com.infotec.pic.swb.INCOTERMS
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByIncoterms(com.infotec.pic.swb.INCOTERMS value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_incoterms,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.QuoteRequest
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.QuoteRequest with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.QuoteRequest
       */

        public static java.util.Iterator<com.infotec.pic.swb.QuoteRequest> listQuoteRequestByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.QuoteRequest> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static QuoteRequestBase.ClassMgr getQuoteRequestClassMgr()
    {
        return new QuoteRequestBase.ClassMgr();
    }

   /**
   * Constructs a QuoteRequestBase with a SemanticObject
   * @param base The SemanticObject with the properties for the QuoteRequest
   */
    public QuoteRequestBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the PayMethod property
* @return String with the PayMethod
*/
    public String getPayMethod()
    {
        return getSemanticObject().getProperty(pic_payMethod);
    }

/**
* Sets the PayMethod property
* @param value long with the PayMethod
*/
    public void setPayMethod(String value)
    {
        getSemanticObject().setProperty(pic_payMethod, value);
    }
   /**
   * Sets the value for the property Incoterms
   * @param value Incoterms to set
   */

    public void setIncoterms(com.infotec.pic.swb.INCOTERMS value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_incoterms, value.getSemanticObject());
        }else
        {
            removeIncoterms();
        }
    }
   /**
   * Remove the value for Incoterms property
   */

    public void removeIncoterms()
    {
        getSemanticObject().removeProperty(pic_incoterms);
    }

   /**
   * Gets the Incoterms
   * @return a com.infotec.pic.swb.INCOTERMS
   */
    public com.infotec.pic.swb.INCOTERMS getIncoterms()
    {
         com.infotec.pic.swb.INCOTERMS ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_incoterms);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.INCOTERMS)obj.createGenericInstance();
         }
         return ret;
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
* Gets the Conditions property
* @return String with the Conditions
*/
    public String getConditions()
    {
        return getSemanticObject().getProperty(pic_conditions);
    }

/**
* Sets the Conditions property
* @param value long with the Conditions
*/
    public void setConditions(String value)
    {
        getSemanticObject().setProperty(pic_conditions, value);
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
* Gets the Insurance property
* @return String with the Insurance
*/
    public String getInsurance()
    {
        return getSemanticObject().getProperty(pic_insurance);
    }

/**
* Sets the Insurance property
* @param value long with the Insurance
*/
    public void setInsurance(String value)
    {
        getSemanticObject().setProperty(pic_insurance, value);
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
* Gets the ItemNumber property
* @return String with the ItemNumber
*/
    public String getItemNumber()
    {
        return getSemanticObject().getProperty(pic_itemNumber);
    }

/**
* Sets the ItemNumber property
* @param value long with the ItemNumber
*/
    public void setItemNumber(String value)
    {
        getSemanticObject().setProperty(pic_itemNumber, value);
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

/**
* Gets the AdditionalNotes property
* @return String with the AdditionalNotes
*/
    public String getAdditionalNotes()
    {
        return getSemanticObject().getProperty(pic_additionalNotes);
    }

/**
* Sets the AdditionalNotes property
* @param value long with the AdditionalNotes
*/
    public void setAdditionalNotes(String value)
    {
        getSemanticObject().setProperty(pic_additionalNotes, value);
    }
}
