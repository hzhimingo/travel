package com.zhiming.travel.picture.controller;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.picture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @author HuangZhiMing
 */
@RestController
public class PictureController {

    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/{pictureId}")
    public Result<PictureDTO> fetchSinglePicture(@PathVariable("pictureId") @NotNull Long pictureId) {
        PictureDTO result = pictureService.obtainSinglePicture(pictureId);
        return ResultUtil.ok(result);
    }

    @GetMapping("/list")
    public Result<List<PictureDTO>> fetchPictures(@NotNull Long[] pictures) {
        List<PictureDTO> result = pictureService.obtainMultiplePicture(pictures);
        return ResultUtil.ok(result);
    }

    @PostMapping("/single")
    public Result<PictureDTO> uploadSinglePicture(@NotNull MultipartFile picture) throws IOException {
        PictureDTO result = pictureService.uploadSinglePicture(picture);
        return ResultUtil.ok(result);
    }

    @PostMapping("/multiple")
    public Result<List<PictureDTO>> uploadMulPicture(@NotNull MultipartFile[] pictures) throws IOException {
        List<PictureDTO> result = pictureService.uploadMultiplePicture(pictures);
        return ResultUtil.ok(result);
    }
}
