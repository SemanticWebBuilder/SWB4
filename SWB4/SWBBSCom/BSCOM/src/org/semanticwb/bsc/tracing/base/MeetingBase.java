package org.semanticwb.bsc.tracing.base;


   /**
   * Persiste la información de una Sesión. Existen  dos tipos de sesiones: RAE y NOA 
   */
public abstract class MeetingBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Roleable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Persiste información de dos dígitos. El número de sesiones se reinicia con el número de año actual.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_meetingSerial=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#meetingSerial");
   /**
   * Almacena la clasificación de sesiones: RAE y NOA.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_meetingType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#meetingType");
   /**
   * Define las características de un Acuerdo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Agreement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Agreement");
   /**
   * Persiste información de los acuerdos asociados a una sesión
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasAgreement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasAgreement");
   /**
   * Persiste la fecha en que se lleva acabo la sesión
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_meetingDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#meetingDate");
   /**
   * Persiste la información de una Sesión. Existen  dos tipos de sesiones: RAE y NOA
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Meeting=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Meeting");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Meeting");

    public static class ClassMgr
    {
       /**
       * Returns a list of Meeting for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Meeting for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Meeting createMeeting(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Meeting.ClassMgr.createMeeting(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Meeting
       * @param id Identifier for org.semanticwb.bsc.tracing.Meeting
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return A org.semanticwb.bsc.tracing.Meeting
       */
        public static org.semanticwb.bsc.tracing.Meeting getMeeting(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Meeting)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Meeting
       * @param id Identifier for org.semanticwb.bsc.tracing.Meeting
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return A org.semanticwb.bsc.tracing.Meeting
       */
        public static org.semanticwb.bsc.tracing.Meeting createMeeting(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Meeting)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Meeting
       * @param id Identifier for org.semanticwb.bsc.tracing.Meeting
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       */
        public static void removeMeeting(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Meeting
       * @param id Identifier for org.semanticwb.bsc.tracing.Meeting
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return true if the org.semanticwb.bsc.tracing.Meeting exists, false otherwise
       */

        public static boolean hasMeeting(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeeting(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined Agreement
       * @param value Agreement of the type org.semanticwb.bsc.element.Agreement
       * @param model Model of the org.semanticwb.bsc.tracing.Meeting
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByAgreement(org.semanticwb.bsc.element.Agreement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasAgreement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Meeting with a determined Agreement
       * @param value Agreement of the type org.semanticwb.bsc.element.Agreement
       * @return Iterator with all the org.semanticwb.bsc.tracing.Meeting
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Meeting> listMeetingByAgreement(org.semanticwb.bsc.element.Agreement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Meeting> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasAgreement,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MeetingBase.ClassMgr getMeetingClassMgr()
    {
        return new MeetingBase.ClassMgr();
    }

   /**
   * Constructs a MeetingBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Meeting
   */
    public MeetingBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the MeetingSerial property
* @return String with the MeetingSerial
*/
    public String getMeetingSerial()
    {
        //Override this method in Meeting object
        return getSemanticObject().getProperty(bsc_meetingSerial,false);
    }

/**
* Sets the MeetingSerial property
* @param value long with the MeetingSerial
*/
    public void setMeetingSerial(String value)
    {
        //Override this method in Meeting object
        getSemanticObject().setProperty(bsc_meetingSerial, value,false);
    }

/**
* Gets the MeetingType property
* @return String with the MeetingType
*/
    public String getMeetingType()
    {
        return getSemanticObject().getProperty(bsc_meetingType);
    }

/**
* Sets the MeetingType property
* @param value long with the MeetingType
*/
    public void setMeetingType(String value)
    {
        getSemanticObject().setProperty(bsc_meetingType, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Agreement
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Agreement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement> listAgreements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Agreement>(getSemanticObject().listObjectProperties(bsc_hasAgreement));
    }

   /**
   * Gets true if has a Agreement
   * @param value org.semanticwb.bsc.element.Agreement to verify
   * @return true if the org.semanticwb.bsc.element.Agreement exists, false otherwise
   */
    public boolean hasAgreement(org.semanticwb.bsc.element.Agreement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasAgreement,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Agreement
   * @param value org.semanticwb.bsc.element.Agreement to add
   */

    public void addAgreement(org.semanticwb.bsc.element.Agreement value)
    {
        getSemanticObject().addObjectProperty(bsc_hasAgreement, value.getSemanticObject());
    }
   /**
   * Removes all the Agreement
   */

    public void removeAllAgreement()
    {
        getSemanticObject().removeProperty(bsc_hasAgreement);
    }
   /**
   * Removes a Agreement
   * @param value org.semanticwb.bsc.element.Agreement to remove
   */

    public void removeAgreement(org.semanticwb.bsc.element.Agreement value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasAgreement,value.getSemanticObject());
    }

   /**
   * Gets the Agreement
   * @return a org.semanticwb.bsc.element.Agreement
   */
    public org.semanticwb.bsc.element.Agreement getAgreement()
    {
         org.semanticwb.bsc.element.Agreement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasAgreement);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Agreement)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the MeetingDate property
* @return java.util.Date with the MeetingDate
*/
    public java.util.Date getMeetingDate()
    {
        return getSemanticObject().getDateProperty(bsc_meetingDate);
    }

/**
* Sets the MeetingDate property
* @param value long with the MeetingDate
*/
    public void setMeetingDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_meetingDate, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
