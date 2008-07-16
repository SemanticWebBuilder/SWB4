
package org.semanticwb.model;

import org.semanticwb.platform.SWBVocabulary;

/**
 *
 * @author Jei
 */
public class Vocabulary extends SWBVocabulary
{
    //Classes
    public TopicClass RDFModel;
    public TopicClass User;
    public TopicClass Template;
    public TopicClass Topic;
    public TopicClass WebPage;
    public TopicClass WebSite;
    //Interfaces   

    //Properties
    public TopicProperty value;    

    @Override
    public void init()
    {
        //Classes
        RDFModel=getTopicClass(URI+"RDFModel");
        User=getTopicClass(URI+"User");
        Template=getTopicClass(URI+"Template");
        Topic=getTopicClass(URI+"Topic");
        WebPage=getTopicClass(URI+"WebPage");
        WebSite=getTopicClass(URI+"WebSite");
        //Interfaces
        
        //Properties
        value=getTopicProperty(URI+"value");
    }    

}
