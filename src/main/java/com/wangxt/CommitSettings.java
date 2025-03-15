package com.wangxt;

/**
 * @author wangxt
 * @desc 模型设置
 * @date 2025/3/15 10:47
 **/
public class CommitSettings {
    private static String apiKey = "";
    private static String baseUrl = "";

    private static String model = "";

    private static String rule = """
            - 标题必须以 (新增|修复|测试|代码规范|调试|重构|文档|配置|清理)[:|：] 开头\s
            - 写完标题需要换2行写明细\s
            - 明细需要以1. 2. 开头，并且每条明细需要换行\s
            """;

    public static String getRule() {
        return rule;
    }

    public static void setRule(String rule) {
        CommitSettings.rule = rule;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String key) {
        apiKey = key;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        CommitSettings.baseUrl = baseUrl;
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        CommitSettings.model = model;
    }
}