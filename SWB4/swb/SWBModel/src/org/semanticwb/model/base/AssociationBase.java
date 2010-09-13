/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
    * Define una asociacion entre dos miembros en la especificacion de TopicMaps.
    */
public abstract class AssociationBase extends org.semanticwb.model.SWBClass 
{
   
   /** Superclase utilizada por compatibilidad con estandar TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
    
    /** The Constant swb_assType. */
    public static final org.semanticwb.platform.SemanticProperty swb_assType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assType");
   
   /** Miembro participante dentro de una asociacion en la especificacion de TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    
    /** The Constant swb_hasMember. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasMember");
   
   /** Define una asociacion entre dos miembros en la especificacion de TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of Association for a model.
        * 
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

        /**
         * Creates the association.
         * 
         * @param model the model
         * @return the org.semanticwb.model. association
         */
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
    * Constructs a AssociationBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the Association
    */
    public AssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Type.
    * 
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
    * Remove the value for Type property.
    */

    public void removeType()
    {
        getSemanticObject().removeProperty(swb_assType);
    }

   /**
    * Gets the Type.
    * 
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
    * Gets true if has a Member.
    * 
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
    * Adds a Member.
    * 
    * @param value org.semanticwb.model.AssMember to add
    */

    public void addMember(org.semanticwb.model.AssMember value)
    {
        getSemanticObject().addObjectProperty(swb_hasMember, value.getSemanticObject());
    }
   
   /**
    * Removes all the Member.
    */

    public void removeAllMember()
    {
        getSemanticObject().removeProperty(swb_hasMember);
    }
   
   /**
    * Removes a Member.
    * 
    * @param value org.semanticwb.model.AssMember to remove
    */

    public void removeMember(org.semanticwb.model.AssMember value)
    {
        getSemanticObject().removeObjectProperty(swb_hasMember,value.getSemanticObject());
    }

   /**
    * Gets the Member.
    * 
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
    * Gets the WebSite.
    * 
    * @return a instance of org.semanticwb.model.WebSite
    */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
