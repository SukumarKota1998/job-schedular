package airtribe.job_schedular.queue;

import airtribe.job_schedular.worker.JobExecutionEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class JobExecutionQueue {

    private final BlockingQueue<JobExecutionEvent> queue =
            new LinkedBlockingQueue<>();

    public void publish(JobExecutionEvent event) {
        queue.offer(event);
    }

    public JobExecutionEvent take() throws InterruptedException {
        return queue.take();
    }
}
