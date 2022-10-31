package com.hcmute.myanime.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DigitalOceanSpaceService {

    private final AmazonS3 space;

    public DigitalOceanSpaceService() {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        "DO00HP88FXFT9NV4DXTL",
                        "MwvI89UN/dKUsD36J9pvPnGYFFSyxamfDbAVpmrEd7s"
                )
        );

        space = AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "sgp1.digitaloceanspaces.com",
                                "sgp1")
                )
                .build();
    }

    public List<String> getFileNames() {

        ListObjectsV2Result result = space.listObjectsV2("myanime");
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        objects.stream()
                .forEach(s3ObjectSummary -> {
                    System.out.println(s3ObjectSummary.toString());
                });
        return objects.stream()
                .map(s3ObjectSummary -> {
                    return s3ObjectSummary.getKey();
                }).collect(Collectors.toList());
    }

    public void uploadFile(MultipartFile file, String imgName, String directory) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        space.putObject(
                new PutObjectRequest(
                        "myanime/" + directory,
                        imgName,
                        file.getInputStream(),
                        objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead)
        );
    }

}
