/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.repository;

/**
 *
 * @author victor.lorenzana
 */
public interface LockUserComparator {
    /**
     * 
     * @param lockOwner
     * @param unlockUser
     * @return 0 if the user has the same level, positive if
     */
    public boolean canUnlockNodeLockedByUser(String lockOwner,String unlockUser);
}
