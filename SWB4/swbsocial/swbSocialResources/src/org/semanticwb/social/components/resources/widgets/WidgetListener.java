/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.widgets;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.MessageIn;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author jorge.jimenez
 */
public class WidgetListener extends GenericForwardComposer<Component> {

    private static Logger log = SWBUtils.getLogger(WidgetListener.class);
    private static final long serialVersionUID = 1L;

    private final ListModelList<Message> messages =new ListModelList<Message>(new WidgetModelData().getMessages());

    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        try
        {
            System.out.println("Entra a WidgetListener/doAfterCompose-1");
            EventQueue eq = EventQueues.lookup("timelineReady",EventQueues.APPLICATION, true); //create a queue
            eq.publish(new Event("onTimelineReady", null, null)); 
            System.out.println("Entra a WidgetListener/doAfterCompose-2");
        }catch(Exception e)
        {
            log.debug(e);
        }
    }
    
    public ListModel<Message> getMessages() {
        EventQueue queOnTimelineReady = EventQueues.lookup("timelineReadyResp", EventQueues.SESSION, true);
        queOnTimelineReady.subscribe(new EventListener() {
            public void onEvent(Event evt) 
            {
                System.out.println("getMessages/timelineReadyResp-0");
                MessageIn messageIn=(MessageIn)evt.getData();
                System.out.println("getMessages/timelineReadyResp-1:"+messageIn);
                Message message=new Message(messageIn.getMsg_Text(), messageIn.getPostSource(), ""+messageIn.getPostSentimentalValue());
                messages.add(message);
                System.out.println("getMessages/timelineReadyResp-2");
            }
        });
        System.out.println("getMessages/timelineReadyResp-3");
        return messages;
    }



}