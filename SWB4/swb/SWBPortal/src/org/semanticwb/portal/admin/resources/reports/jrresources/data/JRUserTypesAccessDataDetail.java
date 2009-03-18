
package org.semanticwb.portal.admin.resources.reports.jrresources.data;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.admin.resources.reports.datadetail.UserTypesAccessDataDetail;
import org.semanticwb.portal.admin.resources.reports.datadetail.SWBDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class JRUserTypesAccessDataDetail implements JRDataSourceable{
    private SWBDataDetail dataDetail;
    
    public JRUserTypesAccessDataDetail(WBAFilterReportBean filterReportBean){
        dataDetail = new UserTypesAccessDataDetail(filterReportBean);
    }
    
    public JRDataSource orderJRReport() throws IncompleteFilterException{
        List dataList = dataDetail.execute();
        JRBeanCollectionDataSource dataSource = null;
        
        if(!dataList.isEmpty())
            dataSource = new JRBeanCollectionDataSource(dataList);
        return dataSource;
    }
    
    public void prepareJRReport() throws JRException{
    }
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
    }
}
