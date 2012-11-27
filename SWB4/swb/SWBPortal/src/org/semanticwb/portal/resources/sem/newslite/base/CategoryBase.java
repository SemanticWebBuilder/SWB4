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
package org.semanticwb.portal.resources.sem.newslite.base;


// TODO: Auto-generated Javadoc
/**
 * The Class CategoryBase.
 */
public abstract class CategoryBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    
    /** The Constant news_Category. */
    public static final org.semanticwb.platform.SemanticClass news_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#Category");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#Category");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List categories.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.Category> listCategories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.Category>(it, true);
        }

        /**
         * List categories.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.Category> listCategories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.Category>(it, true);
        }

        /**
         * Creates the category.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.newslite. category
         */
        public static org.semanticwb.portal.resources.sem.newslite.Category createCategory(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.newslite.Category.ClassMgr.createCategory(String.valueOf(id), model);
        }

        /**
         * Gets the category.
         * 
         * @param id the id
         * @param model the model
         * @return the category
         */
        public static org.semanticwb.portal.resources.sem.newslite.Category getCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.newslite.Category)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the category.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.newslite. category
         */
        public static org.semanticwb.portal.resources.sem.newslite.Category createCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.newslite.Category)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the category.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for category.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCategory(id, model)!=null);
        }
    }

    /**
     * Instantiates a new category base.
     * 
     * @param base the base
     */
    public CategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /**
     * Sets the title.
     * 
     * @param value the new title
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /**
     * Gets the title.
     * 
     * @param lang the lang
     * @return the title
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /**
     * Gets the display title.
     * 
     * @param lang the lang
     * @return the display title
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /**
     * Sets the title.
     * 
     * @param title the title
     * @param lang the lang
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /**
     * Sets the description.
     * 
     * @param value the new description
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /**
     * Gets the description.
     * 
     * @param lang the lang
     * @return the description
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /**
     * Gets the display description.
     * 
     * @param lang the lang
     * @return the display description
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /**
     * Sets the description.
     * 
     * @param description the description
     * @param lang the lang
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
