package org.semanticwb.transparencia.model.base;


public abstract class DependenciaBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass trans_Dependencia=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Dependencia");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Dependencia");

    public static class ClassMgr
    {
       /**
       * Returns a list of Dependencia for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.Dependencia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Dependencia> listDependencias(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Dependencia>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.Dependencia for all models
       * @return Iterator of org.semanticwb.transparencia.model.Dependencia
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Dependencia> listDependencias()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Dependencia>(it, true);
        }

        public static org.semanticwb.transparencia.model.Dependencia createDependencia(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.transparencia.model.Dependencia.ClassMgr.createDependencia(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.Dependencia
       * @param id Identifier for org.semanticwb.transparencia.model.Dependencia
       * @param model Model of the org.semanticwb.transparencia.model.Dependencia
       * @return A org.semanticwb.transparencia.model.Dependencia
       */
        public static org.semanticwb.transparencia.model.Dependencia getDependencia(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Dependencia)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.Dependencia
       * @param id Identifier for org.semanticwb.transparencia.model.Dependencia
       * @param model Model of the org.semanticwb.transparencia.model.Dependencia
       * @return A org.semanticwb.transparencia.model.Dependencia
       */
        public static org.semanticwb.transparencia.model.Dependencia createDependencia(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Dependencia)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.Dependencia
       * @param id Identifier for org.semanticwb.transparencia.model.Dependencia
       * @param model Model of the org.semanticwb.transparencia.model.Dependencia
       */
        public static void removeDependencia(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.Dependencia
       * @param id Identifier for org.semanticwb.transparencia.model.Dependencia
       * @param model Model of the org.semanticwb.transparencia.model.Dependencia
       * @return true if the org.semanticwb.transparencia.model.Dependencia exists, false otherwise
       */

        public static boolean hasDependencia(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDependencia(id, model)!=null);
        }
    }

   /**
   * Constructs a DependenciaBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Dependencia
   */
    public DependenciaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
}
