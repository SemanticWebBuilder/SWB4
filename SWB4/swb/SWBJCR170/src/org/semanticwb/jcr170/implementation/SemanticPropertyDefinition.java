/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public final class SemanticPropertyDefinition extends DefaultPropertyDefinition
{
    private final SemanticProperty property;
    private final BaseNode node;
    SemanticPropertyDefinition(SemanticProperty property,BaseNode node)
    {
        super(property.getPrefix()+":"+property.getName());
        this.property=property;
        this.node=node;
    }
    
    
}
