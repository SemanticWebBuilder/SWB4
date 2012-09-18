/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.components.streams;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zk.ui.select.SelectorComposer;

/**
 *
 * @author jorge.jimenez
 */
public class TimeLimeRendererController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    private final ListModel<LanguageContribution> langContributors = new ListModelList<LanguageContribution>(new ContributorData().getLanguageContributors());

    public ListModel<LanguageContribution> getContributors() {
        return langContributors;
    }
}
