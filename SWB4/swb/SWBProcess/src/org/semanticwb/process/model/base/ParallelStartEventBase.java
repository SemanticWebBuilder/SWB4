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


public abstract class ParallelStartEventBase extends org.semanticwb.process.model.StartEventNode implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ParallelStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelStartEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelStartEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of ParallelStartEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ParallelStartEvent for all models
       * @return Iterator of org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent>(it, true);
        }

        public static org.semanticwb.process.model.ParallelStartEvent createParallelStartEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ParallelStartEvent.ClassMgr.createParallelStartEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ParallelStartEvent
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEvent
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return A org.semanticwb.process.model.ParallelStartEvent
       */
        public static org.semanticwb.process.model.ParallelStartEvent getParallelStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParallelStartEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ParallelStartEvent
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEvent
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return A org.semanticwb.process.model.ParallelStartEvent
       */
        public static org.semanticwb.process.model.ParallelStartEvent createParallelStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParallelStartEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ParallelStartEvent
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEvent
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       */
        public static void removeParallelStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ParallelStartEvent
       * @param id Identifier for org.semanticwb.process.model.ParallelStartEvent
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return true if the org.semanticwb.process.model.ParallelStartEvent exists, false otherwise
       */

        public static boolean hasParallelStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParallelStartEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByNext(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByNext(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ParallelStartEvent
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelStartEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ParallelStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ParallelStartEventBase.ClassMgr getParallelStartEventClassMgr()
    {
        return new ParallelStartEventBase.ClassMgr();
    }

   /**
   * Constructs a ParallelStartEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ParallelStartEvent
   */
    public ParallelStartEventBase(org.semanticwb.platform.SemanticObject base)
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
