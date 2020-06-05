package com.cfets.cide.plugin.game.ew.dialogs;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.cfets.cide.plugin.game.ew.bean.EwSettingsBean;
import com.cfets.cide.plugin.game.ew.bean.WordBookBean;
import com.cfets.cide.plugin.game.ew.utils.EwUtil;

/**
 * 背单词 bar
 * 
 * @author TangTao
 *
 */
public class EwDiaglog extends AbstractDialog {

	protected static Logger logger = Logger.getLogger(EwDiaglog.class);

	private final static String SHELL_TITLE = "EW";

	// 容器
	private Composite compContent;

	// 标签
	private Label labLogo, labWord, labMore, labSettings, labPlay, labHelp, labClose;

	// 帮组弹窗
	private EwHelpDiaglog ewHelpDiaglog;

	// 释义弹窗
	private EwParaphraseDiaglog ewParaphraseDiaglog;

	// 设置弹窗
	private EwSettingsDiaglog ewSettingsDiaglog;

	// 调度计划
	private Timer timer;

	// 延迟时间
	private static final long DELAY = 0;

	// 播放标记
	private boolean playFlag = true;

	public EwDiaglog() {
		// 创建一个shell
		shell = new Shell(SWT.NONE);
		initComp();
		initLister();
		setShellMove();
	}

	private void initComp() {
		// 获取显示器的尺寸
		Rectangle bounds = Display.getCurrent().getBounds();
		shell.setText(SHELL_TITLE);
		shell.setImage(EwUtil.getImage("icons/ew_32.png"));

		// 设置Shell出现位置
		int leftMgn = bounds.width - 310, topMgn = bounds.height - 105;
		shell.setBounds(leftMgn, topMgn, 300, 35);
		shell.setLayout(new FillLayout());

		// 创建容器
		compContent = new Composite(shell, SWT.NONE);
		compContent.setBounds(shell.getBounds());

		// 创建Logo
		labLogo = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labLogo.setBounds(5, 8, 20, 20);
		labLogo.setImage(EwUtil.getImage("icons/ew_16.png"));
		labLogo.setToolTipText("EW-背单词");

		// 创建单词显示的Label
		labWord = new Label(compContent, SWT.LEFT | SWT.NO_BACKGROUND);
		labWord.setBounds(35, 0, 135, 40);
		// labWord.setText("点击开始按钮开始");

		// 创建更多释义的按钮
		labMore = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labMore.setBounds(175, 8, 20, 20);
		labMore.setImage(EwUtil.getImage("icons/more_16.png"));
		labMore.setToolTipText("更多释义");
		labMore.setData("status", false);

		// 创建开始按钮
		labPlay = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labPlay.setBounds(200, 8, 20, 20);
		labPlay.setImage(EwUtil.getImage("icons/play_16.png"));
		labPlay.setToolTipText("开始");
		labPlay.setData("status", false);

		// 创建设置按钮
		labSettings = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labSettings.setBounds(225, 8, 20, 20);
		labSettings.setImage(EwUtil.getImage("icons/settings_16.png"));
		labSettings.setToolTipText("设置");
		labSettings.setData("status", false);

		// 创建帮组按钮
		labHelp = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labHelp.setBounds(250, 8, 20, 20);
		labHelp.setImage(EwUtil.getImage("icons/help_16.png"));
		labHelp.setToolTipText("帮助");
		labHelp.setData("status", false);

		// 创建关闭按钮
		labClose = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labClose.setBounds(275, 8, 20, 20);
		labClose.setImage(EwUtil.getImage("icons/close_16.png"));
		labClose.setToolTipText("关闭");
	}

