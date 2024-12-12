package com.example.demo.repository.configurationRepository;

import com.example.demo.model.configuration.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface configurationRepository extends JpaRepository<Configuration, Long> {
    // Can be implemented later when implementing a database
}
