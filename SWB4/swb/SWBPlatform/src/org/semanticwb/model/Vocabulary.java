
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
        RDFModel=getTopicClass("RDFModel");
        User=getTopicClass("User");
        Template=getTopicClass("Template");
        Topic=getTopicClass("Topic");
        WebPage=getTopicClass("WebPage");
        WebSite=getTopicClass("WebSite");
        //Interfaces
        
        //Properties
        value=getTopicProperty("value");
    }    

}
