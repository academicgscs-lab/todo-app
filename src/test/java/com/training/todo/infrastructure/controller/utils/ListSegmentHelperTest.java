package com.training.todo.infrastructure.controller.utils;

import com.training.todo.domain.Task;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ListSegmentHelperTest {

    // [MethodName]_[StateUnderTest]_[ExpectedBehavior]
    @Test
    void pagination_paginateOddList_noExceptionThrown() {
        List<Task> taskList = new Vector<>();
        for (int i = 0; i < 32; i++) {
            taskList.add(Task.builder().build());
        }

        ListSegmentHelper<Task> listSegmentHelper = new ListSegmentHelper<>(taskList);
        for (int i = 0; i < 3; i++) {
            try {
                assertEquals(ListSegmentHelper.SEGMENT_SIZE, listSegmentHelper.get(i).size());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }

        assertTrue(listSegmentHelper.get(3).size() < ListSegmentHelper.SEGMENT_SIZE);
    }


    @Test
    void pagination_paginateEvenList_noExceptionThrown() {
        List<Task> taskList = new Vector<>();
        for (int i = 0; i < 30; i++) {
            taskList.add(Task.builder().build());
        }

        ListSegmentHelper<Task> listSegmentHelper = new ListSegmentHelper<>(taskList);
        for (int i = 0; i < 3; i++) {
            try {
                assertEquals(ListSegmentHelper.SEGMENT_SIZE, listSegmentHelper.get(i).size());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }

        assertThrows(IndexOutOfBoundsException.class, () -> listSegmentHelper.get(3));
    }
}