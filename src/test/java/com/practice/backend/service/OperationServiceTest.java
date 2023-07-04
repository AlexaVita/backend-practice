package com.practice.backend.service;

import com.practice.backend.exception.EmptyTableException;
import com.practice.backend.enums.EOperationStates;
import com.practice.backend.enums.EOperationTypes;
import com.practice.backend.model.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperationServiceTest {

    @Autowired
    OperationService operationServiceTest;

    Random random = new Random();
    Time time = new Time(9999L);

    // Тестовая запись (если хотите добавить её несколько раз, то нужно менять sectorID или paymentSystem)
    Operation operationTest = new Operation(0L, 0L,new Timestamp(time.getTime()), 100L, 0L,
            "good description", "best@email", EOperationStates.APPROVED, EOperationTypes.PAYMENT);

    @Test
    void insertAndGetLastShouldBeEqualInitialInsert() {

        // Просто пробуем вставить запись в бд, и проверяем её присутствие в БД
        operationServiceTest.insert(operationTest);

        Operation actualOperation;

        try {
            actualOperation = getLastOperation();
        } catch (EmptyTableException e) {
            fail(e);
            return;
        }

        assertEquals(operationTest, actualOperation);

    }


    Operation getLastOperation() throws EmptyTableException {
        ArrayList<Operation> operations = getAllOperations();
        if (operations.size() < 1) {
            throw new EmptyTableException("Operations table is empty!");
        }
        return operations.get(operations.size() - 1);
    }

    ArrayList<Operation> getAllOperations() {
        System.out.println(operationServiceTest.getAll());
        return (ArrayList<Operation>) operationServiceTest.getAll();
    }

}