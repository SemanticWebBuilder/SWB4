package org.semanticwb.process.model.base;


public abstract class OwnerPropertyBase extends org.semanticwb.process.model.BaseElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_userProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#userProperty");
    public static final org.semanticwb.platform.SemanticClass swp_OwnerProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#OwnerProperty");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#OwnerProperty");

    public static class ClassMgr
    {
       /**
       * Returns a list of OwnerProperty for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.OwnerProperty
       */

        public static java.util.Iterator<org.semanticwb.process.model.OwnerProperty> listOwnerProperties(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OwnerProperty>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.OwnerProperty for all models
       * @return Iterator of org.semanticwb.process.model.OwnerProperty
       */

        public static java.util.Iterator<org.semanticwb.process.model.OwnerProperty> listOwnerProperties()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OwnerProperty>(it, true);
        }

        public static org.semanticwb.process.model.OwnerProperty createOwnerProperty(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.OwnerProperty.ClassMgr.createOwnerProperty(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.OwnerProperty
       * @param id Identifier for org.semanticwb.process.model.OwnerProperty
       * @param model Model of the org.semanticwb.process.model.OwnerProperty
       * @return A org.semanticwb.process.model.OwnerProperty
       */
        public static org.semanticwb.process.model.OwnerProperty getOwnerProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.OwnerProperty)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.OwnerProperty
       * @param id Identifier for org.semanticwb.process.model.OwnerProperty
       * @param model Model of the org.semanticwb.process.model.OwnerProperty
       * @return A org.semanticwb.process.model.OwnerProperty
       */
        public static org.semanticwb.process.model.OwnerProperty createOwnerProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.OwnerProperty)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.OwnerProperty
       * @param id Identifier for org.semanticwb.process.model.OwnerProperty
       * @param model Model of the org.semanticwb.process.model.OwnerProperty
       */
        public static void removeOwnerProperty(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.OwnerProperty
       * @param id Identifier for org.semanticwb.process.model.OwnerProperty
       * @param model Model of the org.semanticwb.process.model.OwnerProperty
       * @return true if the org.semanticwb.process.model.OwnerProperty exists, false otherwise
       */

        public static boolean hasOwnerProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOwnerProperty(id, model)!=null);
        }
    }

    public static OwnerPropertyBase.ClassMgr getOwnerPropertyClassMgr()
    {
        return new OwnerPropertyBase.ClassMgr();
    }

   /**
   * Constructs a OwnerPropertyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OwnerProperty
   */
    public OwnerPropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the UserProperty property
* @return String with the UserProperty
*/
    public String getUserProperty()
    {
        return getSemanticObject().getProperty(swp_userProperty);
    }

/**
* Sets the UserProperty property
* @param value long with the UserProperty
*/
    public void setUserProperty(String value)
    {
        getSemanticObject().setProperty(swp_userProperty, value);
    }
}
