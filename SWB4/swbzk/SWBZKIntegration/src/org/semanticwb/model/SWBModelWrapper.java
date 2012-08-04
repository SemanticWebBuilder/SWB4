/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.model;

import java.util.List;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis.g
 */
public class SWBModelWrapper
{

    public List<User> listUsers()
    {
        return SWBUtils.Collections.copyIterator(User.ClassMgr.listUsers());
    }
    
    public List<User> listUsers(SWBModel model)
    {
        return SWBUtils.Collections.copyIterator(User.ClassMgr.listUsers(model));
    }
    
    public List<WebPage> listWebPages()
    {
        return SWBUtils.Collections.copyIterator(WebPage.ClassMgr.listWebPages());
    }
    
    public List<WebPage> listWebPages(SWBModel model)
    {
        return SWBUtils.Collections.copyIterator(WebPage.ClassMgr.listWebPages(model));
    }
    

}
