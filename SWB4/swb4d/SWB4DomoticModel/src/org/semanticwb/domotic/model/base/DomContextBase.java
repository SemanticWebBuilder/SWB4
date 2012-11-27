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


public abstract class DomContextBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasDomEventsInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasDomEventsInv");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomContext");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomContext");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomContext for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomContext for all models
       * @return Iterator of org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext>(it, true);
        }

        public static org.semanticwb.domotic.model.DomContext createDomContext(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomContext.ClassMgr.createDomContext(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomContext
       * @param id Identifier for org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       * @return A org.semanticwb.domotic.model.DomContext
       */
        public static org.semanticwb.domotic.model.DomContext getDomContext(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomContext)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomContext
       * @param id Identifier for org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       * @return A org.semanticwb.domotic.model.DomContext
       */
        public static org.semanticwb.domotic.model.DomContext createDomContext(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomContext)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomContext
       * @param id Identifier for org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       */
        public static void removeDomContext(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomContext
       * @param id Identifier for org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       * @return true if the org.semanticwb.domotic.model.DomContext exists, false otherwise
       */

        public static boolean hasDomContext(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomContext(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomContext with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContextByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomContext with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContextByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomContext with a determined DomEvents
       * @param value DomEvents of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContextByDomEvents(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomEventsInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomContext with a determined DomEvents
       * @param value DomEvents of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContextByDomEvents(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomEventsInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomContext with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContextByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomContext with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomContext
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomContext> listDomContextByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomContext> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomContextBase.ClassMgr getDomContextClassMgr()
    {
        return new DomContextBase.ClassMgr();
    }

   /**
   * Constructs a DomContextBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomContext
   */
    public DomContextBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.domotic.model.DomEvent
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomEvent
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent> listDomEventses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent>(getSemanticObject().listObjectProperties(swb4d_hasDomEventsInv));
    }

   /**
   * Gets true if has a DomEvents
   * @param value org.semanticwb.domotic.model.DomEvent to verify
   * @return true if the org.semanticwb.domotic.model.DomEvent exists, false otherwise
   */
    public boolean hasDomEvents(org.semanticwb.domotic.model.DomEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasDomEventsInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the DomEvents
   * @return a org.semanticwb.domotic.model.DomEvent
   */
    public org.semanticwb.domotic.model.DomEvent getDomEvents()
    {
         org.semanticwb.domotic.model.DomEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasDomEventsInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomEvent)obj.createGenericInstance();
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
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
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
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
