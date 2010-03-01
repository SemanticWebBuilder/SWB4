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
 * The Class FormViewBase.
 */
public abstract class FormViewBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    
    /** The Constant rdf_Property. */
    public static final org.semanticwb.platform.SemanticClass rdf_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property");
    
    /** The Constant swbxf_hasCreateProperty. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasCreateProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasCreateProperty");
    
    /** The Constant swbxf_hasViewProperty. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasViewProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasViewProperty");
    
    /** The Constant swbxf_FormViewRef. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormViewRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");
    
    /** The Constant swb_hasFormViewRefInv. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasFormViewRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasFormViewRefInv");
    
    /** The Constant swbxf_hasEditProperty. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasEditProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasEditProperty");
    
    /** The Constant swbxf_FormView. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List form views.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViews(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView>(it, true);
        }

        /**
         * List form views.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViews()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView>(it, true);
        }

        /**
         * Creates the form view.
         * 
         * @param model the model
         * @return the org.semanticwb.model. form view
         */
        public static org.semanticwb.model.FormView createFormView(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.FormView.ClassMgr.createFormView(String.valueOf(id), model);
        }

        /**
         * Gets the form view.
         * 
         * @param id the id
         * @param model the model
         * @return the form view
         */
        public static org.semanticwb.model.FormView getFormView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormView)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the form view.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. form view
         */
        public static org.semanticwb.model.FormView createFormView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormView)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the form view.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeFormView(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for form view.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasFormView(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormView(id, model)!=null);
        }

        /**
         * List form view by form view ref inv.
         * 
         * @param hasformviewrefinv the hasformviewrefinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViewByFormViewRefInv(org.semanticwb.model.FormViewRef hasformviewrefinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasFormViewRefInv, hasformviewrefinv.getSemanticObject()));
            return it;
        }

        /**
         * List form view by form view ref inv.
         * 
         * @param hasformviewrefinv the hasformviewrefinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViewByFormViewRefInv(org.semanticwb.model.FormViewRef hasformviewrefinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView> it=new org.semanticwb.model.GenericIterator(hasformviewrefinv.getSemanticObject().getModel().listSubjects(swb_hasFormViewRefInv,hasformviewrefinv.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new form view base.
     * 
     * @param base the base
     */
    public FormViewBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * List create properties.
     * 
     * @return the org.semanticwb.platform. semantic iterator
     */
    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listCreateProperties()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swbxf_hasCreateProperty.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    /**
     * Adds the create property.
     * 
     * @param value the value
     */
    public void addCreateProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swbxf_hasCreateProperty, value);
    }

    /**
     * Removes the all create property.
     */
    public void removeAllCreateProperty()
    {
        getSemanticObject().removeProperty(swbxf_hasCreateProperty);
    }

    /**
     * Removes the create property.
     * 
     * @param semanticobject the semanticobject
     */
    public void removeCreateProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasCreateProperty,semanticobject);
    }

    /**
     * Gets the creates the property.
     * 
     * @return the creates the property
     */
    public org.semanticwb.platform.SemanticObject getCreateProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_hasCreateProperty);
         return ret;
    }

    /**
     * List view properties.
     * 
     * @return the org.semanticwb.platform. semantic iterator
     */
    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listViewProperties()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swbxf_hasViewProperty.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    /**
     * Adds the view property.
     * 
     * @param value the value
     */
    public void addViewProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swbxf_hasViewProperty, value);
    }

    /**
     * Removes the all view property.
     */
    public void removeAllViewProperty()
    {
        getSemanticObject().removeProperty(swbxf_hasViewProperty);
    }

    /**
     * Removes the view property.
     * 
     * @param semanticobject the semanticobject
     */
    public void removeViewProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasViewProperty,semanticobject);
    }

    /**
     * Gets the view property.
     * 
     * @return the view property
     */
    public org.semanticwb.platform.SemanticObject getViewProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_hasViewProperty);
         return ret;
    }

    /**
     * List form view ref invs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef> listFormViewRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef>(getSemanticObject().listObjectProperties(swb_hasFormViewRefInv));
    }

    /**
     * Checks for form view ref inv.
     * 
     * @param formviewref the formviewref
     * @return true, if successful
     */
    public boolean hasFormViewRefInv(org.semanticwb.model.FormViewRef formviewref)
    {
        boolean ret=false;
        if(formviewref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasFormViewRefInv,formviewref.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the form view ref inv.
     * 
     * @return the form view ref inv
     */
    public org.semanticwb.model.FormViewRef getFormViewRefInv()
    {
         org.semanticwb.model.FormViewRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasFormViewRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.FormViewRef)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List edit properties.
     * 
     * @return the org.semanticwb.platform. semantic iterator
     */
    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listEditProperties()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swbxf_hasEditProperty.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    /**
     * Adds the edit property.
     * 
     * @param value the value
     */
    public void addEditProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swbxf_hasEditProperty, value);
    }

    /**
     * Removes the all edit property.
     */
    public void removeAllEditProperty()
    {
        getSemanticObject().removeProperty(swbxf_hasEditProperty);
    }

    /**
     * Removes the edit property.
     * 
     * @param semanticobject the semanticobject
     */
    public void removeEditProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasEditProperty,semanticobject);
    }

    /**
     * Gets the edits the property.
     * 
     * @return the edits the property
     */
    public org.semanticwb.platform.SemanticObject getEditProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_hasEditProperty);
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
