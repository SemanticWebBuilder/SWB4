/*
 * SWBException.java
 *
 * Created on 08 de Julio de 2008, 11:06 AM
 */

package org.semanticwb;

/**
 * SemanticWebBuilder Exception
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBException extends java.lang.Exception
{
    /**
     *
     **/

    public SWBException(String msg)
    {
        super(msg);
    }

    public SWBException(String msg, Exception e)
    {
        super(msg,e);
    }

}


