package org.munmanagerthymeleaf.databaseManager;

import org.munmanagerthymeleaf.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

@Service
public class NullCheck {

    private final DataService dataService;
    private final Logger logger;

    @Autowired
    public NullCheck(DataService dataService) {
        this.dataService = dataService;
        this.logger = Logger.getLogger("NullCheck");
    }

    public void checkForNulls() {
        checkNullValues(dataService.getAssignments(), "Assignment");
        checkNullValues(dataService.getConferences(), "Conference");
        checkNullValues(dataService.getStudents(), "Student");
        checkNullValues(dataService.getStudentAssignments(), "Student Assignment");
        checkNullValues(dataService.getStudentConferences(), "Student Conference");
    }

    // using reflection to keep the code dynamic, even though it's slower and more complicated
    private <T> void checkNullValues(List<T> dataList, String dataTypeName) {
        for (T dataItem : dataList) {
            for (Field field : dataItem.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(dataItem);
                    if (value == null) {
                        logger.warning(dataTypeName + " " + field.getName() + " is null for: " + dataItem);
                    }
                } catch (IllegalAccessException e) {
                    logger.warning("Error while checking for null values: " + e.getMessage());
                }
            }
        }
    }
}