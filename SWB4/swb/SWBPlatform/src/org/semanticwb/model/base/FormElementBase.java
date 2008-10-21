/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model.base;

import org.semanticwb.model.FormElement;
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

    public String getCode(SemanticObject obj, SemanticProperty prop, String type)
    {
        String name=prop.getName();
        String label=prop.getDisplayName();
        String value=obj.getProperty(prop);
        String txt="<label>"+label+"</label><input type=\"text\" name=\""+name+"\" id=\""+label+"\" value=\""+value+"\"/>";
        return txt;
    }
}
