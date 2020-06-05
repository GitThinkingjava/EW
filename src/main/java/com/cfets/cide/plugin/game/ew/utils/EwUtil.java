package com.cfets.cide.plugin.game.ew.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.dom4j.Element;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.cfets.cide.plugin.game.ew.bean.WordBookBean;
import com.cfets.cide.plugin.util.utils.XmlUtil;

/**
 * English Word -背单词工具类
 * 
 * @author WAIHUI
 *
 */
public class EwUtil {
	
	//public final static String FILE_DIR =  Constant.CFETS_PATH + "/ew/";
	
	public final static String FILE_DIR = "src/main/resources/words/";
	
	//词库文件
	public final static String FILE_CET4_ELAY = FILE_DIR + "CET4-EALY.xml",FILE_CET4_HEAD = FILE_DIR +"CET4-HARD.xml",FILE_CET6 = FILE_DIR + "CET6.xml",FILE_CET_700 = FILE_DIR + "CORE-700.xml";
	
	//类型
	public final static String TYPE_CET4_EALY = "CET4-EALY",TYPE_CET4_HARD = "CET4-HARD", TYPE_CET6 = "CET-6",TYPE_CORE_700 = "CORE-700";
	
	public static Map<String, List<WordBookBean>> MAP_WORDS = new ConcurrentHashMap<String, List<WordBookBean>>();
	
	static {
		MAP_WORDS.put(TYPE_CET4_EALY, parserWordBook(FILE_CET4_ELAY));
		MAP_WORDS.put(TYPE_CET4_HARD, parserWordBook(FILE_CET4_HEAD));
		MAP_WORDS.put(TYPE_CET6, parserWordBook(FILE_CET6));
		MAP_WORDS.put(TYPE_CORE_700, parserWordBook(FILE_CET_700));
	}
	
	/**
	 * 解析词库
	 * @param filePath
	 * @return
	 */
	public static List<WordBookBean> parserWordBook(String filePath) {
		List<WordBookBean> itmes = new ArrayList<WordBookBean>();
		Element rootElement = XmlUtil.getDocument(filePath).getRootElement();
		List<Element> rootNodeList = XmlUtil.getChildNodeList(rootElement);
		for (Element root : rootNodeList) {
			WordBookBean wb = new WordBookBean();
			List<Element> itemElements = XmlUtil.getChildNodeList(root);
			for (Element element : itemElements) {
				String name = element.getName();
				String value = element.getText();
				if ("word".equals(name)) {
					wb.word(element.getText());
				} else if ("trans".equals(name)) {
					wb.setTrans(value);
				} else if ("phonetic".equals(name)) {
					wb.setPhonetic(value);
				} else if ("tags".equals(name)) {
					wb.setTags(value);
				}
			}
			itmes.add(wb);
		}
		return itmes.stream().sorted().collect(Collectors.toList());
	}
	
	
	/***
	 * 获取图片
	 * 
	 * @param args
	 */
	public static Image getImage(String imgPath) {
//		Image img = CfetsAbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, imgPath).createImage();
//		return img;
		return new Image(Display.getDefault(), imgPath);
	}
	
	 /**
     * 读取文件内容
     *
     * @param fileName
     * @return
     */
    public static String readFileAll(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return "";
        }
    }
	
}
