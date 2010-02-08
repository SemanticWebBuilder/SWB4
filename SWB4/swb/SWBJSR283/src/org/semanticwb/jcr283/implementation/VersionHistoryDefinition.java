/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.RepositoryException;
import javax.jcr.version.OnParentVersionAction;

/**
 *
 * @author victor.lorenzana
 */
public final class VersionHistoryDefinition extends NodeDefinitionImp {
    private static final String NT_VERSION_HISTORY = "nt:versionHistory";


    public VersionHistoryDefinition(VersionHistoryImp vh) throws RepositoryException
    {
        super(vh.getSemanticObject(), SWBRepository.getNodeTypeManagerImp().getNodeTypeImp(NT_VERSION_HISTORY));

    }
    public VersionHistoryDefinition() throws RepositoryException
    {
        super("jcr:versionHistory", true, true, OnParentVersionAction.IGNORE, SWBRepository.getNodeTypeManagerImp().getNodeTypeImp(NT_VERSION_HISTORY), true, true, SWBRepository.getNodeTypeManagerImp().getNodeTypeImp(NT_VERSION_HISTORY));
    }

}
