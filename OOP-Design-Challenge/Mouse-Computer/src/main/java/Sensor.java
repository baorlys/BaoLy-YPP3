import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Sensor {
    String sensorName;
    int timeResponse;
    public Sensor(String sensorName, int timeResponse) {
        this.sensorName = sensorName;
        this.timeResponse = timeResponse;
    }

    public String getName() {
        return sensorName;
    }

    public void detecting() {
        // Simulate detecting
        Random random = new Random();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int x = random.nextInt(100);
                int y = random.nextInt(100);
                System.out.println("Detected at: (" + x + ", " + y + ")");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, timeResponse, 1000);  // Delay and period both set to 1000 millisecond
    }
}
