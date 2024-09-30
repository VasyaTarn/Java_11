package com.example.Java_11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CafeDbInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTables()
    {
        List<String> strings;
        try
        {
            strings = Files.readAllLines(Paths.get("schema.sql"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        StringBuilder createTablesQuery = new StringBuilder();

        for (var currentString : strings)
        {
            createTablesQuery.append(currentString);
            createTablesQuery.append("\n");
        }

        String resStr = createTablesQuery.toString();

        jdbcTemplate.execute(resStr);
    }
}
