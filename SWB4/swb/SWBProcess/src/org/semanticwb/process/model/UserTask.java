package org.semanticwb.process.model;

import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;


public class UserTask extends org.semanticwb.process.model.base.UserTaskBase 
{
    public UserTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public WrapperTaskWebPage getTaskWebPage() {
        WrapperTaskWebPage wp=super.getTaskWebPage();
        if(wp==null)
        {
            wp=WrapperTaskWebPage.ClassMgr.createWrapperTaskWebPage("SWPTask_"+getId(), getProcessSite());
            setTaskWebPage(wp);
            wp.setActive(true);
        }
        return wp;
    }

}
