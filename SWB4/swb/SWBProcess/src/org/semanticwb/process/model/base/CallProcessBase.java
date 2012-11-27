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


public abstract class CallProcessBase extends org.semanticwb.process.model.CallActivity implements org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.process.model.ResourceAssignmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_CallProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallProcess");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallProcess");

    public static class ClassMgr
    {
       /**
       * Returns a list of CallProcess for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CallProcess for all models
       * @return Iterator of org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess>(it, true);
        }

        public static org.semanticwb.process.model.CallProcess createCallProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CallProcess.ClassMgr.createCallProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CallProcess
       * @param id Identifier for org.semanticwb.process.model.CallProcess
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return A org.semanticwb.process.model.CallProcess
       */
        public static org.semanticwb.process.model.CallProcess getCallProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CallProcess
       * @param id Identifier for org.semanticwb.process.model.CallProcess
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return A org.semanticwb.process.model.CallProcess
       */
        public static org.semanticwb.process.model.CallProcess createCallProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CallProcess
       * @param id Identifier for org.semanticwb.process.model.CallProcess
       * @param model Model of the org.semanticwb.process.model.CallProcess
       */
        public static void removeCallProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CallProcess
       * @param id Identifier for org.semanticwb.process.model.CallProcess
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return true if the org.semanticwb.process.model.CallProcess exists, false otherwise
       */

        public static boolean hasCallProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.CallProcess
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallProcess with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.CallProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcess> listCallProcessByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CallProcessBase.ClassMgr getCallProcessClassMgr()
    {
        return new CallProcessBase.ClassMgr();
    }

   /**
   * Constructs a CallProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CallProcess
   */
    public CallProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
