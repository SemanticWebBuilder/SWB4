package org.semanticwb.social;


   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web 
   */
public class SocialPFlow extends org.semanticwb.social.base.SocialPFlowBase 
{
    public SocialPFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
     /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
