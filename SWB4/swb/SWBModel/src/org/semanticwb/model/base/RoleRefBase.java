package org.semanticwb.model.base;


   /**
   * Referencia a un objeto de tipo Role 
   */
public abstract class RoleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#role");
   /**
   * Referencia a un objeto de tipo Role
   */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of RoleRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.RoleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.RoleRef for all models
       * @return Iterator of org.semanticwb.model.RoleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(it, true);
        }

        public static org.semanticwb.model.RoleRef createRoleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.RoleRef
       * @param id Identifier for org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.RoleRef
       * @return A org.semanticwb.model.RoleRef
       */
        public static org.semanticwb.model.RoleRef getRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.RoleRef
       * @param id Identifier for org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.RoleRef
       * @return A org.semanticwb.model.RoleRef
       */
        public static org.semanticwb.model.RoleRef createRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.RoleRef
       * @param id Identifier for org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.RoleRef
       */
        public static void removeRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.RoleRef
       * @param id Identifier for org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.RoleRef
       * @return true if the org.semanticwb.model.RoleRef exists, false otherwise
       */

        public static boolean hasRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRoleRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.RoleRef with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.model.RoleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_role, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RoleRef with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.model.RoleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_role,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a RoleRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RoleRef
   */
    public RoleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Role
   * @param value Role to set
   */

    public void setRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_role, value.getSemanticObject());
        }else
        {
            removeRole();
        }
    }
   /**
   * Remove the value for Role property
   */

    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_role);
    }

   /**
   * Gets the Role
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_role);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
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
