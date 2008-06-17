/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice;

/**
 *
 * @author victor.lorenzana
 */
public interface MenuListener {
    public void onDocumentPublished(OfficeDocument document);
    public void onDocumentNoPublished(OfficeDocument document);
    public void onLogin();
    public void onLogout();    
}
