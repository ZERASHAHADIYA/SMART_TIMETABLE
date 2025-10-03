/*import java.util.*;

class Staff {
    private String name;
    private String subject;

    public Staff(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }

    public String getName() { return name; }
    public String getSubject() { return subject; }

    @Override
    public String toString() {
        return subject + " (" + name + ")";
    }
}

class Timetable {
    private List<Staff> staffList;
    private String[][] schedule; // rows = days, cols = periods
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    public Timetable(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public void generate(int startHour, int endHour) {
        int periods = endHour - startHour;
        schedule = new String[days.length][periods];
        Random rand = new Random();

        for (int d = 0; d < days.length; d++) {
            for (int p = 0; p < periods; p++) {
                Staff s = staffList.get(rand.nextInt(staffList.size()));
                schedule[d][p] = s.toString();
            }
        }

        display(startHour, endHour);
    }

    private void display(int startHour, int endHour) {
        int periods = endHour - startHour;
        System.out.println("\n================= Generated Timetable =================");

        // Print header row (periods)
        System.out.print(String.format("%-12s", "Day/Time"));
        for (int h = startHour; h < endHour; h++) {
            System.out.print(String.format("%-20s", h + ":00-" + (h + 1) + ":00"));
        }
        System.out.println();

        // Print schedule rows
        for (int d = 0; d < days.length; d++) {
            System.out.print(String.format("%-12s", days[d]));
            for (int p = 0; p < periods; p++) {
                System.out.print(String.format("%-20s", schedule[d][p]));
            }
            System.out.println();
        }
        System.out.println("======================================================");
    }
}

public class SmartTimetable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Staff> staffList = new ArrayList<>();

        // Input staff
        System.out.print("Enter number of staff: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter staff name: ");
            String name = sc.nextLine();
            System.out.print("Enter subject for " + name + ": ");
            String subject = sc.nextLine();
            staffList.add(new Staff(name, subject));
        }

        // Input time
        System.out.print("Enter start time (hour, e.g., 9): ");
        int start = sc.nextInt();
        System.out.print("Enter end time (hour, e.g., 15 for 3 PM): ");
        int end = sc.nextInt();

        // Generate timetable
        Timetable tt = new Timetable(staffList);
        tt.generate(start, end);

        sc.close();
    }
}
*/
/*
import java.util.*;

class Staff {
    private String name;
    private String subject;
    private boolean isAbsent;

    public Staff(String name, String subject) {
        this.name = name;
        this.subject = subject;
        this.isAbsent = false;
    }

    public String getName() { return name; }
    public String getSubject() { return subject; }
    public boolean isAbsent() { return isAbsent; }
    public void setAbsent(boolean absent) { isAbsent = absent; }

    @Override
    public String toString() {
        return subject + " (" + name + ")";
    }
}

class Timetable {
    private List<Staff> staffList;
    private String[][] schedule; // rows = days, cols = periods
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private int startHour, endHour;
    private int breakHour; // e.g., 13 for 1 PM

    public Timetable(List<Staff> staffList, int startHour, int endHour, int breakHour) {
        this.staffList = staffList;
        this.startHour = startHour;
        this.endHour = endHour;
        this.breakHour = breakHour;
    }

    public void generate() {
        int periods = endHour - startHour;
        schedule = new String[days.length][periods];
        Random rand = new Random();

        for (int d = 0; d < days.length; d++) {
            for (int p = 0; p < periods; p++) {
                int currentHour = startHour + p;

                // Check for break time
                if (currentHour == breakHour) {
                    schedule[d][p] = "BREAK";
                    continue;
                }

                // Choose random available staff
                List<Staff> availableStaff = new ArrayList<>();
                for (Staff s : staffList) {
                    if (!s.isAbsent()) availableStaff.add(s);
                }

                if (availableStaff.isEmpty()) {
                    schedule[d][p] = "No Staff Available";
                } else {
                    Staff s = availableStaff.get(rand.nextInt(availableStaff.size()));
                    schedule[d][p] = s.toString();
                }
            }
        }

        display();
    }

    private void display() {
        int periods = endHour - startHour;

        System.out.println("\n================= Generated Timetable =================");
        System.out.print(String.format("%-12s", "Day/Time"));

        for (int h = startHour; h < endHour; h++) {
            System.out.print(String.format("%-20s", h + ":00-" + (h + 1) + ":00"));
        }
        System.out.println();

        for (int d = 0; d < days.length; d++) {
            System.out.print(String.format("%-12s", days[d]));
            for (int p = 0; p < periods; p++) {
                System.out.print(String.format("%-20s", schedule[d][p]));
            }
            System.out.println();
        }
        System.out.println("======================================================");
    }
}

public class SmartTimetable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Staff> staffList = new ArrayList<>();

        // Input staff
        System.out.print("Enter number of staff: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter staff name: ");
            String name = sc.nextLine();
            System.out.print("Enter subject for " + name + ": ");
            String subject = sc.nextLine();
            staffList.add(new Staff(name, subject));
        }

        // Input absent staff
        System.out.print("Enter number of absent staff today: ");
        int absentCount = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < absentCount; i++) {
            System.out.print("Enter absent staff name: ");
            String absentName = sc.nextLine();
            for (Staff s : staffList) {
                if (s.getName().equalsIgnoreCase(absentName)) {
                    s.setAbsent(true);
                    break;
                }
            }
        }

        // Input time and break
        System.out.print("Enter start time (hour, e.g., 9): ");
        int start = sc.nextInt();
        System.out.print("Enter end time (hour, e.g., 15 for 3 PM): ");
        int end = sc.nextInt();
        System.out.print("Enter break hour (e.g., 13 for 1 PM): ");
        int breakHour = sc.nextInt();

        // Generate timetable
        Timetable tt = new Timetable(staffList, start, end, breakHour);
        tt.generate();

        sc.close();
    }
}
*/
/*
import java.util.*;

class Staff {
    private String name;
    private String subject;
    private boolean isAbsent;

    public Staff(String name, String subject) {
        this.name = name;
        this.subject = subject;
        this.isAbsent = false;
    }

    public String getName() { return name; }
    public String getSubject() { return subject; }
    public boolean isAbsent() { return isAbsent; }
    public void setAbsent(boolean absent) { isAbsent = absent; }

    @Override
    public String toString() { return subject + " (" + name + ")"; }
}

class Timetable {
    private List<Staff> staffList;
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private double startHour, endHour, breakHour;
    private double periodDuration;
    private String[][] schedule;

    public Timetable(List<Staff> staffList, double startHour, double endHour, double breakHour, double periodDuration) {
        this.staffList = staffList;
        this.startHour = startHour;
        this.endHour = endHour;
        this.breakHour = breakHour;
        this.periodDuration = periodDuration;
    }

    private String formatTime(double hour) {
        int h = (int) hour;
        int m = (int) ((hour - h) * 60);
        return String.format("%02d:%02d", h, m);
    }

    public void generate() {
    int periods = (int) Math.ceil((endHour - startHour) / periodDuration);
    schedule = new String[days.length][periods];
    Random rand = new Random();

    for (int d = 0; d < days.length; d++) {
        Staff lastAssigned = null; // to avoid consecutive same teacher
        for (int p = 0; p < periods; p++) {
            double periodStart = startHour + p * periodDuration;
            double periodEnd = periodStart + periodDuration;

            // BREAK if breakHour is inside this period
            if (breakHour >= periodStart && breakHour < periodEnd) {
                schedule[d][p] = "BREAK";
                lastAssigned = null; // reset last teacher after break
                continue;
            }

            // Available staff excluding last assigned to avoid consecutive repetition
            List<Staff> availableStaff = new ArrayList<>();
            for (Staff s : staffList)
                if (!s.isAbsent() && s != lastAssigned)
                    availableStaff.add(s);

            if (availableStaff.isEmpty()) {
                // If only lastAssigned is available, allow it
                for (Staff s : staffList)
                    if (!s.isAbsent()) availableStaff.add(s);
            }

            if (availableStaff.isEmpty()) schedule[d][p] = "No Staff";
            else {
                Staff s = availableStaff.get(rand.nextInt(availableStaff.size()));
                schedule[d][p] = s.toString();
                lastAssigned = s;
            }
        }
    }

    display(periods);
}


    private void display(int periods) {
        System.out.println("\n================= Generated Timetable =================");

        // Header row
        System.out.print(String.format("%-12s", "Day/Time"));
        for (int p = 0; p < periods; p++) {
            double t = startHour + p * periodDuration;
            System.out.print(String.format("%-20s", formatTime(t) + "-" + formatTime(t + periodDuration)));
        }
        System.out.println();

        // Rows
        for (int d = 0; d < days.length; d++) {
            System.out.print(String.format("%-12s", days[d]));
            for (int p = 0; p < periods; p++) {
                System.out.print(String.format("%-20s", schedule[d][p]));
            }
            System.out.println();
        }

        System.out.println("======================================================");
    }
}

public class SmartTimetable{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Staff> staffList = new ArrayList<>();

        System.out.print("Enter number of staff: ");
        int n = sc.nextInt(); sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter staff name: ");
            String name = sc.nextLine();
            System.out.print("Enter subject: ");
            String subject = sc.nextLine();
            staffList.add(new Staff(name, subject));
        }

        // Absent staff
        System.out.print("Enter number of absent staff: ");
        int absent = sc.nextInt(); sc.nextLine();
        for (int i = 0; i < absent; i++) {
            System.out.print("Enter absent staff name: ");
            String absentName = sc.nextLine();
            for (Staff s : staffList) if (s.getName().equalsIgnoreCase(absentName)) s.setAbsent(true);
        }

        System.out.print("Enter start time (e.g., 9 or 9.5 for 9:30): ");
        double start = sc.nextDouble();
        System.out.print("Enter end time (e.g., 16 or 16.5 for 4:30): ");
        double end = sc.nextDouble();
        System.out.print("Enter break time (e.g., 13 or 13.5 for 1:30): ");
        double breakHour = sc.nextDouble();
        System.out.print("Enter period duration in hours (e.g., 1 or 0.5): ");
        double periodDuration = sc.nextDouble();

        Timetable tt = new Timetable(staffList, start, end, breakHour, periodDuration);
        tt.generate();
        sc.close();
    }
}
*/


