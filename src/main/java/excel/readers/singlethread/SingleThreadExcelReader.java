package excel.readers.singlethread;

import data.EmployeeData;
import excel.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadExcelReader {

    public List<EmployeeData> getExcelData(List<Row> rows) {
        List<EmployeeData> employeeData = new ArrayList<>();
        for (Row row : rows) {
            EmployeeData currentEmployee = ExcelUtils.mapRowToData(row);
            employeeData.add(currentEmployee);
        }
        return employeeData;
    }
}
