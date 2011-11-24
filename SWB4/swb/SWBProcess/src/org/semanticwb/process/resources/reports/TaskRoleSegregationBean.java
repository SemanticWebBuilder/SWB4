package org.semanticwb.process.resources.reports;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hasdai
 */
public class TaskRoleSegregationBean {
    private ArrayList<ProcessBean> processes;

    public TaskRoleSegregationBean(ArrayList<ProcessBean> beans) {
        this.processes = beans;
    }

    /**
     * @return the processes
     */
    public ArrayList<ProcessBean> getProcesses() {
        return processes;
    }

    /**
     * @param processes the processes to set
     */
    public void setProcesses(ArrayList<ProcessBean> processes) {
        this.processes = processes;
    }
}
