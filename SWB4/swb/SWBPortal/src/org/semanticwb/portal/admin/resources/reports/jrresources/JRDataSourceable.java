
package org.semanticwb.portal.admin.resources.reports.jrresources;

import javax.servlet.http.HttpServletResponse;

import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

public interface JRDataSourceable {
    public JRDataSource orderJRReport() throws IncompleteFilterException;
    
    public void prepareJRReport() throws JRException;
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException;
}
