package com.lbstspringboot3.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
			                        .outputDir("src\\main\\java");
		                 })
		                 // 包配置
		                 .packageConfig((scanner, builder) -> {
			                 builder.parent("com.lbstspringboot3")
			                        .entity("entity")
			                        .mapper("mapper")
			                        .service("service")
			                        .serviceImpl("service.impl")
			                        .xml("mapper.xml")
			                        .controller("controller");
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
