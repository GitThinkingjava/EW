package com.cfets.cide.plugin.game.ew.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cfets.cide.plugin.game.ew.bean.WordBookBean;
import com.cfets.cide.plugin.game.ew.utils.EwUtil;
import com.cfets.cide.plugin.game.ew.utils.JacobUtil;

/**
 * 背单词更多释义
 * 
 * @author TangTao
 *
 */
public class EwParaphraseDiaglog extends AbstractDialog {


	private final static String SHELL_TITLE = "EW";

	// 创建容器
	private Composite compContent;

	// 创建标签
	private Label labWord, labPhonetic, labVoice;

	// 翻译更多释义
	private Text txtTrans;

	public EwParaphraseDiaglog() {
		// 创建一个shell
		shell = new Shell(SWT.NONE);
		initComp();
		setShellMove();
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
		
		// 创建容器
		compContent = new Composite(shell, SWT.NONE);
		compContent.setBounds(shell.getBounds());

		//显示单词
		labWord = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labWord.setBounds(10, 10, 120, 20);

		//发音
		labPhonetic = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labPhonetic.setBounds(120, 10, 120, 20);

		//播放声音
		labVoice = new Label(compContent, SWT.NONE);
		labVoice.setBounds(245, 0, 32, 32);
		labVoice.setImage(EwUtil.getImage("icons/voice_32.png"));
		labVoice.setToolTipText("播放");
		labVoice.addListener(SWT.MouseUp, e -> {
			String value = labWord.getText();
			if (!"".equals(value)) {
				//调用工具播放声音
				JacobUtil.textToSpeech(value);
			}
		});
		
		//翻译信息
		txtTrans = new Text(compContent, SWT.BORDER | SWT.MULTI);
		txtTrans.setBounds(10, 35, 280, 120);
	}

	/**
	 * 显示单词
	 * 
	 * @param words
	 */
	public void show(WordBookBean wb) {
		labWord.setText(wb.getWord());
		labPhonetic.setText(wb.getPhonetic());
		txtTrans.setText(wb.getTrans());
		JacobUtil.textToSpeech(wb.getWord());
		JacobUtil.textToSpeech(wb.getTrans());


	}

}
