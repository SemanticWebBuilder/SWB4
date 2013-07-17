package com.infotec.pic.swb.base;


public abstract class ContactBase extends org.semanticwb.model.SWBClass implements com.infotec.pic.swb.AdditionalContactData
{
    public static final org.semanticwb.platform.SemanticClass pic_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Post");
    public static final org.semanticwb.platform.SemanticProperty pic_hasBookmarks=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasBookmarks");
    public static final org.semanticwb.platform.SemanticClass pic_Company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");
    public static final org.semanticwb.platform.SemanticProperty pic_hasFollowingContactsInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasFollowingContactsInv");
    public static final org.semanticwb.platform.SemanticProperty pic_hasOportunityBussiness=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasOportunityBussiness");
    public static final org.semanticwb.platform.SemanticProperty pic_jobTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#jobTitle");
    public static final org.semanticwb.platform.SemanticProperty pic_topics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#topics");
   /**
   * Tipo de empresa
   */
    public static final org.semanticwb.platform.SemanticClass pic_CompanyType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CompanyType");
    public static final org.semanticwb.platform.SemanticProperty pic_companyType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#companyType");
    public static final org.semanticwb.platform.SemanticProperty pic_invCompany=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#invCompany");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty pic_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#user");
    public static final org.semanticwb.platform.SemanticClass pic_Contact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Contact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Contact");

