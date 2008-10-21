/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author Jei
 */
public interface FormElement extends GenericObject
{
    public String render(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang);
}
