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
 * The Class TemplateRefBase.
 */
public abstract class TemplateRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Priorityable,org.semanticwb.model.Inheritable,org.semanticwb.model.Activeable
{
    
    /** The Constant swb_Template. */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    
    /** The Constant swb_template. */
    public static final org.semanticwb.platform.SemanticProperty swb_template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#template");
    
    /** The Constant swb_TemplateRef. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List template refs.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(it, true);
        }

        /**
         * List template refs.
         * 
         * @return the java.util. iterator
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
         * Gets the template ref.
         * 
         * @param id the id
         * @param model the model
         * @return the template ref
         */
        public static org.semanticwb.model.TemplateRef getTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the template ref.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. template ref
         */
        public static org.semanticwb.model.TemplateRef createTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the template ref.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for template ref.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateRef(id, model)!=null);
        }

        /**
         * List template ref by template.
         * 
         * @param template the template
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefByTemplate(org.semanticwb.model.Template template,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_template, template.getSemanticObject()));
            return it;
        }

        /**
         * List template ref by template.
         * 
         * @param template the template
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefByTemplate(org.semanticwb.model.Template template)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> it=new org.semanticwb.model.GenericIterator(template.getSemanticObject().getModel().listSubjects(swb_template,template.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new template ref base.
     * 
     * @param base the base
     */
    public TemplateRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the template.
     * 
     * @param value the new template
     */
    public void setTemplate(org.semanticwb.model.Template value)
    {
        getSemanticObject().setObjectProperty(swb_template, value.getSemanticObject());
    }

    /**
     * Removes the template.
     */
    public void removeTemplate()
    {
        getSemanticObject().removeProperty(swb_template);
    }

    /**
     * Gets the template.
     * 
     * @return the template
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PriorityableBase#getPriority()
     */
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PriorityableBase#setPriority(int)
     */
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.InheritableBase#getInherit()
     */
    public int getInherit()
    {
        return getSemanticObject().getIntProperty(swb_inherit);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.InheritableBase#setInherit(int)
     */
    public void setInherit(int value)
    {
        getSemanticObject().setIntProperty(swb_inherit, value);
    }

    /**
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
