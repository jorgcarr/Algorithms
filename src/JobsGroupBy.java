import java.util.*;

public class JobsGroupBy {

    public static void main(String[] args) {
        String logs = "Started name=dump_logs jobid=f863\n" +
                "Started name=dump_logs jobid=g301gas\n" +
                "Ended jobid=f863 time=1021\n" +
                "Started name=grep_logs jobid=ac3de\n" +
                "Ended jobid=g301gas time=1343\n" +
                "Started name=read_logs jobid=r0eas\n" +
                "Started name=write_logs jobid=dg2dz\n" +
                "Ended jobid=r0eas time=103\n" +
                "Ended jobid=ac3de time=52\n" +
                "Ended jobid=dg2dz time=530";

        String[] logsArray = logs.split("\n");

        Map<String, Integer> jobNameMaxTime = new HashMap<>();
        Map<String, Set<String>> joNameIds = new HashMap<>();


        for (String jobLine: logsArray) {
            String[] jobProperties = getProperties(jobLine);
            if(jobLine.startsWith("Started")) {

                String name = getProperty(1, jobProperties);
                String jobId = getProperty(2, jobProperties);
                if(joNameIds.containsKey(name)) {
                    Set<String> idsList = joNameIds.get(name);
                    idsList.add(jobId);
                } else {
                    Set<String> ids = new HashSet<>();
                    ids.add(jobId);
                    joNameIds.put(name, ids);
                }
            }
            if(jobLine.startsWith("Ended")) {
                String jobId = getProperty(1, jobProperties);
                Integer currentTime = Integer.parseInt(getProperty(2, jobProperties));

                String name = getJobNameById(jobId, joNameIds);

                if(jobNameMaxTime.containsKey(name)) {
                    Integer timeInt = jobNameMaxTime.get(name);
                    if(currentTime > timeInt) {
                        jobNameMaxTime.replace(name, currentTime);
                    }
                } else {
                    jobNameMaxTime.put(name, currentTime);
                }


            }
        }
        System.out.println(jobNameMaxTime);

    }

    private static String getJobNameById(String id, Map<String, Set<String>> jobNameIds) {
        for (String name:
                jobNameIds.keySet()) {
            Set<String> ids = jobNameIds.get(name) ;
            if(ids.contains(id)) {
                return name;
            }
        }
        throw new IllegalArgumentException();
    }

    public static String getProperty(int i, String[] jobProperties) {
        return jobProperties[i].split("=")[1];
    }

    public static String[] getProperties(String jobLine) {
        return  jobLine.split(" ");
    }
}
