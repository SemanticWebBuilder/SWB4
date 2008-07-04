/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

/**
 *
 * @author Jei
 */
public interface RDFNode 
{
    
    /** 
        Answer a String representation of the node.  The form of the string 
        depends on the type of the node and is intended for human consumption,
        not machine analysis.
    */
    @Override
    public String toString();
    
    /** 
        Answer true if this RDFNode is an anonynous resource. Useful for
        one-off tests: see also visitWith() for making literal/anon/URI choices.
    */
    public boolean isAnon();
    
    /** 
        Answer true if this RDFNode is a literal resource. Useful for
        one-off tests: see also visitWith() for making literal/anon/URI choices.
    */
    public boolean isLiteral();
    
    /** 
        Answer true if this RDFNode is an named resource. Useful for
        one-off tests: see also visitWith() for making literal/anon/URI choices.
    */
    public boolean isURIResource();
    
    /**
        Answer true if this RDFNode is a URI resource or an anonynous
        resource (ie is not a literal). Useful for one-off tests: see also 
        visitWith() for making literal/anon/URI choices.
    */
    public boolean isResource();
    
    /**
        RDFNodes can be converted to different implementation types. Convert
        this RDFNode to a type supporting the <code>view</code>interface. The 
        resulting RDFNode should be an instance of <code>view</code> and should 
        have any internal invariants as specified.
    <p>
        If the RDFNode has no Model attached, it can only be .as()ed to
        a type it (this particular RDFNOde) already has.
    <p>
        If the RDFNode cannot be converted, an UnsupportedPolymorphism
        exception is thrown..
    * /
    public RDFNode as( Class view );
    
    /**
        Answer true iff this RDFNode can be viewed as an instance of
        <code>view</code>: that is, if it has already been viewed in this
        way, or if it has an attached model in which it has properties that
        permit it to be viewed in this way. If <code>canAs</code> returns
        <code>true</code>, <code>as</code> on the same view should
        deliver an instance of that class. 
    * /
    public boolean canAs( Class view );
    
    /**
        Answer a .equals() version of this node, except that it's in the model 
        <code>m</code>.
        
        @param m a model to move the node to
        @return this, if it's already in m (or no model), a copy in m otherwise
    * /
    public RDFNode inModel(Model m);
    
    /**
        Apply the appropriate method of the visitor to this node's content and
        return the result.
        
        @param rv an RDFVisitor with a method for URI/blank/literal nodes
        @return the result returned by the selected method
    * /
    public Object visitWith( RDFVisitor rv );
    */
}
