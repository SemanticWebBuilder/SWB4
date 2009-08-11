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

package org.semanticwb.rdf.imp;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import org.semanticwb.rdf.*;

/**
 *
 * @author Jei
 */
public class ModelImp implements Model
{
    private com.hp.hpl.jena.rdf.model.Model interObject;

    public ModelImp(com.hp.hpl.jena.rdf.model.Model interObject)
    {
        this.interObject=interObject;
    }
    
    public com.hp.hpl.jena.rdf.model.Model getInternalObject() 
    {
        return interObject;
    }    

    public Resource createResource()
    {
        return new ResourceImp(interObject.createResource());
    }

    public Resource createResource( String uri )
    {
        return new ResourceImp(interObject.createResource(uri));
    }

    public long size() 
    {
        return interObject.size();
    }

    public boolean isEmpty() {
        return interObject.isEmpty();
    }

    public Resource getResource(String uri) {
        return new ResourceImp(interObject.getResource(uri));
    }

    public Property getProperty(String nameSpace, String localName) {
        return new PropertyImp(interObject.getProperty(nameSpace,localName));
    }

    public Property createProperty(String nameSpace, String localName) {
        return new PropertyImp(interObject.createProperty(nameSpace,localName));
    }

    public Literal createLiteral(String v, String language) {
        return new LiteralImp(interObject.createLiteral(v,language));
    }

    public Literal createLiteral(String v, boolean wellFormed) {
        return new LiteralImp(interObject.createLiteral(v,wellFormed));
    }

    public Statement createStatement(Resource s, Property p, RDFNode o) {
        return new StatementImp(interObject.createStatement(
                ((ResourceImp)s).getInternalObject(),
                ((PropertyImp)p).getInternalObject(),
                ((RDFNodeImp)o).getInternalObject()
                ));
    }

    public Model add(Statement s) {
        interObject.add(((StatementImp)s).getInternalObject());
        return this;
    }

    public Model add(Statement[] statements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model remove(Statement[] statements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model add(List statements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model remove(List statements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model add(Model m) {
        interObject.add(((ModelImp)m).getInternalObject());
        return this;
    }

    public Model add(Model m, boolean suppressReifications) {
        interObject.add(((ModelImp)m).getInternalObject(),suppressReifications);
        return this;
    }

    public Model read(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model read(InputStream in, String base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model read(InputStream in, String base, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model read(Reader reader, String base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model read(String url, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model read(Reader reader, String base, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model read(String url, String base, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model write(Writer writer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model write(Writer writer, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model write(Writer writer, String lang, String base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model write(OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model write(OutputStream out, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model write(OutputStream out, String lang, String base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model remove(Statement s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Statement getRequiredProperty(Resource s, Property p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Statement getProperty(Resource s, Property p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Resource s, Property p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsResource(RDFNode r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Resource s, Property p, RDFNode o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Statement s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAny(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAll(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isReified(Statement s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Resource getAnyReifiedStatement(Statement s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeAllReifications(Statement s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model union(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model intersection(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model difference(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model begin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model abort() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model commit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean independent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean supportsTransactions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean supportsSetOperations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isIsomorphicWith(Model g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model notifyEvent(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model removeAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Model removeAll(Resource s, Property p, RDFNode r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isClosed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StmtIterator listStatements() {
        return new StmtIteratorImp(interObject.listStatements());
    }

    public StmtIterator listStatements(Resource s, Property p, RDFNode o) {
        return new StmtIteratorImp(interObject.listStatements(
                ((ResourceImp)s).getInternalObject(),
                ((PropertyImp)s).getInternalObject(),
                ((RDFNodeImp)s).getInternalObject()
                ));
    }
    
}
