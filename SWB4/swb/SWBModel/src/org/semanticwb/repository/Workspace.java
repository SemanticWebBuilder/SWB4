package org.semanticwb.repository;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.repository.base.*;
import org.semanticwb.platform.SemanticObject;

public class Workspace extends WorkspaceBase
{    
    static Logger log = SWBUtils.getLogger(Workspace.class);
    public Workspace(SemanticObject base)
    {
        super(base);        
    }
    public static org.semanticwb.repository.Workspace createWorkspace(String id, org.semanticwb.model.SWBModel model)
    {
        log.debug("Creating workspace..." + id + " ...");
        Unstructured root = Unstructured.createUnstructured(model);
        log.debug("Creating root node...");
        root.setName("jcr:root");
        root.setPath("/");
        org.semanticwb.repository.Workspace ws=(org.semanticwb.repository.Workspace)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        ws.setRoot(root);
        log.debug("Adding root node to workspace...");
        log.debug("Workspace created...");
        return ws;
    }

    
}
