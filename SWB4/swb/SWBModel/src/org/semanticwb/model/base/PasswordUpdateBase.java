package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class PasswordUpdateBase extends SWBFormElement 
{

    public PasswordUpdateBase(SemanticObject base)
    {
        super(base);
    }

    public String getVerifyText()
    {
        return getSemanticObject().getProperty(vocabulary.swb_passUpdVerify);
    }

    public void setVerifyText(String passUpdVerify)
    {
        getSemanticObject().setProperty(vocabulary.swb_passUpdVerify, passUpdVerify);
    }

    public String getVerifyText(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_passUpdVerify, null, lang);
    }

    public String getDisplayVerifyText(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_passUpdVerify, lang);
    }

    public void setVerifyText(String passUpdVerify, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_passUpdVerify, passUpdVerify, lang);
    }
}
