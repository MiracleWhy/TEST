package com.uton.carsokApi.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 七牛图片上传
 *
 * @author Administrator
 */
public class UploadUtil {
    protected static String ACCESS_KEY = "odMtT9s1fMYdHRYDK5RVlHizm4UnfAgET1bqOBBx";

    protected static String SECRET_KEY = "Gfk9b7ww_cx6K0kEcw0rwVAbkKHEcKBEYckux2bV";

    protected static String BUCKETNAME = "utoncars";

    // 秘钥配置
    static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    static Zone z = Zone.autoZone();
    static Configuration c = new Configuration(z);
    // 创建上传对象
    static UploadManager uploadManager = new UploadManager(c);

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        return auth.uploadToken(BUCKETNAME);
    }

    /**
     * 图片上传
     *
     * @return
     * @throws IOException
     */
    public static String upload(String filePath) throws IOException {
        try {
            // 调用put方法上传
            Response res = uploadManager.put(filePath, null, getUpToken());
            StringMap resmap = res.jsonToMap();
            return resmap.get("key").toString();
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            if (r != null) {
                System.out.println(r.toString());
            }
            try {
                // 响应的文本信息
                if (r != null) {
                    System.out.println(r.bodyString());
                }
            } catch (QiniuException e1) {
                // ignore
            }
        }
        return null;
    }

    public static boolean generateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    public static void deleteFiles(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            file.delete();
        }
    }
}
