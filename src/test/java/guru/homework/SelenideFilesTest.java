package guru.homework;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFilesTest {

    private static final String
            //names with testzip because it's a feature of zip compressor in Mac OSX
            pdf = "testzip/schedule.pdf",
            xlsx = "testzip/music.xlsx",
            csv = "testzip/mail.csv";

    @Test
    void selenideZipTest() throws Exception {
        ZipFile testZip = new ZipFile("src/test/resources/testzip.zip");
        Enumeration<? extends ZipEntry> entries = testZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().contains("pdf")) {
                assertThat(entry.getName()).isEqualTo(pdf);
                parsePdfFile(testZip.getInputStream(entry));
            } else if (entry.getName().contains("xlsx")) {
                assertThat(entry.getName()).isEqualTo(xlsx);
                parseXlsxFile(testZip.getInputStream(entry));
            } else if (entry.getName().contains("csv")) {
                assertThat(entry.getName()).isEqualTo(csv);
                parseCsvFile(testZip.getInputStream(entry));
            }
        }
    }

    private void parsePdfFile(InputStream file) throws IOException {
        PDF pdf = new PDF(file);
        assertThat(pdf.text).contains(
                "Селищев Валерий Анатольевич"
        );
    }

    private void parseXlsxFile(InputStream file) throws IOException {
        XLS xlsx = new XLS(file);
        assertThat(xlsx.excel
                .getSheetAt(0)
                .getRow(1)
                .getCell(1)
                .getNumericCellValue()).isEqualTo(40);
    }

    private void parseCsvFile(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file));) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(0)).contains(
                    "Login email;Identifier;First name;Last name"
            );
        }
    }
}