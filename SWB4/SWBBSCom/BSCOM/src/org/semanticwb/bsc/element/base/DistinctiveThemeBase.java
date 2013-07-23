package org.semanticwb.bsc.element.base;


   /**
   * Los temas para distintivos agrupan distintivos con fines en común. A su vez, los temas para distintivos están agrupados dentro de las perspectivas. 
   */
public abstract class DistinctiveThemeBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Roleable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Distinctive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Distinctive");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDistintictive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDistintictive");
   /**
   * Los temas para distintivos agrupan distintivos con fines en común. A su vez, los temas para distintivos están agrupados dentro de las perspectivas.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DistinctiveTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DistinctiveTheme");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DistinctiveTheme");

    public static class ClassMgr
    {
       /**
       * Returns a list of DistinctiveTheme for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.DistinctiveTheme for all models
       * @return Iterator of org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme>(it, true);
        }

        public static org.semanticwb.bsc.element.DistinctiveTheme createDistinctiveTheme(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.DistinctiveTheme.ClassMgr.createDistinctiveTheme(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.DistinctiveTheme
       * @param id Identifier for org.semanticwb.bsc.element.DistinctiveTheme
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return A org.semanticwb.bsc.element.DistinctiveTheme
       */
        public static org.semanticwb.bsc.element.DistinctiveTheme getDistinctiveTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.DistinctiveTheme)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.DistinctiveTheme
       * @param id Identifier for org.semanticwb.bsc.element.DistinctiveTheme
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return A org.semanticwb.bsc.element.DistinctiveTheme
       */
        public static org.semanticwb.bsc.element.DistinctiveTheme createDistinctiveTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.DistinctiveTheme)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.DistinctiveTheme
       * @param id Identifier for org.semanticwb.bsc.element.DistinctiveTheme
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       */
        public static void removeDistinctiveTheme(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.DistinctiveTheme
       * @param id Identifier for org.semanticwb.bsc.element.DistinctiveTheme
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return true if the org.semanticwb.bsc.element.DistinctiveTheme exists, false otherwise
       */

        public static boolean hasDistinctiveTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDistinctiveTheme(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined Distintictive
       * @param value Distintictive of the type org.semanticwb.bsc.element.Distinctive
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByDistintictive(org.semanticwb.bsc.element.Distinctive value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDistintictive, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined Distintictive
       * @param value Distintictive of the type org.semanticwb.bsc.element.Distinctive
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByDistintictive(org.semanticwb.bsc.element.Distinctive value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDistintictive,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.DistinctiveTheme
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.DistinctiveTheme with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.DistinctiveTheme
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.DistinctiveTheme> listDistinctiveThemeByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.DistinctiveTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DistinctiveThemeBase.ClassMgr getDistinctiveThemeClassMgr()
    {
        return new DistinctiveThemeBase.ClassMgr();
    }

   /**
   * Constructs a DistinctiveThemeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DistinctiveTheme
   */
    public DistinctiveThemeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Distinctive
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Distinctive
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> listDistintictives()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive>(getSemanticObject().listObjectProperties(bsc_hasDistintictive));
    }

   /**
   * Gets true if has a Distintictive
   * @param value org.semanticwb.bsc.element.Distinctive to verify
   * @return true if the org.semanticwb.bsc.element.Distinctive exists, false otherwise
   */
    public boolean hasDistintictive(org.semanticwb.bsc.element.Distinctive value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasDistintictive,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Distintictive
   * @param value org.semanticwb.bsc.element.Distinctive to add
   */

    public void addDistintictive(org.semanticwb.bsc.element.Distinctive value)
    {
        getSemanticObject().addObjectProperty(bsc_hasDistintictive, value.getSemanticObject());
    }
   /**
   * Removes all the Distintictive
   */

    public void removeAllDistintictive()
    {
        getSemanticObject().removeProperty(bsc_hasDistintictive);
    }
   /**
   * Removes a Distintictive
   * @param value org.semanticwb.bsc.element.Distinctive to remove
   */

    public void removeDistintictive(org.semanticwb.bsc.element.Distinctive value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasDistintictive,value.getSemanticObject());
    }

   /**
   * Gets the Distintictive
   * @return a org.semanticwb.bsc.element.Distinctive
   */
    public org.semanticwb.bsc.element.Distinctive getDistintictive()
    {
         org.semanticwb.bsc.element.Distinctive ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasDistintictive);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Distinctive)obj.createGenericInstance();
         }
         return ret;
    }
}
