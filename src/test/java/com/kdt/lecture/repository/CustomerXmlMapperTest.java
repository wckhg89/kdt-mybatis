package com.kdt.lecture.repository;

import com.kdt.lecture.repository.domain.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerXmlMapperTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerXmlMapper customerXmlMapper;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("TRUNCATE TABLE customers");
    }


    @Test
    void 고객정보가_저장되는지_확인한다() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");

        // When
        customerXmlMapper.save(customer);

        // Then
        Customer guppy = customerXmlMapper.findById(1);
        assertThat(guppy.getFirstName()).isEqualTo("honggu");
    }

    @Test
    void 고객정보가_수정되는지_확인한다() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");
        customerXmlMapper.save(customer);
        customer.setFirstName("guppy");
        customer.setLastName("hong");

        // When
        customerXmlMapper.update(customer);

        // Then
        Customer updated = customerXmlMapper.findById(1);
        assertThat(updated.getLastName()).isEqualTo(customer.getLastName());
        assertThat(updated.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void 단건조회를_확인한다() {
        // Given
        Customer customer = new Customer(1L, "honggu", "kang");
        customerXmlMapper.save(customer);

        // When
        Customer selected = customerXmlMapper.findById(customer.getId());

        // Then
        assertThat(customer.getId()).isEqualTo(selected.getId());
    }

    @Test
    void 리스트조회를_확인한다() {
        // Given
        Customer customer1 = new Customer(1L, "honggu", "kang");
        Customer customer2 = new Customer(2L, "guppy", "hong");

        customerXmlMapper.save(customer1);
        customerXmlMapper.save(customer2);

        // When
        List<Customer> selectedCustomers = customerXmlMapper.findAll();

        // Then
        assertThat(selectedCustomers.size()).isEqualTo(2);
    }
}