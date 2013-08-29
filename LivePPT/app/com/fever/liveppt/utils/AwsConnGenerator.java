package com.fever.liveppt.utils;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

/**
 * AWS服务连接生成类
 *
 * @author 梁博文
 */
public class AwsConnGenerator {

    /**
     * 组装东京S3client
     *
     * @return
     */
    public static AmazonS3 genTokyoS3() {
        AmazonS3 S3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
        Region tokyoRegion = Region.getRegion(Regions.AP_NORTHEAST_1);
        S3Client.setRegion(tokyoRegion);
        return S3Client;
    }

    /**
     * 组装东京SNSclient
     *
     * @return
     */
    public static AmazonSNS genTokyoSNS() {
        AmazonSNSClient S3Client = new AmazonSNSClient(new ClasspathPropertiesFileCredentialsProvider());
        Region tokyoRegion = Region.getRegion(Regions.AP_NORTHEAST_1);
        S3Client.setRegion(tokyoRegion);
        return S3Client;
    }

    ;

    /**
     * 组装东京SQSclient
     *
     * @return
     */
    public static AmazonSQS genTokyoSQS() {
        AmazonSQSClient S3Client = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
        Region tokyoRegion = Region.getRegion(Regions.AP_NORTHEAST_1);
        S3Client.setRegion(tokyoRegion);
        return S3Client;
    }

}
