package com.cfets.cide.plugin.game.ew.dialogs;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.alibaba.fastjson.JSONObject;
import com.cfets.cide.plugin.game.ew.bean.EwSettingsBean;
import com.cfets.cide.plugin.game.ew.utils.EwUtil;
import com.cfets.cide.plugin.util.utils.FileUtil;

/**
 * 背单词配置页面
 * 
 * @author TangTao
 *
 */
public class EwSettingsDiaglog extends AbstractDialog {

	protected static Logger logger = Logger.getLogger(EwSettingsDiaglog.class);

	private final static String SHELL_TITLE = "EW";

	// 创建词典,速度，字母容器
	private Composite compLexicon, compSpeed, compLetter;

	private Label labConf, labSpeed, labLetter, labOk;

	// 创建单词选择按钮
	private Button btnCet4E, btnCet4H, btnCet6, btnCet700, btnSpeed1, btnSpeed3, btnSpeed5, btnSpeed10, btnRandom,
			btnAz;

	// 创建字母选择下拉框
	private Combo comboAz;

	// 创建速率
	private static final String SPEED1 = "1", SPEED3 = "3", SPEED5 = "5", SPEED10 = "10";

	//中文释义
	private static final String RANDOM_ZH = "随机";

	//选择 26个字母
	private static final String[] LETTER = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	// 存储Preference
//	private static IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

//	// 存储Preference KEY
	private static final String KEY = "EW_CONF";

	// 背单词弹框
	private EwDiaglog ewDiaglog;

	// 配置对象
	private EwSettingsBean settings;

	public EwSettingsDiaglog(EwDiaglog ewDiaglog) {
		this.ewDiaglog = ewDiaglog;
		// 创建一个shell
		shell = new Shell(SWT.NONE);
		initComp();
		setShellMove();
		initData();
	}

	private void initComp() {
		// 获取显示器的尺寸
		Rectangle bounds = Display.getCurrent().getBounds();
		shell.setText(SHELL_TITLE);
		shell.setImage(EwUtil.getImage("icons/ew_32.png"));

		// 设置Shell出现位置
		int leftMgn = bounds.width - 310, topMgn = bounds.height - 275;
		shell.setBounds(leftMgn, topMgn, 300, 170);
		shell.setLayout(new FillLayout());
		
		
		compLexicon = new Composite(shell, SWT.NONE);
		compLexicon.setBounds(0, 0, 100, 260);
		labConf = new Label(compLexicon, SWT.LEFT | SWT.NO_BACKGROUND);
		
		labConf.setBounds(10, 10, 30, 20);
		labConf.setText("词库");

		compSpeed = new Composite(shell, SWT.NONE);
		compSpeed.setBounds(100, 0, 100, 260);

		labSpeed = new Label(compSpeed, SWT.LEFT | SWT.NO_BACKGROUND);
		labSpeed.setBounds(10, 10, 60, 20);
		labSpeed.setText("速度");

		compLetter = new Composite(shell, SWT.NONE);
		compLetter.setBounds(200, 0, 80, 260);

		labLetter = new Label(compLetter, SWT.LEFT | SWT.NO_BACKGROUND);
		labLetter.setBounds(10, 10, 30, 20);
		labLetter.setText("字母");
		
		//创建词库按钮
		btnCet4E = new Button(compLexicon, SWT.RADIO | SWT.LEFT);
		btnCet4E.setBounds(10, 40, 90, 20);
		btnCet4E.setText(EwUtil.TYPE_CET4_EALY);
		btnCet4E.setData(EwUtil.TYPE_CET4_EALY);

		btnCet4H = new Button(compLexicon, SWT.RADIO | SWT.LEFT);
		btnCet4H.setBounds(10, 70, 90, 20);
		btnCet4H.setText(EwUtil.TYPE_CET4_HARD);
		btnCet4H.setData(EwUtil.TYPE_CET4_HARD);

		btnCet6 = new Button(compLexicon, SWT.RADIO | SWT.LEFT);
		btnCet6.setBounds(10, 100, 90, 20);
		btnCet6.setText(EwUtil.TYPE_CET6);
		btnCet6.setData(EwUtil.TYPE_CET6);

		btnCet700 = new Button(compLexicon, SWT.RADIO | SWT.LEFT);
		btnCet700.setBounds(10, 130, 90, 20);
		btnCet700.setText(EwUtil.TYPE_CORE_700);
		btnCet700.setData(EwUtil.TYPE_CORE_700);

		//创建速度按钮
		btnSpeed1 = new Button(compSpeed, SWT.RADIO | SWT.LEFT);
		btnSpeed1.setBounds(10, 40, 80, 20);
		btnSpeed1.setText(SPEED1);
		btnSpeed1.setData(SPEED1);

		btnSpeed3 = new Button(compSpeed, SWT.RADIO | SWT.LEFT);
		btnSpeed3.setBounds(10, 70, 80, 20);
		btnSpeed3.setText(SPEED3);
		btnSpeed3.setData(SPEED3);

		btnSpeed5 = new Button(compSpeed, SWT.RADIO | SWT.LEFT);
		btnSpeed5.setBounds(10, 100, 80, 20);
		btnSpeed5.setText(SPEED5);
		btnSpeed5.setData(SPEED5);

		btnSpeed10 = new Button(compSpeed, SWT.RADIO | SWT.LEFT);
		btnSpeed10.setBounds(10, 130, 80, 20);
		btnSpeed10.setText(SPEED10);
		btnSpeed10.setData(SPEED10);

		//创建策略按钮
		btnRandom = new Button(compLetter, SWT.RADIO | SWT.LEFT);
		btnRandom.setBounds(10, 40, 80, 20);
		btnRandom.setText(RANDOM_ZH);
		btnRandom.setData(EwSettingsBean.PATTERN_RANDOM);

		btnAz = new Button(compLetter, SWT.RADIO | SWT.LEFT);
		btnAz.setBounds(10, 75, 20, 20);
//		btnAz.setText(EwSettingsBean.POLICY_AZ);
		btnAz.setData(EwSettingsBean.PATTERN_AZ);
		comboAz = new Combo(compLetter, SWT.LEFT);
		comboAz.setBounds(30, 70, 50, 20);
		comboAz.setItems(LETTER);
		comboAz.select(0);

		labOk = new Label(compLetter, SWT.CENTER | SWT.NO_BACKGROUND);
		labOk.setBounds(30, 120, 32, 32);
		labOk.setImage(EwUtil.getImage("icons/ok_32.png"));
		labOk.setToolTipText("确认");

		// 保存数据
		labOk.addListener(SWT.MouseUp, e -> {

			// 构建对象
			settings = new EwSettingsBean();

			// 保存配置
			if (btnCet4E.getSelection()) {
				settings.setWordType(btnCet4E.getData().toString());
			} else if (btnCet4H.getSelection()) {
				settings.setWordType(btnCet4H.getData().toString());
			} else if (btnCet6.getSelection()) {
				settings.setWordType(btnCet6.getData().toString());
			} else if (btnCet700.getSelection()) {
				settings.setWordType(btnCet700.getData().toString());
			}

			// 保存速率
			if (btnSpeed1.getSelection()) {
				settings.setSpeed(btnSpeed1.getData().toString());
			} else if (btnSpeed3.getSelection()) {
				settings.setSpeed(btnSpeed3.getData().toString());
			} else if (btnSpeed5.getSelection()) {
				settings.setSpeed(btnSpeed5.getData().toString());
			} else if (btnSpeed10.getSelection()) {
				settings.setSpeed(btnSpeed10.getData().toString());
			}

			// 保存数据
			if (btnRandom.getSelection()) {
				settings.setPolicy(EwSettingsBean.PATTERN_RANDOM);
			} else if (btnAz.getSelection()) {
				settings.setPolicy(EwSettingsBean.PATTERN_AZ);// 设置策略
				settings.setAssignLetter(comboAz.getText()); // 获取并设置指定的字母
			}

			String jsonSettings = JSONObject.toJSONString(settings);
			System.out.println(jsonSettings);
			save(jsonSettings);
//			
			// 重启
			restart();
			// 关闭页面
			close();
		});
	}
	
