package com.tempalych.fcrdle.server.service;

import java.io.FileNotFoundException;

public interface CsvService {
    void loadFromCsv() throws FileNotFoundException;
    void updateCsvFile();
}
