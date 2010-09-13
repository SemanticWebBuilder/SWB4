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
    * Referencia a un objeto de tipo Template.
    */
public abstract class TemplateRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable,org.semanticwb.model.Priorityable,org.semanticwb.model.Inheritable
{
   /**
   * Las Plantillas son documentos HTML que sirven de base a SemanticWebBuilder para poder mostrar el "look & feel" del sitio, así como la distribución de todos los elementos en la pagina.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    
    /** The Constant swb_template. */
    public static final org.semanticwb.platform.SemanticProperty swb_template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#template");
   
   /** Referencia a un objeto de tipo Template. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of TemplateRef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.TemplateRef
        */

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.TemplateRef for all models
       * @return Iterator of org.semanticwb.model.TemplateRef
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(it, true);
        }

        /**
         * Creates the template ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. template ref
         */
        public static org.semanticwb.model.TemplateRef createTemplateRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.TemplateRef
       * @param id Identifier for org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.TemplateRef
       * @return A org.semanticwb.model.TemplateRef
       */
        public static org.semanticwb.model.TemplateRef getTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.TemplateRef
       * @param id Identifier for org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.TemplateRef
       * @return A org.semanticwb.model.TemplateRef
       */
        public static org.semanticwb.model.TemplateRef createTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.TemplateRef
       * @param id Identifier for org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.TemplateRef
       */
        public static void removeTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.TemplateRef
       * @param id Identifier for org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.TemplateRef
       * @return true if the org.semanticwb.model.TemplateRef exists, false otherwise
       */

        public static boolean hasTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.TemplateRef with a determined Template
       * @param value Template of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.model.TemplateRef
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefByTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_template, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.TemplateRef with a determined Template
       * @param value Template of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.model.TemplateRef
       */

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefByTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_template,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a TemplateRefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the TemplateRef
    */
    public TemplateRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Template.
    * 
    * @param value Template to set
    */

    public void setTemplate(org.semanticwb.model.Template value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_template, value.getSemanticObject());
        }else
        {
            removeTemplate();
        }
    }
   
   /**
    * Remove the value for Template property.
    */

    public void removeTemplate()
    {
        getSemanticObject().removeProperty(swb_template);
    }

   /**
    * Gets the Template.
    * 
    * @return a org.semanticwb.model.Template
    */
    public org.semanticwb.model.Template getTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_template);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the Priority property.
 * 
 * @return int with the Priority
 */
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

/**
 * Sets the Priority property.
 * 
 * @param value long with the Priority
 */
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

/**
 * Gets the Inherit property.
 * 
 * @return int with the Inherit
 */
    public int getInherit()
    {
        return getSemanticObject().getIntProperty(swb_inherit);
    }

/**
 * Sets the Inherit property.
 * 
 * @param value long with the Inherit
 */
    public void setInherit(int value)
    {
        getSemanticObject().setIntProperty(swb_inherit, value);
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
