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


public abstract class DomGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_parentGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#parentGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasChildGroupInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasChildGroupInv");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasDomDeviceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasDomDeviceInv");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnGroupChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnGroupChange");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasOnGroupChangeInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasOnGroupChangeInv");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomGroup for all models
       * @return Iterator of org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup>(it, true);
        }

        public static org.semanticwb.domotic.model.DomGroup createDomGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomGroup.ClassMgr.createDomGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomGroup
       * @param id Identifier for org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return A org.semanticwb.domotic.model.DomGroup
       */
        public static org.semanticwb.domotic.model.DomGroup getDomGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomGroup
       * @param id Identifier for org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return A org.semanticwb.domotic.model.DomGroup
       */
        public static org.semanticwb.domotic.model.DomGroup createDomGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomGroup
       * @param id Identifier for org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       */
        public static void removeDomGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomGroup
       * @param id Identifier for org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return true if the org.semanticwb.domotic.model.DomGroup exists, false otherwise
       */

        public static boolean hasDomGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined ParentGroup
       * @param value ParentGroup of the type org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByParentGroup(org.semanticwb.domotic.model.DomGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_parentGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined ParentGroup
       * @param value ParentGroup of the type org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByParentGroup(org.semanticwb.domotic.model.DomGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_parentGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined ChildGroup
       * @param value ChildGroup of the type org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByChildGroup(org.semanticwb.domotic.model.DomGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasChildGroupInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined ChildGroup
       * @param value ChildGroup of the type org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByChildGroup(org.semanticwb.domotic.model.DomGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasChildGroupInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByDomDevice(org.semanticwb.domotic.model.DomDevice value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomDeviceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByDomDevice(org.semanticwb.domotic.model.DomDevice value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomDeviceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined OnGroupChange
       * @param value OnGroupChange of the type org.semanticwb.domotic.model.OnGroupChange
       * @param model Model of the org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByOnGroupChange(org.semanticwb.domotic.model.OnGroupChange value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasOnGroupChangeInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomGroup with a determined OnGroupChange
       * @param value OnGroupChange of the type org.semanticwb.domotic.model.OnGroupChange
       * @return Iterator with all the org.semanticwb.domotic.model.DomGroup
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomGroup> listDomGroupByOnGroupChange(org.semanticwb.domotic.model.OnGroupChange value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasOnGroupChangeInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomGroupBase.ClassMgr getDomGroupClassMgr()
    {
        return new DomGroupBase.ClassMgr();
    }

   /**
   * Constructs a DomGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomGroup
   */
    public DomGroupBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Sets the value for the property ParentGroup
   * @param value ParentGroup to set
   */

    public void setParentGroup(org.semanticwb.domotic.model.DomGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_parentGroup, value.getSemanticObject());
        }else
        {
            removeParentGroup();
        }
    }
   /**
   * Remove the value for ParentGroup property
   */

    public void removeParentGroup()
    {
        getSemanticObject().removeProperty(swb4d_parentGroup);
    }

   /**
   * Gets the ParentGroup
   * @return a org.semanticwb.domotic.model.DomGroup
   */
    public org.semanticwb.domotic.model.DomGroup getParentGroup()
    {
         org.semanticwb.domotic.model.DomGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_parentGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomGroup)obj.createGenericInstance();
         }
         return ret;
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

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomGroup
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> listChildGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup>(getSemanticObject().listObjectProperties(swb4d_hasChildGroupInv));
    }

   /**
   * Gets true if has a ChildGroup
   * @param value org.semanticwb.domotic.model.DomGroup to verify
   * @return true if the org.semanticwb.domotic.model.DomGroup exists, false otherwise
   */
    public boolean hasChildGroup(org.semanticwb.domotic.model.DomGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasChildGroupInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ChildGroup
   * @return a org.semanticwb.domotic.model.DomGroup
   */
    public org.semanticwb.domotic.model.DomGroup getChildGroup()
    {
         org.semanticwb.domotic.model.DomGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasChildGroupInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomGroup)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomDevice
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomDevice
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> listDomDevices()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice>(getSemanticObject().listObjectProperties(swb4d_hasDomDeviceInv));
    }

   /**
   * Gets true if has a DomDevice
   * @param value org.semanticwb.domotic.model.DomDevice to verify
   * @return true if the org.semanticwb.domotic.model.DomDevice exists, false otherwise
   */
    public boolean hasDomDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasDomDeviceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the DomDevice
   * @return a org.semanticwb.domotic.model.DomDevice
   */
    public org.semanticwb.domotic.model.DomDevice getDomDevice()
    {
         org.semanticwb.domotic.model.DomDevice ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasDomDeviceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDevice)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.domotic.model.OnGroupChange
   * @return A GenericIterator with all the org.semanticwb.domotic.model.OnGroupChange
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnGroupChange> listOnGroupChanges()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnGroupChange>(getSemanticObject().listObjectProperties(swb4d_hasOnGroupChangeInv));
    }

   /**
   * Gets true if has a OnGroupChange
   * @param value org.semanticwb.domotic.model.OnGroupChange to verify
   * @return true if the org.semanticwb.domotic.model.OnGroupChange exists, false otherwise
   */
    public boolean hasOnGroupChange(org.semanticwb.domotic.model.OnGroupChange value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasOnGroupChangeInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the OnGroupChange
   * @return a org.semanticwb.domotic.model.OnGroupChange
   */
    public org.semanticwb.domotic.model.OnGroupChange getOnGroupChange()
    {
         org.semanticwb.domotic.model.OnGroupChange ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasOnGroupChangeInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.OnGroupChange)obj.createGenericInstance();
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
