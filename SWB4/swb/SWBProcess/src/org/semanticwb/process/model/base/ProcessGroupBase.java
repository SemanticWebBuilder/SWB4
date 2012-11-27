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
   * Grupo de Procesos 
   */
public abstract class ProcessGroupBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass
{
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessInv");
   /**
   * Grupo de Procesos
   */
    public static final org.semanticwb.platform.SemanticClass swp_ProcessGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessGroup for all models
       * @return Iterator of org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup>(it, true);
        }

        public static org.semanticwb.process.model.ProcessGroup createProcessGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessGroup.ClassMgr.createProcessGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return A org.semanticwb.process.model.ProcessGroup
       */
        public static org.semanticwb.process.model.ProcessGroup getProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return A org.semanticwb.process.model.ProcessGroup
       */
        public static org.semanticwb.process.model.ProcessGroup createProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       */
        public static void removeProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return true if the org.semanticwb.process.model.ProcessGroup exists, false otherwise
       */

        public static boolean hasProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessGroupBase.ClassMgr getProcessGroupClassMgr()
    {
        return new ProcessGroupBase.ClassMgr();
    }

   /**
   * Constructs a ProcessGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessGroup
   */
    public ProcessGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.Process
   * @return A GenericIterator with all the org.semanticwb.process.model.Process
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> listProcesses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(getSemanticObject().listObjectProperties(swp_hasProcessInv));
    }

   /**
   * Gets true if has a Process
   * @param value org.semanticwb.process.model.Process to verify
   * @return true if the org.semanticwb.process.model.Process exists, false otherwise
   */
    public boolean hasProcess(org.semanticwb.process.model.Process value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Process
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
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
