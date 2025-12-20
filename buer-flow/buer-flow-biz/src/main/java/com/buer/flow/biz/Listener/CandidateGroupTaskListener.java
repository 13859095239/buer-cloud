package com.buer.flow.biz.Listener;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * 多实例任务分配候选组监听器,获取当前任务候选用户组
 * 作用：
 * 在任务创建时触发（event = "create"）
 * 根据 BPMN XML 中配置的 flowable:elementVariable 变量（assignee）,从流程实例变量里取出当前循环对应的分组 ID
 * 为当前任务动态设置候选组，实现多分组、多实例,每个实例一个分组
 * 场景：会签审批，每个分组独立审批
 *
 * @author zoulan
 * @since 2025-09-06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CandidateGroupTaskListener implements TaskListener {

    /**
     * 当任务事件触发时被调用
     *
     * @param delegateTask 任务上下文对象
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        // 获取 BPMN XML上配置的assigneeList列表，对应每个assignee变量
        // assignee变量对应一个用户组
        String assignType = (String) delegateTask.getVariable("assignType");
        String groupId = (String) delegateTask.getVariable("assignee");
        if (groupId != null) {
            String assignTypeGroupId = assignType + ":" + groupId;
            // 设置候选组
            delegateTask.addCandidateGroup(assignTypeGroupId);
        }
    }
}