import java.util.*;

class Staff {
    private String name;
    private String subject;
    private boolean isAbsent;
    private int assignedPeriodsToday; // Track periods assigned per day
    private int maxDailyPeriods;      // Maximum allowed periods per day

    public Staff(String name, String subject, int maxDailyPeriods) {
        this.name = name;
        this.subject = subject;
        this.isAbsent = false;
        this.assignedPeriodsToday = 0;
        this.maxDailyPeriods = maxDailyPeriods;
    }

    public String getName() { return name; }
    public String getSubject() { return subject; }
    public boolean isAbsent() { return isAbsent; }
    public void setAbsent(boolean absent) { isAbsent = absent; }
    public int getAssignedPeriodsToday() { return assignedPeriodsToday; }
    public void resetAssignedPeriods() { assignedPeriodsToday = 0; }
    public boolean canTakeMorePeriods() { return assignedPeriodsToday < maxDailyPeriods; }
    public void assignPeriod() { assignedPeriodsToday++; }

    @Override
    public String toString() {
        return subject + " (" + name + ")";
    }
}

class BreakPeriod {
    private double startHour;
    private double duration;

    public BreakPeriod(double startHour, double duration) {
        this.startHour = startHour;
        this.duration = duration;
    }

    public double getStartHour() { return startHour; }
    public double getEndHour() { return startHour + duration; }
    public boolean overlaps(double periodStart, double periodEnd) {
        return !(periodEnd <= startHour || periodStart >= getEndHour());
    }
}

