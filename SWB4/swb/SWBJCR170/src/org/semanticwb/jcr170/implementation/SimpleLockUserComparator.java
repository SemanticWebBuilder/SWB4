/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import org.semanticwb.repository.LockUserComparator;

/**
 *
 * @author victor.lorenzana
 */
public class SimpleLockUserComparator implements LockUserComparator
{
    public boolean canUnlockNodeLockedByUser(String lockOwner, String unlockUser)
    {
        return true;
    }
}
