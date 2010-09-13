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
    * Referencia a un objeto de tipo UserGroup.
    */
public abstract class UserGroupRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    
    /** The Constant swb_userGroup. */
    public static final org.semanticwb.platform.SemanticProperty swb_userGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userGroup");
   
   /** Referencia a un objeto de tipo UserGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of UserGroupRef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.UserGroupRef
        */

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UserGroupRef for all models
       * @return Iterator of org.semanticwb.model.UserGroupRef
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(it, true);
        }

        /**
         * Creates the user group ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. user group ref
         */
        public static org.semanticwb.model.UserGroupRef createUserGroupRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.UserGroupRef.ClassMgr.createUserGroupRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.UserGroupRef
       * @param id Identifier for org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.UserGroupRef
       * @return A org.semanticwb.model.UserGroupRef
       */
        public static org.semanticwb.model.UserGroupRef getUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroupRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UserGroupRef
       * @param id Identifier for org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.UserGroupRef
       * @return A org.semanticwb.model.UserGroupRef
       */
        public static org.semanticwb.model.UserGroupRef createUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroupRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UserGroupRef
       * @param id Identifier for org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.UserGroupRef
       */
        public static void removeUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UserGroupRef
       * @param id Identifier for org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.UserGroupRef
       * @return true if the org.semanticwb.model.UserGroupRef exists, false otherwise
       */

        public static boolean hasUserGroupRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserGroupRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.UserGroupRef with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.model.UserGroupRef
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserGroupRef with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.UserGroupRef
       */

        public static java.util.Iterator<org.semanticwb.model.UserGroupRef> listUserGroupRefByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userGroup,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a UserGroupRefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the UserGroupRef
    */
    public UserGroupRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property UserGroup.
    * 
    * @param value UserGroup to set
    */

    public void setUserGroup(org.semanticwb.model.UserGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_userGroup, value.getSemanticObject());
        }else
        {
            removeUserGroup();
        }
    }
   
   /**
    * Remove the value for UserGroup property.
    */

    public void removeUserGroup()
    {
        getSemanticObject().removeProperty(swb_userGroup);
    }

   /**
    * Gets the UserGroup.
    * 
    * @return a org.semanticwb.model.UserGroup
    */
    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }
}
