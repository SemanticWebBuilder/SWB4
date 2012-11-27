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


public abstract class ProcessDataInstanceModelBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessDataInstanceModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessDataInstanceModel");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessDataInstanceModel");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessDataInstanceModel for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessDataInstanceModel for all models
       * @return Iterator of org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessDataInstanceModel
       * @param id Identifier for org.semanticwb.process.model.ProcessDataInstanceModel
       * @return A org.semanticwb.process.model.ProcessDataInstanceModel
       */
        public static org.semanticwb.process.model.ProcessDataInstanceModel getProcessDataInstanceModel(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.process.model.ProcessDataInstanceModel ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.process.model.ProcessDataInstanceModel)
                    {
                        ret=(org.semanticwb.process.model.ProcessDataInstanceModel)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.process.model.ProcessDataInstanceModel
       * @param id Identifier for org.semanticwb.process.model.ProcessDataInstanceModel
       * @return A org.semanticwb.process.model.ProcessDataInstanceModel
       */
        public static org.semanticwb.process.model.ProcessDataInstanceModel createProcessDataInstanceModel(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.process.model.ProcessDataInstanceModel)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessDataInstanceModel
       * @param id Identifier for org.semanticwb.process.model.ProcessDataInstanceModel
       */
        public static void removeProcessDataInstanceModel(String id)
        {
            org.semanticwb.process.model.ProcessDataInstanceModel obj=getProcessDataInstanceModel(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessDataInstanceModel
       * @param id Identifier for org.semanticwb.process.model.ProcessDataInstanceModel
       * @return true if the org.semanticwb.process.model.ProcessDataInstanceModel exists, false otherwise
       */

        public static boolean hasProcessDataInstanceModel(String id)
        {
            return (getProcessDataInstanceModel(id)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessDataInstanceModel
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessDataInstanceModel
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.process.model.ProcessDataInstanceModel
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.process.model.ProcessDataInstanceModel
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessDataInstanceModel with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.process.model.ProcessDataInstanceModel
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessDataInstanceModel> listProcessDataInstanceModelByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessDataInstanceModel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessDataInstanceModelBase.ClassMgr getProcessDataInstanceModelClassMgr()
    {
        return new ProcessDataInstanceModelBase.ClassMgr();
    }

   /**
   * Constructs a ProcessDataInstanceModelBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessDataInstanceModel
   */
    public ProcessDataInstanceModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
