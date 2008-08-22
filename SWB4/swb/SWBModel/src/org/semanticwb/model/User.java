package org.semanticwb.model;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class User extends UserBase implements Principal
{
    Logger log = SWBUtils.getLogger(User.class);
    public User(SemanticObject base)
    {
        super(base);
    }

    public String getName() {
        return getUsrLogin();
    }
    
    @Override
    public void setUsrPassword(String password){
        String tmpPasswd=null;
        try {
            tmpPasswd = SWBUtils.CryptoWrapper.passwordDigest(password);
            super.setUsrPassword(tmpPasswd);
            setUsrPasswordChanged(new Date());
        } catch (NoSuchAlgorithmException ex) {
            log.error("User: Can't set a crypted Password", ex);
        }
        
    }
}
