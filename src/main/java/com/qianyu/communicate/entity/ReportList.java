package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class ReportList {
    private int reportId;
    private String reportName;

    public ReportList(int reportId, String reportName) {
        this.reportId = reportId;
        this.reportName = reportName;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
