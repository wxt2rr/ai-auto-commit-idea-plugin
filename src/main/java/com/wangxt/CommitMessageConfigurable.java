package com.wangxt;

import com.intellij.openapi.options.Configurable;

import javax.swing.*;
import java.awt.*;

/**
 * @author wangxt
 * @desc 配置类
 * @date 2025/3/15 15:55
 **/
public class CommitMessageConfigurable implements Configurable {
    private JTextField apiKeyField;
    private JTextField apiUrl;
    private JTextField model;
    private JTextField rule;

    @Override
    public String getDisplayName() {
        return "AiAutoCommit";
    }

    @Override
    public JComponent createComponent() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        apiKeyField = new JTextField(CommitSettings.getApiKey(), 0);
        apiUrl = new JTextField(CommitSettings.getApiKey(), 0);
        model = new JTextField(CommitSettings.getModel(), 0);
        rule = new JTextField(CommitSettings.getRule(), 10);

        panel.add(new JLabel("Api Key:"));
        panel.add(apiKeyField);
        panel.add(new JLabel("Base Url:"));
        panel.add(apiUrl);
        panel.add(new JLabel("Model :"));
        panel.add(model);
        panel.add(new JLabel("Rule :"));
        panel.add(rule);
        return panel;
    }

    @Override
    public boolean isModified() {
        return !apiKeyField.getText().equals(CommitSettings.getApiKey()) ||
                !apiUrl.getText().equals(CommitSettings.getBaseUrl()) ||
                !model.getText().equals(CommitSettings.getModel()) ||
                !rule.getText().equals(CommitSettings.getRule());
    }

    @Override
    public void apply() {
        CommitSettings.setApiKey(apiKeyField.getText());
        CommitSettings.setBaseUrl(apiUrl.getText());
        CommitSettings.setRule(rule.getText());
        CommitSettings.setModel(model.getText());
        ModelCaller.init();
    }

    @Override
    public void reset() {
        apiKeyField.setText(CommitSettings.getApiKey());
        apiUrl.setText(CommitSettings.getBaseUrl());
        model.setText(CommitSettings.getModel());
        rule.setText(CommitSettings.getRule());
    }
}