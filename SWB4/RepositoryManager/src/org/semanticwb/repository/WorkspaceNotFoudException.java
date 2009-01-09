/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.repository;

/**
 *
 * @author victor.lorenzana
 */
public class WorkspaceNotFoudException extends Exception{

        public WorkspaceNotFoudException(String workspace)
        {
            super("The workspace "+workspace+" was not found");
        }
}
