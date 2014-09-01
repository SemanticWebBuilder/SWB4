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
package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * The Interface FormElement.
 * 
 * @author Jei
 */
public interface FormElement extends GenericObject
{

    /**
     * Render label.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);
    
    /**
     * Render label.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang);

    /**
     * Render label.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @param label the label
     * @return the string
     */
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang, String label);
    
    
    /**
     * Render element.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);

    /**
     * Render element.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang);


    /**
     * Validate.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @throws FormValidateException the form validate exception
     */
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) throws FormValidateException;

    /**
     * Validate.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @throws FormValidateException the form validate exception
     */
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) throws FormValidateException;


    /**
     * Process.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     */
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop);

    /**
     * Process.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     */
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName);

    /**
     * Sets the attribute.
     * 
     * @param name the name
     * @param value the value
     */
    public void setAttribute(String name, String value);

    /**
     * Gets the render url.
     * 
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the render url
     */
    public FormElementURL getRenderURL(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);

    /**
     * Gets the validate url.
     * 
     * @param obj the obj
     * @param prop the prop
     * @return the validate url
     */
    public FormElementURL getValidateURL(SemanticObject obj, SemanticProperty prop);

    /**
     * Gets the process url.
     * 
     * @param obj the obj
     * @param prop the prop
     * @return the process url
     */
    public FormElementURL getProcessURL(SemanticObject obj, SemanticProperty prop);

    /**
     * Gets the locale string.
     * 
     * @param key the key
     * @param lang the lang
     * @return the locale string
     */
    public String getLocaleString(String key, String lang);

    /**
     * Gets the model.
     * 
     * @return the model
     */
    public SemanticModel getModel();

    /**
     * Sets the model.
     * 
     * @param model the new model
     */
    public void setModel(SemanticModel model);

    //public void setLabel(String label);
    
    /**
     * Gets the FormMgr.
     * 
     * @return the SWBFormMgr
     */
    public Object getFormMgr();

    /**
     * Sets the FormMgr.
     * 
     * @param formMgr the new SWBFormMgr
     */
    public void setFormMgr(Object formMgr);

    //public void setLabel(String label);    
}
