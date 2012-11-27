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


public abstract class SendTwitterActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_SendTwitterAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendTwitterAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#SendTwitterAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of SendTwitterAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.SendTwitterAction for all models
       * @return Iterator of org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction>(it, true);
        }

        public static org.semanticwb.domotic.model.SendTwitterAction createSendTwitterAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.SendTwitterAction.ClassMgr.createSendTwitterAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.SendTwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.SendTwitterAction
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       * @return A org.semanticwb.domotic.model.SendTwitterAction
       */
        public static org.semanticwb.domotic.model.SendTwitterAction getSendTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.SendTwitterAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.SendTwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.SendTwitterAction
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       * @return A org.semanticwb.domotic.model.SendTwitterAction
       */
        public static org.semanticwb.domotic.model.SendTwitterAction createSendTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.SendTwitterAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.SendTwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.SendTwitterAction
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       */
        public static void removeSendTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.SendTwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.SendTwitterAction
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       * @return true if the org.semanticwb.domotic.model.SendTwitterAction exists, false otherwise
       */

        public static boolean hasSendTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSendTwitterAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendTwitterAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendTwitterAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendTwitterAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendTwitterAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendTwitterAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.SendTwitterAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.SendTwitterAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.SendTwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.SendTwitterAction> listSendTwitterActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.SendTwitterAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SendTwitterActionBase.ClassMgr getSendTwitterActionClassMgr()
    {
        return new SendTwitterActionBase.ClassMgr();
    }

   /**
   * Constructs a SendTwitterActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SendTwitterAction
   */
    public SendTwitterActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
