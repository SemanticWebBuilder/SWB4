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


public abstract class StoreRepositoryFileBase extends org.semanticwb.process.model.ProcessService implements org.semanticwb.process.model.StoreRepositoryNodeable
{
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeVarName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeVarName");
    public static final org.semanticwb.platform.SemanticClass swp_StoreRepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of StoreRepositoryFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.StoreRepositoryFile for all models
       * @return Iterator of org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile>(it, true);
        }

        public static org.semanticwb.process.model.StoreRepositoryFile createStoreRepositoryFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.StoreRepositoryFile.ClassMgr.createStoreRepositoryFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return A org.semanticwb.process.model.StoreRepositoryFile
       */
        public static org.semanticwb.process.model.StoreRepositoryFile getStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StoreRepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return A org.semanticwb.process.model.StoreRepositoryFile
       */
        public static org.semanticwb.process.model.StoreRepositoryFile createStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StoreRepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       */
        public static void removeStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.StoreRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.StoreRepositoryFile
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return true if the org.semanticwb.process.model.StoreRepositoryFile exists, false otherwise
       */

        public static boolean hasStoreRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStoreRepositoryFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined NodeDirectory
       * @param value NodeDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined NodeDirectory
       * @param value NodeDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined NodeStatus
       * @param value NodeStatus of the type org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByNodeStatus(org.semanticwb.process.model.ItemAwareStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined NodeStatus
       * @param value NodeStatus of the type org.semanticwb.process.model.ItemAwareStatus
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByNodeStatus(org.semanticwb.process.model.ItemAwareStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.StoreRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StoreRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.StoreRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.StoreRepositoryFile> listStoreRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StoreRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StoreRepositoryFileBase.ClassMgr getStoreRepositoryFileClassMgr()
    {
        return new StoreRepositoryFileBase.ClassMgr();
    }

   /**
   * Constructs a StoreRepositoryFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StoreRepositoryFile
   */
    public StoreRepositoryFileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NodeId property
* @return String with the NodeId
*/
    public String getNodeId()
    {
        return getSemanticObject().getProperty(swp_storeRepNodeId);
    }

/**
* Sets the NodeId property
* @param value long with the NodeId
*/
    public void setNodeId(String value)
    {
        getSemanticObject().setProperty(swp_storeRepNodeId, value);
    }
   /**
   * Sets the value for the property NodeDirectory
   * @param value NodeDirectory to set
   */

    public void setNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_storeRepNodeDirectory, value.getSemanticObject());
        }else
        {
            removeNodeDirectory();
        }
    }
   /**
   * Remove the value for NodeDirectory property
   */

    public void removeNodeDirectory()
    {
        getSemanticObject().removeProperty(swp_storeRepNodeDirectory);
    }

   /**
   * Gets the NodeDirectory
   * @return a org.semanticwb.process.model.RepositoryDirectory
   */
    public org.semanticwb.process.model.RepositoryDirectory getNodeDirectory()
    {
         org.semanticwb.process.model.RepositoryDirectory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_storeRepNodeDirectory);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.RepositoryDirectory)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NodeName property
* @return String with the NodeName
*/
    public String getNodeName()
    {
        return getSemanticObject().getProperty(swp_storeRepNodeName);
    }

/**
* Sets the NodeName property
* @param value long with the NodeName
*/
    public void setNodeName(String value)
    {
        getSemanticObject().setProperty(swp_storeRepNodeName, value);
    }
   /**
   * Sets the value for the property NodeStatus
   * @param value NodeStatus to set
   */

    public void setNodeStatus(org.semanticwb.process.model.ItemAwareStatus value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_storeRepNodeStatus, value.getSemanticObject());
        }else
        {
            removeNodeStatus();
        }
    }
   /**
   * Remove the value for NodeStatus property
   */

    public void removeNodeStatus()
    {
        getSemanticObject().removeProperty(swp_storeRepNodeStatus);
    }

   /**
   * Gets the NodeStatus
   * @return a org.semanticwb.process.model.ItemAwareStatus
   */
    public org.semanticwb.process.model.ItemAwareStatus getNodeStatus()
    {
         org.semanticwb.process.model.ItemAwareStatus ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_storeRepNodeStatus);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAwareStatus)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NodeComment property
* @return String with the NodeComment
*/
    public String getNodeComment()
    {
        return getSemanticObject().getProperty(swp_storeRepNodeComment);
    }

/**
* Sets the NodeComment property
* @param value long with the NodeComment
*/
    public void setNodeComment(String value)
    {
        getSemanticObject().setProperty(swp_storeRepNodeComment, value);
    }

/**
* Gets the NodeVarName property
* @return String with the NodeVarName
*/
    public String getNodeVarName()
    {
        return getSemanticObject().getProperty(swp_storeRepNodeVarName);
    }

/**
* Sets the NodeVarName property
* @param value long with the NodeVarName
*/
    public void setNodeVarName(String value)
    {
        getSemanticObject().setProperty(swp_storeRepNodeVarName, value);
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
