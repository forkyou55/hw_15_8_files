package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class ZipTest {

    ClassLoader classLoader = ZipTest.class.getClassLoader();
    String zipArhive = "TestFile.zip";
    String zipPath = "src/test/resources/";
    String xlsFileName = "RaceLOTR.xls";
    String pdfFileName = "Maps.pdf";
    String csvFileName = "RaceLotrCSV.csv";

    @Test
    @DisplayName("Reading pdf from zip archive")
    void readPdfFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipArhive);
        assert is != null;
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile(new File(zipPath + zipArhive));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(pdfFileName)) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(stream);
                    assertThat(pdf.text).contains("Карта Нуменора");
                }
            }
        }
    }

    @Test
    @DisplayName("Reading xls from zip archive")
    void readXlsFromZip() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipArhive);
        assert is != null;
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile(new File(zipPath + zipArhive));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(xlsFileName)) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS(stream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(3)
                            .getCell(3)
                            .getStringCellValue())
                            .contains("Melkor");
                }
            }
        }
    }

    @Test
    @DisplayName("Reading cvs from zip archive")
    void testZipCsv() throws Exception {
        InputStream is = classLoader.getResourceAsStream(zipArhive);
        assert is != null;
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile zfile = new ZipFile(new File(zipPath + zipArhive));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(csvFileName)) {
                try (InputStream stream = zfile.getInputStream(entry);
                     CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                    List<String[]> csv = reader.readAll();
                    assertThat(csv).contains(
                            new String[]{"Eru Ilúvatar", "Male", "Eru Ilúvatar", "", ""},
                            new String[]{"Elves", "Female", "Galadriel", "", ""}
                    );
                }
            }
        }
    }

}