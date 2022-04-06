package domain.services;

public class MetricsMonitoringServiceWrapper implements Runnable {

    private final MetricsMonitoringService monitoringService;

    public MetricsMonitoringServiceWrapper(MetricsMonitoringService monitor) {
        this.monitoringService = monitor;
    }

    @Override
    public void run() {
        monitoringService.start();
    }

}
