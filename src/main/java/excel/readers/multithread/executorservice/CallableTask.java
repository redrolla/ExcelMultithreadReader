package excel.readers.multithread.executorservice;

import data.EmployeeData;
import excel.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<EmployeeData> {
    private final Row row;

    public CallableTask(Row row) {
        this.row = row;
    }

    @Override
    public EmployeeData call() throws Exception {
        return ExcelUtils.mapRowToData(row);
    }
}
