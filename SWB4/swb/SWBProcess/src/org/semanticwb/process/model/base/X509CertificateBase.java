package org.semanticwb.process.model.base;


public abstract class X509CertificateBase extends org.semanticwb.process.model.BaseElement implements org.semanticwb.model.Expirable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_X509Subject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#X509Subject");
    public static final org.semanticwb.platform.SemanticProperty swp_X509Name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#X509Name");
    public static final org.semanticwb.platform.SemanticProperty swp_X509Serial=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#X509Serial");
    public static final org.semanticwb.platform.SemanticProperty swp_X509File=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#X509File");
    public static final org.semanticwb.platform.SemanticClass swp_X509Certificate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#X509Certificate");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#X509Certificate");

    public static class ClassMgr
    {
       /**
       * Returns a list of X509Certificate for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.X509Certificate
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509Certificate> listX509Certificates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509Certificate>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.X509Certificate for all models
       * @return Iterator of org.semanticwb.process.model.X509Certificate
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509Certificate> listX509Certificates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509Certificate>(it, true);
        }

        public static org.semanticwb.process.model.X509Certificate createX509Certificate(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.X509Certificate.ClassMgr.createX509Certificate(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.X509Certificate
       * @param id Identifier for org.semanticwb.process.model.X509Certificate
       * @param model Model of the org.semanticwb.process.model.X509Certificate
       * @return A org.semanticwb.process.model.X509Certificate
       */
        public static org.semanticwb.process.model.X509Certificate getX509Certificate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.X509Certificate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.X509Certificate
       * @param id Identifier for org.semanticwb.process.model.X509Certificate
       * @param model Model of the org.semanticwb.process.model.X509Certificate
       * @return A org.semanticwb.process.model.X509Certificate
       */
        public static org.semanticwb.process.model.X509Certificate createX509Certificate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.X509Certificate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.X509Certificate
       * @param id Identifier for org.semanticwb.process.model.X509Certificate
       * @param model Model of the org.semanticwb.process.model.X509Certificate
       */
        public static void removeX509Certificate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.X509Certificate
       * @param id Identifier for org.semanticwb.process.model.X509Certificate
       * @param model Model of the org.semanticwb.process.model.X509Certificate
       * @return true if the org.semanticwb.process.model.X509Certificate exists, false otherwise
       */

        public static boolean hasX509Certificate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getX509Certificate(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.X509Certificate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.X509Certificate
       * @return Iterator with all the org.semanticwb.process.model.X509Certificate
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509Certificate> listX509CertificateByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509Certificate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509Certificate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.X509Certificate
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509Certificate> listX509CertificateByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509Certificate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509Certificate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.X509Certificate
       * @return Iterator with all the org.semanticwb.process.model.X509Certificate
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509Certificate> listX509CertificateByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509Certificate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.X509Certificate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.X509Certificate
       */

        public static java.util.Iterator<org.semanticwb.process.model.X509Certificate> listX509CertificateByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.X509Certificate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static X509CertificateBase.ClassMgr getX509CertificateClassMgr()
    {
        return new X509CertificateBase.ClassMgr();
    }

   /**
   * Constructs a X509CertificateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the X509Certificate
   */
    public X509CertificateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Subject property
* @return String with the Subject
*/
    public String getSubject()
    {
        return getSemanticObject().getProperty(swp_X509Subject);
    }

/**
* Sets the Subject property
* @param value long with the Subject
*/
    public void setSubject(String value)
    {
        getSemanticObject().setProperty(swp_X509Subject, value);
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
* Gets the Name property
* @return String with the Name
*/
    public String getName()
    {
        return getSemanticObject().getProperty(swp_X509Name);
    }

/**
* Sets the Name property
* @param value long with the Name
*/
    public void setName(String value)
    {
        getSemanticObject().setProperty(swp_X509Name, value);
    }

/**
* Gets the Expiration property
* @return java.util.Date with the Expiration
*/
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
* Sets the Expiration property
* @param value long with the Expiration
*/
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
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
* Gets the Serial property
* @return String with the Serial
*/
    public String getSerial()
    {
        return getSemanticObject().getProperty(swp_X509Serial);
    }

/**
* Sets the Serial property
* @param value long with the Serial
*/
    public void setSerial(String value)
    {
        getSemanticObject().setProperty(swp_X509Serial, value);
    }

/**
* Gets the File property
* @return String with the File
*/
    public String getFile()
    {
        return getSemanticObject().getProperty(swp_X509File);
    }

/**
* Sets the File property
* @param value long with the File
*/
    public void setFile(String value)
    {
        getSemanticObject().setProperty(swp_X509File, value);
    }
}
