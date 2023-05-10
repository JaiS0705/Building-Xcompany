package com.crio.xcompany.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Company{
    private String companyName;
    private Employee founder;
    private Map<String,Employee> employeeBook;
    private Map<String,ArrayList<String>> managerMap;

    private Company(String companyName, Employee founder) {
        this.companyName = companyName;
        this.founder = founder;
        employeeBook = new HashMap<String,Employee>();
        employeeBook.put(founder.getName(), founder);

    }
    

    public static Company create(String companyName, Employee founder){
        return new Company(companyName,founder);
    } 


    public String getCompanyName() {
        return companyName;
    }

    public void registerEmployee(String employeeName,Gender gender)
    {
        if(!employeeBook.containsKey(employeeName))
        {
            Employee newEmployee = new Employee(employeeName,gender);
            employeeBook.put(employeeName,newEmployee);
        }
    }

    public Employee getEmployee(String employeeName)
    {
        if(employeeBook.containsKey(employeeName))
        {
            return employeeBook.get(employeeName);
        }
        return null;
    }

    public void deleteEmployee(String employeeName)
    {
        if(employeeBook.containsKey(employeeName))
        {
            employeeBook.remove(employeeName);
            System.out.println("EMPLOYEE_DELETION_SUCCEEDED");
        }
    }
    
    public void assignManager(String employeeName, String managerName)
    {
        if(managerMap == null)
        {
            managerMap = new HashMap<String,ArrayList<String>>();
        }
       
        if(!managerMap.containsKey(managerName))
        {
            managerMap.put(managerName,new ArrayList<String>());
        }
        managerMap.get(managerName).add(employeeName);
        employeeBook.get(employeeName).assignManager(managerName);
        
    }

    public List<Employee> getDirectReports(String managerName)
    {
        List<Employee> answer=new ArrayList<>();
        if(managerMap.containsKey(managerName))
        {
            for(int i=0;i<managerMap.get(managerName).size();i++)
            {
                answer.add(employeeBook.get(managerMap.get(managerName).get(i)));
            }
        }
        return answer;
    }

    public List<Employee> getTeamMates(String employeeName)
    {
        List<Employee> answer=new ArrayList<>();
        if(employeeBook.get(employeeName).getManager()!="")
        {
            answer.add(employeeBook.get(employeeBook.get(employeeName).getManager()));
            String manager=employeeBook.get(employeeName).getManager();
            for(int i=0;i<managerMap.get(manager).size();i++)
            {
                answer.add(employeeBook.get(managerMap.get(manager).get(i)));
            }
        }
        return answer;
    }

    public List<List<Employee>> getEmployeeHierarchy(String employeeName) {
        List<List<Employee>> hierarchy = new ArrayList<>();
        Queue<Employee> queue = new LinkedList<>();
        queue.add(employeeBook.get(employeeName));
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Employee> sub = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Employee e = queue.remove();
                sub.add(e);
                List<Employee> subOrdinates = getDirectReports(e.getName());
                for (int j = 0; j < subOrdinates.size(); j++) {
                    queue.add(subOrdinates.get(j));
                }
            }
            hierarchy.add(sub);
        }
        return hierarchy;
    }
    




    // TODO: CRIO_TASK_MODULE_XCOMPANY
    // Please define all the methods required here as mentioned in the XCompany BuildOut Milestone for each functionality before implementing the logic.
    // This will ensure that the project can be compiled successfully.

}
