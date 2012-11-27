/*
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
 */
package org.semanticwb.process.model.base;


public abstract class EventBasedGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_EventBasedGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EventBasedGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EventBasedGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of EventBasedGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.EventBasedGateway for all models
       * @return Iterator of org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return A org.semanticwb.process.model.EventBasedGateway
       */
        public static org.semanticwb.process.model.EventBasedGateway getEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EventBasedGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return A org.semanticwb.process.model.EventBasedGateway
       */
        public static org.semanticwb.process.model.EventBasedGateway createEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EventBasedGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       */
        public static void removeEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.EventBasedGateway
       * @param id Identifier for org.semanticwb.process.model.EventBasedGateway
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return true if the org.semanticwb.process.model.EventBasedGateway exists, false otherwise
       */

        public static boolean hasEventBasedGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEventBasedGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.EventBasedGateway
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.EventBasedGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.EventBasedGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EventBasedGatewayBase.ClassMgr getEventBasedGatewayClassMgr()
    {
        return new EventBasedGatewayBase.ClassMgr();
    }

   /**
   * Constructs a EventBasedGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EventBasedGateway
   */
    public EventBasedGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
