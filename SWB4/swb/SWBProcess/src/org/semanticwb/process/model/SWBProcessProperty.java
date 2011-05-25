/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.model;

import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author javier.solis
 */
public class SWBProcessProperty
{
    private String m_name=null;
    private SemanticProperty m_prop=null;
    private String m_mode=null;
    private String varTitle=null;

    public SWBProcessProperty(String varName, SemanticProperty prop, String mode)
    {
        this(varName, prop, null, mode);
    }

    public SWBProcessProperty(String varName, SemanticProperty prop, String varTitle, String mode)
    {
        this.m_name=varName;
        this.m_prop=prop;
        this.m_mode=mode;
        this.varTitle=varTitle;
    }

    /**
     * @return the m_cls
     */
    public String getVarName() {
        return m_name;
    }

    /**
     * @param m_cls the m_cls to set
     */
    public void setVarName(String varName) {
        this.m_name = varName;
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

    /**
     * @return the m_view
     */
    public String getMode() {
        return m_mode;
    }

    /**
     * @param view the m_view to set
     */
    public void setMode(String mode) {
        this.m_mode = mode;
    }

    public String getVarTitle()
    {
        return varTitle;
    }

    public void setVarTitle(String varTitle)
    {
        this.varTitle = varTitle;
    }


}
