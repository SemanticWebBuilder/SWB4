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
    * Objeto que define un tipo de usuarios (utilizado para agrupar usuarios en base a sus caracteristicas).
    */
public abstract class UserTypeDefBase extends org.semanticwb.model.SWBClass 
{
   
   /** Objeto que define un tipo de usuarios (utilizado para agrupar usuarios en base a sus caracteristicas). */
    public static final org.semanticwb.platform.SemanticClass swb_UserTypeDef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserTypeDef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserTypeDef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of UserTypeDef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.UserTypeDef
        */

        public static java.util.Iterator<org.semanticwb.model.UserTypeDef> listUserTypeDefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserTypeDef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UserTypeDef for all models
       * @return Iterator of org.semanticwb.model.UserTypeDef
       */

        public static java.util.Iterator<org.semanticwb.model.UserTypeDef> listUserTypeDefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserTypeDef>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.UserTypeDef
       * @param id Identifier for org.semanticwb.model.UserTypeDef
       * @param model Model of the org.semanticwb.model.UserTypeDef
       * @return A org.semanticwb.model.UserTypeDef
       */
        public static org.semanticwb.model.UserTypeDef getUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserTypeDef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UserTypeDef
       * @param id Identifier for org.semanticwb.model.UserTypeDef
       * @param model Model of the org.semanticwb.model.UserTypeDef
       * @return A org.semanticwb.model.UserTypeDef
       */
        public static org.semanticwb.model.UserTypeDef createUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserTypeDef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UserTypeDef
       * @param id Identifier for org.semanticwb.model.UserTypeDef
       * @param model Model of the org.semanticwb.model.UserTypeDef
       */
        public static void removeUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UserTypeDef
       * @param id Identifier for org.semanticwb.model.UserTypeDef
       * @param model Model of the org.semanticwb.model.UserTypeDef
       * @return true if the org.semanticwb.model.UserTypeDef exists, false otherwise
       */

        public static boolean hasUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserTypeDef(id, model)!=null);
        }
    }

   /**
    * Constructs a UserTypeDefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the UserTypeDef
    */
    public UserTypeDefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
