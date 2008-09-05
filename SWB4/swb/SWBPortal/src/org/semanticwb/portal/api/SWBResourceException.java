/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.api;

import org.semanticwb.SWBException;

/**
 *
 * @author Jei
 */
public class SWBResourceException extends SWBException
{

    public SWBResourceException(String msg)
    {
        super(msg);
    }

    public SWBResourceException(String msg, Exception e)
    {
        super(msg,e);
    }

}
