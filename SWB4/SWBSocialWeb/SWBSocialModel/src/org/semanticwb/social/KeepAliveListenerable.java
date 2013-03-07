package org.semanticwb.social;

import org.semanticwb.model.SWBModel;

   /**
   * Interface para las redes sociales que pueden mantener abierta la conección para realizar las busquedas (listener) como es el caso de Twitter con su Streaming Api. 
   */
public interface KeepAliveListenerable extends org.semanticwb.social.base.KeepAliveListenerableBase
{
    public void listenAlive(SWBModel model);

    public void stopListenAlive();
}
