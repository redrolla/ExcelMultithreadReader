package excel.readers.multithread.multithread;

import data.EmployeeData;
import excel.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ExcelReaderThread implements Callable<List<EmployeeData>> {
    private List<Row> rowPartition;
    private List<EmployeeData> employeeDataPartition;
    private int partitionNumber;

    public ExcelReaderThread(List<Row> rowPartition, List<EmployeeData> employeeDataPartition, int partitionNumber){
        this.rowPartition = rowPartition;
        this.employeeDataPartition = employeeDataPartition;
        this.partitionNumber = partitionNumber;
    }

    //@Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " for partition " + partitionNumber + " running");
        employeeDataPartition = rowPartition.stream()
                .map(ExcelUtils::mapRowToData)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeData> call() throws Exception {
        //System.out.println("Thread " + Thread.currentThread().getName() + " for partition " + partitionNumber + " running");
        return rowPartition.stream()
                .map(ExcelUtils::mapRowToData)
                .collect(Collectors.toList());
    }
}
