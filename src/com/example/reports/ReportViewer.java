package com.example.reports;

/**
 * Client — depends only on the Report interface.
 * No reference to RealReport or ReportProxy here.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
