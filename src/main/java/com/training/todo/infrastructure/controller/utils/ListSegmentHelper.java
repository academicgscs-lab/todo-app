package com.training.todo.infrastructure.controller.utils;

import java.util.List;
import java.util.Vector;

public class ListSegmentHelper<T> {
    public static final int SEGMENT_SIZE = 10;
    private final List<List<T>> pageList;

    public ListSegmentHelper(List<T> taskList) {
        pageList = paginate(new Vector<>(), taskList, 0);
    }

    private List<List<T>> paginate(List<List<T>> pageList, List<T> taskList, int anchor) {
        if (taskList == null || taskList.isEmpty()) {
            return pageList;
        }

        int jump = anchor + SEGMENT_SIZE;
        if (jump >= taskList.size()) {
            pageList.add(taskList.subList(anchor, taskList.size()));
            return pageList;
        }
        pageList.add(taskList.subList(anchor, jump));
        return paginate(pageList, taskList, jump);
    }

    public int getSize(){
        return pageList.size();
    }

    public List<T> get(int index) {
        return pageList.get(index);
    }
}