class Timetable {
    private List<Staff> staffList;
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private double startHour, endHour;
    private double periodDuration;
    private String[][] schedule;
    private BreakPeriod[] breaks;

    public Timetable(List<Staff> staffList, double startHour, double endHour,
                     double periodDuration, BreakPeriod... breaks) {
        this.staffList = staffList;
        this.startHour = startHour;
        this.endHour = endHour;
        this.periodDuration = periodDuration;
        this.breaks = breaks;
    }

    private String formatTime(double hour) {
        int h = (int) hour;
        int m = (int) Math.round((hour - h) * 60);
        // Fix for rounding e.g. 14.999 => 15:00
        if (m == 60) {
            h++;
            m = 0;
        }
        return String.format("%02d:%02d", h, m);
    }

    public void generate() {
        int periods = (int) Math.ceil((endHour - startHour) / periodDuration);
        schedule = new String[days.length][periods];
        Random rand = new Random();

        for (int d = 0; d < days.length; d++) {
            // Reset assigned periods tracker per staff at start of each day
            for (Staff s : staffList) {
                s.resetAssignedPeriods();
            }

            Staff lastAssigned = null; // Prevent same teacher consecutive periods

            for (int p = 0; p < periods; p++) {
                double periodStart = startHour + p * periodDuration;
                double periodEnd = periodStart + periodDuration;

                // Check if current period overlaps any break
                boolean isBreak = false;
                for (BreakPeriod brk : breaks) {
                    if (brk.overlaps(periodStart, periodEnd)) {
                        schedule[d][p] = "BREAK";
                        lastAssigned = null; // Reset lastAssigned after break
                        isBreak = true;
                        break;
                    }
                }
                if (isBreak) continue;

                // Filter available staff who are not absent, not last assigned, and can take more periods
                List<Staff> availableStaff = new ArrayList<>();
                for (Staff s : staffList) {
                    if (!s.isAbsent() && s != lastAssigned && s.canTakeMorePeriods()) {
                        availableStaff.add(s);
                    }
                }

                // If no one available excluding lastAssigned, try allowing lastAssigned if allowed capacity
                if (availableStaff.isEmpty()) {
                    for (Staff s : staffList) {
                        if (!s.isAbsent() && s.canTakeMorePeriods()) availableStaff.add(s);
                    }
                }

                if (availableStaff.isEmpty()) {
                    schedule[d][p] = "No Staff";
                    lastAssigned = null;
                } else {
                    Staff s = availableStaff.get(rand.nextInt(availableStaff.size()));
                    schedule[d][p] = s.toString();
                    s.assignPeriod();
                    lastAssigned = s;
                }
            }
        }
        display(periods);
    }

