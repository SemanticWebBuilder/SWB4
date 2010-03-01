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
 * The Class SWBModelBase.
 */
public abstract class SWBModelBase extends org.semanticwb.model.base.GenericObjectBase 
{
    
    /** The Constant swb_WebSite. */
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
    
    /** The Constant swb_parentWebSite. */
    public static final org.semanticwb.platform.SemanticProperty swb_parentWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#parentWebSite");
    
    /** The Constant swb_SWBModel. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List swb models.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(it, true);
        }

        /**
         * List swb models.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(it, true);
        }

        /**
         * Gets the sWB model.
         * 
         * @param id the id
         * @param model the model
         * @return the sWB model
         */
        public static org.semanticwb.model.SWBModel getSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the swb model.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. swb model
         */
        public static org.semanticwb.model.SWBModel createSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the swb model.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for swb model.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWBModel(id, model)!=null);
        }

        /**
         * List swb model by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModelByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List swb model by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModelByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new sWB model base.
     * 
     * @param base the base
     */
    public SWBModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the parent web site.
     * 
     * @param value the new parent web site
     */
    public void setParentWebSite(org.semanticwb.model.WebSite value)
    {
        getSemanticObject().setObjectProperty(swb_parentWebSite, value.getSemanticObject());
    }

    /**
     * Removes the parent web site.
     */
    public void removeParentWebSite()
    {
        getSemanticObject().removeProperty(swb_parentWebSite);
    }

    /**
     * Gets the parent web site.
     * 
     * @return the parent web site
     */
    public org.semanticwb.model.WebSite getParentWebSite()
    {
         org.semanticwb.model.WebSite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_parentWebSite);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebSite)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Removes the.
     */
    public void remove()
    {
        getSemanticObject().remove();
    }

    /**
     * List related objects.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
