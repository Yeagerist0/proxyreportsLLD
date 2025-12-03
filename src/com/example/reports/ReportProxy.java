package com.example.reports;

/**
 * Proxy — sits in front of RealReport and adds:
 *   1. Access control  : blocks users whose role is below the report classification.
 *   2. Lazy loading    : RealReport is created only when access is first granted.
 *   3. Caching         : once loaded, the same RealReport is reused on every
 *                        subsequent display() call through this proxy.
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification; // PUBLIC / FACULTY / ADMIN

    private RealReport realReport; // null until first authorised access

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId       = reportId;
        this.title          = title;
        this.classification = classification;
        // RealReport is NOT created here — that is the point of lazy loading
    }

    @Override
    public void display(User user) {
        if (!isAllowed(user)) {
            System.out.println("[proxy] ACCESS DENIED — " + user.getName()
                    + " (" + user.getRole() + ") cannot open "
                    + title + " [" + classification + "]");
            return;
        }

        // Lazy-load: create RealReport only on first authorised call
        if (realReport == null) {
            System.out.println("[proxy] authorised — loading report for the first time");
            realReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] cache hit — reusing already-loaded report");
        }

        realReport.display(user);
    }

    /**
     * Role hierarchy: ADMIN > FACULTY > STUDENT > (PUBLIC = anyone)
     */
    private boolean isAllowed(User user) {
        String role = user.getRole();
        switch (classification) {
            case "PUBLIC":  return true;
            case "FACULTY": return role.equals("FACULTY") || role.equals("ADMIN");
            case "ADMIN":   return role.equals("ADMIN");
            default:        return false;
        }
    }
}
