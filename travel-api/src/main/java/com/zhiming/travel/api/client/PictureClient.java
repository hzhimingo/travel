package com.zhiming.travel.api.client;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("travel-picture-service")
public interface PictureClient {
    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<PictureDTO> uploadSinglePicture(@RequestPart("picture") MultipartFile picture);
    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<List<PictureDTO>> uploadMulPicture(@RequestPart("pictures") MultipartFile[] pictures);
    @GetMapping("/{pictureId}")
    Result<PictureDTO> fetchSinglePictureInfo(@PathVariable("pictureId") Long pictureId);
    @GetMapping("/list")
    Result<List<PictureDTO>> fetchMulPictureInfo(@RequestParam("pictures") Long[] pictures);
}
