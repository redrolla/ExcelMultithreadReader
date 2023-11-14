package excel.readers.multithread.executorservice;

import data.EmployeeData;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExcelReader {

    @SneakyThrows
    public List<EmployeeData> getExcelData(List<Row> rows) throws InterruptedException {
        List<CallableTask> callableTasks = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (Row row : rows) {
            CallableTask callableTask = new CallableTask(row);
            callableTasks.add(callableTask);
        }

        List<Future<EmployeeData>> employeeDataFutures = executor.invokeAll(callableTasks);

        List<EmployeeData> employeeDataList = new ArrayList<>();
        for(Future<EmployeeData> future : employeeDataFutures) {
            employeeDataList.add(future.get());
        }

        executor.shutdown();

        return employeeDataList;
    }
}
