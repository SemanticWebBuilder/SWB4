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
    * Objeto que representa una relacion de favoritos entre un usuario y algun elemento del arbol de navegacion dentro de la administración de SWB.
    */
public abstract class UserFavoriteBase extends org.semanticwb.model.SWBClass 
{
    
    /** The Constant owl_Thing. */
    public static final org.semanticwb.platform.SemanticClass owl_Thing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Thing");
    
    /** The Constant swb_usrfHasObject. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrfHasObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrfHasObject");
   
   /** Objeto que representa una relacion de favoritos entre un usuario y algun elemento del arbol de navegacion dentro de la administración de SWB. */
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of UserFavorite for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.UserFavorite
        */

        public static java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorite>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UserFavorite for all models
       * @return Iterator of org.semanticwb.model.UserFavorite
       */

        public static java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorite>(it, true);
        }

        /**
         * Creates the user favorite.
         * 
         * @param model the model
         * @return the org.semanticwb.model. user favorite
         */
        public static org.semanticwb.model.UserFavorite createUserFavorite(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.UserFavorite.ClassMgr.createUserFavorite(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.UserFavorite
       * @param id Identifier for org.semanticwb.model.UserFavorite
       * @param model Model of the org.semanticwb.model.UserFavorite
       * @return A org.semanticwb.model.UserFavorite
       */
        public static org.semanticwb.model.UserFavorite getUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserFavorite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UserFavorite
       * @param id Identifier for org.semanticwb.model.UserFavorite
       * @param model Model of the org.semanticwb.model.UserFavorite
       * @return A org.semanticwb.model.UserFavorite
       */
        public static org.semanticwb.model.UserFavorite createUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserFavorite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UserFavorite
       * @param id Identifier for org.semanticwb.model.UserFavorite
       * @param model Model of the org.semanticwb.model.UserFavorite
       */
        public static void removeUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UserFavorite
       * @param id Identifier for org.semanticwb.model.UserFavorite
       * @param model Model of the org.semanticwb.model.UserFavorite
       * @return true if the org.semanticwb.model.UserFavorite exists, false otherwise
       */

        public static boolean hasUserFavorite(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserFavorite(id, model)!=null);
        }
    }

   /**
    * Constructs a UserFavoriteBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the UserFavorite
    */
    public UserFavoriteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List objects.
     * 
     * @return the org.semanticwb.platform. semantic iterator
     */
    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listObjects()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swb_usrfHasObject.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    /**
     * Adds the object.
     * 
     * @param value the value
     */
    public void addObject(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swb_usrfHasObject, value);
    }

    /**
     * Removes the all object.
     */
    public void removeAllObject()
    {
        getSemanticObject().removeProperty(swb_usrfHasObject);
    }

    /**
     * Removes the object.
     * 
     * @param value the value
     */
    public void removeObject(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(swb_usrfHasObject,value);
    }

/**
 * Gets the Object property.
 * 
 * @return the value for the property as org.semanticwb.platform.SemanticObject
 */
    public org.semanticwb.platform.SemanticObject getObject()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_usrfHasObject);
         return ret;
    }

   /**
    * Gets the UserRepository.
    * 
    * @return a instance of org.semanticwb.model.UserRepository
    */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
