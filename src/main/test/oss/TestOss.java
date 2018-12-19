package oss;/**
 * Created by chenjia on 2018/12/19.
 */

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author chenjia
 * @create 2018-12-19 10:29
 * @desc
 **/
public class TestOss {

    public static void main(String[] args) throws Exception {

//        createBucketName();
        uploadFile();

    }


    /**
     * 创建空间
     */
    public static void createBucketName(){

        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com/";
        String accessKeyId = "LTAI5djljr6H3GI5";
        String accessKeySecret = "ZpM73FexeX0bXr91naOSzITtkZJcWf";
        String bucketName = "register-test";

        //创建ossClient是咧
        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        ossClient.createBucket(bucketName);
        ossClient.shutdown();

    }


    /**
     * 上传文件流
     */
    public static void uploadFile() throws Exception{

        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com/";
        String accessKeyId = "LTAI5djljr6H3GI5";
        String accessKeySecret = "ZpM73FexeX0bXr91naOSzITtkZJcWf";
        String bucketName = "register-test";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        InputStream inputStream = new FileInputStream("E:\\111.jpg");

        PutObjectResult putObjectResult = ossClient.putObject(bucketName, "baiyunqu/2018/421127199104203730/11.jpg",inputStream);
        System.out.println(JSONObject.toJSON(putObjectResult).toString());
        ossClient.shutdown();


    }


}
