package tetris;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CpuTimeTracker {
    private final ThreadMXBean bean;
    private long startTime;

    public CpuTimeTracker() {
        this.bean = ManagementFactory.getThreadMXBean();
        if (!this.bean.isThreadCpuTimeSupported()) {
            throw new UnsupportedOperationException("CPU time measurement is not supported on this JVM.");
        }
        this.bean.setThreadCpuTimeEnabled(true);
    }

    public void start() {
        this.startTime = this.getTotalCpuTime();
    }

    public long stop() {
        long endTime = this.getTotalCpuTime();
        return endTime - this.startTime; // returns nanoseconds
    }

    private long getTotalCpuTime() {
        long total = 0;
        for (long id : this.bean.getAllThreadIds()) {
            long time = this.bean.getThreadCpuTime(id);
            if (time != -1) {
                total += time;
            }
        }
        return total;
    }

    public double estimateWatts(double cpuSeconds) {
        // Assume average CPU power draw (adjust for your machine)
        double averageCpuPowerWatts = 45.0; // Example: laptop CPU under moderate load
        return averageCpuPowerWatts * cpuSeconds;
    }

    public void outputEstimate(){
        long cpuTimeNanosecs = this.stop();
        double cpuTimeSeconds = cpuTimeNanosecs / 1_000_000_000.0;
        double watts = this.estimateWatts(cpuTimeSeconds);

        System.out.printf("CPU Time: %.3f seconds\n", cpuTimeSeconds);
        System.out.printf("Estimated Energy Usage: %.3f watt seconds/joules\n", watts);
    }
}
