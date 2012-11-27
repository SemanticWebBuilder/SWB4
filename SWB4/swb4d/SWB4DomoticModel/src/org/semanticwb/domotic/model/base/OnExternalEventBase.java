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
package org.semanticwb.domotic.model.base;


public abstract class OnExternalEventBase extends org.semanticwb.domotic.model.DomEvent implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_OnExternalEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnExternalEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnExternalEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnExternalEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnExternalEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnExternalEvent> listOnExternalEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnExternalEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnExternalEvent for all models
       * @return Iterator of org.semanticwb.domotic.model.OnExternalEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnExternalEvent> listOnExternalEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnExternalEvent>(it, true);
        }

        public static org.semanticwb.domotic.model.OnExternalEvent createOnExternalEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnExternalEvent.ClassMgr.createOnExternalEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnExternalEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnExternalEvent
       * @param model Model of the org.semanticwb.domotic.model.OnExternalEvent
       * @return A org.semanticwb.domotic.model.OnExternalEvent
       */
        public static org.semanticwb.domotic.model.OnExternalEvent getOnExternalEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnExternalEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnExternalEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnExternalEvent
       * @param model Model of the org.semanticwb.domotic.model.OnExternalEvent
       * @return A org.semanticwb.domotic.model.OnExternalEvent
       */
        public static org.semanticwb.domotic.model.OnExternalEvent createOnExternalEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnExternalEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnExternalEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnExternalEvent
       * @param model Model of the org.semanticwb.domotic.model.OnExternalEvent
       */
        public static void removeOnExternalEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnExternalEvent
       * @param id Identifier for org.semanticwb.domotic.model.OnExternalEvent
       * @param model Model of the org.semanticwb.domotic.model.OnExternalEvent
       * @return true if the org.semanticwb.domotic.model.OnExternalEvent exists, false otherwise
       */

        public static boolean hasOnExternalEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnExternalEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnExternalEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnExternalEvent
       * @return Iterator with all the org.semanticwb.domotic.model.OnExternalEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnExternalEvent> listOnExternalEventByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnExternalEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnExternalEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnExternalEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnExternalEvent> listOnExternalEventByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnExternalEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnExternalEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnExternalEvent
       * @return Iterator with all the org.semanticwb.domotic.model.OnExternalEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnExternalEvent> listOnExternalEventByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnExternalEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnExternalEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnExternalEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnExternalEvent> listOnExternalEventByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnExternalEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnExternalEventBase.ClassMgr getOnExternalEventClassMgr()
    {
        return new OnExternalEventBase.ClassMgr();
    }

   /**
   * Constructs a OnExternalEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnExternalEvent
   */
    public OnExternalEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
