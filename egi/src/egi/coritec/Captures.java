// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.coritec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Reads the real POST bodies saved in capturas/coritec-2026-11-03.tsv
public class Captures {

    public static final Path FILE = Path.of("capturas", "coritec-2026-11-03.tsv");

    // n: capture number (1..13)
    public record Capture(int n, String body) {}

    public static List<Capture> load() throws IOException {
        List<Capture> list = new ArrayList<>();
        for (String line : Files.readAllLines(FILE)) {
            if (line.isBlank() || line.startsWith("#")) {
                continue;
            }
            String[] cols = line.split("\t", 2);
            list.add(new Capture(Integer.parseInt(cols[0]), cols[1]));
        }
        return list;
    }
}
