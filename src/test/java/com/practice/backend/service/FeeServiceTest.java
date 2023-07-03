package com.practice.backend.service;

import com.practice.backend.exception.EmptyTableException;
import com.practice.backend.model.Fee.EPaymentSystem;
import com.practice.backend.model.Fee.Fee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeeServiceTest {

    @Autowired
    FeeService feeServiceTest;

    Random random = new Random();
    // Тестовая запись (если хотите добавить её несколько раз, то нужно менять sectorID или paymentSystem)
    Fee feeTest = new Fee(0L, 0L, EPaymentSystem.MASTERCARD, "0.12", "10.0", "50");

    @Test
    void insertAndGetLastShouldBeEqualInitialInsert() {

        System.out.println(feeTest.getPaymentSystem().name());

        // Просто пробуем вставить запись в бд, и проверяем её присутствие в БД
        feeServiceTest.insert(feeTest);

        Fee actualFee;

        try {
            actualFee = getLastFee();
        } catch (EmptyTableException e) {
            fail(e);
            return;
        }

        assertEquals(feeTest, actualFee);

    }

    @Test
    void deletedLastEntityShouldNotBeInDB() {

        // Просто удаляем последнюю запись и смотрим, чтобы её не было в БД
        Fee deletingFee;

        try {
            deletingFee = getLastFee();
        } catch (EmptyTableException e) {
            fail(e);
            return;
        }

        feeServiceTest.delete(deletingFee.getId());

        ArrayList<Fee> fees = getAllFees();

        assertFalse(fees.contains(deletingFee));

    }

    @Test
    void deletedAnyEntityShouldNotBeInDB() {

        // Просто удаляем рандомную запись и смотрим, чтобы её не было в БД
        Fee deletingFee;
        try {
            deletingFee = getRandomFee();
        } catch (EmptyTableException e) {
            fail(e);
            return;
        }

        feeServiceTest.delete(deletingFee.getId());

        ArrayList<Fee> fees = getAllFees();

        assertFalse(fees.contains(deletingFee));

        // После проверки добавляем назад, чтобы не остаться без записей
        feeServiceTest.insert(deletingFee);

    }

    @Test
    void updatedAndGottenShouldBeEqualUpdated() {

        Fee initial;
        try {
            initial = getRandomFee(); // Берём случайную запись
        } catch (EmptyTableException e) {
            fail(e);
            return;
        }

        // Генерируем обновлённую на основе имеющейся (рандомим fix и notLess)
        Fee updated = new Fee(initial.getId(), initial.getSectorId(), initial.getPaymentSystem(),
                initial.getPercent(), Integer.toString(random.nextInt(1000)), Integer.toString(random.nextInt(1000)));

        // Используем сервис и обновляем запись
        feeServiceTest.update(updated.getId(), updated);

        Fee actualFee = feeServiceTest.getById(updated.getId());

        // Проверка будет успешна, если запись обновилась в таблице
        assertEquals(updated, actualFee);
        // И старой записи нет в таблице
        assertFalse(getAllFees().contains(initial));

    }

    Fee getLastFee() throws EmptyTableException {
        ArrayList<Fee> fees = getAllFees();
        if (fees.size() < 1) {
            throw new EmptyTableException("Fee table is empty!");
        }
        return fees.get(fees.size() - 1);
    }


    ArrayList<Fee> getAllFees() {
        return (ArrayList<Fee>) feeServiceTest.getAll();
    }

    Fee getRandomFee() throws EmptyTableException {
        ArrayList<Fee> fees = getAllFees();
        if (fees.size() < 1) {
            throw new EmptyTableException("Fee table is empty!");
        }
        return fees.get(random.nextInt(fees.size()));
    }

}