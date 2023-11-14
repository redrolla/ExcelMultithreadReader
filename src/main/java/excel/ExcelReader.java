package excel;

import data.EmployeeData;
import excel.readers.multithread.multithread.MultiThreadExcelReader;
import excel.readers.singlethread.SingleThreadExcelReader;
import excel.readers.multithread.parallelstream.ParallelStreamExcelReader;
import excel.readers.multithread.executorservice.ExecutorServiceExcelReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    String fileLocation = "C:\\Users\\Professional\\IdeaProjects\\ExcelMultithreadReader";
    String fileName = "employees with salaries large.xlsx";
            //"employees with salaries.xlsx";

    public void readFile() throws IOException, InterruptedException {
        File file = new File(fileLocation + "\\" + fileName);

        System.out.println(file.getName());

        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Row> rows = getRowsFromSheet(sheet);
        SingleThreadExcelReader singleThreadExcelReader = new SingleThreadExcelReader();
        long startTime = System.currentTimeMillis();
        List<EmployeeData> employeeData = singleThreadExcelReader.getExcelData(rows);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        countTotalSalary(employeeData);

        long startTimeM1 = System.currentTimeMillis();
        MultiThreadExcelReader multiThreadExcelReader = new MultiThreadExcelReader();
        List<EmployeeData> employeeDataMultiThread = multiThreadExcelReader.getExcelData(rows);
        long endTimeM1 = System.currentTimeMillis();
        System.out.println("MultiThread took " + (endTimeM1 - startTimeM1) + " milliseconds");

        countTotalSalary(employeeDataMultiThread);

        long startTimeM2 = System.currentTimeMillis();
        ParallelStreamExcelReader parallelStreamExcelReader = new ParallelStreamExcelReader();
        List<EmployeeData> employeeDataParallelStream = parallelStreamExcelReader.getExcelData(rows);
        long endTimeM2 = System.currentTimeMillis();
        System.out.println("ParallelStream took " + (endTimeM2 - startTimeM2) + " milliseconds");

        countTotalSalary(employeeDataParallelStream);

        long startTimeES = System.currentTimeMillis();
        ExecutorServiceExcelReader executorServiceExcelReader = new ExecutorServiceExcelReader();
        List<EmployeeData> employeeDataExecutorService = executorServiceExcelReader.getExcelData(rows);
        long endTimeES = System.currentTimeMillis();
        System.out.println("ExecutorService took " + (endTimeES - startTimeES) + " milliseconds");

        countTotalSalary(employeeDataExecutorService);

        fileInputStream.close();
        System.out.println("Fin.");
    }

    private ArrayList<Row> getRowsFromSheet(Sheet sheet) {
        ArrayList<Row> rows = new ArrayList<>();
        for (Row row : sheet) {
            rows.add(row);
        }
        return rows;
    }

    private void countTotalSalary(List<EmployeeData> employeeData) {
        int totalSalary = employeeData.stream()
                .map(EmployeeData::getSalary)
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Total salary is " + totalSalary);
    }
}
