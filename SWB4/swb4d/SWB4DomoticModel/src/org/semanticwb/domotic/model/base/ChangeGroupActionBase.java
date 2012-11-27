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


public abstract class ChangeGroupActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_changeGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#changeGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_changeGroupStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#changeGroupStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeGroupAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeGroupAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeGroupAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeGroupAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeGroupAction for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeGroupAction createChangeGroupAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.createChangeGroupAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return A org.semanticwb.domotic.model.ChangeGroupAction
       */
        public static org.semanticwb.domotic.model.ChangeGroupAction getChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeGroupAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return A org.semanticwb.domotic.model.ChangeGroupAction
       */
        public static org.semanticwb.domotic.model.ChangeGroupAction createChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeGroupAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       */
        public static void removeChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return true if the org.semanticwb.domotic.model.ChangeGroupAction exists, false otherwise
       */

        public static boolean hasChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeGroupAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined ChangeGroup
       * @param value ChangeGroup of the type org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByChangeGroup(org.semanticwb.domotic.model.DomGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_changeGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined ChangeGroup
       * @param value ChangeGroup of the type org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByChangeGroup(org.semanticwb.domotic.model.DomGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_changeGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeGroupActionBase.ClassMgr getChangeGroupActionClassMgr()
    {
        return new ChangeGroupActionBase.ClassMgr();
    }

   /**
   * Constructs a ChangeGroupActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeGroupAction
   */
    public ChangeGroupActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ChangeGroup
   * @param value ChangeGroup to set
   */

    public void setChangeGroup(org.semanticwb.domotic.model.DomGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_changeGroup, value.getSemanticObject());
        }else
        {
            removeChangeGroup();
        }
    }
   /**
   * Remove the value for ChangeGroup property
   */

    public void removeChangeGroup()
    {
        getSemanticObject().removeProperty(swb4d_changeGroup);
    }

   /**
   * Gets the ChangeGroup
   * @return a org.semanticwb.domotic.model.DomGroup
   */
    public org.semanticwb.domotic.model.DomGroup getChangeGroup()
    {
         org.semanticwb.domotic.model.DomGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_changeGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ChangeGroupStat property
* @return int with the ChangeGroupStat
*/
    public int getChangeGroupStat()
    {
        return getSemanticObject().getIntProperty(swb4d_changeGroupStat);
    }

/**
* Sets the ChangeGroupStat property
* @param value long with the ChangeGroupStat
*/
    public void setChangeGroupStat(int value)
    {
        getSemanticObject().setIntProperty(swb4d_changeGroupStat, value);
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
