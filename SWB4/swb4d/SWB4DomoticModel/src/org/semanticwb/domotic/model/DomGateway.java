package org.semanticwb.domotic.model;

import java.util.Iterator;
import org.semanticwb.domotic.server.Connection;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


public class DomGateway extends org.semanticwb.domotic.model.base.DomGatewayBase 
{
    private Connection con=null;
    
    public DomGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setConnection(Connection con)
    {
        this.con = con;
    }

    public Connection getConnection()
    {
        return con;
    }
    
    public static DomGateway getDomDeviceBySerial(String serial, SWBModel model)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(swb4d_gtwSerial, serial);
        while (it.hasNext())
        {
            SemanticObject semanticObject = it.next();
            return (DomGateway)semanticObject.createGenericInstance();
        }
        return null;
    }
    
}
