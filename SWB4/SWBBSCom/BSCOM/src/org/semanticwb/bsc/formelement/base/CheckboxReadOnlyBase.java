package org.semanticwb.bsc.formelement.base;


   /**
   * Casilla de verificación accesible solo con permisos una vez marcada 
   */
public abstract class CheckboxReadOnlyBase extends org.semanticwb.model.BooleanElement 
{
   /**
   * Read only for users with any of the roles in this list of identifiers of roles
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_readonlyRoleIds=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#readonlyRoleIds");
   /**
   * Solo lectura para usuarios en alguno de los grupos de usuarios en esta lista de identificadores de grupos de usuarios
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_readonlyUserGroupIds=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#readonlyUserGroupIds");
   /**
   * Casilla de verificación accesible solo con permisos una vez marcada
   */
    public static final org.semanticwb.platform.SemanticClass bsc_CheckboxReadOnly=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#CheckboxReadOnly");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#CheckboxReadOnly");

    public static class ClassMgr
    {
       /**
       * Returns a list of CheckboxReadOnly for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.CheckboxReadOnly
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.CheckboxReadOnly> listCheckboxReadOnlies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.CheckboxReadOnly>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.CheckboxReadOnly for all models
       * @return Iterator of org.semanticwb.bsc.formelement.CheckboxReadOnly
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.CheckboxReadOnly> listCheckboxReadOnlies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.CheckboxReadOnly>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param id Identifier for org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param model Model of the org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @return A org.semanticwb.bsc.formelement.CheckboxReadOnly
       */
        public static org.semanticwb.bsc.formelement.CheckboxReadOnly getCheckboxReadOnly(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.CheckboxReadOnly)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param id Identifier for org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param model Model of the org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @return A org.semanticwb.bsc.formelement.CheckboxReadOnly
       */
        public static org.semanticwb.bsc.formelement.CheckboxReadOnly createCheckboxReadOnly(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.CheckboxReadOnly)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param id Identifier for org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param model Model of the org.semanticwb.bsc.formelement.CheckboxReadOnly
       */
        public static void removeCheckboxReadOnly(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param id Identifier for org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @param model Model of the org.semanticwb.bsc.formelement.CheckboxReadOnly
       * @return true if the org.semanticwb.bsc.formelement.CheckboxReadOnly exists, false otherwise
       */

        public static boolean hasCheckboxReadOnly(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCheckboxReadOnly(id, model)!=null);
        }
    }

    public static CheckboxReadOnlyBase.ClassMgr getCheckboxReadOnlyClassMgr()
    {
        return new CheckboxReadOnlyBase.ClassMgr();
    }

   /**
   * Constructs a CheckboxReadOnlyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CheckboxReadOnly
   */
    public CheckboxReadOnlyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ReadonlyRoleIds property
* @return String with the ReadonlyRoleIds
*/
    public String getReadonlyRoleIds()
    {
        return getSemanticObject().getProperty(bsc_readonlyRoleIds);
    }

/**
* Sets the ReadonlyRoleIds property
* @param value long with the ReadonlyRoleIds
*/
    public void setReadonlyRoleIds(String value)
    {
        getSemanticObject().setProperty(bsc_readonlyRoleIds, value);
    }

/**
* Gets the ReadonlyUserGroupIds property
* @return String with the ReadonlyUserGroupIds
*/
    public String getReadonlyUserGroupIds()
    {
        return getSemanticObject().getProperty(bsc_readonlyUserGroupIds);
    }

/**
* Sets the ReadonlyUserGroupIds property
* @param value long with the ReadonlyUserGroupIds
*/
    public void setReadonlyUserGroupIds(String value)
    {
        getSemanticObject().setProperty(bsc_readonlyUserGroupIds, value);
    }
}