    public static class ClassMgr
    {
       /**
       * Returns a list of Contact for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Contact for all models
       * @return Iterator of com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.Contact
       * @param id Identifier for com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return A com.infotec.pic.swb.Contact
       */
        public static com.infotec.pic.swb.Contact getContact(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Contact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Contact
       * @param id Identifier for com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return A com.infotec.pic.swb.Contact
       */
        public static com.infotec.pic.swb.Contact createContact(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Contact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Contact
       * @param id Identifier for com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Contact
       */
        public static void removeContact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Contact
       * @param id Identifier for com.infotec.pic.swb.Contact
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return true if the com.infotec.pic.swb.Contact exists, false otherwise
       */

        public static boolean hasContact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getContact(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined Bookmarks
       * @param value Bookmarks of the type com.infotec.pic.swb.Post
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByBookmarks(com.infotec.pic.swb.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasBookmarks, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined Bookmarks
       * @param value Bookmarks of the type com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByBookmarks(com.infotec.pic.swb.Post value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasBookmarks,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined FollowingContactsInv
       * @param value FollowingContactsInv of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByFollowingContactsInv(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasFollowingContactsInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined FollowingContactsInv
       * @param value FollowingContactsInv of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByFollowingContactsInv(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasFollowingContactsInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined Phone
       * @param value Phone of the type com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByPhone(com.infotec.pic.swb.Phone value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasPhone, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined Phone
       * @param value Phone of the type com.infotec.pic.swb.Phone
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByPhone(com.infotec.pic.swb.Phone value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasPhone,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined Social
       * @param value Social of the type com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactBySocial(com.infotec.pic.swb.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasSocial, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined Social
       * @param value Social of the type com.infotec.pic.swb.SocialNetwork
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactBySocial(com.infotec.pic.swb.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasSocial,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined CompanyType
       * @param value CompanyType of the type com.infotec.pic.swb.CompanyType
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByCompanyType(com.infotec.pic.swb.CompanyType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_companyType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined CompanyType
       * @param value CompanyType of the type com.infotec.pic.swb.CompanyType
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByCompanyType(com.infotec.pic.swb.CompanyType value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_companyType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined InvCompany
       * @param value InvCompany of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByInvCompany(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_invCompany, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined InvCompany
       * @param value InvCompany of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByInvCompany(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_invCompany,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Contact
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_user, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Contact with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Contact
       */

        public static java.util.Iterator<com.infotec.pic.swb.Contact> listContactByUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Contact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_user,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ContactBase.ClassMgr getContactClassMgr()
    {
        return new ContactBase.ClassMgr();
    }

   /**
   * Constructs a ContactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Contact
   */
    public ContactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the com.infotec.pic.swb.Post
   * @return A GenericIterator with all the com.infotec.pic.swb.Post
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> listBookmarkses()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post>(getSemanticObject().listObjectProperties(pic_hasBookmarks));
    }

   /**
   * Gets true if has a Bookmarks
   * @param value com.infotec.pic.swb.Post to verify
   * @return true if the com.infotec.pic.swb.Post exists, false otherwise
   */
    public boolean hasBookmarks(com.infotec.pic.swb.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasBookmarks,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Bookmarks
   * @param value com.infotec.pic.swb.Post to add
   */

    public void addBookmarks(com.infotec.pic.swb.Post value)
    {
        getSemanticObject().addObjectProperty(pic_hasBookmarks, value.getSemanticObject());
    }
   /**
   * Removes all the Bookmarks
   */

    public void removeAllBookmarks()
    {
        getSemanticObject().removeProperty(pic_hasBookmarks);
    }
   /**
   * Removes a Bookmarks
   * @param value com.infotec.pic.swb.Post to remove
   */

    public void removeBookmarks(com.infotec.pic.swb.Post value)
    {
        getSemanticObject().removeObjectProperty(pic_hasBookmarks,value.getSemanticObject());
    }

   /**
   * Gets the Bookmarks
   * @return a com.infotec.pic.swb.Post
   */
    public com.infotec.pic.swb.Post getBookmarks()
    {
         com.infotec.pic.swb.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasBookmarks);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Post)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the com.infotec.pic.swb.Company
   * @return A GenericIterator with all the com.infotec.pic.swb.Company
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company> listFollowingContactsInvs()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Company>(getSemanticObject().listObjectProperties(pic_hasFollowingContactsInv));
    }

   /**
   * Gets true if has a FollowingContactsInv
   * @param value com.infotec.pic.swb.Company to verify
   * @return true if the com.infotec.pic.swb.Company exists, false otherwise
   */
    public boolean hasFollowingContactsInv(com.infotec.pic.swb.Company value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasFollowingContactsInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the FollowingContactsInv
   * @return a com.infotec.pic.swb.Company
   */
    public com.infotec.pic.swb.Company getFollowingContactsInv()
    {
         com.infotec.pic.swb.Company ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasFollowingContactsInv);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Company)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the com.infotec.pic.swb.Phone
   * @return A GenericIterator with all the com.infotec.pic.swb.Phone
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone> listPhones()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone>(getSemanticObject().listObjectProperties(pic_hasPhone));
    }

   /**
   * Gets true if has a Phone
   * @param value com.infotec.pic.swb.Phone to verify
   * @return true if the com.infotec.pic.swb.Phone exists, false otherwise
   */
    public boolean hasPhone(com.infotec.pic.swb.Phone value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasPhone,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Phone
   * @param value com.infotec.pic.swb.Phone to add
   */

    public void addPhone(com.infotec.pic.swb.Phone value)
    {
        getSemanticObject().addObjectProperty(pic_hasPhone, value.getSemanticObject());
    }
   /**
   * Removes all the Phone
   */

    public void removeAllPhone()
    {
        getSemanticObject().removeProperty(pic_hasPhone);
    }
   /**
   * Removes a Phone
   * @param value com.infotec.pic.swb.Phone to remove
   */

    public void removePhone(com.infotec.pic.swb.Phone value)
    {
        getSemanticObject().removeObjectProperty(pic_hasPhone,value.getSemanticObject());
    }

   /**
   * Gets the Phone
   * @return a com.infotec.pic.swb.Phone
   */
    public com.infotec.pic.swb.Phone getPhone()
    {
         com.infotec.pic.swb.Phone ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasPhone);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Phone)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the JobTitle property
* @return String with the JobTitle
*/
    public String getJobTitle()
    {
        return getSemanticObject().getProperty(pic_jobTitle);
    }

/**
* Sets the JobTitle property
* @param value long with the JobTitle
*/
    public void setJobTitle(String value)
    {
        getSemanticObject().setProperty(pic_jobTitle, value);
    }
   /**
   * Gets all the com.infotec.pic.swb.SocialNetwork
   * @return A GenericIterator with all the com.infotec.pic.swb.SocialNetwork
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> listSocials()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork>(getSemanticObject().listObjectProperties(pic_hasSocial));
    }

   /**
   * Gets true if has a Social
   * @param value com.infotec.pic.swb.SocialNetwork to verify
   * @return true if the com.infotec.pic.swb.SocialNetwork exists, false otherwise
   */
    public boolean hasSocial(com.infotec.pic.swb.SocialNetwork value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasSocial,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Social
   * @param value com.infotec.pic.swb.SocialNetwork to add
   */

    public void addSocial(com.infotec.pic.swb.SocialNetwork value)
    {
        getSemanticObject().addObjectProperty(pic_hasSocial, value.getSemanticObject());
    }
   /**
   * Removes all the Social
   */

    public void removeAllSocial()
    {
        getSemanticObject().removeProperty(pic_hasSocial);
    }
   /**
   * Removes a Social
   * @param value com.infotec.pic.swb.SocialNetwork to remove
   */

    public void removeSocial(com.infotec.pic.swb.SocialNetwork value)
    {
        getSemanticObject().removeObjectProperty(pic_hasSocial,value.getSemanticObject());
    }

   /**
   * Gets the Social
   * @return a com.infotec.pic.swb.SocialNetwork
   */
    public com.infotec.pic.swb.SocialNetwork getSocial()
    {
         com.infotec.pic.swb.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasSocial);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Topics property
* @return String with the Topics
*/
    public String getTopics()
    {
        return getSemanticObject().getProperty(pic_topics);
    }

/**
* Sets the Topics property
* @param value long with the Topics
*/
    public void setTopics(String value)
    {
        getSemanticObject().setProperty(pic_topics, value);
    }

    public java.util.Iterator<String> listEmails()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasEmail);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addEmail(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasEmail, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllEmail()
    {
        getSemanticObject().removeProperty(pic_hasEmail);
    }

    public void removeEmail(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasEmail,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Sets the value for the property CompanyType
   * @param value CompanyType to set
   */

    public void setCompanyType(com.infotec.pic.swb.CompanyType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_companyType, value.getSemanticObject());
        }else
        {
            removeCompanyType();
        }
    }
   /**
   * Remove the value for CompanyType property
   */

    public void removeCompanyType()
    {
        getSemanticObject().removeProperty(pic_companyType);
    }

   /**
   * Gets the CompanyType
   * @return a com.infotec.pic.swb.CompanyType
   */
    public com.infotec.pic.swb.CompanyType getCompanyType()
    {
         com.infotec.pic.swb.CompanyType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_companyType);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.CompanyType)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property InvCompany
   * @param value InvCompany to set
   */

    public void setInvCompany(com.infotec.pic.swb.Company value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_invCompany, value.getSemanticObject());
        }else
        {
            removeInvCompany();
        }
    }
   /**
   * Remove the value for InvCompany property
   */

    public void removeInvCompany()
    {
        getSemanticObject().removeProperty(pic_invCompany);
    }

   /**
   * Gets the InvCompany
   * @return a com.infotec.pic.swb.Company
   */
    public com.infotec.pic.swb.Company getInvCompany()
    {
         com.infotec.pic.swb.Company ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_invCompany);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Company)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property User
   * @param value User to set
   */

    public void setUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_user, value.getSemanticObject());
        }else
        {
            removeUser();
        }
    }
   /**
   * Remove the value for User property
   */

    public void removeUser()
    {
        getSemanticObject().removeProperty(pic_user);
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_user);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
