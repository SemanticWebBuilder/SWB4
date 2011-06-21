/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices;

/**
 *
 * @author victor.lorenzana
 */
public class ServiceException extends Exception {

    private String detail;
    public ServiceException(String message,String detail)
    {
        this(message);
        this.detail=detail;
    }
    public ServiceException(String message)
    {
        super(message);
    }
    public ServiceException(Throwable e)
    {
        super(e);
    }
    public ServiceException(String msg,Throwable e)
    {
        super(msg,e);
    }
    public String getDetail()
    {
        return detail;
    }
}