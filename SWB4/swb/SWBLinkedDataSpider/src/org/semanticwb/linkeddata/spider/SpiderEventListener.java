/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URI;
import java.net.URL;

/**
 *
 * @author victor.lorenzana
 */


public interface SpiderEventListener
{
    
    public void onStart(URL url);

    public void onEnd(URL url);

    public boolean onNewSubject(URI url,TYPE type,Spider spider);

    public void onTriple(URI suj, URI pred, String obj, Spider source, String lang);

    public void onTriple(URI suj, URI pred, URI obj, Spider source, String lang);

    public void onNTFormat(String row);

    public void onError(URL url, int error);

    public void onError(URL url, Throwable e);
}
