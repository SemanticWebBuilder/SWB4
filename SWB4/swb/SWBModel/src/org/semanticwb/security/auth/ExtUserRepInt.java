package org.semanticwb.security.auth;

import org.semanticwb.model.User;

/**
 *
 * @author serch
 */
public abstract class ExtUserRepInt {
    public abstract void syncUsers();
    public abstract boolean validateCredential(String login, Object credential);
    public abstract boolean syncUser(String login, User user);
}
