package com.rajesh.stocky.v1.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages={"com.rajesh.stocky.v1.entity"})
@EnableJpaRepositories(basePackages={"com.rajesh.stocky.v1.repository"})
@EnableJpaAuditing
public class PersistanceConfig {}
