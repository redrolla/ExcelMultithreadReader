package excel.readers.multithread.parallelstream;

import data.EmployeeData;
import excel.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamExcelReader {
    public List<EmployeeData> getExcelData(List<Row> rows) {
        return rows.parallelStream()
                .map(ExcelUtils::mapRowToData)
                .collect(Collectors.toList());
    }
}
