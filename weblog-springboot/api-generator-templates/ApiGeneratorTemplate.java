package com.suoyike.weblog.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: REST API接口生成助手
 **/
public class RestApiGenerator {

    private Configuration configuration;

    public RestApiGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File("api-generator-templates"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize template directory", e);
        }
    }

    /**
     * 生成Controller
     */
    public void generateController(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("ControllerTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成Service接口
     */
    public void generateServiceInterface(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("ServiceTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成Service实现
     */
    public void generateServiceImpl(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("ServiceImplTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成Converter
     */
    public void generateConverter(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("ConverterTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成请求VO
     */
    public void generateRequestVO(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("RequestVoTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成响应VO
     */
    public void generateResponseVO(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("ResponseVoTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成DO
     */
    public void generateDO(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("DoTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }

    /**
     * 生成Mapper
     */
    public void generateMapper(Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        dataModel.put("date", LocalDate.now().toString());
        Template template = configuration.getTemplate("MapperTemplate.java");
        try (FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }
}