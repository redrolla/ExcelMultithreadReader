package excel;

import data.EmployeeData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

public class ExcelUtils {
    public static String getCellDataAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                throw new RuntimeException("Unexpected excel cell type");
        }
    }

    public static String getStringCell(Cell cell) {
        return cell.getStringCellValue();
    }

    public static Double getNumericCell(Cell cell) {
        return cell.getNumericCellValue();
    }


    public static EmployeeData mapRowToData(Row row) {
        Iterator iterator = row.cellIterator();
        int index = ExcelUtils.getNumericCell((Cell)iterator.next()).intValue();
        String name = ExcelUtils.getStringCell((Cell)iterator.next());
        String surname = ExcelUtils.getStringCell((Cell)iterator.next());
        int salary = ExcelUtils.getNumericCell((Cell)iterator.next()).intValue();
        String uuid = ExcelUtils.getStringCell((Cell)iterator.next());
        return new EmployeeData(index, name, surname, salary, uuid);
    }
}
