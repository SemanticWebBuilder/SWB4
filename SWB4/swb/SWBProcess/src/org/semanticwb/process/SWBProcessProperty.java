/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author javier.solis
 */
public class SWBProcessProperty
{
    private SemanticClass m_cls=null;
    private SemanticProperty m_prop=null;

    public SWBProcessProperty(SemanticClass cls, SemanticProperty prop)
    {
        this.m_cls=cls;
        this.m_prop=prop;
    }

    /**
     * @return the m_cls
     */
    public SemanticClass getSemanticClass() {
        return m_cls;
    }

    /**
     * @param m_cls the m_cls to set
     */
    public void setSemanticClass(SemanticClass cls) {
        this.m_cls = cls;
    }

    /**
     * @return the m_prop
     */
    public SemanticProperty getSemanticProperty() {
        return m_prop;
    }

    /**
     * @param m_prop the m_prop to set
     */
    public void setSemanticProperty(SemanticProperty prop) {
        this.m_prop = prop;
    }


}
