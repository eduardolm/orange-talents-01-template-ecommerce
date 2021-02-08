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
        // Arrange and Act
        CreateBucketRequest actualConnectS3Result = this.bucketHandler.connectS3();

        // Assert
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
    public void testSetEndpointOverride() {
        // Arrange
        URI toUriResult = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

        // Act
        this.bucketHandler.setEndpointOverride(toUriResult);

        // Assert
        assertSame(toUriResult, this.bucketHandler.getEndpointOverride());
    }
}

