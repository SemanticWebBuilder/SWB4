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
    * Objeto por medio del cual se define un tipo de componente o recurso, con especializacion de estilo JSP.
    */
public abstract class JSPResourceTypeBase extends org.semanticwb.model.ResourceType implements org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_rtJspPath. */
    public static final org.semanticwb.platform.SemanticProperty swb_rtJspPath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rtJspPath");
   
   /** Objeto por medio del cual se define un tipo de componente o recurso, con especializacion de estilo JSP. */
    public static final org.semanticwb.platform.SemanticClass swb_JSPResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#JSPResourceType");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#JSPResourceType");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of JSPResourceType for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.JSPResourceType
        */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.JSPResourceType for all models
       * @return Iterator of org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.JSPResourceType
       * @param id Identifier for org.semanticwb.model.JSPResourceType
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return A org.semanticwb.model.JSPResourceType
       */
        public static org.semanticwb.model.JSPResourceType getJSPResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.JSPResourceType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.JSPResourceType
       * @param id Identifier for org.semanticwb.model.JSPResourceType
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return A org.semanticwb.model.JSPResourceType
       */
        public static org.semanticwb.model.JSPResourceType createJSPResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.JSPResourceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.JSPResourceType
       * @param id Identifier for org.semanticwb.model.JSPResourceType
       * @param model Model of the org.semanticwb.model.JSPResourceType
       */
        public static void removeJSPResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.JSPResourceType
       * @param id Identifier for org.semanticwb.model.JSPResourceType
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return true if the org.semanticwb.model.JSPResourceType exists, false otherwise
       */

        public static boolean hasJSPResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getJSPResourceType(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined ResourceCollection
       * @param value ResourceCollection of the type org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByResourceCollection(org.semanticwb.model.ResourceCollection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollectionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined ResourceCollection
       * @param value ResourceCollection of the type org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByResourceCollection(org.semanticwb.model.ResourceCollection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollectionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined SubType
       * @param value SubType of the type org.semanticwb.model.ResourceSubType
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeBySubType(org.semanticwb.model.ResourceSubType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTSubType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined SubType
       * @param value SubType of the type org.semanticwb.model.ResourceSubType
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeBySubType(org.semanticwb.model.ResourceSubType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTSubType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.JSPResourceType
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.JSPResourceType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.JSPResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.JSPResourceType> listJSPResourceTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.JSPResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a JSPResourceTypeBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the JSPResourceType
    */
    public JSPResourceTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the JspPath property.
 * 
 * @return String with the JspPath
 */
    public String getJspPath()
    {
        return getSemanticObject().getProperty(swb_rtJspPath);
    }

/**
 * Sets the JspPath property.
 * 
 * @param value long with the JspPath
 */
    public void setJspPath(String value)
    {
        getSemanticObject().setProperty(swb_rtJspPath, value);
    }
}
