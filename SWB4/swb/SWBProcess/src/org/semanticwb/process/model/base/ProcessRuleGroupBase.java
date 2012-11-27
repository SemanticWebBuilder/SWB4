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


   /**
   * Objeto que define un Grupo de reglas de negocio 
   */
public abstract class ProcessRuleGroupBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRule");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessRuleInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessRuleInv");
   /**
   * Objeto que define un Grupo de reglas de negocio
   */
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessRuleGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessRuleGroup for all models
       * @return Iterator of org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup>(it, true);
        }

        public static org.semanticwb.process.model.ProcessRuleGroup createProcessRuleGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessRuleGroup.ClassMgr.createProcessRuleGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessRuleGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleGroup
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       * @return A org.semanticwb.process.model.ProcessRuleGroup
       */
        public static org.semanticwb.process.model.ProcessRuleGroup getProcessRuleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRuleGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessRuleGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleGroup
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       * @return A org.semanticwb.process.model.ProcessRuleGroup
       */
        public static org.semanticwb.process.model.ProcessRuleGroup createProcessRuleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRuleGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessRuleGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleGroup
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       */
        public static void removeProcessRuleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessRuleGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessRuleGroup
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       * @return true if the org.semanticwb.process.model.ProcessRuleGroup exists, false otherwise
       */

        public static boolean hasProcessRuleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessRuleGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleGroup with a determined ProcessRule
       * @param value ProcessRule of the type org.semanticwb.process.model.ProcessRule
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroupByProcessRule(org.semanticwb.process.model.ProcessRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleGroup with a determined ProcessRule
       * @param value ProcessRule of the type org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroupByProcessRule(org.semanticwb.process.model.ProcessRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessRuleGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRuleGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessRuleGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleGroup> listProcessRuleGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessRuleGroupBase.ClassMgr getProcessRuleGroupClassMgr()
    {
        return new ProcessRuleGroupBase.ClassMgr();
    }

   /**
   * Constructs a ProcessRuleGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessRuleGroup
   */
    public ProcessRuleGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessRule
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessRule
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> listProcessRules()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule>(getSemanticObject().listObjectProperties(swp_hasProcessRuleInv));
    }

   /**
   * Gets true if has a ProcessRule
   * @param value org.semanticwb.process.model.ProcessRule to verify
   * @return true if the org.semanticwb.process.model.ProcessRule exists, false otherwise
   */
    public boolean hasProcessRule(org.semanticwb.process.model.ProcessRule value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessRuleInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ProcessRule
   * @return a org.semanticwb.process.model.ProcessRule
   */
    public org.semanticwb.process.model.ProcessRule getProcessRule()
    {
         org.semanticwb.process.model.ProcessRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessRuleInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRule)obj.createGenericInstance();
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
