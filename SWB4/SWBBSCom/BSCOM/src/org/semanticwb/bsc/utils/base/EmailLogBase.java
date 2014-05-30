package org.semanticwb.bsc.utils.base;


   /**
   * Contiene la bitácora de correos enviados desde la aplicación 
   */
public abstract class EmailLogBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasTo");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasCc=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasCc");
    public static final org.semanticwb.platform.SemanticProperty bsc_otherAccounts=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#otherAccounts");
    public static final org.semanticwb.platform.SemanticProperty bsc_subject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#subject");
    public static final org.semanticwb.platform.SemanticProperty bsc_from=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#from");
    public static final org.semanticwb.platform.SemanticProperty bsc_date=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#date");
    public static final org.semanticwb.platform.SemanticProperty bsc_message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#message");
   /**
   * Contiene la bitácora de correos enviados desde la aplicación
   */
    public static final org.semanticwb.platform.SemanticClass bsc_EmailLog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#EmailLog");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#EmailLog");

    public static class ClassMgr
    {
       /**
       * Returns a list of EmailLog for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.utils.EmailLog for all models
       * @return Iterator of org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog>(it, true);
        }

        public static org.semanticwb.bsc.utils.EmailLog createEmailLog(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.utils.EmailLog.ClassMgr.createEmailLog(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.utils.EmailLog
       * @param id Identifier for org.semanticwb.bsc.utils.EmailLog
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       * @return A org.semanticwb.bsc.utils.EmailLog
       */
        public static org.semanticwb.bsc.utils.EmailLog getEmailLog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.utils.EmailLog)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.utils.EmailLog
       * @param id Identifier for org.semanticwb.bsc.utils.EmailLog
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       * @return A org.semanticwb.bsc.utils.EmailLog
       */
        public static org.semanticwb.bsc.utils.EmailLog createEmailLog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.utils.EmailLog)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.utils.EmailLog
       * @param id Identifier for org.semanticwb.bsc.utils.EmailLog
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       */
        public static void removeEmailLog(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.utils.EmailLog
       * @param id Identifier for org.semanticwb.bsc.utils.EmailLog
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       * @return true if the org.semanticwb.bsc.utils.EmailLog exists, false otherwise
       */

        public static boolean hasEmailLog(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEmailLog(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.utils.EmailLog with a determined To
       * @param value To of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       * @return Iterator with all the org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogByTo(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasTo, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.EmailLog with a determined To
       * @param value To of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogByTo(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasTo,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.EmailLog with a determined Cc
       * @param value Cc of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       * @return Iterator with all the org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogByCc(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCc, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.EmailLog with a determined Cc
       * @param value Cc of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogByCc(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCc,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.EmailLog with a determined From
       * @param value From of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.EmailLog
       * @return Iterator with all the org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogByFrom(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_from, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.EmailLog with a determined From
       * @param value From of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.EmailLog
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.EmailLog> listEmailLogByFrom(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.EmailLog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_from,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EmailLogBase.ClassMgr getEmailLogClassMgr()
    {
        return new EmailLogBase.ClassMgr();
    }

   /**
   * Constructs a EmailLogBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EmailLog
   */
    public EmailLogBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.model.User
   * @return A GenericIterator with all the org.semanticwb.model.User
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listTos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(bsc_hasTo));
    }

   /**
   * Gets true if has a To
   * @param value org.semanticwb.model.User to verify
   * @return true if the org.semanticwb.model.User exists, false otherwise
   */
    public boolean hasTo(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasTo,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a To
   * @param value org.semanticwb.model.User to add
   */

    public void addTo(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(bsc_hasTo, value.getSemanticObject());
    }
   /**
   * Removes all the To
   */

    public void removeAllTo()
    {
        getSemanticObject().removeProperty(bsc_hasTo);
    }
   /**
   * Removes a To
   * @param value org.semanticwb.model.User to remove
   */

    public void removeTo(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasTo,value.getSemanticObject());
    }

   /**
   * Gets the To
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getTo()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasTo);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.User
   * @return A GenericIterator with all the org.semanticwb.model.User
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listCcs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(bsc_hasCc));
    }

   /**
   * Gets true if has a Cc
   * @param value org.semanticwb.model.User to verify
   * @return true if the org.semanticwb.model.User exists, false otherwise
   */
    public boolean hasCc(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasCc,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Cc
   * @param value org.semanticwb.model.User to add
   */

    public void addCc(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(bsc_hasCc, value.getSemanticObject());
    }
   /**
   * Removes all the Cc
   */

    public void removeAllCc()
    {
        getSemanticObject().removeProperty(bsc_hasCc);
    }
   /**
   * Removes a Cc
   * @param value org.semanticwb.model.User to remove
   */

    public void removeCc(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasCc,value.getSemanticObject());
    }

   /**
   * Gets the Cc
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCc()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasCc);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the OtherAccounts property
* @return String with the OtherAccounts
*/
    public String getOtherAccounts()
    {
        return getSemanticObject().getProperty(bsc_otherAccounts);
    }

/**
* Sets the OtherAccounts property
* @param value long with the OtherAccounts
*/
    public void setOtherAccounts(String value)
    {
        getSemanticObject().setProperty(bsc_otherAccounts, value);
    }

/**
* Gets the Subject property
* @return String with the Subject
*/
    public String getSubject()
    {
        return getSemanticObject().getProperty(bsc_subject);
    }

/**
* Sets the Subject property
* @param value long with the Subject
*/
    public void setSubject(String value)
    {
        getSemanticObject().setProperty(bsc_subject, value);
    }
   /**
   * Sets the value for the property From
   * @param value From to set
   */

    public void setFrom(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_from, value.getSemanticObject());
        }else
        {
            removeFrom();
        }
    }
   /**
   * Remove the value for From property
   */

    public void removeFrom()
    {
        getSemanticObject().removeProperty(bsc_from);
    }

   /**
   * Gets the From
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getFrom()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_from);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Date property
* @return java.util.Date with the Date
*/
    public java.util.Date getDate()
    {
        return getSemanticObject().getDateProperty(bsc_date);
    }

/**
* Sets the Date property
* @param value long with the Date
*/
    public void setDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_date, value);
    }

/**
* Gets the Message property
* @return String with the Message
*/
    public String getMessage()
    {
        return getSemanticObject().getProperty(bsc_message);
    }

/**
* Sets the Message property
* @param value long with the Message
*/
    public void setMessage(String value)
    {
        getSemanticObject().setProperty(bsc_message, value);
    }
}
