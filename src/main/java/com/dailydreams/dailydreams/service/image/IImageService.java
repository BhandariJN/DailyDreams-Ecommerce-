package com.dailydreams.dailydreams.service.image;

import com.dailydreams.dailydreams.Dtos.ImageDto;
import com.dailydreams.dailydreams.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IImageService {
Image getImageById(Long imageId);
void deleteImageById(Long id);

List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
void UpdateImage(MultipartFile file, Long imageId);


}
