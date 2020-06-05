package com.cfets.cide.plugin.game.ew.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 抽象的 Dialog
 * 
 * @author TangTao
 *
 */
public class AbstractDialog {

	protected Shell shell;

	public void open() {
		// 打开页面
		shell.open();

		while (!shell.isDisposed()) {// 窗口帧是否关闭
			if (!shell.getDisplay().readAndDispatch()) {// 是否还有任务未完成
				shell.getDisplay().sleep();
			}
		}
		shell.dispose();// 释放系统资源
	}

	public void setVisible(boolean visible) {
		shell.setVisible(visible);
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		shell.dispose();// 释放系统资源
	}

	/**
	 * 页面是否销毁
	 * 
	 * @return
	 */
	public boolean isDisposed() {
		return shell.isDisposed();
	}

	// 移动位置~
	protected Point movePoint = null;

	/**
	 * 设置窗口可移动
	 */
	protected void setShellMove() {
		Control[] children = shell.getChildren();
		for (Control control : children) {
			if (control instanceof Composite) {
				control.addListener(SWT.MouseDown, e -> movePoint = new Point(e.x, e.y));
				control.addListener(SWT.MouseUp, e -> movePoint = null);
				control.addListener(SWT.MouseMove, e -> {
					if ((null != movePoint)) {
						Point point = Display.getDefault().map(shell, null, e.x, e.y);
						shell.setLocation(point.x - movePoint.x, point.y - movePoint.y);
						// System.out.println("point == " + point + "\tmovePoint == " + movePoint);
					}
				});
			}
		}
	}

}
