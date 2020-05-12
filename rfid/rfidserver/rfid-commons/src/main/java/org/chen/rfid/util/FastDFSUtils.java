package org.chen.rfid.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

/**
 * @author 言少钱
 * @date 2020年05月12日 21:09
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
public class FastDFSUtils {
    private static StorageClient1 client1;

    static {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            client1 = new StorageClient1(trackerServer, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static String upload(MultipartFile file) {
        String oldName = file.getOriginalFilename();
        try {
            return client1.upload_file1(file.getBytes(), oldName.substring(oldName.lastIndexOf(".") + 1), null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取带令牌的文件地址（注意令牌有效期900s）
     * @param fileId
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws MyException
     */
    public static String getFileAddressWithToken(String fileId) throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
        String remote_filename = fileId.substring("group1/".length());
        int ts = (int) Instant.now().getEpochSecond();
        String token = ProtoCommon.getToken(remote_filename, ts, Constants.FILE_WEB_TOKEN);
        StringBuffer buffer = new StringBuffer();
        buffer.append(Constants.FILE_WEB_URL);
        buffer.append(fileId);
        buffer.append("?token=").append(token);
        buffer.append("&ts=").append(ts);
        return buffer.toString();
    }

    // public static void main(String[] args) {
    //     System.out.println("group1/M00/00/00/wKgB8166ozCALZu5AAVo9EWUTMw443.jpg".substring("group1/".length()));
    // }
}
