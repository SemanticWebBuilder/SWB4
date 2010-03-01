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
 * The Class PropertyGroupBase.
 */
public abstract class PropertyGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
    
    /** The Constant swbxf_PropertyGroup. */
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List property groups.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }

        /**
         * List property groups.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }

        /**
         * Gets the property group.
         * 
         * @param id the id
         * @param model the model
         * @return the property group
         */
        public static org.semanticwb.model.PropertyGroup getPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the property group.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. property group
         */
        public static org.semanticwb.model.PropertyGroup createPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the property group.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removePropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for property group.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPropertyGroup(id, model)!=null);
        }
    }

    /**
     * Instantiates a new property group base.
     * 
     * @param base the base
     */
    public PropertyGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#getIndex()
     */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#setIndex(int)
     */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }
}
