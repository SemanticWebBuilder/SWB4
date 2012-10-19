/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.widgets;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

/**
 *
 * @author jorge.jimenez
 */
public class WidgetListenerRenderer implements RowRenderer<Message> {

    public void render(Row row, Message data, int index) {
        // the data append to each row with simple label
        row.appendChild(new Label(Integer.toString(index)));
        row.appendChild(new Label(data.getMsg()));
        row.appendChild(new Label(data.getDevice()));
        row.appendChild(new Label((String.valueOf(data.getSentiment()))));
        // we create a thumb up/down comment to each row
        /*
        final Div d = new Div();
        final Button thumbBtn = new Button(null, "/images/thumb-up.png");
        thumbBtn.setParent(d);
        thumbBtn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
                d.appendChild(new Label("Thumbs up"));
                thumbBtn.setDisabled(true);
            }
        row.appendChild(d); // any component could created as a child of grid
         *
         */
    }
}
