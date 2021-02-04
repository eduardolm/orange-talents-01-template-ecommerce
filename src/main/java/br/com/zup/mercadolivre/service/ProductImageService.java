package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.controller.request.ProductImageRequestDto;
import br.com.zup.mercadolivre.handler.BucketHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductImage;
import br.com.zup.mercadolivre.repository.ProductImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageService {

    BucketHandler bucket = new BucketHandler();
    private final CreateBucketRequest createBucketRequest = bucket.connectS3();

    @Autowired
    private ProductImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    @Value("${file.path}")
    private String filePath;

    @Value("${AWS_BUCKET_URL}")
    private String bucketUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductImageService.class);

    public void createImage(ProductImageRequestDto requestDto, Product product) throws IOException {
        ProductImage productImage = new ProductImage();

        try {
            for (MultipartFile image : requestDto.getImages()) {
                File file = fileService.convertMultiPartToFile(image);

                String imageAddress = this.uploadFileToS3Bucket(
                        this.createBucket(),
                        file,
                        this.filePath,
                        product);

                productImage.setLink(bucketUrl + imageAddress);
//                imageRepository.save(productImage);
            }
        }
        catch (IOException ex) {
            LOGGER.error("Erro ao enviar arquivo para a nuvem", ex);
        }


        // TODO: Acertar como vincular o produto e usuário ao arquivo a ser enviado.


    }

    public String createBucket(){

        List<String> bucketList = new ArrayList<>();
        this.bucket.listBucketStream().buckets().stream().forEach(x -> bucketList.add(x.name()));
        if (this.bucket.listBucketStream().buckets().stream().findAny().isEmpty()){
            return this.bucket.createBucket(this.createBucketRequest);
        }
        return bucketList.get(0);
    }

    public String uploadFileToS3Bucket(String bucketName, File file, String filePath,
                                       Product product) throws FileNotFoundException {

        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        LOGGER.info("Uploading file with name= " + uniqueFileName);
        String key = "_productId" + "_" + product.getId() + "_" + product.getProductOwner().getEmail() + "_" +  file.getName();
        this.bucket.getS3().putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(fileService.getObjectFile(filePath)));
        return bucketName + key;
    }

    public void listBuckets() {
        bucket.listBucketObjects(bucket.getS3(), "mercadolivre-bucket1612446310284");
        bucket.deleteAllBucketObjects("mercadolivre-bucket1612446310284");
    }


}
