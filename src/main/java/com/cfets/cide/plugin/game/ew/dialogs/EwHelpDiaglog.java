package com.cfets.cide.plugin.game.ew.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.cfets.cide.plugin.game.ew.utils.EwUtil;

/**
 * TangTao 帮组信息
 * 
 * @author TangTao
 *
 */
public class EwHelpDiaglog extends AbstractDialog {

	private final static String SHELL_TITLE = "EW";

	// 创建容器
	private Composite compContent;

	// 创建标签
	private Label labHelpIcon, labHelpInfo, labOk;

	// 设置帮组信息
	private final static String HELP_INFO = "介绍：背单词\n版本：CIDE-1.2.0\n提供：唐涛";

	public EwHelpDiaglog() {
		// 创建一个shell
		shell = new Shell(SWT.NONE);
		//初始化容器控件
		initComp();
		// 设置窗体可移动
		setShellMove();
	}

	/**
	 * 初始化容器控件
	 */
	private void initComp() {
		// 获取显示器的尺寸
		Rectangle bounds = Display.getCurrent().getBounds();
		shell.setText(SHELL_TITLE);
		shell.setImage(EwUtil.getImage("icons/ew_32.png"));
		
		// 设置Shell出现位置
		int leftMgn = bounds.width - 310, topMgn = bounds.height - 205;
		shell.setBounds(leftMgn, topMgn, 300, 100);
		shell.setLayout(new FillLayout());

		// 创建容器
		compContent = new Composite(shell, SWT.NONE);
		compContent.setBounds(shell.getBounds());

		// 创建帮组Icon
		labHelpIcon = new Label(compContent, SWT.LEFT | SWT.NO_BACKGROUND);
		labHelpIcon.setBounds(10, 25, 32, 32);
		labHelpIcon.setImage(EwUtil.getImage("icons/help_32.png"));

		// 创建帮组信息
		labHelpInfo = new Label(compContent, SWT.LEFT | SWT.NO_BACKGROUND);
		labHelpInfo.setBounds(70, 15, 100, 55);
		labHelpInfo.setText(HELP_INFO);

		// 确定按钮
		labOk = new Label(compContent, SWT.CENTER | SWT.NO_BACKGROUND);
		labOk.setBounds(240, 60, 32, 32);
		labOk.setImage(EwUtil.getImage("icons/ok_32.png"));
		labOk.setToolTipText("确定");
		// 关闭窗体
		labOk.addListener(SWT.MouseUp, e -> shell.dispose());

	}

}
