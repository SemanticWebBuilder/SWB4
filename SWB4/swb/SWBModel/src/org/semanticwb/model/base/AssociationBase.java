package org.semanticwb.model.base;


   /**
   * Define una asociacion entre dos miembros en la especificacion de TopicMaps 
   */
public abstract class AssociationBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Superclase utilizada por compatibilidad con estandar TopicMaps
   */
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
    public static final org.semanticwb.platform.SemanticProperty swb_assType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assType");
   /**
   * Miembro participante dentro de una asociacion en la especificacion de TopicMaps
   */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    public static final org.semanticwb.platform.SemanticProperty swb_hasMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasMember");
   /**
   * Define una asociacion entre dos miembros en la especificacion de TopicMaps
   */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");

    public static class ClassMgr
    {
       /**
       * Returns a list of Association for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Association
       */

        public static java.util.Iterator<org.semanticwb.model.Association> listAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Association>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Association for all models
       * @return Iterator of org.semanticwb.model.Association
       */

        public static java.util.Iterator<org.semanticwb.model.Association> listAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Association>(it, true);
        }

        public static org.semanticwb.model.Association createAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Association.ClassMgr.createAssociation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Association
       * @param id Identifier for org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.Association
       * @return A org.semanticwb.model.Association
       */
        public static org.semanticwb.model.Association getAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Association)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Association
       * @param id Identifier for org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.Association
       * @return A org.semanticwb.model.Association
       */
        public static org.semanticwb.model.Association createAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Association)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Association
       * @param id Identifier for org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.Association
       */
        public static void removeAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Association
       * @param id Identifier for org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.Association
       * @return true if the org.semanticwb.model.Association exists, false otherwise
       */

        public static boolean hasAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssociation(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Association with a determined Type
       * @param value Type of the type org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.Association
       */

        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByType(org.semanticwb.model.Topic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_assType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Association with a determined Type
       * @param value Type of the type org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.Association
       */

        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByType(org.semanticwb.model.Topic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_assType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Association with a determined Member
       * @param value Member of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.Association
       */

        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMember, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Association with a determined Member
       * @param value Member of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.Association
       */

        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMember,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a AssociationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Association
   */
    public AssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Type
   * @param value Type to set
   */

    public void setType(org.semanticwb.model.Topic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_assType, value.getSemanticObject());
        }else
        {
            removeType();
        }
    }
   /**
   * Remove the value for Type property
   */

    public void removeType()
    {
        getSemanticObject().removeProperty(swb_assType);
    }

   /**
   * Gets the Type
   * @return a org.semanticwb.model.Topic
   */
    public org.semanticwb.model.Topic getType()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.AssMember
   * @return A GenericIterator with all the org.semanticwb.model.AssMember
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> listMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(getSemanticObject().listObjectProperties(swb_hasMember));
    }

   /**
   * Gets true if has a Member
   * @param value org.semanticwb.model.AssMember to verify
   * @return true if the org.semanticwb.model.AssMember exists, false otherwise
   */
    public boolean hasMember(org.semanticwb.model.AssMember value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasMember,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Member
   * @param value org.semanticwb.model.AssMember to add
   */

    public void addMember(org.semanticwb.model.AssMember value)
    {
        getSemanticObject().addObjectProperty(swb_hasMember, value.getSemanticObject());
    }
   /**
   * Removes all the Member
   */

    public void removeAllMember()
    {
        getSemanticObject().removeProperty(swb_hasMember);
    }
   /**
   * Removes a Member
   * @param value org.semanticwb.model.AssMember to remove
   */

    public void removeMember(org.semanticwb.model.AssMember value)
    {
        getSemanticObject().removeObjectProperty(swb_hasMember,value.getSemanticObject());
    }

   /**
   * Gets the Member
   * @return a org.semanticwb.model.AssMember
   */
    public org.semanticwb.model.AssMember getMember()
    {
         org.semanticwb.model.AssMember ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AssMember)obj.createGenericInstance();
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
