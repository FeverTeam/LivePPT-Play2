package com.fever.liveppt.utils;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * 用于存放有关AWS服务的常用工具
 * @author 梁博文
 *
 */
public class AWSUtils {

	/**
	 * 组装东京S3Client
	 * @return
	 */
	public static AmazonS3 genTokyoS3(){
		AmazonS3 S3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
		Region tokyoRegion = Region.getRegion(Regions.AP_NORTHEAST_1);
		S3Client.setRegion(tokyoRegion);
		return S3Client;
	};

}
