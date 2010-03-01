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
 * The Class FormViewRefBase.
 */
public abstract class FormViewRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    
    /** The Constant swbxf_FormView. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
    
    /** The Constant swb_formView. */
    public static final org.semanticwb.platform.SemanticProperty swb_formView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#formView");
    
    /** The Constant swb_formMode. */
    public static final org.semanticwb.platform.SemanticProperty swb_formMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#formMode");
    
    /** The Constant swbxf_FormViewRef. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormViewRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List form view refs.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef>(it, true);
        }

        /**
         * List form view refs.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef>(it, true);
        }

        /**
         * Gets the form view ref.
         * 
         * @param id the id
         * @param model the model
         * @return the form view ref
         */
        public static org.semanticwb.model.FormViewRef getFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormViewRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the form view ref.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. form view ref
         */
        public static org.semanticwb.model.FormViewRef createFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormViewRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the form view ref.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for form view ref.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormViewRef(id, model)!=null);
        }

        /**
         * List form view ref by form view.
         * 
         * @param formview the formview
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefByFormView(org.semanticwb.model.FormView formview,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_formView, formview.getSemanticObject()));
            return it;
        }

        /**
         * List form view ref by form view.
         * 
         * @param formview the formview
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefByFormView(org.semanticwb.model.FormView formview)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef> it=new org.semanticwb.model.GenericIterator(formview.getSemanticObject().getModel().listSubjects(swb_formView,formview.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new form view ref base.
     * 
     * @param base the base
     */
    public FormViewRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the form view.
     * 
     * @param value the new form view
     */
    public void setFormView(org.semanticwb.model.FormView value)
    {
        getSemanticObject().setObjectProperty(swb_formView, value.getSemanticObject());
    }

    /**
     * Removes the form view.
     */
    public void removeFormView()
    {
        getSemanticObject().removeProperty(swb_formView);
    }

    /**
     * Gets the form view.
     * 
     * @return the form view
     */
    public org.semanticwb.model.FormView getFormView()
    {
         org.semanticwb.model.FormView ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_formView);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.FormView)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the form mode.
     * 
     * @return the form mode
     */
    public String getFormMode()
    {
        return getSemanticObject().getProperty(swb_formMode);
    }

    /**
     * Sets the form mode.
     * 
     * @param value the new form mode
     */
    public void setFormMode(String value)
    {
        getSemanticObject().setProperty(swb_formMode, value);
    }
}
