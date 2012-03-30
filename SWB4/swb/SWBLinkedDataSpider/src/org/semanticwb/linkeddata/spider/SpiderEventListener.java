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
    public void visit(URI suj);
    public void onTriple(URI suj, URI pred, String obj, URL url);
    public void onTripleAndFollow(URI suj, URI pred, URI obj, URL url);
//    public void newRDFSTriple(URI suj, URI pred, String obj, URL url);
//    public void newRDFTriple(URI suj, URI pred, String obj, URL url);
//    public void newOWLTriple(URI suj, URI pred, String obj, URL url);
    public void onError(URL url,int error);
    public void onError(URL url,Throwable e);

}
