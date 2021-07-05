import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobsGroupByTest {

    @Test
    public void getPropertyTest() {

        String jobLine = "Started name=dump_logs jobid=f863";
        String[] jobProperties = JobsGroupBy.getProperties(jobLine);
        Assert.assertEquals("dump_logs", JobsGroupBy.getProperty(1, jobProperties));
    }

}