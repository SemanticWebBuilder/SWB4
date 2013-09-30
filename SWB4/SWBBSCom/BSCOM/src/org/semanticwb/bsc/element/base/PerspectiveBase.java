package org.semanticwb.bsc.element.base;


public abstract class PerspectiveBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.bsc.Recognizable,org.semanticwb.model.FilterableClass,org.semanticwb.bsc.Sortable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Serializable,org.semanticwb.bsc.Help,org.semanticwb.model.RuleRefable,org.semanticwb.model.Roleable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupable
{
   /**
   * Un DifferentiatorGroup es una clase que permitir contener uno o varios Differentiator que se dibujan en el mapa estratégico del scorecard.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DifferentiatorGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DifferentiatorGroup");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDifferentiatorGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDifferentiatorGroup");
   /**
   * Los temas estratégicos agrupan objetivos con fines en común. A su vez, los temas están agrupados dentro de las perspectivas.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasTheme");
    public static final org.semanticwb.platform.SemanticClass bsc_Perspective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");

    public static class ClassMgr
    {
       /**
       * Returns a list of Perspective for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Perspective for all models
       * @return Iterator of org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective>(it, true);
        }

        public static org.semanticwb.bsc.element.Perspective createPerspective(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Perspective.ClassMgr.createPerspective(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return A org.semanticwb.bsc.element.Perspective
       */
        public static org.semanticwb.bsc.element.Perspective getPerspective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Perspective)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return A org.semanticwb.bsc.element.Perspective
       */
        public static org.semanticwb.bsc.element.Perspective createPerspective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Perspective)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       */
        public static void removePerspective(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return true if the org.semanticwb.bsc.element.Perspective exists, false otherwise
       */

        public static boolean hasPerspective(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPerspective(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined DifferentiatorGroup
       * @param value DifferentiatorGroup of the type org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDifferentiatorGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined DifferentiatorGroup
       * @param value DifferentiatorGroup of the type org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDifferentiatorGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByTheme(org.semanticwb.bsc.element.Theme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasTheme, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByTheme(org.semanticwb.bsc.element.Theme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasTheme,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PerspectiveBase.ClassMgr getPerspectiveClassMgr()
    {
        return new PerspectiveBase.ClassMgr();
    }

   /**
   * Constructs a PerspectiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Perspective
   */
    public PerspectiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.DifferentiatorGroup
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup>(getSemanticObject().listObjectProperties(bsc_hasDifferentiatorGroup));
    }

   /**
   * Gets true if has a DifferentiatorGroup
   * @param value org.semanticwb.bsc.accessory.DifferentiatorGroup to verify
   * @return true if the org.semanticwb.bsc.accessory.DifferentiatorGroup exists, false otherwise
   */
    public boolean hasDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasDifferentiatorGroup,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DifferentiatorGroup
   * @param value org.semanticwb.bsc.accessory.DifferentiatorGroup to add
   */

    public void addDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value)
    {
        getSemanticObject().addObjectProperty(bsc_hasDifferentiatorGroup, value.getSemanticObject());
    }
   /**
   * Removes all the DifferentiatorGroup
   */

    public void removeAllDifferentiatorGroup()
    {
        getSemanticObject().removeProperty(bsc_hasDifferentiatorGroup);
    }
   /**
   * Removes a DifferentiatorGroup
   * @param value org.semanticwb.bsc.accessory.DifferentiatorGroup to remove
   */

    public void removeDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasDifferentiatorGroup,value.getSemanticObject());
    }

   /**
   * Gets the DifferentiatorGroup
   * @return a org.semanticwb.bsc.accessory.DifferentiatorGroup
   */
    public org.semanticwb.bsc.accessory.DifferentiatorGroup getDifferentiatorGroup()
    {
         org.semanticwb.bsc.accessory.DifferentiatorGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasDifferentiatorGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.DifferentiatorGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(bsc_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(bsc_index, value);
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Perspective object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Perspective object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Theme
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Theme
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> listThemes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme>(getSemanticObject().listObjectProperties(bsc_hasTheme));
    }

   /**
   * Gets true if has a Theme
   * @param value org.semanticwb.bsc.element.Theme to verify
   * @return true if the org.semanticwb.bsc.element.Theme exists, false otherwise
   */
    public boolean hasTheme(org.semanticwb.bsc.element.Theme value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasTheme,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Theme
   * @param value org.semanticwb.bsc.element.Theme to add
   */

    public void addTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().addObjectProperty(bsc_hasTheme, value.getSemanticObject());
    }
   /**
   * Removes all the Theme
   */

    public void removeAllTheme()
    {
        getSemanticObject().removeProperty(bsc_hasTheme);
    }
   /**
   * Removes a Theme
   * @param value org.semanticwb.bsc.element.Theme to remove
   */

    public void removeTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasTheme,value.getSemanticObject());
    }

   /**
   * Gets the Theme
   * @return a org.semanticwb.bsc.element.Theme
   */
    public org.semanticwb.bsc.element.Theme getTheme()
    {
         org.semanticwb.bsc.element.Theme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasTheme);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Theme)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Serial property
* @return int with the Serial
*/
    public int getSerial()
    {
        return getSemanticObject().getIntProperty(bsc_serial);
    }

/**
* Sets the Serial property
* @param value long with the Serial
*/
    public void setSerial(int value)
    {
        getSemanticObject().setIntProperty(bsc_serial, value);
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
