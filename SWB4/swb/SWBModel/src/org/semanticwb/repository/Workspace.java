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
    public static org.semanticwb.repository.Workspace createWorkspace(String id, String namespace)
    {

        log.debug("Creating workspace..." + id + " ...");
        org.semanticwb.repository.Workspace ws=WorkspaceBase.createWorkspace(id, namespace);
        Unstructured root = Unstructured.createUnstructured(ws);
        log.debug("Creating root node...");
        root.setName("jcr:root");
        root.setPath("/");        
        log.debug("Adding root node to workspace...");
        ws.setRoot(root);        
        log.debug("Workspace created...");
        return ws;
    }

    
}
