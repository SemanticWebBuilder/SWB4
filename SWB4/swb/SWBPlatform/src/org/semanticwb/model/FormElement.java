/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author Jei
 */
public interface FormElement extends GenericObject
{
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);

    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);

    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) throws FormValidateException;

    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop);

    public void setAttribute(String name, String value);

    public FormElementURL getRenderURL(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);

    public FormElementURL getValidateURL(SemanticObject obj, SemanticProperty prop);

    public FormElementURL getProcessURL(SemanticObject obj, SemanticProperty prop);

    public String getLocaleString(String key, String lang);

    public SemanticModel getModel();

    public void setModel(SemanticModel model);
}
