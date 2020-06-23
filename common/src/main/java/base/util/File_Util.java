package base.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class File_Util {
    private static Map<String, File> dirMap = new HashMap<>();

    /**
     * 上传图片
     * @param file 文件流对象
     * @param realpath 文件存放路径
     * @return
     */
    public static String inputUploadFile(MultipartFile file, String realpath, String name) {
        String filename = file.getOriginalFilename();//文件名createFileName(file.getOriginalFilename());//
        File dir = getDir(realpath);
        String extname = FilenameUtils.getExtension(filename);//文件扩展名
        String allowImgFormat = "gif,jpg,jpeg,png";
        if (!allowImgFormat.contains(extname.toLowerCase())) {
            return "NOT_IMAGE";
        }
        filename = name + "." + extname;
        InputStream input = null;
        FileOutputStream fos = null;
        try {
            input = file.getInputStream();
            fos = new FileOutputStream(realpath + "/" + filename);
            IOUtils.copy(input, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fos);
        }

        return filename;
    }

    private static String createFileName(String filename) {//重新改变文件名
        //FilenameUtils.getExtension(filename);//文件扩展名
        String dataReadom = String.valueOf(Math.floor(System.currentTimeMillis() / 1000));
        dataReadom += Random_Util.createRandomString(6);
        return dataReadom + FilenameUtils.getExtension(filename);
    }

    /**
     * 判断目录是否存在不存在则创建目录
     * @param dirName
     * @return
     */
    private static File getDir(@NonNull String dirName) {
        File dir = dirMap.get(dirName);
        if (dir != null) {
            return dir;
        }

        dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dirMap.put(dirName, dir);
        return dir;
    }

    /**
     * 获取网站域名地址
     * @param request
     * @return
     */
    public static String domain(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();// + path;
        return basePath;
    }
    /*获得文件MD5值*/
    public static String getFileMD5(File file) {
        if (!file.exists()||file.isDirectory()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
