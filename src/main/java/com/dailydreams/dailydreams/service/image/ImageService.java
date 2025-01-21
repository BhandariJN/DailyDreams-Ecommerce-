package com.dailydreams.dailydreams.service.image;

import com.dailydreams.dailydreams.Dtos.ImageDto;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Image;
import com.dailydreams.dailydreams.model.Product;
import com.dailydreams.dailydreams.repository.ImageRepository;
import com.dailydreams.dailydreams.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    public ImageService(ImageRepository imageRepository, IProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Image not found"+id));
    }

    @Override
    public void deleteImageById(Long id) {

        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()->{
            throw new ResourceNotFoundException("Image not found"+id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);


                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + image.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDtos.add(imageDto);


            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());

            }
        }
        return savedImageDtos;
    }

    @Override
    public void UpdateImage(MultipartFile file, Long imageId) {

        Image image =getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }


    }
}
