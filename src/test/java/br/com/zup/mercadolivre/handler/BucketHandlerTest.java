package br.com.zup.mercadolivre.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.net.URI;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {BucketHandler.class})
@ExtendWith(SpringExtension.class)
public class BucketHandlerTest {
    @Autowired
    private BucketHandler bucketHandler;

    @Test
    public void testConnectS3() {
        CreateBucketRequest actualConnectS3Result = this.bucketHandler.connectS3();

        assertNull(actualConnectS3Result.objectLockEnabledForBucket());
        assertNull(actualConnectS3Result.grantWrite());
        assertNull(actualConnectS3Result.grantRead());
        assertNull(actualConnectS3Result.grantReadACP());
        assertNull(actualConnectS3Result.grantWriteACP());
        assertNull(actualConnectS3Result.grantFullControl());
        assertEquals("CreateBucketConfiguration(LocationConstraint=us-east-1)",
                actualConnectS3Result.createBucketConfiguration().toString());
    }

    @Test
    public void testConnectS32() {
        CreateBucketRequest actualConnectS3Result = this.bucketHandler.connectS3();

        assertNull(actualConnectS3Result.objectLockEnabledForBucket());
        assertNull(actualConnectS3Result.grantWrite());
        assertNull(actualConnectS3Result.grantRead());
        assertNull(actualConnectS3Result.grantReadACP());
        assertNull(actualConnectS3Result.grantWriteACP());
        assertNull(actualConnectS3Result.grantFullControl());
        assertEquals("CreateBucketConfiguration(LocationConstraint=us-east-1)",
                actualConnectS3Result.createBucketConfiguration().toString());
    }

    @Test
    public void testSetRegion() {
        this.bucketHandler.setRegion(null);

        assertNull(this.bucketHandler.getRegion());
    }

    @Test
    public void testSetEndpointOverride() {
        URI toUriResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

        this.bucketHandler.setEndpointOverride(toUriResult);

        assertSame(toUriResult, this.bucketHandler.getEndpointOverride());
    }

    @Test
    public void testSetEndpointOverride2() {
        URI toUriResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

        this.bucketHandler.setEndpointOverride(toUriResult);

        assertSame(toUriResult, this.bucketHandler.getEndpointOverride());
    }
}

