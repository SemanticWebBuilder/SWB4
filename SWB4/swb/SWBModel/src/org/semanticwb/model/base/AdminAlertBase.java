package org.semanticwb.model.base;


   /**
   * AntiDDoS Parameters, this class holds the thresholds limits to recognize an attack 
   */
public abstract class AdminAlertBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_alertTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertTitle");
    public static final org.semanticwb.platform.SemanticProperty swb_alertAttackMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertAttackMode");
    public static final org.semanticwb.platform.SemanticProperty swb_alertTimeTH=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertTimeTH");
    public static final org.semanticwb.platform.SemanticProperty swb_alertSistemStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertSistemStatus");
    public static final org.semanticwb.platform.SemanticProperty swb_alertPPSTH=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertPPSTH");
    public static final org.semanticwb.platform.SemanticProperty swb_alertMailList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertMailList");
    public static final org.semanticwb.platform.SemanticProperty swb_alertCPUTH=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#alertCPUTH");
   /**
   * AntiDDoS Parameters, this class holds the thresholds limits to recognize an attack
   */
    public static final org.semanticwb.platform.SemanticClass swb_AdminAlert=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminAlert");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminAlert");

    public static class ClassMgr
    {
       /**
       * Returns a list of AdminAlert for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.AdminAlert
       */

        public static java.util.Iterator<org.semanticwb.model.AdminAlert> listAdminAlerts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminAlert>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.AdminAlert for all models
       * @return Iterator of org.semanticwb.model.AdminAlert
       */

        public static java.util.Iterator<org.semanticwb.model.AdminAlert> listAdminAlerts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminAlert>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.AdminAlert
       * @param id Identifier for org.semanticwb.model.AdminAlert
       * @param model Model of the org.semanticwb.model.AdminAlert
       * @return A org.semanticwb.model.AdminAlert
       */
        public static org.semanticwb.model.AdminAlert getAdminAlert(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AdminAlert)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.AdminAlert
       * @param id Identifier for org.semanticwb.model.AdminAlert
       * @param model Model of the org.semanticwb.model.AdminAlert
       * @return A org.semanticwb.model.AdminAlert
       */
        public static org.semanticwb.model.AdminAlert createAdminAlert(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AdminAlert)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.AdminAlert
       * @param id Identifier for org.semanticwb.model.AdminAlert
       * @param model Model of the org.semanticwb.model.AdminAlert
       */
        public static void removeAdminAlert(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.AdminAlert
       * @param id Identifier for org.semanticwb.model.AdminAlert
       * @param model Model of the org.semanticwb.model.AdminAlert
       * @return true if the org.semanticwb.model.AdminAlert exists, false otherwise
       */

        public static boolean hasAdminAlert(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAdminAlert(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.AdminAlert with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.AdminAlert
       * @return Iterator with all the org.semanticwb.model.AdminAlert
       */

        public static java.util.Iterator<org.semanticwb.model.AdminAlert> listAdminAlertByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminAlert> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminAlert with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.AdminAlert
       */

        public static java.util.Iterator<org.semanticwb.model.AdminAlert> listAdminAlertByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminAlert> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminAlert with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.AdminAlert
       * @return Iterator with all the org.semanticwb.model.AdminAlert
       */

        public static java.util.Iterator<org.semanticwb.model.AdminAlert> listAdminAlertByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminAlert> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AdminAlert with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.AdminAlert
       */

        public static java.util.Iterator<org.semanticwb.model.AdminAlert> listAdminAlertByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminAlert> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AdminAlertBase.ClassMgr getAdminAlertClassMgr()
    {
        return new AdminAlertBase.ClassMgr();
    }

   /**
   * Constructs a AdminAlertBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AdminAlert
   */
    public AdminAlertBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the AlertTitle property
* @return String with the AlertTitle
*/
    public String getAlertTitle()
    {
        return getSemanticObject().getProperty(swb_alertTitle);
    }

/**
* Sets the AlertTitle property
* @param value long with the AlertTitle
*/
    public void setAlertTitle(String value)
    {
        getSemanticObject().setProperty(swb_alertTitle, value);
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

    public java.util.Iterator<String> listAlertAttackModes()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swb_alertAttackMode);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addAlertAttackMode(String value)
    {
        getSemanticObject().addLiteralProperty(swb_alertAttackMode, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllAlertAttackMode()
    {
        getSemanticObject().removeProperty(swb_alertAttackMode);
    }

    public void removeAlertAttackMode(String value)
    {
        getSemanticObject().removeLiteralProperty(swb_alertAttackMode,new org.semanticwb.platform.SemanticLiteral(value));
    }

/**
* Gets the AlertTimeTH property
* @return long with the AlertTimeTH
*/
    public long getAlertTimeTH()
    {
        return getSemanticObject().getLongProperty(swb_alertTimeTH);
    }

/**
* Sets the AlertTimeTH property
* @param value long with the AlertTimeTH
*/
    public void setAlertTimeTH(long value)
    {
        getSemanticObject().setLongProperty(swb_alertTimeTH, value);
    }

/**
* Gets the AlertSistemStatus property
* @return boolean with the AlertSistemStatus
*/
    public boolean isAlertSistemStatus()
    {
        return getSemanticObject().getBooleanProperty(swb_alertSistemStatus);
    }

/**
* Sets the AlertSistemStatus property
* @param value long with the AlertSistemStatus
*/
    public void setAlertSistemStatus(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_alertSistemStatus, value);
    }

/**
* Gets the AlertPPSTH property
* @return long with the AlertPPSTH
*/
    public long getAlertPPSTH()
    {
        return getSemanticObject().getLongProperty(swb_alertPPSTH);
    }

/**
* Sets the AlertPPSTH property
* @param value long with the AlertPPSTH
*/
    public void setAlertPPSTH(long value)
    {
        getSemanticObject().setLongProperty(swb_alertPPSTH, value);
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
* Gets the AlertMailList property
* @return String with the AlertMailList
*/
    public String getAlertMailList()
    {
        return getSemanticObject().getProperty(swb_alertMailList);
    }

/**
* Sets the AlertMailList property
* @param value long with the AlertMailList
*/
    public void setAlertMailList(String value)
    {
        getSemanticObject().setProperty(swb_alertMailList, value);
    }

/**
* Gets the AlertCPUTH property
* @return float with the AlertCPUTH
*/
    public float getAlertCPUTH()
    {
        return getSemanticObject().getFloatProperty(swb_alertCPUTH);
    }

/**
* Sets the AlertCPUTH property
* @param value long with the AlertCPUTH
*/
    public void setAlertCPUTH(float value)
    {
        getSemanticObject().setFloatProperty(swb_alertCPUTH, value);
    }
}
