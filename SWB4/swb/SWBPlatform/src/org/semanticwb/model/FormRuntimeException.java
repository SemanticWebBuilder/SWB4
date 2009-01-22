/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.model;

import org.semanticwb.SWBRuntimeException;

/**
 *
 * @author serch
 */
public class FormRuntimeException extends SWBRuntimeException
{

    public FormRuntimeException()
    {
        super();
    }

    public FormRuntimeException(String msg)
    {
        super(msg);
    }

    public FormRuntimeException(String msg, Exception e)
    {
        super(msg, e);
    }
}
