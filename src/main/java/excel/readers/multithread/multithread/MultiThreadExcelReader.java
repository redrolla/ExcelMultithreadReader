package excel.readers.multithread.multithread;

import data.EmployeeData;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiThreadExcelReader {

    private static final int ITEMS_IN_PARTITION = 100;

    /**
     *
     * 1. do partition to list -> make 10 lists from 1
     * 2. map each of 10 lists in separate Thread -> 10 mapped lists
     * 3. collect 10 mapped list to 1 mapped list
     */
    @SneakyThrows
    public List<EmployeeData> getExcelData(List<Row> rows) throws InterruptedException {
        List<List<Row>> partitions = doPartition(rows);

        //prepare list before multiThread
        List<List<EmployeeData>> employeeDataPartitions = new ArrayList<>();
        for (int i = 0; i < partitions.size(); i++) {
            employeeDataPartitions.add(new ArrayList<>());
        }

        //multiThread the list
        for (int i = 0; i < partitions.size(); i++) {
            List<EmployeeData> employeeDataList = new ExcelReaderThread(partitions.get(i),
                    employeeDataPartitions.get(i),
                    i)
                    .call();
            employeeDataPartitions.get(i).addAll(employeeDataList);
        }

        return employeeDataPartitions.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<List<Row>> doPartition(List<Row> rows) {
        int numberOfPartitions = (int) Math.ceil((double) (rows.size()) / ITEMS_IN_PARTITION);
        List<List<Row>> partitions = new ArrayList<>();
        for (int i = 0; i < numberOfPartitions; i++) {
            partitions.add(new ArrayList<>());
        }

        int i = 0;
        int partitionNumber = 1;
        while (i < rows.size()) {
            partitions.get(partitionNumber - 1).add(rows.get(i));
            i++;
            if(partitionNumber * ITEMS_IN_PARTITION <= i) {
                partitionNumber++;
            }
        }

        return partitions;
    }
}
