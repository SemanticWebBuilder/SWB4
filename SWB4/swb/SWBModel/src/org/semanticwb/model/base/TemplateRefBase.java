package org.semanticwb.model.base;


   /**
   * Referencia a un objeto de tipo Template 
   */
public abstract class TemplateRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable,org.semanticwb.model.Priorityable,org.semanticwb.model.Inheritable
{
   /**
   * Las Plantillas son documentos HTML que sirven de base a SemanticWebBuilder para poder mostrar el "look & feel" del sitio, así como la distribución de todos los elementos en la pagina.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#template");
   /**
   * Referencia a un objeto de tipo Template
   */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of TemplateRef for a model
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

    public static TemplateRefBase.ClassMgr getTemplateRefClassMgr()
    {
        return new TemplateRefBase.ClassMgr();
    }

   /**
   * Constructs a TemplateRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TemplateRef
   */
    public TemplateRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Priority property
* @return int with the Priority
*/
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

/**
* Sets the Priority property
* @param value long with the Priority
*/
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }
   /**
   * Sets the value for the property Template
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
   * Remove the value for Template property
   */

    public void removeTemplate()
    {
        getSemanticObject().removeProperty(swb_template);
    }

   /**
   * Gets the Template
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
* Gets the Inherit property
* @return int with the Inherit
*/
    public int getInherit()
    {
        return getSemanticObject().getIntProperty(swb_inherit);
    }

/**
* Sets the Inherit property
* @param value long with the Inherit
*/
    public void setInherit(int value)
    {
        getSemanticObject().setIntProperty(swb_inherit, value);
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
