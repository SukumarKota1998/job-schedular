package airtribe.job_schedular.utills;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.cronutils.model.CronType.QUARTZ;
import static com.cronutils.model.CronType.UNIX;

public class CommonUtils {

    public static LocalDateTime getNextExecutionTime(String cronExpression) {
        CronExpression cron = CronExpression.parse(cronExpression);

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedNow = now.atZone(java.time.ZoneId.systemDefault());

        // Get next execution time
        ZonedDateTime next = cron.next(zonedNow);
        assert next != null;
        LocalDateTime nextExecution = next.toLocalDateTime();

        System.out.println("Current time: " + now);
        System.out.println("Next execution: " + nextExecution);

        return nextExecution;
    }
}
