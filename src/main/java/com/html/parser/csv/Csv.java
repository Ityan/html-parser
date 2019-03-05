package com.html.parser.csv;

import com.html.parser.Node;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Csv {
    public void write(List<Node> nodes) throws IOException {
        String filename = String.format(
            "%s.csv",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"))
        );

        try (CSVPrinter csvPrinter = new CSVPrinter(
            Files.newBufferedWriter(new File(filename).toPath()),
            CSVFormat.DEFAULT
            .withHeader("Price", "URL"))) {

            for (Node node : nodes) {
                csvPrinter.printRecord(node.getPrice(), node.getUrl());
            }

            csvPrinter.flush();
        }
    }
}
