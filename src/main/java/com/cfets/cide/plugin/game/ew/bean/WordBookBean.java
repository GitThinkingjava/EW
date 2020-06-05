package com.cfets.cide.plugin.game.ew.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 单词本
 * 
 * @author TangTao
 *
 */
@Data
public class WordBookBean implements Serializable, Comparable<WordBookBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 单词
	private String word = "";

	// 翻译
	private String trans = "";

	// 发音
	private String phonetic = "";

	// 标签
	private String tags = "";

	// 进展
	private String progress = "";

	public WordBookBean word(String word) {
		this.setWord(word);
		return this;
	}

	public WordBookBean trans(String trans) {
		this.setTrans(trans);
		return this;
	}

	public WordBookBean phonetic(String phonetic) {
		this.setPhonetic(phonetic);
		return this;
	}

	public WordBookBean tags(String tags) {
		this.setTags(tags);
		return this;
	}

	@Override
	public int compareTo(WordBookBean o) {

		return word.compareTo(o.getWord());
	}

}
