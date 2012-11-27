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
package org.semanticwb.opensocial.model.base;


public abstract class GadgetBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty opensocial_url=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial#url");
    public static final org.semanticwb.platform.SemanticClass opensocial_Gadget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#Gadget");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#Gadget");

    public static class ClassMgr
    {
       /**
       * Returns a list of Gadget for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.Gadget
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.Gadget> listGadgets(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.Gadget>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.Gadget for all models
       * @return Iterator of org.semanticwb.opensocial.model.Gadget
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.Gadget> listGadgets()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.Gadget>(it, true);
        }

        public static org.semanticwb.opensocial.model.Gadget createGadget(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.opensocial.model.Gadget.ClassMgr.createGadget(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.Gadget
       * @param id Identifier for org.semanticwb.opensocial.model.Gadget
       * @param model Model of the org.semanticwb.opensocial.model.Gadget
       * @return A org.semanticwb.opensocial.model.Gadget
       */
        public static org.semanticwb.opensocial.model.Gadget getGadget(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.Gadget)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.Gadget
       * @param id Identifier for org.semanticwb.opensocial.model.Gadget
       * @param model Model of the org.semanticwb.opensocial.model.Gadget
       * @return A org.semanticwb.opensocial.model.Gadget
       */
        public static org.semanticwb.opensocial.model.Gadget createGadget(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.Gadget)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.Gadget
       * @param id Identifier for org.semanticwb.opensocial.model.Gadget
       * @param model Model of the org.semanticwb.opensocial.model.Gadget
       */
        public static void removeGadget(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.Gadget
       * @param id Identifier for org.semanticwb.opensocial.model.Gadget
       * @param model Model of the org.semanticwb.opensocial.model.Gadget
       * @return true if the org.semanticwb.opensocial.model.Gadget exists, false otherwise
       */

        public static boolean hasGadget(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGadget(id, model)!=null);
        }
    }

   /**
   * Constructs a GadgetBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Gadget
   */
    public GadgetBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
* Gets the Url property
* @return String with the Url
*/
    public String getUrl()
    {
        return getSemanticObject().getProperty(opensocial_url);
    }

/**
* Sets the Url property
* @param value long with the Url
*/
    public void setUrl(String value)
    {
        getSemanticObject().setProperty(opensocial_url, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
