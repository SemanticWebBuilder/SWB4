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
    * Miembro participante dentro de una asociacion en la especificacion de TopicMaps.
    */
public abstract class AssMemberBase extends org.semanticwb.model.SWBClass 
{
   
   /** Superclase utilizada por compatibilidad con estandar TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
    
    /** The Constant swb_assMember. */
    public static final org.semanticwb.platform.SemanticProperty swb_assMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assMember");
    
    /** The Constant swb_assRole. */
    public static final org.semanticwb.platform.SemanticProperty swb_assRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assRole");
   
   /** Define una asociacion entre dos miembros en la especificacion de TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    
    /** The Constant swb_associationInv. */
    public static final org.semanticwb.platform.SemanticProperty swb_associationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#associationInv");
   
   /** Miembro participante dentro de una asociacion en la especificacion de TopicMaps. */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of AssMember for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.AssMember
        */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.AssMember for all models
       * @return Iterator of org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(it, true);
        }

        /**
         * Creates the ass member.
         * 
         * @param model the model
         * @return the org.semanticwb.model. ass member
         */
        public static org.semanticwb.model.AssMember createAssMember(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.AssMember.ClassMgr.createAssMember(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.AssMember
       * @param id Identifier for org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.AssMember
       * @return A org.semanticwb.model.AssMember
       */
        public static org.semanticwb.model.AssMember getAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AssMember)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.AssMember
       * @param id Identifier for org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.AssMember
       * @return A org.semanticwb.model.AssMember
       */
        public static org.semanticwb.model.AssMember createAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AssMember)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.AssMember
       * @param id Identifier for org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.AssMember
       */
        public static void removeAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.AssMember
       * @param id Identifier for org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.AssMember
       * @return true if the org.semanticwb.model.AssMember exists, false otherwise
       */

        public static boolean hasAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssMember(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.AssMember with a determined Member
       * @param value Member of the type org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByMember(org.semanticwb.model.Topic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_assMember, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AssMember with a determined Member
       * @param value Member of the type org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByMember(org.semanticwb.model.Topic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_assMember,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AssMember with a determined Role
       * @param value Role of the type org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByRole(org.semanticwb.model.Topic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_assRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AssMember with a determined Role
       * @param value Role of the type org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByRole(org.semanticwb.model.Topic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_assRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AssMember with a determined Association
       * @param value Association of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_associationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.AssMember with a determined Association
       * @param value Association of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.AssMember
       */

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_associationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a AssMemberBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the AssMember
    */
    public AssMemberBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Member.
    * 
    * @param value Member to set
    */

    public void setMember(org.semanticwb.model.Topic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_assMember, value.getSemanticObject());
        }else
        {
            removeMember();
        }
    }
   
   /**
    * Remove the value for Member property.
    */

    public void removeMember()
    {
        getSemanticObject().removeProperty(swb_assMember);
    }

   /**
    * Gets the Member.
    * 
    * @return a org.semanticwb.model.Topic
    */
    public org.semanticwb.model.Topic getMember()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property Role.
    * 
    * @param value Role to set
    */

    public void setRole(org.semanticwb.model.Topic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_assRole, value.getSemanticObject());
        }else
        {
            removeRole();
        }
    }
   
   /**
    * Remove the value for Role property.
    */

    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_assRole);
    }

   /**
    * Gets the Role.
    * 
    * @return a org.semanticwb.model.Topic
    */
    public org.semanticwb.model.Topic getRole()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property Association.
    * 
    * @param value Association to set
    */

    public void setAssociation(org.semanticwb.model.Association value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_associationInv, value.getSemanticObject());
        }else
        {
            removeAssociation();
        }
    }
   
   /**
    * Remove the value for Association property.
    */

    public void removeAssociation()
    {
        getSemanticObject().removeProperty(swb_associationInv);
    }

   /**
    * Gets the Association.
    * 
    * @return a org.semanticwb.model.Association
    */
    public org.semanticwb.model.Association getAssociation()
    {
         org.semanticwb.model.Association ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_associationInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Association)obj.createGenericInstance();
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
