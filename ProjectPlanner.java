import java.util.ArrayList;
import java.util.List;

public class ProjectPlanner {
    private static class Result {
        int maxIncome;
        List<String> schedule;

        Result(int maxIncome, List<String> schedule) {
            this.maxIncome = maxIncome;
            this.schedule = schedule;
        }
    }

    public static Result bestIncome(int[] standardProj, int[] intensiveProj) {
        int n = standardProj.length;
        int[] opt = new int[n + 1];
        boolean[] takeIntensive = new boolean[n + 1];
        List<String> schedule = new ArrayList<>();

        // Base cases
        opt[0] = 0; // Implicitly done by array initialization in Java
        opt[1] = Math.max(standardProj[0], intensiveProj[0]);
        takeIntensive[1] = intensiveProj[0] > standardProj[0];

        // Fill the dynamic programming table
        for (int i = 2; i <= n; i++) {
            int standTotal = standardProj[i - 1] + opt[i - 1];
            int intenTotal = intensiveProj[i - 1] + opt[i - 2]; // i - 2 because we need to take a break
            opt[i] = Math.max(standTotal, intenTotal);

            takeIntensive[i] = intenTotal > standTotal;
        }

        // Reconstruct the schedule
        for (int i = n; i > 0;) {
            if (takeIntensive[i]) {
                schedule.add(0, "Intensive");
                schedule.add(0, "Break");
                i -= 2;
            } else {
                schedule.add(0, "Standard");
                i -= 1;
            }
        }

        return new Result(opt[n], schedule);
    }

    public static void main(String[] args) {
        int[] standard = { 26, 21, 16, 23, 16, 21, 25, 4, 25, 6 };
        int[] intensive = { 10, 32, 40, 42, 13, 16, 8, 42, 41, 48 };
        Result result = bestIncome(standard, intensive);
        System.out.println("Max Income: " + result.maxIncome);
        System.out.println("Schedule: " + result.schedule);
    }
}
