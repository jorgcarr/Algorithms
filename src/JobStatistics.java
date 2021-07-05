import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

class JobStatistics {
    public Map<String, Long> findJobStatistics(String[] lines) {
        List<Job> jobs = Arrays.stream(lines)
                .map(Job::StringToJob)
                .collect(Collectors.toList());

        Map<String, Job> jobStatistics = jobs.stream().collect(toMap(Job::getJobId, Function.identity(), mergeJobs));

        Map<String, Long> jobLogsResult = jobStatistics.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(job -> Objects.nonNull(job.getTime()))
                .collect(toMap(Job::getName, Job::getTime, (ins, old) -> ins > old ? ins: old));

        return jobLogsResult;
    }

    private static final BinaryOperator<Job> mergeJobs = (prev, ins) -> {

        if (Objects.nonNull(ins.getTime()))
            prev.setTime(ins.getTime());

        if(Objects.nonNull(ins.getName()))
            prev.setName(ins.getName());

        return prev;
    };
}

class Job {
    private String jobId;

    private String name;
    private Long time;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public static Job StringToJob(String line) {
        Job job = new Job();

        for(String value : line.split(" ")) {
            if(value.contains("jobid=")){
                job.setJobId(value.split("=")[1]);
            }else if(value.contains("name=")){
                job.setName(value.split("=")[1]);
            } else if(value.contains("time=")){
                job.setTime(Long.valueOf(value.split("=")[1]));
            }
        }



        return job;
    }
}

class JobStatisticsTest{
    public static void main(String[] args){
        test1();
    }

    @Test
    public static void test1(){
        String[] lines = {
                "Started name=dump_logs jobid=f863",
                "Started name=dump_logs jobid=g301gas",
                "  ",
                "Ended jobid=f863 time=1021",
                "Started name=grep_logs jobid=ac3de",
                "Ended jobid=g301gas time=1343",
                "Started name=read_logs jobid=r0eas",
                "  ",
                "Started name=write_logs jobid=dg2dz",
                "Ended jobid=r0eas time=103",
                "Ended jobid=ac3de time=52"};

        JobStatistics jobStatistics = new JobStatistics();
        Map<String, Long> statistics = jobStatistics.findJobStatistics(lines);

        statistics.entrySet().forEach(entry -> System.out.println(entry.getKey()+"  "+entry.getValue()));
        assertEquals(Long.valueOf(1343), statistics.get("dump_logs"));
    }
}