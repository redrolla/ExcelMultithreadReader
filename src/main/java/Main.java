import excel.ExcelReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello");

        ExcelReader excelReader = new ExcelReader();
        excelReader.readFile();
    }
}
