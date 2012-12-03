package org.semanticwb.process.model.base;


public abstract class ItemAwareMappingBase extends org.semanticwb.process.model.BaseElement 
{
    public static final org.semanticwb.platform.SemanticClass swp_ItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAware");
    public static final org.semanticwb.platform.SemanticProperty swp_remoteItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#remoteItemAware");
    public static final org.semanticwb.platform.SemanticProperty swp_localItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#localItemAware");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareMapping=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareMapping");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareMapping");

    public static class ClassMgr
    {
       /**
       * Returns a list of ItemAwareMapping for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ItemAwareMapping
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ItemAwareMapping for all models
       * @return Iterator of org.semanticwb.process.model.ItemAwareMapping
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping>(it, true);
        }

        public static org.semanticwb.process.model.ItemAwareMapping createItemAwareMapping(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ItemAwareMapping.ClassMgr.createItemAwareMapping(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ItemAwareMapping
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMapping
       * @param model Model of the org.semanticwb.process.model.ItemAwareMapping
       * @return A org.semanticwb.process.model.ItemAwareMapping
       */
        public static org.semanticwb.process.model.ItemAwareMapping getItemAwareMapping(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareMapping)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ItemAwareMapping
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMapping
       * @param model Model of the org.semanticwb.process.model.ItemAwareMapping
       * @return A org.semanticwb.process.model.ItemAwareMapping
       */
        public static org.semanticwb.process.model.ItemAwareMapping createItemAwareMapping(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareMapping)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ItemAwareMapping
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMapping
       * @param model Model of the org.semanticwb.process.model.ItemAwareMapping
       */
        public static void removeItemAwareMapping(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ItemAwareMapping
       * @param id Identifier for org.semanticwb.process.model.ItemAwareMapping
       * @param model Model of the org.semanticwb.process.model.ItemAwareMapping
       * @return true if the org.semanticwb.process.model.ItemAwareMapping exists, false otherwise
       */

        public static boolean hasItemAwareMapping(String id, org.semanticwb.model.SWBModel model)
        {
            return (getItemAwareMapping(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareMapping with a determined RemoteItemAware
       * @param value RemoteItemAware of the type org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAwareMapping
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareMapping
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappingByRemoteItemAware(org.semanticwb.process.model.ItemAware value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_remoteItemAware, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareMapping with a determined RemoteItemAware
       * @param value RemoteItemAware of the type org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareMapping
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappingByRemoteItemAware(org.semanticwb.process.model.ItemAware value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_remoteItemAware,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareMapping with a determined LocalItemAware
       * @param value LocalItemAware of the type org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAwareMapping
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareMapping
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappingByLocalItemAware(org.semanticwb.process.model.ItemAware value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_localItemAware, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareMapping with a determined LocalItemAware
       * @param value LocalItemAware of the type org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareMapping
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappingByLocalItemAware(org.semanticwb.process.model.ItemAware value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_localItemAware,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ItemAwareMappingBase.ClassMgr getItemAwareMappingClassMgr()
    {
        return new ItemAwareMappingBase.ClassMgr();
    }

   /**
   * Constructs a ItemAwareMappingBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ItemAwareMapping
   */
    public ItemAwareMappingBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property RemoteItemAware
   * @param value RemoteItemAware to set
   */

    public void setRemoteItemAware(org.semanticwb.process.model.ItemAware value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_remoteItemAware, value.getSemanticObject());
        }else
        {
            removeRemoteItemAware();
        }
    }
   /**
   * Remove the value for RemoteItemAware property
   */

    public void removeRemoteItemAware()
    {
        getSemanticObject().removeProperty(swp_remoteItemAware);
    }

   /**
   * Gets the RemoteItemAware
   * @return a org.semanticwb.process.model.ItemAware
   */
    public org.semanticwb.process.model.ItemAware getRemoteItemAware()
    {
         org.semanticwb.process.model.ItemAware ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_remoteItemAware);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAware)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property LocalItemAware
   * @param value LocalItemAware to set
   */

    public void setLocalItemAware(org.semanticwb.process.model.ItemAware value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_localItemAware, value.getSemanticObject());
        }else
        {
            removeLocalItemAware();
        }
    }
   /**
   * Remove the value for LocalItemAware property
   */

    public void removeLocalItemAware()
    {
        getSemanticObject().removeProperty(swp_localItemAware);
    }

   /**
   * Gets the LocalItemAware
   * @return a org.semanticwb.process.model.ItemAware
   */
    public org.semanticwb.process.model.ItemAware getLocalItemAware()
    {
         org.semanticwb.process.model.ItemAware ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_localItemAware);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAware)obj.createGenericInstance();
         }
         return ret;
    }
}
