package com.example.reports;

/**
 * Entry point for CampusVault demo.
 *
 * Clients now talk to ReportProxy (via the Report interface).
 * Access control and lazy loading are handled transparently by the proxy.
 */
public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin   = new User("Kshitij", "ADMIN");

        // Proxies are cheap to create — no disk I/O at this point
        Report publicReport  = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review",   "FACULTY");
        Report adminReport   = new ReportProxy("R-303", "Budget Audit",     "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo ===\n");

        // PUBLIC report — student can access
        viewer.open(publicReport, student);
        System.out.println();

        // FACULTY report — student blocked
        viewer.open(facultyReport, student);
        System.out.println();

        // FACULTY report — faculty allowed; real report loaded here for first time
        viewer.open(facultyReport, faculty);
        System.out.println();

        // ADMIN report — admin allowed; real report loaded here for first time
        viewer.open(adminReport, admin);
        System.out.println();

        // ADMIN report — same proxy, second call: cache hit, no disk I/O
        viewer.open(adminReport, admin);
    }
}
