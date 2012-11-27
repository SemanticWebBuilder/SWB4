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
package org.semanticwb.process.model.base;


public abstract class WebServiceParameterBase extends org.semanticwb.process.model.BaseElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_wspValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#wspValue");
    public static final org.semanticwb.platform.SemanticProperty swp_wspName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#wspName");
    public static final org.semanticwb.platform.SemanticClass swp_WebServiceParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceParameter");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceParameter");

    public static class ClassMgr
    {
       /**
       * Returns a list of WebServiceParameter for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.WebServiceParameter
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceParameter> listWebServiceParameters(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.WebServiceParameter for all models
       * @return Iterator of org.semanticwb.process.model.WebServiceParameter
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceParameter> listWebServiceParameters()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(it, true);
        }

        public static org.semanticwb.process.model.WebServiceParameter createWebServiceParameter(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.WebServiceParameter.ClassMgr.createWebServiceParameter(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       * @return A org.semanticwb.process.model.WebServiceParameter
       */
        public static org.semanticwb.process.model.WebServiceParameter getWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WebServiceParameter)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       * @return A org.semanticwb.process.model.WebServiceParameter
       */
        public static org.semanticwb.process.model.WebServiceParameter createWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WebServiceParameter)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       */
        public static void removeWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       * @return true if the org.semanticwb.process.model.WebServiceParameter exists, false otherwise
       */

        public static boolean hasWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWebServiceParameter(id, model)!=null);
        }
    }

    public static WebServiceParameterBase.ClassMgr getWebServiceParameterClassMgr()
    {
        return new WebServiceParameterBase.ClassMgr();
    }

   /**
   * Constructs a WebServiceParameterBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WebServiceParameter
   */
    public WebServiceParameterBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ParameterValue property
* @return String with the ParameterValue
*/
    public String getParameterValue()
    {
        return getSemanticObject().getProperty(swp_wspValue);
    }

/**
* Sets the ParameterValue property
* @param value long with the ParameterValue
*/
    public void setParameterValue(String value)
    {
        getSemanticObject().setProperty(swp_wspValue, value);
    }

/**
* Gets the ParameterName property
* @return String with the ParameterName
*/
    public String getParameterName()
    {
        return getSemanticObject().getProperty(swp_wspName);
    }

/**
* Sets the ParameterName property
* @param value long with the ParameterName
*/
    public void setParameterName(String value)
    {
        getSemanticObject().setProperty(swp_wspName, value);
    }
}
