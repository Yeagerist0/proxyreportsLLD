package com.example.reports;

/**
 * Common abstraction for all report types.
 * Both RealReport and ReportProxy implement this interface,
 * which allows client code (ReportViewer) to remain decoupled
 * from the concrete implementation.
 */
public interface Report {
    void display(User user);
}
