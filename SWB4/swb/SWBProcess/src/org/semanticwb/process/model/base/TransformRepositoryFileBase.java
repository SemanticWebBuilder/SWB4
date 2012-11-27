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


public abstract class TransformRepositoryFileBase extends org.semanticwb.process.model.ProcessService implements org.semanticwb.process.model.StoreRepositoryNodeable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessFileTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_transformRepFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#transformRepFileTemplate");
    public static final org.semanticwb.platform.SemanticProperty swp_transformRepNodeVarName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#transformRepNodeVarName");
    public static final org.semanticwb.platform.SemanticClass swp_TransformRepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransformRepositoryFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransformRepositoryFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of TransformRepositoryFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.TransformRepositoryFile for all models
       * @return Iterator of org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile>(it, true);
        }

        public static org.semanticwb.process.model.TransformRepositoryFile createTransformRepositoryFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TransformRepositoryFile.ClassMgr.createTransformRepositoryFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return A org.semanticwb.process.model.TransformRepositoryFile
       */
        public static org.semanticwb.process.model.TransformRepositoryFile getTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TransformRepositoryFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return A org.semanticwb.process.model.TransformRepositoryFile
       */
        public static org.semanticwb.process.model.TransformRepositoryFile createTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TransformRepositoryFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       */
        public static void removeTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.TransformRepositoryFile
       * @param id Identifier for org.semanticwb.process.model.TransformRepositoryFile
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return true if the org.semanticwb.process.model.TransformRepositoryFile exists, false otherwise
       */

        public static boolean hasTransformRepositoryFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTransformRepositoryFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined NodeDirectory
       * @param value NodeDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeDirectory, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined NodeDirectory
       * @param value NodeDirectory of the type org.semanticwb.process.model.RepositoryDirectory
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeDirectory,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined NodeStatus
       * @param value NodeStatus of the type org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByNodeStatus(org.semanticwb.process.model.ItemAwareStatus value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined NodeStatus
       * @param value NodeStatus of the type org.semanticwb.process.model.ItemAwareStatus
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByNodeStatus(org.semanticwb.process.model.ItemAwareStatus value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_storeRepNodeStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined FileTemplate
       * @param value FileTemplate of the type org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_transformRepFileTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined FileTemplate
       * @param value FileTemplate of the type org.semanticwb.process.model.ProcessFileTemplate
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_transformRepFileTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.TransformRepositoryFile
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransformRepositoryFile with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.TransformRepositoryFile
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransformRepositoryFile> listTransformRepositoryFileByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransformRepositoryFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TransformRepositoryFileBase.ClassMgr getTransformRepositoryFileClassMgr()
    {
        return new TransformRepositoryFileBase.ClassMgr();
    }

   /**
   * Constructs a TransformRepositoryFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TransformRepositoryFile
   */
    public TransformRepositoryFileBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property FileTemplate
   * @param value FileTemplate to set
   */

    public void setFileTemplate(org.semanticwb.process.model.ProcessFileTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_transformRepFileTemplate, value.getSemanticObject());
        }else
        {
            removeFileTemplate();
        }
    }
   /**
   * Remove the value for FileTemplate property
   */

    public void removeFileTemplate()
    {
        getSemanticObject().removeProperty(swp_transformRepFileTemplate);
    }

   /**
   * Gets the FileTemplate
   * @return a org.semanticwb.process.model.ProcessFileTemplate
   */
    public org.semanticwb.process.model.ProcessFileTemplate getFileTemplate()
    {
         org.semanticwb.process.model.ProcessFileTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_transformRepFileTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessFileTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NodeVarName property
* @return String with the NodeVarName
*/
    public String getNodeVarName()
    {
        return getSemanticObject().getProperty(swp_transformRepNodeVarName);
    }

/**
* Sets the NodeVarName property
* @param value long with the NodeVarName
*/
    public void setNodeVarName(String value)
    {
        getSemanticObject().setProperty(swp_transformRepNodeVarName, value);
    }
}
