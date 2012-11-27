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


public abstract class ConditionalFlowBase extends org.semanticwb.process.model.SequenceFlow implements org.semanticwb.model.Referensable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessRuleRefable,org.semanticwb.process.model.ConnectionObjectEditable
{
    public static final org.semanticwb.platform.SemanticClass swp_ConditionalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConditionalFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConditionalFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of ConditionalFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ConditionalFlow for all models
       * @return Iterator of org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow>(it, true);
        }

        public static org.semanticwb.process.model.ConditionalFlow createConditionalFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ConditionalFlow.ClassMgr.createConditionalFlow(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ConditionalFlow
       * @param id Identifier for org.semanticwb.process.model.ConditionalFlow
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return A org.semanticwb.process.model.ConditionalFlow
       */
        public static org.semanticwb.process.model.ConditionalFlow getConditionalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConditionalFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ConditionalFlow
       * @param id Identifier for org.semanticwb.process.model.ConditionalFlow
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return A org.semanticwb.process.model.ConditionalFlow
       */
        public static org.semanticwb.process.model.ConditionalFlow createConditionalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConditionalFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ConditionalFlow
       * @param id Identifier for org.semanticwb.process.model.ConditionalFlow
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       */
        public static void removeConditionalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ConditionalFlow
       * @param id Identifier for org.semanticwb.process.model.ConditionalFlow
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return true if the org.semanticwb.process.model.ConditionalFlow exists, false otherwise
       */

        public static boolean hasConditionalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConditionalFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined ProcessRuleRef
       * @param value ProcessRuleRef of the type org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined ProcessRuleRef
       * @param value ProcessRuleRef of the type org.semanticwb.process.model.ProcessRuleRef
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ConditionalFlow
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConditionalFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ConditionalFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlowByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ConditionalFlowBase.ClassMgr getConditionalFlowClassMgr()
    {
        return new ConditionalFlowBase.ClassMgr();
    }

   /**
   * Constructs a ConditionalFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ConditionalFlow
   */
    public ConditionalFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessRuleRef
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessRuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(getSemanticObject().listObjectProperties(swp_hasProcessRuleRef));
    }

   /**
   * Gets true if has a ProcessRuleRef
   * @param value org.semanticwb.process.model.ProcessRuleRef to verify
   * @return true if the org.semanticwb.process.model.ProcessRuleRef exists, false otherwise
   */
    public boolean hasProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessRuleRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ProcessRuleRef
   * @param value org.semanticwb.process.model.ProcessRuleRef to add
   */

    public void addProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
    {
        getSemanticObject().addObjectProperty(swp_hasProcessRuleRef, value.getSemanticObject());
    }
   /**
   * Removes all the ProcessRuleRef
   */

    public void removeAllProcessRuleRef()
    {
        getSemanticObject().removeProperty(swp_hasProcessRuleRef);
    }
   /**
   * Removes a ProcessRuleRef
   * @param value org.semanticwb.process.model.ProcessRuleRef to remove
   */

    public void removeProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
    {
        getSemanticObject().removeObjectProperty(swp_hasProcessRuleRef,value.getSemanticObject());
    }

   /**
   * Gets the ProcessRuleRef
   * @return a org.semanticwb.process.model.ProcessRuleRef
   */
    public org.semanticwb.process.model.ProcessRuleRef getProcessRuleRef()
    {
         org.semanticwb.process.model.ProcessRuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRuleRef)obj.createGenericInstance();
         }
         return ret;
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
