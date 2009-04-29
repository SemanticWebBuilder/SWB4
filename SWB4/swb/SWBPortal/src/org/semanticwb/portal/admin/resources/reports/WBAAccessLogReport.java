
package org.semanticwb.portal.admin.resources.reports;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class WBAAccessLogReport {






    /*private ArrayList getFileNames(HttpServletRequest request) {
        ArrayList al_files = new ArrayList();
        GregorianCalendar cal;
        GregorianCalendar iniDate = null;
        GregorianCalendar finDate = null;

        String s_accesslog = SWBPlatform.getEnv("swb/accessLog");
        String s_period = SWBPlatform.getEnv("swb/accessLogPeriod");
        String s_path = SWBPlatform.getWorkPath();
        
        String websiteId = request.getParameter("wb_site");
        String fecha11 = request.getParameter("wb_fecha11"); 
        String fecha12 = request.getParameter("wb_fecha12");

//        String s_year_11 = request.getParameter("wb_year_11");
//        String s_month_11 = request.getParameter("wb_month_11");
//        String s_day_11 = request.getParameter("wb_day_11");
//        String s_hour_11 = request.getParameter("wb_hour_11");
//        String s_minute_11 = request.getParameter("wb_minute_11");
//        String s_year_12 = request.getParameter("wb_year_12");
//        String s_month_12 = request.getParameter("wb_month_12");
//        String s_day_12 = request.getParameter("wb_day_12");
//        String s_hour_12 = request.getParameter("wb_hour_12");
//        String s_minute_12 = request.getParameter("wb_minute_12");
        int iniYear = Integer.parseInt(fecha11.substring(0,4));
        int iniMonth = Integer.parseInt(fecha11.substring(5,7));
        int iniDay = Integer.parseInt(fecha11.substring(8));
        int iniHour = Integer.parseInt("");
        int iniMin = Integer.parseInt("");
//        int iniSec = Integer.parseInt("");

        int finYear = Integer.parseInt(fecha12.substring(0,4));
        int finMonth = Integer.parseInt(fecha12.substring(5,7));
        int finDay = Integer.parseInt(fecha12.substring(8));
        int finHour = Integer.parseInt("");
        int finMin = Integer.parseInt("");
//        int finSec = Integer.parseInt("");
        
        String s_auxyear;
        String s_auxmonth;
        String s_auxday;
        boolean validDates;
        boolean b_day = false;

//        if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
//        if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
//        if(s_month_11.length() == 1) s_month_11 = "0" + s_month_11;
//        if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);
//
//        if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
//        if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
//        if(s_month_12.length() == 1) s_month_12 = "0" + s_month_12;
//        if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));

//        int i_year1 = Integer.parseInt(s_year_11);
//        int i_year2 = Integer.parseInt(s_year_12);
//        int i_month1 = Integer.parseInt(s_month_11);
//        int i_month2 = Integer.parseInt(s_month_12);
//        int i_day1 = Integer.parseInt(s_day_11);
//        int i_day2 = Integer.parseInt(s_day_12);
//        int i_hour1 = Integer.parseInt(s_hour_11);
//        int i_hour2 = Integer.parseInt(s_hour_12);
//        int i_minute1 = Integer.parseInt(s_minute_11);
//        int i_minute2 = Integer.parseInt(s_minute_12);

        iniDate = new GregorianCalendar(iniYear, iniMonth, iniDay, iniHour, iniMin);
        finDate = new GregorianCalendar(finYear, finMonth, finDay, finHour, finMin);

        //Evaluates if dates are correct
        if(iniDate.before(finDate)) {
            validDates = true;
        }else if(iniDate.equals(finDate)) {
            validDates = true;
        }else {
            validDates = false;
        }

        if(validDates && websiteId!=null) {
            s_accesslog += "_" + websiteId + "_acc";
            //Period by day
            if(s_period.equalsIgnoreCase("daily")) {
//                if(i_year1 <= i_year2) {
                    while(iniYear <= finYear) {
                        s_auxyear = Integer.toString(iniYear);
                        if(iniYear < finYear) {
                            while(iniMonth <= 12) {
                                s_auxmonth = Integer.toString(iniMonth);
                                if(s_auxmonth.length()== 1) {
                                    s_auxmonth = "0"+s_auxmonth;
                                }
                                cal = new GregorianCalendar(iniYear, iniMonth, 1);
                                int daysOfMonth = cal. getActualMaximum(cal.DAY_OF_MONTH);
                                while(iniDay <= daysOfMonth) {
                                    s_auxday = Integer.toString(iniDay);
                                    if(s_auxday.length() == 1) {
                                        s_auxday = "0"+s_auxday;
                                    }
                                    al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                    iniDay++;
                                }
                                iniDay = 1;
                                b_day = true;
                                iniMonth++;
                            }
                            iniMonth = 1;
                        }else {
                            if(i_month1 <= i_month2)
                            {
                                while(i_month1 <= i_month2)
                                {
                                    if(i_month1 != i_month2)
                                    {
                                        s_auxmonth = Integer.toString(i_month1);
                                        if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                        while(i_day1 <= 31)
                                        {
                                            s_auxday = Integer.toString(i_day1);
                                            if(s_auxday.length() == 1) s_auxday = "0"+s_auxday;
                                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            i_day1++;
                                        }
                                        i_day1 = 1;
                                        b_day = true;
                                    }
                                    else
                                    {
                                        if(b_day)
                                        {
                                            i_day1 = 1;
                                        }
                                        else
                                        {
                                            i_day1 = Integer.parseInt(s_day_11);
                                        }
                                        s_auxmonth = Integer.toString(i_month1);
                                        if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                        while(i_day1 <= i_day2)
                                        {
                                            s_auxday = Integer.toString(i_day1);
                                            if(s_auxday.length() == 1) s_auxday = "0"+s_auxday;
                                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            i_day1++;
                                        }
                                    }
                                    i_month1++;
                                }
                            }
                        }
                        i_year1 ++;
                    }
//                }
            }else if(s_period.equalsIgnoreCase("monthly")) {
//                if(i_year1 <= i_year2) {
                    while(i_year1 <= i_year2)
                    {
                        s_auxyear = Integer.toString(i_year1);
                        if(i_year1 != i_year2)
                        {
                            while(i_month1 <= 12)
                            {
                                s_auxmonth = Integer.toString(i_month1);
                                if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                i_month1 ++;
                            }
                            i_month1 = 1;
                        }
                        else
                        {
                            while(i_month1 <= i_month2)
                            {
                                if(i_month1 != i_month2)
                                {
                                    s_auxmonth = Integer.toString(i_month1);
                                    if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                    //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                    al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                }
                                else
                                {
                                    s_auxmonth = Integer.toString(i_month1);
                                    if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                    //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                    al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                }
                                i_month1 ++;
                            }
                        }
                        i_year1 ++;
                    }
//                }
            }else if(s_period.equalsIgnoreCase("yearly")) {
//                if(i_year1 <= i_year2) {
                    while(i_year1 <= i_year2)
                    {
                        s_auxyear = Integer.toString(i_year1);
                        if(i_year1 != i_year2)
                        {
                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + ".log");
                            al_files.add(s_path + s_accesslog + "." + s_auxyear + ".log");
                        }
                        else
                        {
                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + ".log");
                            al_files.add(s_path + s_accesslog + "." + s_auxyear + ".log");
                        }
                        i_year1 ++;
                    }
//                }

            }
        }
        return al_files;
    }*/

}
