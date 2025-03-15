package com.wangxt;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

/**
 * @author wangxt
 * @date 2025/3/15 13:12
 * @desc
 */
public class ModelCaller {

    private static OpenAIClient CLIENT = null;

    static {
        init();
    }

    public static void init() {
        String apiKey = CommitSettings.getApiKey();
        String baseUrl = CommitSettings.getBaseUrl();
        if (StringUtils.isNoneBlank(apiKey, baseUrl)) {
            CLIENT = OpenAIOkHttpClient.builder()
                    .apiKey(apiKey)
                    .baseUrl(baseUrl)
                    .timeout(Duration.ofMinutes(5))
                    .build();
        }
    }

    public static String call(String diff) {
        if (CLIENT == null) {
            throw new RuntimeException("请先配置 API Key 和 BaseUrl");
        }

        String generate = PromptGenerator.generate(diff);
        System.out.println(generate);
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(generate)
                .model(CommitSettings.getModel())
                .build();
        ChatCompletion chatCompletion = CLIENT.chat().completions().create(params);

        StringBuilder stringBuilder = new StringBuilder();
        chatCompletion.choices().forEach(choice -> {
            choice.message().content().ifPresent(stringBuilder::append);
        });
        return stringBuilder.toString();
    }

    static class PromptGenerator {
        private static final String SYSTEM_PROMPT = """
                你是一个 Git 提交消息生成助手。我希望你根据我提供的 Git diff 信息和提交示例，自动生成一条符合规范的提交消息。
                                
                ### 提交风格
                我的提交消息遵循以下规则：""";


        private static final String DIFF = """
                ### Diff 信息
                以下是我提供的 Git diff 内容，请根据它生成提交消息：""";

        private static final String RESULT = """
                ### 输出要求
                - 生成一条符合上述风格的提交消息。
                - 只返回提交消息本身，不要包含其他说明文字。
                - 输出字数不能超过100字
                - 使用中文返回""";

        public static String generate(String diff) {
            return SYSTEM_PROMPT + "\n" + CommitSettings.getRule() + "\n" + DIFF + "\n" + diff + "\n" + RESULT;
        }
    }
}
