package org.semanticwb.process.resources.reports.base;


   /**
   * Almacena las columnas pertenecientes a determinado reporte 
   */
public abstract class ColumnReportBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticProperty rep_titleColumn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#titleColumn");
    public static final org.semanticwb.platform.SemanticProperty rep_nameProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#nameProperty");
    public static final org.semanticwb.platform.SemanticProperty rep_columnVisible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#columnVisible");
    public static final org.semanticwb.platform.SemanticProperty rep_defaultValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#defaultValue");
    public static final org.semanticwb.platform.SemanticProperty rep_filter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#filter");
    public static final org.semanticwb.platform.SemanticClass rep_Report=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#Report");
    public static final org.semanticwb.platform.SemanticProperty rep_reportName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#reportName");
    public static final org.semanticwb.platform.SemanticProperty rep_enabledOrder=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#enabledOrder");
    public static final org.semanticwb.platform.SemanticProperty rep_defaultValueMax=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#defaultValueMax");
   /**
   * Almacena las columnas pertenecientes a determinado reporte
   */
    public static final org.semanticwb.platform.SemanticClass rep_ColumnReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#ColumnReport");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#ColumnReport");

    public static class ClassMgr
    {
       /**
       * Returns a list of ColumnReport for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReports(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.resources.reports.ColumnReport for all models
       * @return Iterator of org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReports()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport>(it, true);
        }

        public static org.semanticwb.process.resources.reports.ColumnReport createColumnReport(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.reports.ColumnReport.ClassMgr.createColumnReport(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.resources.reports.ColumnReport
       * @param id Identifier for org.semanticwb.process.resources.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       * @return A org.semanticwb.process.resources.reports.ColumnReport
       */
        public static org.semanticwb.process.resources.reports.ColumnReport getColumnReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.ColumnReport)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.resources.reports.ColumnReport
       * @param id Identifier for org.semanticwb.process.resources.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       * @return A org.semanticwb.process.resources.reports.ColumnReport
       */
        public static org.semanticwb.process.resources.reports.ColumnReport createColumnReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.ColumnReport)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.resources.reports.ColumnReport
       * @param id Identifier for org.semanticwb.process.resources.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       */
        public static void removeColumnReport(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.resources.reports.ColumnReport
       * @param id Identifier for org.semanticwb.process.resources.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       * @return true if the org.semanticwb.process.resources.reports.ColumnReport exists, false otherwise
       */

        public static boolean hasColumnReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (getColumnReport(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.ColumnReport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReportByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.ColumnReport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReportByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.ColumnReport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReportByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.ColumnReport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReportByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.ColumnReport with a determined ReportName
       * @param value ReportName of the type org.semanticwb.process.resources.reports.Report
       * @param model Model of the org.semanticwb.process.resources.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReportByReportName(org.semanticwb.process.resources.reports.Report value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_reportName, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.ColumnReport with a determined ReportName
       * @param value ReportName of the type org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.ColumnReport
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReportByReportName(org.semanticwb.process.resources.reports.Report value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_reportName,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ColumnReportBase.ClassMgr getColumnReportClassMgr()
    {
        return new ColumnReportBase.ClassMgr();
    }

   /**
   * Constructs a ColumnReportBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ColumnReport
   */
    public ColumnReportBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the TitleColumn property
* @return String with the TitleColumn
*/
    public String getTitleColumn()
    {
        return getSemanticObject().getProperty(rep_titleColumn);
    }

/**
* Sets the TitleColumn property
* @param value long with the TitleColumn
*/
    public void setTitleColumn(String value)
    {
        getSemanticObject().setProperty(rep_titleColumn, value);
    }

/**
* Gets the NameProperty property
* @return String with the NameProperty
*/
    public String getNameProperty()
    {
        return getSemanticObject().getProperty(rep_nameProperty);
    }

/**
* Sets the NameProperty property
* @param value long with the NameProperty
*/
    public void setNameProperty(String value)
    {
        getSemanticObject().setProperty(rep_nameProperty, value);
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
* Gets the ColumnVisible property
* @return boolean with the ColumnVisible
*/
    public boolean isColumnVisible()
    {
        return getSemanticObject().getBooleanProperty(rep_columnVisible);
    }

/**
* Sets the ColumnVisible property
* @param value long with the ColumnVisible
*/
    public void setColumnVisible(boolean value)
    {
        getSemanticObject().setBooleanProperty(rep_columnVisible, value);
    }

/**
* Gets the DefaultValue property
* @return String with the DefaultValue
*/
    public String getDefaultValue()
    {
        return getSemanticObject().getProperty(rep_defaultValue);
    }

/**
* Sets the DefaultValue property
* @param value long with the DefaultValue
*/
    public void setDefaultValue(String value)
    {
        getSemanticObject().setProperty(rep_defaultValue, value);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
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
   * Sets the value for the property ReportName
   * @param value ReportName to set
   */

    public void setReportName(org.semanticwb.process.resources.reports.Report value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(rep_reportName, value.getSemanticObject());
        }else
        {
            removeReportName();
        }
    }
   /**
   * Remove the value for ReportName property
   */

    public void removeReportName()
    {
        getSemanticObject().removeProperty(rep_reportName);
    }

   /**
   * Gets the ReportName
   * @return a org.semanticwb.process.resources.reports.Report
   */
    public org.semanticwb.process.resources.reports.Report getReportName()
    {
         org.semanticwb.process.resources.reports.Report ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_reportName);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.Report)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the EnabledOrder property
* @return boolean with the EnabledOrder
*/
    public boolean isEnabledOrder()
    {
        return getSemanticObject().getBooleanProperty(rep_enabledOrder);
    }

/**
* Sets the EnabledOrder property
* @param value long with the EnabledOrder
*/
    public void setEnabledOrder(boolean value)
    {
        getSemanticObject().setBooleanProperty(rep_enabledOrder, value);
    }

/**
* Gets the DefaultValueMax property
* @return String with the DefaultValueMax
*/
    public String getDefaultValueMax()
    {
        return getSemanticObject().getProperty(rep_defaultValueMax);
    }

/**
* Sets the DefaultValueMax property
* @param value long with the DefaultValueMax
*/
    public void setDefaultValueMax(String value)
    {
        getSemanticObject().setProperty(rep_defaultValueMax, value);
    }
}