	private  static String PATH_CONF ="D:\\ew_conf.json";

	public void save(String jsonSettings) {
//		ps.setValue(KEY, jsonSettings);
		//Activator.getDefault().savePluginPreferences();
		FileUtil.writeFile(PATH_CONF, jsonSettings);
		logger.info("ew.save == " + jsonSettings);
	}

	// 默认数据
	public String DEFAULT_CONF = "{\"assignLetter\":\"A\",\"policy\":\"RANDOM\",\"speed\":\"3\",\"wordType\":\"CET4-HARD\"}";

	/**
	 * 初始化数据
	 */
	public void initData() {
		// IPreferenceStore 获取设置的数据存储
			String ewConf=EwUtil.readFileAll(PATH_CONF);
	
//
		if(ewConf!=null) {
			settings = JSONObject.parseObject(ewConf, EwSettingsBean.class);
		}else {
			settings = JSONObject.parseObject(DEFAULT_CONF, EwSettingsBean.class);
		}
//		


		// 读取配置设置数据
		if (btnCet4E.getData().toString().equals(settings.getWordType())) {
			btnCet4E.setSelection(true);
		} else if (btnCet4H.getData().toString().equals(settings.getWordType())) {
			btnCet4H.setSelection(true);
		} else if (btnCet6.getData().toString().equals(settings.getWordType())) {
			btnCet6.setSelection(true);
		} else if (btnCet700.getData().toString().equals(settings.getWordType())) {
			btnCet700.setSelection(true);
		}

		// 读取速度设置数据
		if (btnSpeed1.getData().toString().equals(settings.getSpeed())) {
			btnSpeed1.setSelection(true);
		} else if (btnSpeed3.getData().toString().equals(settings.getSpeed())) {
			btnSpeed3.setSelection(true);
		} else if (btnSpeed5.getData().toString().equals(settings.getSpeed())) {
			btnSpeed5.setSelection(true);
		} else if (btnSpeed10.getData().toString().equals(settings.getSpeed())) {
			btnSpeed10.setSelection(true);
		}

		// 读取策略设置数据
		if (btnRandom.getData().toString().equals(settings.getPolicy())) {
			btnRandom.setSelection(true);
		} else if (btnAz.getData().toString().equals(settings.getPolicy())) {
			btnAz.setSelection(true);
			comboAz.indexOf("B");
			comboAz.select(comboAz.indexOf(settings.getAssignLetter()));
		}
	}

	/**
	 * 重启任务
	 */
	public void restart() {
		if (ewDiaglog.getTimer() != null) {
			ewDiaglog.getTimer().cancel();
			ewDiaglog.setPlayFlag(true);
			ewDiaglog.start();
		}
	}

	public EwSettingsBean getSettings() {
		return settings;
	}

	public void setSettings(EwSettingsBean settings) {
		this.settings = settings;
	}
	
}
