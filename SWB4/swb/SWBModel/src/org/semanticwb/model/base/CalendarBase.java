package org.semanticwb.model.base;


   /**
   * Objeto de calendarización que permite configurar una página o un recurso para desplegarse en cierta fecha, entre un rango de fechas o incluso en periodos de tiempo definidos. 
   */
public abstract class CalendarBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Filterable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.XMLable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable
{
   /**
   * Referencia a un objeto de tipo Calendar
   */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRefInv");
   /**
   * Objeto de calendarización que permite configurar una página o un recurso para desplegarse en cierta fecha, entre un rango de fechas o incluso en periodos de tiempo definidos.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");

    public static class ClassMgr
    {
       /**
       * Returns a list of Calendar for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendars(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Calendar for all models
       * @return Iterator of org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendars()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar>(it, true);
        }

        public static org.semanticwb.model.Calendar createCalendar(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Calendar.ClassMgr.createCalendar(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Calendar
       * @param id Identifier for org.semanticwb.model.Calendar
       * @param model Model of the org.semanticwb.model.Calendar
       * @return A org.semanticwb.model.Calendar
       */
        public static org.semanticwb.model.Calendar getCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Calendar)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Calendar
       * @param id Identifier for org.semanticwb.model.Calendar
       * @param model Model of the org.semanticwb.model.Calendar
       * @return A org.semanticwb.model.Calendar
       */
        public static org.semanticwb.model.Calendar createCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Calendar)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Calendar
       * @param id Identifier for org.semanticwb.model.Calendar
       * @param model Model of the org.semanticwb.model.Calendar
       */
        public static void removeCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Calendar
       * @param id Identifier for org.semanticwb.model.Calendar
       * @param model Model of the org.semanticwb.model.Calendar
       * @return true if the org.semanticwb.model.Calendar exists, false otherwise
       */

        public static boolean hasCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCalendar(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Calendar with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Calendar
       * @return Iterator with all the org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendarByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Calendar with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendarByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Calendar with a determined CalendarRefInv
       * @param value CalendarRefInv of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.Calendar
       * @return Iterator with all the org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendarByCalendarRefInv(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Calendar with a determined CalendarRefInv
       * @param value CalendarRefInv of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendarByCalendarRefInv(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Calendar with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Calendar
       * @return Iterator with all the org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendarByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Calendar with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Calendar
       */

        public static java.util.Iterator<org.semanticwb.model.Calendar> listCalendarByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CalendarBase.ClassMgr getCalendarClassMgr()
    {
        return new CalendarBase.ClassMgr();
    }

   /**
   * Constructs a CalendarBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Calendar
   */
    public CalendarBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Xml property
* @return String with the Xml
*/
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

/**
* Sets the Xml property
* @param value long with the Xml
*/
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
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
   * Gets all the org.semanticwb.model.CalendarRef
   * @return A GenericIterator with all the org.semanticwb.model.CalendarRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRefInv));
    }

   /**
   * Gets true if has a CalendarRefInv
   * @param value org.semanticwb.model.CalendarRef to verify
   * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
   */
    public boolean hasCalendarRefInv(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the CalendarRefInv
   * @return a org.semanticwb.model.CalendarRef
   */
    public org.semanticwb.model.CalendarRef getCalendarRefInv()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
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
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
