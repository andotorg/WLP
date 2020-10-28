package com.farm.sfile.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.farm.core.config.AppConfig;

 
public class PdfToImgConvertor {
	private static Queue<File> QUEUE = new LinkedList<File>();

	private static boolean isStart = false;

	public static File getImgDir(File pdfFile) {
		return new File(pdfFile.getParentFile().getPath() + File.separator + pdfFile.getName() + "-img");
	}

	 
	public synchronized static void waitingConvert(File pdffile) {
		if (pdffile.length() > 1024 * 1024 * 100) {
			// 大于100M不予转换
			return;
		}
		if (QUEUE.size() > 5000) {
			QUEUE.clear();
		}
		QUEUE.offer(pdffile);
		startConvert();
	}

	private static void startConvert() {
		if (!isStart) {
			isStart = true;
			// 实现runnable借口，创建多线程并启动
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							if (QUEUE.size() > 0) {
								File file = QUEUE.poll();
								doConvert(file, getImgDir(file));
							} else {
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}) {
			}.start();
		}
	}

	private final static Logger log = Logger.getLogger(PdfToImgConvertor.class);

	private static long CONVERT_MAX_TIME = 5 * 60 * 1000;

	public static void doConvert(File pdffile, File imgdir) {
		try {
			Date startDate = new Date();
			imgdir.mkdirs();
			// 清空目录
			FarmDocFiles.deleteFilesByDir(imgdir);
			PDDocument doc = PDDocument.load(pdffile);
			try {
				long timeout = (CONVERT_MAX_TIME - 5000) / 1000;
				PDFRenderer renderer = new PDFRenderer(doc);
				int pageCount = doc.getNumberOfPages();
				List<String> imgpaths = new ArrayList<>();
				// 最大DPI
				int DPI = 120;
				// 最小DPI
				int minDpi = 90;
				String logText1 = "pdf to png conf：max-dpi-" + DPI + "  min-dpi-" + minDpi + "  timeout-" + timeout
						+ " second";
				log.info(logText1);
				int dpiStep = (DPI - minDpi) / 8;
				if (pageCount > 10) {
					DPI = DPI - dpiStep;
				}
				if (pageCount > 20) {
					DPI = DPI - dpiStep;
				}
				if (pageCount > 30) {
					DPI = DPI - dpiStep;
				}
				if (pageCount > 40) {
					DPI = DPI - dpiStep;
				}
				// 写图片到文件夹中(图片必须未img0.png命名，必须以0为开始，因为html页面是从0开始得)
				int num = 0;
				for (int i = 0; i < pageCount; i++) {
					int innerDpi = DPI;
					if (i > 20) {
						innerDpi = DPI - 1 * dpiStep;
					}
					if (i > 45) {
						innerDpi = DPI - 2 * dpiStep;
					}
					if (i > 70) {
						innerDpi = DPI - 3 * dpiStep;
					}
					if (i > 90) {
						innerDpi = DPI - 4 * dpiStep;
					}
					BufferedImage image = renderer.renderImageWithDPI(i, innerDpi);
					ImageIO.write(image, "PNG", new File(imgdir.getPath() + "\\img" + i + ".png"));
					String logText2 = "DPI=" + innerDpi + " by" + num + "/" + pageCount + " file:\\img" + i + ".png";
					log.info(logText2);
					imgpaths.add("img" + i + ".png");
					num++;
					if (((new Date().getTime()) - startDate.getTime()) / 1000 > timeout) {
						log.info("转换时间大于超時時間，只转换部分页面!");
						break;
					}
				}
				String logText3 = "creat imgs num is:" + num + "/" + pageCount + " by time "
						+ ((new Date().getTime()) - startDate.getTime()) / 1000 + " s";
				log.info(logText3);
			} finally {
				doc.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
