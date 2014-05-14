package org.semanticwb.social;

import java.util.HashMap;


   /**
   * Cuenta de Instagram 
   */
public class Instagram extends org.semanticwb.social.base.InstagramBase 
{
    public Instagram(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

   
    @Override
    public void postPhoto(Photo photo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HashMap<String, Long> monitorPostOutResponses(PostOut postOut) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
