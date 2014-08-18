package org.semanticwb.social;

   /**
   * Interface que es agregaga a SocialNetworks que seran monitoreadas en cuanto a estadisticas (Insights), para poder saber como se van moviendo las cuentas en cuanto a Friends, Followers, PTAT (People talking about this), etc. 
   */
public interface SocialStatsMonitorable extends org.semanticwb.social.base.SocialStatsMonitorableBase
{
    public void getSocialNetStats(SocialNetwork socialNet);
}
