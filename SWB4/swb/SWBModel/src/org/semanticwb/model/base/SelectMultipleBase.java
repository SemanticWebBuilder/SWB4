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
 * The Class SelectMultipleBase.
 */
public abstract class SelectMultipleBase extends org.semanticwb.model.base.FormElementBase 
{
    
    /** The Constant swbxf_sm_globalScope. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_globalScope");
    
    /** The Constant swbxf_sm_nullSupport. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_nullSupport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_nullSupport");
    
    /** The Constant swbxf_SelectMultiple. */
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectMultiple=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultiple");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultiple");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List select multiples.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SelectMultiple> listSelectMultiples(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultiple>(it, true);
        }

        /**
         * List select multiples.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SelectMultiple> listSelectMultiples()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultiple>(it, true);
        }

        /**
         * Gets the select multiple.
         * 
         * @param id the id
         * @param model the model
         * @return the select multiple
         */
        public static org.semanticwb.model.SelectMultiple getSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultiple)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the select multiple.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. select multiple
         */
        public static org.semanticwb.model.SelectMultiple createSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultiple)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the select multiple.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for select multiple.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectMultiple(id, model)!=null);
        }
    }

    /**
     * Instantiates a new select multiple base.
     * 
     * @param base the base
     */
    public SelectMultipleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Checks if is global scope.
     * 
     * @return true, if is global scope
     */
    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_globalScope);
    }

    /**
     * Sets the global scope.
     * 
     * @param value the new global scope
     */
    public void setGlobalScope(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_globalScope, value);
    }

    /**
     * Checks if is null support.
     * 
     * @return true, if is null support
     */
    public boolean isNullSupport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_nullSupport);
    }

    /**
     * Sets the null support.
     * 
     * @param value the new null support
     */
    public void setNullSupport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_nullSupport, value);
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
