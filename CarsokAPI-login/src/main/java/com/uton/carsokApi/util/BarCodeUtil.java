package com.uton.carsokApi.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code128;


/**
 * 条形码
 * @author xjz
 *http://www.cnblogs.com/yuchuan/p/4250328.html
 */
@SuppressWarnings("deprecation")
public class BarCodeUtil {
	/**
	 * 获取条形码
	 * @param code 条形码内容
	 * @param imageType 图片类型（jpeg、png、gif...）
	 * @return 返回条形码图片路径
	 */
	public static String getBarcode(String code,String imageType,HttpServletRequest request){
		String resultStr = "";
		try {
			JBarcodeBean jb = new JBarcodeBean();
			jb.setCodeType(new Code128());// 条形码类型
			jb.setBarcodeHeight(30);// 条形码高度
			jb.setLabelPosition(JBarcodeBean.LABEL_BOTTOM);// 在条形码下面显示文字
			jb.setCode(code);
            
			String path = request.getSession().getServletContext().getRealPath("jbarcode");
			File filePath = new File(path);
			if(!filePath.exists() && !filePath.isDirectory()){
				filePath.mkdir();
			}
			String imageName = code + "." + imageType;//图片名称
			String imagePath = path + "/" + imageName;//图片路径
	    	OutputStream out = new FileOutputStream(imagePath);
            BufferedImage image = new BufferedImage(90, 50,  BufferedImage.TYPE_INT_RGB);
            image = jb.draw(image);
            ImageIO.write(image, "png", out);
            out.close();
	    	//上传到七牛服务器
            resultStr = UploadUtil.upload(imagePath);
	        System.out.println("条形码：" + resultStr);
	        deleteFiles(imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	public static void deleteFiles(String path){
	       File file = new File(path);
	       if(!file.isDirectory()){
	           file.delete();
	       }
		}
}
