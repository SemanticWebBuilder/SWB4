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
package org.semanticwb.portal.resources.sem.videolibrary.base;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBVideoLibraryBase.
 */
public abstract class SWBVideoLibraryBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant swb_ResourceCollectionCategory. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollectionCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollectionCategory");
    
    /** The Constant video_category. */
    public static final org.semanticwb.platform.SemanticProperty video_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#category");
    
    /** The Constant swb_ResourceCollection. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");
    
    /** The Constant video_collection. */
    public static final org.semanticwb.platform.SemanticProperty video_collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#collection");
    
    /** The Constant video_SWBVideoLibrary. */
    public static final org.semanticwb.platform.SemanticClass video_SWBVideoLibrary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#SWBVideoLibrary");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#SWBVideoLibrary");

    /**
     * Instantiates a new sWB video library base.
     */
    public SWBVideoLibraryBase()
    {
    }

    /**
     * Instantiates a new sWB video library base.
     * 
     * @param base the base
     */
    public SWBVideoLibraryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the category.
     * 
     * @param value the new category
     */
    public void setCategory(org.semanticwb.model.ResourceCollectionCategory value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(video_category, value.getSemanticObject());
        }else
        {
            removeCategory();
        }
    }

    /**
     * Removes the category.
     */
    public void removeCategory()
    {
        getSemanticObject().removeProperty(video_category);
    }

    /**
     * Gets the category.
     * 
     * @return the category
     */
    public org.semanticwb.model.ResourceCollectionCategory getCategory()
    {
         org.semanticwb.model.ResourceCollectionCategory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(video_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollectionCategory)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the collection.
     * 
     * @param value the new collection
     */
    public void setCollection(org.semanticwb.model.ResourceCollection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(video_collection, value.getSemanticObject());
        }else
        {
            removeCollection();
        }
    }

    /**
     * Removes the collection.
     */
    public void removeCollection()
    {
        getSemanticObject().removeProperty(video_collection);
    }

    /**
     * Gets the collection.
     * 
     * @return the collection
     */
    public org.semanticwb.model.ResourceCollection getCollection()
    {
         org.semanticwb.model.ResourceCollection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(video_collection);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollection)obj.createGenericInstance();
         }
         return ret;
    }
}
