package com.wangxt;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author wangxt
 * @desc 生成提交信息
 * @date 2025/3/15 15:56
 **/
public class CommitMessageGenerator {

    public static String generate(Project project) throws Exception {
        Collection<Change> changes = ChangeListManager.getInstance(project).getAllChanges();
        StringBuilder diffBuilder = new StringBuilder();
        for (Change change : changes) {
            String diff = getDiff(change);
            if (StringUtils.isNotBlank(diff)) {
                String filePath = change.getVirtualFile() != null ? change.getVirtualFile().getPath() : "unknown";
                diffBuilder.append("File: ").append(filePath).append("\nDiff:\n").append(diff).append("\n");
            }
        }

        String diffContent = diffBuilder.toString();
        if (diffContent.isEmpty()) {
            return null;
        }

        return ModelCaller.call(diffContent);
    }

    private static String getDiff(Change change) throws Exception {
        if (change.getAfterRevision() != null) {
            return change.getAfterRevision().getContent() != null ? change.getAfterRevision().getContent() : "";
        }
        return null;
    }
}