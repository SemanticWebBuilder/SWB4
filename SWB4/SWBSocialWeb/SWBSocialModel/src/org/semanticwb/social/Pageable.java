package org.semanticwb.social;

   /**
   * Interface that is used to those SocialNets that supports the manage of FanPages. (Just Facebook at this momment) 
   */
public interface Pageable extends org.semanticwb.social.base.PageableBase
{
    public void createPageTab(PageTab pageTab);
    
    public void removePageTab(PageTab pageTab);
}
