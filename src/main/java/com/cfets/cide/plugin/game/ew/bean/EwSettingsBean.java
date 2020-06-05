package com.cfets.cide.plugin.game.ew.bean;

import lombok.Data;

/**
 * 单词
 */
@Data
public class EwSettingsBean {

	public final static String PATTERN_RANDOM = "RANDOM", PATTERN_AZ = "A-Z";

	// 设置类型
	private String wordType = "";

	// 速度
	private String speed = "";

	// 策略
	private String policy = "";

	// 指定字母
	private String assignLetter = "A";

}
