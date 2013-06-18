
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


public class StatesManager extends GenericResource {

    @Override
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        State.ClassMgr.listStateByStateGroup(null);
        Objective obj = new Objective(null);
        obj.addObjState(null);
        obj.removeAllObjState();
    }
}
