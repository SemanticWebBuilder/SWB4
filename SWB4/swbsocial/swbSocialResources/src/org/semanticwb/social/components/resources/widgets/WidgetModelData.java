/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.widgets;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge.jimenez
 */
public class WidgetModelData {

    private List<Message> messages = new ArrayList<Message>();

    public WidgetModelData() {

        messages.add(new Message("Mensaje-1", "Iphone", "Pos"));
        messages.add(new Message("Mensaje-1", "Android", "Neg"));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
