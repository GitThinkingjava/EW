package com.cfets.jstack.cide.plugin.game.ew.test;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EwTest extends JFrame {

	JLabel lblWord = new JLabel("word"); // JLabel 标签
	JLabel lblMeaning = new JLabel("meaning");

	public void init() {
		// GUI初始化
		setSize(400, 300);
		setLayout(new FlowLayout());// FlowLayout是排版格式，这行放不下放下一行
		getContentPane().add(lblWord);
		getContentPane().add(lblMeaning);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void readAll() throws IOException {

		String fileName = "src/main/resources/words.txt";
		String charset = "GBK";

		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));

		String line;
		while ((line = reader.readLine()) != null) {

			line = line.trim();// 去掉首尾的空格
			if (line.length() == 0)
				continue;
			int idx = line.indexOf("\t");
			System.out.println(idx);
			words.add(line.substring(0, idx));
			meanings.add(line.substring(idx + 1));
		}
	}

	List<String> words = new ArrayList<>();
	List<String> meanings = new ArrayList<>();
	int current = 0;

	public void start() {
		new Thread(() -> {
			try {
				readAll();
			} catch (FileNotFoundException ef) {
				System.out.println("file not founded");

			}

			catch (IOException e) {
				System.out.println("???");
			}

			new javax.swing.Timer(5000, (e) -> {
				lblWord.setText(words.get(current));
				JacobTest.textToSpeech(words.get(current));
				lblMeaning.setText(meanings.get(current));
				current++;
				if (current == 10) {
					current = 0;
				}
			}

			).start();
		}).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EwTest ew = new EwTest();
		ew.init(); // 界面初始化
		ew.start();
	}

}
