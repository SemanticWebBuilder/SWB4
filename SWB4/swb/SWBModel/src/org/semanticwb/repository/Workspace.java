package org.semanticwb.repository;

import org.semanticwb.repository.base.*;
import org.semanticwb.platform.SemanticObject;

public class Workspace extends WorkspaceBase
{    
    
    public Workspace(SemanticObject base)
    {
        super(base);        
    }
    public static org.semanticwb.repository.Workspace createWorkspace(String id, org.semanticwb.model.SWBModel model)
    {
        Unstructured root = Unstructured.createUnstructured(model);
        root.setName("jcr:root");
        root.setPath("/");
        org.semanticwb.repository.Workspace ws=(org.semanticwb.repository.Workspace)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        ws.setRoot(root);
        return ws;
    }

    
}
