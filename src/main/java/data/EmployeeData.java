package data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EmployeeData {
    private int index;
    private String name;
    private String surname;
    private int salary;
    private String uuid;
}
