package com.buer.flow.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 流程监听事件 enum
 *
 * @author zoulan
 * @since 2024-06-22
 */
@Getter
@RequiredArgsConstructor
public enum ListenerEventEnum {

    /**
     * 流程实例启动时触发
     */
    START_EXECUTION("startExecution"),


    /**
     * 流程实例结束时触发
     */
    END_EXECUTION("endExecution"),

    /**
     * 流程连线执行时触发
     */
    TAKE_EXECUTION("takeExecution"),

    /**
     * 任务创建时触发
     */
    CREATE_TASK("createTask"),

    /**
     * 任务被分配给用户或组时触发
     */
    ASSIGNMENT_TASK("assignmentTask"),

    /**
     * 任务完成时触发
     */
    COMPLETE_TASK("completeTask"),

    /**
     * 删除任务时触发
     */
    DELETE_TASK("deleteTask");

    private final String event;
}
