import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.file.*;
import java.util.stream.*;
import java.nio.charset.Charset;


class Log {
    public String hostname;
    public String time;
    public String request;
    public String response;

    public Log(String hostname, String time, String request_, String response_) {
        this.hostname = hostname;
        this.time = time;
        request = request_;
        response = response_;


    }

    public static Log buildFromArray(String[] elementos) {
        return new Log (elementos[0],elementos[3],elementos[6], elementos[8]);
    }
    public String getFileName() {
        int index = request.lastIndexOf("/");
        return request.substring(index + 1);
    }

    public static String getFName(String request) {
        int index = request.lastIndexOf("/");
        return request.substring(index + 1);
    }

    public static boolean isGif(String request) {

        int index1 = request.lastIndexOf("/");
        String filename = request.substring(index1 + 1);
        int index = filename.lastIndexOf(".");
        String extension = filename.substring(index + 1);
        return extension.equals("gif") || extension.equals("GIF");
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(request, log.request);
    }

}

public class Solution2 {
    private static final Scanner scan = new Scanner(System.in);


    public static void main(String args[]) throws Exception {
        // read the string filename
        String filename;
        filename = scan.nextLine();
        Path path = Paths.get(filename);

        Set<Log> logs = new HashSet<>();

        List<String> todo = new ArrayList<>();

            try (Stream<String> stream = Files.lines(Paths.get(filename))) {

           /* logs = stream.map(linea -> linea.split(" "))
                    .map(Log::buildFromArray)
                    .filter(log -> log.isGif())
                    .filter(log -> log.response.equals("200"))
                    .onClose(() -> System.out.println("termino")).
                    collect(Collectors.toSet());
*/

            todo =    stream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Stream<Log> lines = logs.stream();

        List<String> str = todo.stream()
                .filter(l -> l.split(" ")[8].equals("200"))
                .filter(l -> Log.isGif(l.split(" ")[6]))
                .collect(Collectors.toList());

        System.out.println("Cantidad de lineas " + str.size());
        Set<String> gifs = new HashSet<String >();
        for (String prueba:
                str) {
            gifs.add(Log.getFName(prueba.split(" ")[6]));
        }

        System.out.println("Cantidad de lineas " + gifs.size());

        Path pathOut = Paths.get("gifs_hosts_access_log_00.txt");
        try (BufferedWriter br = Files.newBufferedWriter(pathOut,
                Charset.defaultCharset(), StandardOpenOption.CREATE)) {
            //gifs.forEach((s) -> {
            try {
                System.out.println("Cantidad de lineas " + gifs.size());
                for (String s : gifs) {

                    System.out.println("escribiendo " + s);
                    br.write(s);
                    br.newLine();
                }

            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            // } );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}