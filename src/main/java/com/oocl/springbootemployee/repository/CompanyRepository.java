package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.CompanyVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.oocl.springbootemployee.constant.EmployeeConstant.ONE;

@Repository
public class CompanyRepository {

    private int sequence = ONE;

    private final List<Company> companies = new ArrayList<>();

    public List<CompanyVO> getAll() {
        return companies.stream()
                .map(company -> new CompanyVO(company.getId(), company.getName()))
                .toList();
    }

    public void createCompany(Company company) {
        company.setId(sequence);
        sequence += ONE;
        companies.add(company);
    }

    public void resetSequence() {
        sequence = ONE;
    }

    public void clearAll() {
        companies.clear();
    }
}
