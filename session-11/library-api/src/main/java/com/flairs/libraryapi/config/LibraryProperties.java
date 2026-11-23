package com.flairs.libraryapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "library")
public record LibraryProperties(int maxActiveLoans, int loanDays) {
}