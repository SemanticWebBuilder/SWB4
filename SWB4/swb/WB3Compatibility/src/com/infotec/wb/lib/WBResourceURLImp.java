/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.lib;

import java.util.Map;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author javier.solis
 */
public class WBResourceURLImp implements WBResourceURL
{
    private SWBResourceURL url=null;

    public WBResourceURLImp(SWBResourceURL url)
    {
        this.url=url;
    }

    public WBResourceURL setWindowState(String state) {
        url.setWindowState(state);
        return this;
    }

    public String getWindowState() {
        return url.getWindowState();
    }

    public int getCallMethod() {
        return url.getCallMethod();
    }

    public WBResourceURL setCallMethod(int callMethod) {
        url.setCallMethod(callMethod);
        return this;
    }

    public WBResourceURL setMode(String mode) {
        url.setMode(mode);
        return this;
    }

    public String getMode() {
        return url.getMode();
    }

    public boolean isSecure() {
        return url.isSecure();
    }

    public WBResourceURL setSecure(boolean secure) {
        url.setSecure(secure);
        return this;
    }

    public String getAction() {
        return url.getAction();
    }

    public WBResourceURL setAction(String action) {
        url.setAction(action);
        return this;
    }

    public String toString(boolean encodeAmp) {
        return url.toString(encodeAmp);
    }

    public void setParameters(Map parameters) {
        url.setParameters(parameters);
    }

    public void setParameter(String key, String value) {
        url.setParameter(key,value);
    }

    public void setParameter(String key, String[] values) {
        url.setParameter(key,values);
    }

    public int getURLType() {
        return url.getURLType();
    }

    public boolean isOnlyContent() {
        return url.isOnlyContent();
    }

    public void setOnlyContent(boolean onlyContent)
    {
        url.setOnlyContent(onlyContent);
    }

}
