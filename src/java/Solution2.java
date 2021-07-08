// you can also use imports, for example:
import java.util.*;


// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");
class Solution2 {

    List<String> musicExtensions = Arrays.asList("mp3", "aac", "flac");
    List<String> imageExtensions = Arrays.asList("jpg", "bmp", "gif");
    List<String> movieExtensions = Arrays.asList("mp4", "avi", "mkv");

    public String solution(String S) {
        String splitByRow[] = S.split("\\n");
        for(String file: splitByRow) {
            String splitRow[] = file.split(" ");
            String fileType = getTypeFile(splitByRow[0]);
            System.out.println(fileType);
        }
        return ";";

    }

    public String getTypeFile (String fileName) {
        String extension = getExtension(fileName).get();
        if(musicExtensions.contains(extension)) {
            return "music";
        }
        if(imageExtensions.contains(extension)) {
            return "image";
        }
        if(movieExtensions.contains(extension)) {
            return "movie";
        } else {
            return "other";
        }
    }

    public Optional<String> getExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f->f.contains("."))
                .map(f->f.substring(fileName.lastIndexOf(".") + 1));
    }

}
