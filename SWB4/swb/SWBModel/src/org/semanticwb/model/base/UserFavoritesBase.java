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
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class UserFavoritesBase.
 */
public class UserFavoritesBase extends org.semanticwb.model.SWBClass 
{
    
    /** The Constant owl_Thing. */
    public static final org.semanticwb.platform.SemanticClass owl_Thing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Thing");
    
    /** The Constant swb_usrfHasObjects. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrfHasObjects=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrfHasObjects");
    
    /** The Constant swb_UserFavorites. */
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorites=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");

    /**
     * Instantiates a new user favorites base.
     * 
     * @param base the base
     */
    public UserFavoritesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List user favoritess.
     * 
     * @param model the model
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.UserFavorites> listUserFavoritess(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorites>(it, true);
    }

    /**
     * List user favoritess.
     * 
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.UserFavorites> listUserFavoritess()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorites>(it, true);
    }

    /**
     * Creates the user favorites.
     * 
     * @param model the model
     * @return the org.semanticwb.model. user favorites
     */
    public static org.semanticwb.model.UserFavorites createUserFavorites(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.UserFavorites.createUserFavorites(String.valueOf(id), model);
    }

    /**
     * Gets the user favorites.
     * 
     * @param id the id
     * @param model the model
     * @return the user favorites
     */
    public static org.semanticwb.model.UserFavorites getUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.UserFavorites)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    /**
     * Creates the user favorites.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.model. user favorites
     */
    public static org.semanticwb.model.UserFavorites createUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.UserFavorites)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    /**
     * Removes the user favorites.
     * 
     * @param id the id
     * @param model the model
     */
    public static void removeUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    /**
     * Checks for user favorites.
     * 
     * @param id the id
     * @param model the model
     * @return true, if successful
     */
    public static boolean hasUserFavorites(String id, org.semanticwb.model.SWBModel model)
    {
        return (getUserFavorites(id, model)!=null);
    }

    /**
     * List objects.
     * 
     * @return the org.semanticwb.platform. semantic iterator
     */
    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listObjects()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swb_usrfHasObjects.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    /**
     * Adds the object.
     * 
     * @param semanticobject the semanticobject
     */
    public void addObject(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(swb_usrfHasObjects, semanticobject);
    }

    /**
     * Removes the all object.
     */
    public void removeAllObject()
    {
        getSemanticObject().removeProperty(swb_usrfHasObjects);
    }

    /**
     * Removes the object.
     * 
     * @param semanticobject the semanticobject
     */
    public void removeObject(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swb_usrfHasObjects,semanticobject);
    }

    /**
     * Gets the object.
     * 
     * @return the object
     */
    public org.semanticwb.platform.SemanticObject getObject()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_usrfHasObjects);
         return ret;
    }

    /**
     * Gets the user repository.
     * 
     * @return the user repository
     */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
