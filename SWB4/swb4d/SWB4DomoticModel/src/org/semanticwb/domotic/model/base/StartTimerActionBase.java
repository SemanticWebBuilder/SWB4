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


public abstract class StartTimerActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasTimerDomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasTimerDomAction");
    public static final org.semanticwb.platform.SemanticProperty swb4d_delay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#delay");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");
    public static final org.semanticwb.platform.SemanticProperty swb4d_cancelEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#cancelEvent");
    public static final org.semanticwb.platform.SemanticClass swb4d_StartTimerAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#StartTimerAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#StartTimerAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of StartTimerAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.StartTimerAction for all models
       * @return Iterator of org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction>(it, true);
        }

        public static org.semanticwb.domotic.model.StartTimerAction createStartTimerAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.StartTimerAction.ClassMgr.createStartTimerAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return A org.semanticwb.domotic.model.StartTimerAction
       */
        public static org.semanticwb.domotic.model.StartTimerAction getStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.StartTimerAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return A org.semanticwb.domotic.model.StartTimerAction
       */
        public static org.semanticwb.domotic.model.StartTimerAction createStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.StartTimerAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       */
        public static void removeStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.StartTimerAction
       * @param id Identifier for org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return true if the org.semanticwb.domotic.model.StartTimerAction exists, false otherwise
       */

        public static boolean hasStartTimerAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStartTimerAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasTimerDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasTimerDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined CancelEvent
       * @param value CancelEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByCancelEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_cancelEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.StartTimerAction with a determined CancelEvent
       * @param value CancelEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.StartTimerAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.StartTimerAction> listStartTimerActionByCancelEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.StartTimerAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_cancelEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StartTimerActionBase.ClassMgr getStartTimerActionClassMgr()
    {
        return new StartTimerActionBase.ClassMgr();
    }

   /**
   * Constructs a StartTimerActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StartTimerAction
   */
    public StartTimerActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomAction
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomAction
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> listDomActions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(getSemanticObject().listObjectProperties(swb4d_hasTimerDomAction));
    }

   /**
   * Gets true if has a DomAction
   * @param value org.semanticwb.domotic.model.DomAction to verify
   * @return true if the org.semanticwb.domotic.model.DomAction exists, false otherwise
   */
    public boolean hasDomAction(org.semanticwb.domotic.model.DomAction value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasTimerDomAction,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomAction
   * @param value org.semanticwb.domotic.model.DomAction to add
   */

    public void addDomAction(org.semanticwb.domotic.model.DomAction value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasTimerDomAction, value.getSemanticObject());
    }
   /**
   * Removes all the DomAction
   */

    public void removeAllDomAction()
    {
        getSemanticObject().removeProperty(swb4d_hasTimerDomAction);
    }
   /**
   * Removes a DomAction
   * @param value org.semanticwb.domotic.model.DomAction to remove
   */

    public void removeDomAction(org.semanticwb.domotic.model.DomAction value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasTimerDomAction,value.getSemanticObject());
    }

   /**
   * Gets the DomAction
   * @return a org.semanticwb.domotic.model.DomAction
   */
    public org.semanticwb.domotic.model.DomAction getDomAction()
    {
         org.semanticwb.domotic.model.DomAction ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasTimerDomAction);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomAction)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Delay property
* @return long with the Delay
*/
    public long getDelay()
    {
        return getSemanticObject().getLongProperty(swb4d_delay);
    }

/**
* Sets the Delay property
* @param value long with the Delay
*/
    public void setDelay(long value)
    {
        getSemanticObject().setLongProperty(swb4d_delay, value);
    }
   /**
   * Sets the value for the property CancelEvent
   * @param value CancelEvent to set
   */

    public void setCancelEvent(org.semanticwb.domotic.model.DomEvent value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_cancelEvent, value.getSemanticObject());
        }else
        {
            removeCancelEvent();
        }
    }
   /**
   * Remove the value for CancelEvent property
   */

    public void removeCancelEvent()
    {
        getSemanticObject().removeProperty(swb4d_cancelEvent);
    }

   /**
   * Gets the CancelEvent
   * @return a org.semanticwb.domotic.model.DomEvent
   */
    public org.semanticwb.domotic.model.DomEvent getCancelEvent()
    {
         org.semanticwb.domotic.model.DomEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_cancelEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomEvent)obj.createGenericInstance();
         }
         return ret;
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
