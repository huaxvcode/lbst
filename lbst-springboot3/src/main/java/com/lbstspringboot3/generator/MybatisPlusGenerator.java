package com.lbstspringboot3.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MybatisPlusGenerator {

	private static String url;
	private static String username;
	private static String password;

	static {
		url = "jdbc:mysql://localhost:3306/lbst?serverTimezone=UTC&useUniode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
		username = "root";
		password = "123456";
	}

	public static void main(String[] args) {
		FastAutoGenerator.create(url, username, password)
		                 // 全局配置
		                 .globalConfig((scanner, builder) -> {
			                 builder.author("林日清")
			                        .disableOpenDir(); // 不允许自动打开目录
		                 })
		                 // 包配置
		                 .packageConfig((scanner, builder) -> {
			                 builder.parent("com.lbstspringboot3")
			                        .entity("entity")
			                        .mapper("mapper")
			                        .service("service")
			                        .serviceImpl("service.impl")
			                        .xml("mapper")
			                        .pathInfo(Map.of(
					                        OutputFile.entity, "src/main/java/com/lbstspringboot3/entity",
					                        OutputFile.mapper, "src/main/java/com/lbstspringboot3/mapper",
					                        OutputFile.xml, "src/main/resources/mapper",
					                        OutputFile.service, "src/main/java/com/lbstspringboot3/service",
					                        OutputFile.serviceImpl, "src/main/java/com/lbstspringboot3/service/impl"));
		                 })
		                 // 策略配置
		                 .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
		                                                              .entityBuilder()
		                                                              .enableLombok()
		                                                              .addTableFills(
				                                                              new Column("create_time", FieldFill.INSERT)
		                                                                            )
		                                                              .controllerBuilder()
		                                                              .disable() // 禁止生成 controller 层
		                                                              .build())
		                 // 使用Freemarker引擎模板，默认的是Velocity引擎模板
		                 .templateEngine(new FreemarkerTemplateEngine())
		                 .execute();
	}

	// 处理 all 情况
	protected static List<String> getTables(String tables) {
		return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
	}
}
