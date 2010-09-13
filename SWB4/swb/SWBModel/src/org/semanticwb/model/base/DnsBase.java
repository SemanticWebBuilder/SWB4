/**  
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
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
   * Un servidor DNS permite conectarse con la máquina sin necesidad de conocer su dirección IP. En SemanticWebBuilder el DNS local es el nombre asociado al sitio. Al ser invocado el DNS presentará una sección específica a manera de página de inicio. 
   */
public abstract class DnsBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.WebPageable
{
    
    /** The Constant swb_dnsDefault. */
    public static final org.semanticwb.platform.SemanticProperty swb_dnsDefault=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dnsDefault");
    
    /** The Constant swb_dns. */
    public static final org.semanticwb.platform.SemanticProperty swb_dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dns");
   /**
   * Un servidor DNS permite conectarse con la máquina sin necesidad de conocer su dirección IP. En SemanticWebBuilder el DNS local es el nombre asociado al sitio. Al ser invocado el DNS presentará una sección específica a manera de página de inicio.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of Dns for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.Dns
        */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Dns for all models
       * @return Iterator of org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns>(it, true);
        }

        /**
         * Creates the dns.
         * 
         * @param model the model
         * @return the org.semanticwb.model. dns
         */
        public static org.semanticwb.model.Dns createDns(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Dns.ClassMgr.createDns(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Dns
       * @param id Identifier for org.semanticwb.model.Dns
       * @param model Model of the org.semanticwb.model.Dns
       * @return A org.semanticwb.model.Dns
       */
        public static org.semanticwb.model.Dns getDns(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Dns)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Dns
       * @param id Identifier for org.semanticwb.model.Dns
       * @param model Model of the org.semanticwb.model.Dns
       * @return A org.semanticwb.model.Dns
       */
        public static org.semanticwb.model.Dns createDns(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Dns)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Dns
       * @param id Identifier for org.semanticwb.model.Dns
       * @param model Model of the org.semanticwb.model.Dns
       */
        public static void removeDns(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Dns
       * @param id Identifier for org.semanticwb.model.Dns
       * @param model Model of the org.semanticwb.model.Dns
       * @return true if the org.semanticwb.model.Dns exists, false otherwise
       */

        public static boolean hasDns(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDns(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Dns with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Dns
       * @return Iterator with all the org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Dns with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Dns with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.Dns
       * @return Iterator with all the org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnsByWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Dns with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnsByWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Dns with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Dns
       * @return Iterator with all the org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Dns with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Dns
       */

        public static java.util.Iterator<org.semanticwb.model.Dns> listDnsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Dns> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a DnsBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the Dns
    */
    public DnsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the Created property.
 * 
 * @return java.util.Date with the Created
 */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
 * Sets the Created property.
 * 
 * @param value long with the Created
 */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   
   /**
    * Sets the value for the property ModifiedBy.
    * 
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
    * Remove the value for ModifiedBy property.
    */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
    * Gets the ModifiedBy.
    * 
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
 * Gets the Updated property.
 * 
 * @return java.util.Date with the Updated
 */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
 * Sets the Updated property.
 * 
 * @param value long with the Updated
 */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }
   
   /**
    * Sets the value for the property WebPage.
    * 
    * @param value WebPage to set
    */

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_webPage, value.getSemanticObject());
        }else
        {
            removeWebPage();
        }
    }
   
   /**
    * Remove the value for WebPage property.
    */

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swb_webPage);
    }

   /**
    * Gets the WebPage.
    * 
    * @return a org.semanticwb.model.WebPage
    */
    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_webPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   
   /**
    * Sets the value for the property Creator.
    * 
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
    * Remove the value for Creator property.
    */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
    * Gets the Creator.
    * 
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
 * Gets the Default property.
 * 
 * @return boolean with the Default
 */
    public boolean isDefault()
    {
        return getSemanticObject().getBooleanProperty(swb_dnsDefault);
    }

/**
 * Sets the Default property.
 * 
 * @param value long with the Default
 */
    public void setDefault(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_dnsDefault, value);
    }

/**
 * Gets the Dns property.
 * 
 * @return String with the Dns
 */
    public String getDns()
    {
        return getSemanticObject().getProperty(swb_dns);
    }

/**
 * Sets the Dns property.
 * 
 * @param value long with the Dns
 */
    public void setDns(String value)
    {
        getSemanticObject().setProperty(swb_dns, value);
    }

   /**
    * Gets the WebSite.
    * 
    * @return a instance of org.semanticwb.model.WebSite
    */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
