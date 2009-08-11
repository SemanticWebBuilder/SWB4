/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.util.ArrayList;
import org.semanticwb.jcr170.implementation.SWBRepository;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr170.implementation.SWBCredentials;
import org.semanticwb.model.User;

/**
 *
 * @author victor.lorenzana
 */
public final class SWBRepositoryManager implements RepositoryManager
{    
    private static Logger log = SWBUtils.getLogger(SWBRepositoryManager.class);
    private SWBRepository repository;
    private OfficeManager officeManager;
    public SWBRepositoryManager() throws SWBException,RepositoryException
    {
        repository=new SWBRepository();
        officeManager=new SWBOfficeManager(this);
    }

    public void init()
    {
       log.event("Initilizing SWBRepositoryManager with repository "+ repository.getDescriptor(Repository.REP_NAME_DESC) +" ...");
    }
    public Repository getRepository()
    {
        return repository;
    }
    public ArrayList<String> getWorkspaces()
    {
        ArrayList<String> workspaces=new ArrayList<String>();
        for(String  workspace : repository.listWorkspaces())
        {
            workspaces.add(workspace);
        }
        return workspaces;
    }
    public Session openSession(String workspace,String id,String password) throws Exception
    {
        return repository.login(new SimpleCredentials(id, password.toCharArray()), workspace);
    }

    public String getName()
    {
        return "swb";
    }

    public OfficeManager getOfficeManager()
    {
        return officeManager;
    }

    public Session openSession(String workspace, User principal) throws Exception
    {
        return repository.login(new SWBCredentials(principal), workspace);
    }

    public void createWorkspace(String workspace,String title,String desciption) throws Exception
    {
        SWBRepository.createWorkspace(workspace,title,desciption);
    }

    public void deleteWorkspace(String workspace) throws Exception
    {
        SWBRepository.deleteWorkspace(workspace);
    }
    
}