	/**
	 * 初始化按钮的事件
	 */
	private void initLister() {

		ewParaphraseDiaglog = new EwParaphraseDiaglog();
		ewSettingsDiaglog = new EwSettingsDiaglog(this);
		ewHelpDiaglog = new EwHelpDiaglog();

		// 播放事件
		labPlay.addListener(SWT.MouseUp, e -> {
			boolean flag = Boolean.valueOf(labPlay.getData("status").toString());
			// False 开始
			if (!flag) {
				System.out.println("开始播放");
				labPlay.setImage(EwUtil.getImage("icons/stop_16.png"));
				labPlay.setData("status", true);
				labPlay.setToolTipText("停止");
				start();// 开始调度
			} else {
				// True 停止
				labPlay.setImage(EwUtil.getImage("icons/play_16.png"));
				labPlay.setData("status", false);
				labPlay.setToolTipText("开始");
				timer.cancel();// 停止
				playFlag = false;
				// System.out.println("stop == " + seq);
			}
		});

		// 更多弹窗控制
		labMore.addListener(SWT.MouseUp, e -> {
			if ( ewParaphraseDiaglog == null || ewParaphraseDiaglog.isDisposed()) {
				ewParaphraseDiaglog = new EwParaphraseDiaglog();
			}
			
			diglogControl(ewParaphraseDiaglog,labMore);
		});

		// 帮助弹窗控制
		labHelp.addListener(SWT.MouseUp, e -> {
			if (ewHelpDiaglog == null || ewHelpDiaglog.isDisposed()) {
				ewHelpDiaglog = new EwHelpDiaglog();
			}

			diglogControl(ewHelpDiaglog,labHelp);

		});

		// 设置弹窗控制
		labSettings.addListener(SWT.MouseUp, e -> {
			if (ewParaphraseDiaglog == null || ewSettingsDiaglog.isDisposed()) {
				ewSettingsDiaglog = new EwSettingsDiaglog(this);
			}

			diglogControl(ewSettingsDiaglog,labSettings);
			
		});

		// 关闭窗体
		labClose.addListener(SWT.MouseUp, e -> {
			ewParaphraseDiaglog.close();
			ewSettingsDiaglog.close();
			ewHelpDiaglog.close();
			shell.dispose();
		});
	}

	/**
	 * lab 控制
	 * 
	 * @param dialog
	 * @param control
	 */
	public void diglogControl(AbstractDialog dialog, Control control) {
//
//		// 根据不同类类型的弹窗创建对象
//		if (dialog == null || dialog.isDisposed()) {
//			if (dialog instanceof EwSettingsDiaglog) {
//				dialog = new EwSettingsDiaglog(this);
//			} else if (dialog instanceof EwParaphraseDiaglog) {
//				dialog = new EwParaphraseDiaglog();
//			} else if (dialog instanceof EwHelpDiaglog) {
//				dialog = new EwHelpDiaglog();
//			}
//		}
//		// 获取/设置弹窗的状态
		boolean flag = Boolean.valueOf(control.getData("status").toString());
		if (!flag) {
			control.setData("status", true);
			dialog.open();
		} else {
			control.setData("status", false);
			dialog.close();
		}
	}

	// 单词的索引
	private int indexWord = 0;

	public void start() {

		// 获取配置
		EwSettingsBean settings = ewSettingsDiaglog.getSettings();
		logger.info("ew.start == " + ewSettingsDiaglog.getSettings());

		// 根据配置获取不同的词库
		List<WordBookBean> words = EwUtil.MAP_WORDS.get(settings.getWordType());

		// 重指定字段开始,playFlag只有第一次打开，或配置完成确认后，执行
		if (EwSettingsBean.PATTERN_AZ.equals(settings.getPolicy()) && playFlag) {
			indexWord = 0; // 重置为0,每
			for (WordBookBean word : words) {
				// 找到 A-Z 字母开头的 则退出循环
				if (word.getWord().startsWith(settings.getAssignLetter().toLowerCase())) {
					break;
				}
				indexWord++;
			}
			// 播放设置成false
			playFlag = false;
		}

		// 执行任务
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				Display.getDefault().asyncExec(() -> {
					// 执行随机策略
					if (EwSettingsBean.PATTERN_RANDOM.equals(settings.getPolicy())) {
						int random = new Random().nextInt(words.size());
						WordBookBean wb = words.get(random);
						showWord(wb);
					}
					// 执行A-Z策略
					if (EwSettingsBean.PATTERN_AZ.equals(settings.getPolicy())) {
						WordBookBean wb = words.get(indexWord);
						showWord(wb);
						// 索引计数
						indexWord++;
						// size,indexWord相等重头开始
						if (indexWord == words.size()) {
							indexWord = 0;
						}
					}
				});
			}
		}, DELAY, Long.valueOf(settings.getSpeed()) * 1000);
	}

	/**
	 * 显示单词
	 * 
	 * @param wb
	 */
	public void showWord(WordBookBean wb) {
		// 单词与释义
		labWord.setText(wb.getWord() + "\n" + wb.getTrans());
		if (!ewParaphraseDiaglog.isDisposed()) {
			ewParaphraseDiaglog.show(wb);
		}
	}

	public Timer getTimer() {
		return timer;
	}

	// 设置播放状态
	public void setPlayFlag(boolean playFlag) {
		this.playFlag = playFlag;
	}

	/**
	 * 添加样式~
	 */
//	private void addStyle() {
//		Color white = new Color(shell.getDisplay(), 255, 255, 255);
//		compContent.setBackground(white);
//	}

	public static void main(String[] args) {
		new EwDiaglog().open();
	}

}
