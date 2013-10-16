package org.semanticwb.bsc.element.base;


   /**
   * Los temas estratégicos agrupan objetivos con fines en común. A su vez, los temas están agrupados dentro de las perspectivas. 
   */
public abstract class ThemeBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help,org.semanticwb.model.Referensable,org.semanticwb.bsc.Causal,org.semanticwb.bsc.Sortable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableClass
{
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasObjective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasObjective");
    public static final org.semanticwb.platform.SemanticClass bsc_Perspective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");
    public static final org.semanticwb.platform.SemanticProperty bsc_perspectiveInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#perspectiveInv");
   /**
   * Abreviación del título del tema, para distinguirlos en despliegues con poco espacio. Un ejemplo de estos despliegues es el mapa estratégico.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_abbreviation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#abbreviation");
   /**
   * Los temas estratégicos agrupan objetivos con fines en común. A su vez, los temas están agrupados dentro de las perspectivas.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");

    public static class ClassMgr
    {
       /**
       * Returns a list of Theme for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Theme for all models
       * @return Iterator of org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme>(it, true);
        }

        public static org.semanticwb.bsc.element.Theme createTheme(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Theme.ClassMgr.createTheme(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Theme
       * @param id Identifier for org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return A org.semanticwb.bsc.element.Theme
       */
        public static org.semanticwb.bsc.element.Theme getTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Theme)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Theme
       * @param id Identifier for org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return A org.semanticwb.bsc.element.Theme
       */
        public static org.semanticwb.bsc.element.Theme createTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Theme)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Theme
       * @param id Identifier for org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Theme
       */
        public static void removeTheme(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Theme
       * @param id Identifier for org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return true if the org.semanticwb.bsc.element.Theme exists, false otherwise
       */

        public static boolean hasTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTheme(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasObjective, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasObjective,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined CausalTheme
       * @param value CausalTheme of the type org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByCausalTheme(org.semanticwb.bsc.element.Theme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalTheme, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined CausalTheme
       * @param value CausalTheme of the type org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByCausalTheme(org.semanticwb.bsc.element.Theme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalTheme,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined CausalObjective
       * @param value CausalObjective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByCausalObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalObjective, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined CausalObjective
       * @param value CausalObjective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByCausalObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasCausalObjective,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined Perspective
       * @param value Perspective of the type org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByPerspective(org.semanticwb.bsc.element.Perspective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_perspectiveInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined Perspective
       * @param value Perspective of the type org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByPerspective(org.semanticwb.bsc.element.Perspective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_perspectiveInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Theme with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Theme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Theme> listThemeByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ThemeBase.ClassMgr getThemeClassMgr()
    {
        return new ThemeBase.ClassMgr();
    }

   /**
   * Constructs a ThemeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Theme
   */
    public ThemeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Objective
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Objective
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> listObjectives()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(getSemanticObject().listObjectProperties(bsc_hasObjective));
    }

   /**
   * Gets true if has a Objective
   * @param value org.semanticwb.bsc.element.Objective to verify
   * @return true if the org.semanticwb.bsc.element.Objective exists, false otherwise
   */
    public boolean hasObjective(org.semanticwb.bsc.element.Objective value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasObjective,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Objective
   * @param value org.semanticwb.bsc.element.Objective to add
   */

    public void addObjective(org.semanticwb.bsc.element.Objective value)
    {
        getSemanticObject().addObjectProperty(bsc_hasObjective, value.getSemanticObject());
    }
   /**
   * Removes all the Objective
   */

    public void removeAllObjective()
    {
        getSemanticObject().removeProperty(bsc_hasObjective);
    }
   /**
   * Removes a Objective
   * @param value org.semanticwb.bsc.element.Objective to remove
   */

    public void removeObjective(org.semanticwb.bsc.element.Objective value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasObjective,value.getSemanticObject());
    }

   /**
   * Gets the Objective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasObjective);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Objective)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Theme
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Theme
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> listCausalThemes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme>(getSemanticObject().listObjectProperties(bsc_hasCausalTheme));
    }

   /**
   * Gets true if has a CausalTheme
   * @param value org.semanticwb.bsc.element.Theme to verify
   * @return true if the org.semanticwb.bsc.element.Theme exists, false otherwise
   */
    public boolean hasCausalTheme(org.semanticwb.bsc.element.Theme value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasCausalTheme,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CausalTheme
   * @param value org.semanticwb.bsc.element.Theme to add
   */

    public void addCausalTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().addObjectProperty(bsc_hasCausalTheme, value.getSemanticObject());
    }
   /**
   * Removes all the CausalTheme
   */

    public void removeAllCausalTheme()
    {
        getSemanticObject().removeProperty(bsc_hasCausalTheme);
    }
   /**
   * Removes a CausalTheme
   * @param value org.semanticwb.bsc.element.Theme to remove
   */

    public void removeCausalTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasCausalTheme,value.getSemanticObject());
    }

   /**
   * Gets the CausalTheme
   * @return a org.semanticwb.bsc.element.Theme
   */
    public org.semanticwb.bsc.element.Theme getCausalTheme()
    {
         org.semanticwb.bsc.element.Theme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasCausalTheme);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Theme)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.bsc.element.Objective
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Objective
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> listCausalObjectives()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(getSemanticObject().listObjectProperties(bsc_hasCausalObjective));
    }

   /**
   * Gets true if has a CausalObjective
   * @param value org.semanticwb.bsc.element.Objective to verify
   * @return true if the org.semanticwb.bsc.element.Objective exists, false otherwise
   */
    public boolean hasCausalObjective(org.semanticwb.bsc.element.Objective value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasCausalObjective,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CausalObjective
   * @param value org.semanticwb.bsc.element.Objective to add
   */

    public void addCausalObjective(org.semanticwb.bsc.element.Objective value)
    {
        getSemanticObject().addObjectProperty(bsc_hasCausalObjective, value.getSemanticObject());
    }
   /**
   * Removes all the CausalObjective
   */

    public void removeAllCausalObjective()
    {
        getSemanticObject().removeProperty(bsc_hasCausalObjective);
    }
   /**
   * Removes a CausalObjective
   * @param value org.semanticwb.bsc.element.Objective to remove
   */

    public void removeCausalObjective(org.semanticwb.bsc.element.Objective value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasCausalObjective,value.getSemanticObject());
    }

   /**
   * Gets the CausalObjective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getCausalObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasCausalObjective);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Objective)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Perspective
   * @param value Perspective to set
   */

    public void setPerspective(org.semanticwb.bsc.element.Perspective value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_perspectiveInv, value.getSemanticObject());
        }else
        {
            removePerspective();
        }
    }
   /**
   * Remove the value for Perspective property
   */

    public void removePerspective()
    {
        getSemanticObject().removeProperty(bsc_perspectiveInv);
    }

   /**
   * Gets the Perspective
   * @return a org.semanticwb.bsc.element.Perspective
   */
    public org.semanticwb.bsc.element.Perspective getPerspective()
    {
         org.semanticwb.bsc.element.Perspective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_perspectiveInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Perspective)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Hidden property
* @return boolean with the Hidden
*/
    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swb_hidden);
    }

/**
* Sets the Hidden property
* @param value long with the Hidden
*/
    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_hidden, value);
    }
}
