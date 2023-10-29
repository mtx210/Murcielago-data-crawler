package com.chadsoft.murci.tasks;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoadStatistics {

    private int validRecordsSaved = 0;
    private int invalidRecordsSaved = 0;
    private int validRecordsSkipped = 0;
    private int invalidRecordsSkipped = 0;

    public void incrementValidRecordsLoaded() {
        validRecordsSaved++;
    }

    public void incrementInvalidRecordsLoaded() {
        invalidRecordsSaved++;
    }

    public void incrementValidRecordsSkipped() {
        validRecordsSkipped++;
    }

    public void incrementInvalidRecordsSkipped() {
        invalidRecordsSkipped++;
    }
}
