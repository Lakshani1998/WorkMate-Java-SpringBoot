package com.example.WorkMate.services;

import com.example.WorkMate.dto.EmployeeDTO;
import com.example.WorkMate.entity.Employee;
import com.example.WorkMate.repo.EmployeeRepo;
import com.example.WorkMate.util.VarList;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Var;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String saveEmployee(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpId())){
            return VarList.RSP_DUPLICATED;
        }else{
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return VarList.RSP_SUCESS;
        }
    }
    public String updateEmployee(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpId())){
            employeeRepo.save(modelMapper.map(employeeDTO,Employee.class));
            return VarList.RSP_SUCESS;
        }else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List <EmployeeDTO> getAllEmployee(){
        List <Employee> employeeList = employeeRepo.findAll();
        return  modelMapper.map(employeeList, new TypeToken<ArrayList<EmployeeDTO>>(){}.getType());
    }

    public EmployeeDTO searchEmployee(int empId){
        if(employeeRepo.existsById(empId)){
           Employee employee = employeeRepo.findById(empId).orElse(null);
           return modelMapper.map(employee,EmployeeDTO.class);
        }else{
            return null;
        }
    }

    public String deleteEmployee(int empID){
        if(employeeRepo.existsById(empID)){
            employeeRepo.deleteById(empID);
            return VarList.RSP_SUCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