    private void display(int periods) {
        System.out.println("\n================= Generated Timetable =================");

        // Header row
        System.out.print(String.format("%-12s", "Day/Time"));
        for (int p = 0; p < periods; p++) {
            double t = startHour + p * periodDuration;
            System.out.print(String.format("%-20s", formatTime(t) + "-" + formatTime(t + periodDuration)));
        }
        System.out.println();

        // Rows
        for (int d = 0; d < days.length; d++) {
            System.out.print(String.format("%-12s", days[d]));
            for (int p = 0; p < periods; p++) {
                System.out.print(String.format("%-20s", schedule[d][p]));
            }
            System.out.println();
        }
        System.out.println("======================================================");
    }
}

public class SmartTimetable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Staff> staffList = new ArrayList<>();

        System.out.print("Enter number of staff: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter staff name: ");
            String name = sc.nextLine();
            System.out.print("Enter subject: ");
            String subject = sc.nextLine();
            System.out.print("Enter max daily periods allowed for this staff: ");
            int maxPeriods = sc.nextInt();
            sc.nextLine();
            staffList.add(new Staff(name, subject, maxPeriods));
        }

        // Mark absent staff
        System.out.print("Enter number of absent staff: ");
        int absent = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < absent; i++) {
            System.out.print("Enter absent staff name: ");
            String absentName = sc.nextLine();
            for (Staff s : staffList) {
                if (s.getName().equalsIgnoreCase(absentName)) s.setAbsent(true);
            }
        }

        System.out.print("Enter start time (e.g., 9 or 9.5 for 9:30): ");
        double start = sc.nextDouble();
        System.out.print("Enter end time (e.g., 16 or 16.5 for 4:30): ");
        double end = sc.nextDouble();

        System.out.print("Enter first break start time (e.g., 13 or 13.5 for 1:30): ");
        double break1Start = sc.nextDouble();
        System.out.print("Enter first break duration (hours, e.g., 1 or 0.5): ");
        double break1Duration = sc.nextDouble();

        System.out.print("Enter second break start time (e.g., 15 or 15.5 for 3:30): ");
        double break2Start = sc.nextDouble();
        System.out.print("Enter second break duration (hours, e.g., 1 or 0.5): ");
        double break2Duration = sc.nextDouble();

        System.out.print("Enter period duration in hours (e.g., 1 or 0.5): ");
        double periodDuration = sc.nextDouble();

        BreakPeriod break1 = new BreakPeriod(break1Start, break1Duration);
        BreakPeriod break2 = new BreakPeriod(break2Start, break2Duration);

        Timetable tt = new Timetable(staffList, start, end, periodDuration, break1, break2);
        tt.generate();

        sc.close();
    }
}
