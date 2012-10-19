/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.widgets;

/**
 *
 * @author jorge.jimenez
 */
public class Message {

    String msg;
    String device;
    String sentiment;

    public Message(String msg, String device, String sentiment)
    {
        this.msg=msg;
        this.device=device;
        this.sentiment=sentiment;
    }

    public String getMsg()
    {
        return msg;
    }

    public String getDevice()
    {
        return device;
    }

    public String getSentiment()
    {
        return sentiment;
    }

    public void setMsg(String msg)
    {
        this.setMsg(msg);
    }

    public void setDevice(String device)
    {
        this.setDevice(device);
    }

    public void setSentiment(String sentiment)
    {
        this.setSentiment(sentiment);
    }
}
