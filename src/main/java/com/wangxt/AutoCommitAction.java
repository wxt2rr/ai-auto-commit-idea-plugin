package com.wangxt;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.vcs.commit.CommitWorkflowHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wangxt
 * @desc 定义自动提交事件，继承VCS的提交事件
 * @date 2025/3/15 11:04
 **/
public class AutoCommitAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // 获取 CommitWorkflowHandler
        CommitWorkflowHandler commitWorkflowHandler = e.getData(VcsDataKeys.COMMIT_WORKFLOW_HANDLER);
        if (commitWorkflowHandler == null) {
            return;
        }

        // 获取 CommitMessage 组件
        CommitMessageI commitMessage = VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.getDataContext());
        if (commitMessage == null) {
            return;
        }

        // 使用 CommitMessageGenerator 生成提交信息
        String generatedMessage = null;
        try {
            generatedMessage = CommitMessageGenerator.generate(project);
        } catch (Exception ex) {
            Messages.showErrorDialog(project, "生成失败了, 自己写吧😊~ 原因：" + ex.getMessage(), "Error");
            return;
        }

        if (StringUtils.isBlank(generatedMessage)) {
            Messages.showErrorDialog(project, "生成失败了, 自己写吧😊~", "Error");
            return;
        }

        // 在 UI 线程中设置提交信息
        String finalGeneratedMessage = generatedMessage;
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                // 设置到 CommitMessage 组件的输入框
                commitMessage.setCommitMessage(finalGeneratedMessage);
            } catch (Exception exception) {
                // 错误处理：显示通知
                Messages.showErrorDialog(project, "自动填充异常：" + exception.getMessage(), "Error");
            }
        });
    }

    @Override
    public void update(AnActionEvent e) {
        // 确保按钮在提交对话框中始终启用
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(true);

        // 可选：设置图标（如果需要）
        presentation.setIcon(AllIcons.Actions.EnableNewUi);
    }
}