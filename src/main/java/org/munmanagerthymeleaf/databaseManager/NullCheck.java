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

    /**
     * This method checks for null values in the data and logs a warning if it finds any.
     * I have opted to use reflection to keep the code dynamic, even though it's slower and more complicated.
     * I have also used generics to ensure that the method can be used for any type of data for reusability.
     * @param dataList the list of objects to check for null values
     * @param dataTypeName the name of the data type to be used in the log message
     * @param <T> the type of the data that is being checked
     */
    private <T> void checkNullValues(List<T> dataList, String dataTypeName) {
        for (T dataItem : dataList) {
//          get each field in the class, set them to accessible to ensure we can get the value
            for (Field field : dataItem.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
//                  get the value of the field and check if it's null
                    Object value = field.get(dataItem);
                    if (value == null) {
                        logger.warning(dataTypeName + " " + field.getName() + " is null for: " + dataItem);
                    }
                    // catch a IllegalAccessException if it occurs. no need to do anything with it, just log it,
                    // as it will not harm the program in any way, the method will just continue to the next field.
                } catch (IllegalAccessException e) {
                    logger.warning("Error while checking for null values: " + e.getMessage());
                }
            }
        }
    }
}