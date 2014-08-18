package org.semanticwb.social.base;

   /**
   * Interface que es agregaga a SocialNetworks que seran monitoreadas en cuanto a estadisticas (Insights), para poder saber como se van moviendo las cuentas en cuanto a Friends, Followers, PTAT (People talking about this), etc. 
   */
public interface SocialStatsMonitorableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Interface que es agregaga a SocialNetworks que seran monitoreadas en cuanto a estadisticas (Insights), para poder saber como se van moviendo las cuentas en cuanto a Friends, Followers, PTAT (People talking about this), etc. 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialStatsMonitorable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialStatsMonitorable");
}
