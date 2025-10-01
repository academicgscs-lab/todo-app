package com.training.todo.infrastructure.controller.utils;

import lombok.Getter;

import java.util.List;
import java.util.Vector;

public class ListSegmentHelper<T> {
    private final int segmentSize;
    @Getter
    private final List<List<T>> pageList;

    public ListSegmentHelper(int segmentSize, List<T> taskList) {
        this.segmentSize = segmentSize;
        pageList = paginate(new Vector<>(), taskList, 0);
    }

    private List<List<T>> paginate(List<List<T>> pageList, List<T> taskList, int anchor) {
        if (taskList == null || taskList.isEmpty()) {
            return pageList;
        }

        int jump = anchor + segmentSize;
        if (jump >= taskList.size()) {
            pageList.add(taskList.subList(anchor, taskList.size()));
            return pageList;
        }
        pageList.add(taskList.subList(anchor, jump));
        return paginate(pageList, taskList, jump);
    }

    public List<T> get(int index) {
        return pageList.get(index);
    }
}
