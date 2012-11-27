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
package org.semanticwb.portal.resources.sem.blog.base;


// TODO: Auto-generated Javadoc
/**
 * The Class PermisionBase.
 */
public abstract class PermisionBase extends org.semanticwb.model.SWBClass 
{
    
    /** The Constant blog_level. */
    public static final org.semanticwb.platform.SemanticProperty blog_level=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#level");
    
    /** The Constant blog_isRol. */
    public static final org.semanticwb.platform.SemanticProperty blog_isRol=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#isRol");
    
    /** The Constant blog_securityId. */
    public static final org.semanticwb.platform.SemanticProperty blog_securityId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#securityId");
    
    /** The Constant blog_Permision. */
    public static final org.semanticwb.platform.SemanticClass blog_Permision=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Permision");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Permision");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List permisions.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Permision> listPermisions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision>(it, true);
        }

        /**
         * List permisions.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Permision> listPermisions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision>(it, true);
        }

        /**
         * Creates the permision.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. permision
         */
        public static org.semanticwb.portal.resources.sem.blog.Permision createPermision(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Permision.ClassMgr.createPermision(String.valueOf(id), model);
        }

        /**
         * Gets the permision.
         * 
         * @param id the id
         * @param model the model
         * @return the permision
         */
        public static org.semanticwb.portal.resources.sem.blog.Permision getPermision(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Permision)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the permision.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. permision
         */
        public static org.semanticwb.portal.resources.sem.blog.Permision createPermision(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Permision)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the permision.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removePermision(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for permision.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasPermision(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPermision(id, model)!=null);
        }
    }

    /**
     * Instantiates a new permision base.
     * 
     * @param base the base
     */
    public PermisionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the level.
     * 
     * @return the level
     */
    public int getLevel()
    {
        return getSemanticObject().getIntProperty(blog_level);
    }

    /**
     * Sets the level.
     * 
     * @param value the new level
     */
    public void setLevel(int value)
    {
        getSemanticObject().setIntProperty(blog_level, value);
    }

    /**
     * Checks if is checks if is rol.
     * 
     * @return true, if is checks if is rol
     */
    public boolean isIsRol()
    {
        return getSemanticObject().getBooleanProperty(blog_isRol);
    }

    /**
     * Sets the checks if is rol.
     * 
     * @param value the new checks if is rol
     */
    public void setIsRol(boolean value)
    {
        getSemanticObject().setBooleanProperty(blog_isRol, value);
    }

    /**
     * Gets the security id.
     * 
     * @return the security id
     */
    public String getSecurityId()
    {
        return getSemanticObject().getProperty(blog_securityId);
    }

    /**
     * Sets the security id.
     * 
     * @param value the new security id
     */
    public void setSecurityId(String value)
    {
        getSemanticObject().setProperty(blog_securityId, value);
    }
}
