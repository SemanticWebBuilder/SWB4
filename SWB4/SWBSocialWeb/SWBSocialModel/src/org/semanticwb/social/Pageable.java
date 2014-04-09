package org.semanticwb.social;

   /**
   * Interface that is used to those SocialNets that supports the manage of FanPages. (Just Facebook at this momment) 
   */
public interface Pageable extends org.semanticwb.social.base.PageableBase
{
    public boolean createPageTab(PageTab pageTab);
    
    public boolean removePageTab(PageTab pageTab);
    
    public boolean removePageTab(FanPage fanPage, String app_id);
}
