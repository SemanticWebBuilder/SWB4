package org.semanticwb.process.reports.base;


   /**
   * Almacena los filtros creados para un reporte 
   */
public abstract class FilterReportBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Almacena las columnas pertenecientes a determinado reporte
   */
    public static final org.semanticwb.platform.SemanticClass rep_ColumnReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#ColumnReport");
    public static final org.semanticwb.platform.SemanticProperty rep_column=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#column");
   /**
   * Almacena los filtros creados para un reporte
   */
    public static final org.semanticwb.platform.SemanticClass rep_FilterReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReport");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#FilterReport");

    public static class ClassMgr
    {
       /**
       * Returns a list of FilterReport for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReports(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.reports.FilterReport for all models
       * @return Iterator of org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReports()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport>(it, true);
        }

        public static org.semanticwb.process.reports.FilterReport createFilterReport(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.reports.FilterReport.ClassMgr.createFilterReport(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.reports.FilterReport
       * @param id Identifier for org.semanticwb.process.reports.FilterReport
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       * @return A org.semanticwb.process.reports.FilterReport
       */
        public static org.semanticwb.process.reports.FilterReport getFilterReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.reports.FilterReport)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.reports.FilterReport
       * @param id Identifier for org.semanticwb.process.reports.FilterReport
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       * @return A org.semanticwb.process.reports.FilterReport
       */
        public static org.semanticwb.process.reports.FilterReport createFilterReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.reports.FilterReport)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.reports.FilterReport
       * @param id Identifier for org.semanticwb.process.reports.FilterReport
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       */
        public static void removeFilterReport(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.reports.FilterReport
       * @param id Identifier for org.semanticwb.process.reports.FilterReport
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       * @return true if the org.semanticwb.process.reports.FilterReport exists, false otherwise
       */

        public static boolean hasFilterReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFilterReport(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReportByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReportByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReport with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReportByColumn(org.semanticwb.process.reports.ColumnReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_column, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReport with a determined Column
       * @param value Column of the type org.semanticwb.process.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReportByColumn(org.semanticwb.process.reports.ColumnReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_column,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.reports.FilterReport
       * @return Iterator with all the org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReportByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.reports.FilterReport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.reports.FilterReport
       */

        public static java.util.Iterator<org.semanticwb.process.reports.FilterReport> listFilterReportByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.FilterReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FilterReportBase.ClassMgr getFilterReportClassMgr()
    {
        return new FilterReportBase.ClassMgr();
    }

   /**
   * Constructs a FilterReportBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FilterReport
   */
    public FilterReportBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property Column
   * @param value Column to set
   */

    public void setColumn(org.semanticwb.process.reports.ColumnReport value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(rep_column, value.getSemanticObject());
        }else
        {
            removeColumn();
        }
    }
   /**
   * Remove the value for Column property
   */

    public void removeColumn()
    {
        getSemanticObject().removeProperty(rep_column);
    }

   /**
   * Gets the Column
   * @return a org.semanticwb.process.reports.ColumnReport
   */
    public org.semanticwb.process.reports.ColumnReport getColumn()
    {
         org.semanticwb.process.reports.ColumnReport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_column);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.reports.ColumnReport)obj.createGenericInstance();
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
}
