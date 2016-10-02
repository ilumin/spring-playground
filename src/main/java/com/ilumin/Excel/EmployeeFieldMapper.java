package com.ilumin.Excel;

import com.ilumin.Employee.Employee;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component("employeeFieldMapper")
public class EmployeeFieldMapper implements FieldSetMapper<Employee> {

    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        Employee emp = new Employee();

        emp.setEmployeeId(fieldSet.readLong(0));
        emp.setLastName(fieldSet.readString(1));
        emp.setFirstName(fieldSet.readString(2));
        emp.setTitle(fieldSet.readString(3));
        emp.setTitleOfCourtesy(fieldSet.readString(4));
        emp.setBirthDate(DateUtils.toCalendar(fieldSet.readDate(5)));
        emp.setHireDate(DateUtils.toCalendar(fieldSet.readDate(6)));
        emp.setAddress(fieldSet.readString(7));
        emp.setCity(fieldSet.readString(8));
        emp.setRegion(fieldSet.readString(9));
        emp.setPostalCode(fieldSet.readString(10));
        emp.setCountry(fieldSet.readString(11));
        emp.setHomePhone(fieldSet.readString(12));
        emp.setExtension(fieldSet.readString(13));
        emp.setPhoto(fieldSet.readString(14));
        emp.setNotes(fieldSet.readString(15));

        return emp;
    }

}
