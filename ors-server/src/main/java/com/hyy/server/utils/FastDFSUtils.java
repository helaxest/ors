package com.hyy.server.utils;


import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description
 * FastDFS工具类
 * @author helaxest
 * @date 2021/03/27  9:59
 * @since
 */
public class FastDFSUtils {
    private static Logger logger= LoggerFactory.getLogger(FastDFSUtils.class);
    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            logger.error("初始化fastDFS失败",e.getMessage());
        }
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String[] upload(MultipartFile file){
        String name = file.getOriginalFilename();
        logger.info("文件名",name);
        StorageClient storageClient=null;
        String[] uploadResult=null;
        try {
            //获取Storage客户端
           storageClient=getStorageClient();
           //上传
           uploadResult = storageClient.upload_file(file.getBytes(), name.substring(name.lastIndexOf(".") + 1), null);

        } catch (Exception e) {
           logger.error("上传文件失败",e.getMessage());
        }
        if(null==uploadResult){
            logger.error("上传失败",storageClient.getErrorCode());
        }
        return uploadResult;
    }

    /**
     * 获取文件信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFileInfo(String groupName,String remoteFileName){
        StorageClient storageClient=null;

        try {
           storageClient= getStorageClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("文件获取失败",storageClient.getErrorCode());
        }
        return null;
    }

    /**
     * 下载文件
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName,String remoteFileName){
        StorageClient storageClient=null;

        try {
            storageClient= getStorageClient();
            byte[] fileBytes = storageClient.download_file(groupName, remoteFileName);
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            return inputStream;
        } catch (Exception e) {
            logger.error("文件下载失败",storageClient.getErrorCode());
        }
        return null;
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     */
    public static  void deleteFile(String groupName,String remoteFileName){
        StorageClient storageClient=null;

        try {
            storageClient= getStorageClient();
           storageClient.delete_file(groupName,remoteFileName);
        } catch (Exception e) {
            logger.error("文件删除失败",storageClient.getErrorCode());
        }
    }


    /**
     * 生成Storage客户端
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    /**
     * 生成tracker服务器
     * @return
     * @throws IOException
     */

    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return trackerServer;
    }

    /**
     * 获取文件路径
     * @return
     */
    public static String getTrackerUrl(){
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=null;
        StorageServer storeStorage=null;
        try {
            trackerServer = trackerClient.getTrackerServer();
            storeStorage = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e) {
            logger.error("文件路径获取失败",e.getMessage());
        }
        return "http://"+storeStorage.getInetSocketAddress().getHostString()+":8888/";
    }

}
