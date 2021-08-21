package com.kdt.lecture.repository;

import com.kdt.lecture.repository.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerXmlMapper {
    void save(Customer customer);
    void update(Customer customer);
    Customer findById(long id);
    List<Customer> findAll();
}
