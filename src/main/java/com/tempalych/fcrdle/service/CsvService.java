package com.tempalych.fcrdle.service;

import java.io.FileNotFoundException;

public interface CsvService {
    void loadFromCsv() throws FileNotFoundException;
    void updateCsvFile();
}
