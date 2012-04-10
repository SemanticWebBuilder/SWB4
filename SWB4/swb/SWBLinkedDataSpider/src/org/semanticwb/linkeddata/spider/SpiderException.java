/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderException extends Exception
{

    

    private Spider spider;

    public SpiderException(String message, Spider spider)
    {
        super(message);
        this.spider = spider;
    }

    public SpiderException(String message, Throwable cause, Spider spider)
    {
        super(message, cause);
        this.spider = spider;
    }

    public SpiderException(Throwable cause, Spider spider)
    {
        super(cause);
        this.spider = spider;
    }

    public Spider getSpider()
    {
        return spider;
    }
}
