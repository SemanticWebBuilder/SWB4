package org.semanticwb.bsc.formelement;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Form Element que presentará la vista para administrar archivos adjuntos
 * (Creación, Edición y Eliminación)
 */
public class AttachmentElement extends org.semanticwb.bsc.formelement.base.AttachmentElementBase {

    private static Logger log = SWBUtils.getLogger(AttachmentElement.class);

    public AttachmentElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

}
