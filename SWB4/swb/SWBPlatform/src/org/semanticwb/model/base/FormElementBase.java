/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model.base;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


/**
 *
 * @author Jei
 */
public class FormElementBase extends GenericObjectBase implements FormElement, GenericObject
{
    public FormElementBase(SemanticObject obj)
    {
        super(obj);
    }

    public String render(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) throws FormValidateException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
