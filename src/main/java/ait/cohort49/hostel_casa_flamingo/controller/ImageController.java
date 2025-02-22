package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.ImageInfoDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.service.BedServiceImpl;
import ait.cohort49.hostel_casa_flamingo.service.RoomServiceImpl;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private final ImageService imageService;
    private final BedServiceImpl bedService;
    private final RoomServiceImpl roomService;

    public ImageController(ImageService imageService, BedServiceImpl bedService, RoomServiceImpl roomService) {
        this.imageService = imageService;
        this.bedService = bedService;
        this.roomService = roomService;
    }

    @PostMapping(value = "/upload/bed/{bedId}", consumes = "multipart/form-data", produces = "text/plain")
    @Operation(
            summary = "Upload an image for a specific bed",
            description = "Allows the upload of an image for a specified bed. Only PNG and JPEG formats are supported, and the maximum file size is 10 MB.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "File to upload",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data"
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image uploaded successfully. Returns a URL to the uploaded image.",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(value = "https://example.com/images/bed/12345.png")
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "File is not provided or is empty"),
                    @ApiResponse(responseCode = "415", description = "Invalid file format. Only PNG and JPEG are supported"),
                    @ApiResponse(responseCode = "413", description = "File size exceeds the allowed limit (10 MB)")
            }
    )
    public String uploadImageForBed(@Parameter(description = "ID of the bed to which the image will be uploaded", required = true)
                                    @PathVariable Long bedId,

                                    @Parameter(description = "Image file to upload. Only PNG and JPEG are accepted.", required = true)
                                    @RequestParam("file") MultipartFile file) {
        // 1. Check if the file is provided
        if (file == null || file.isEmpty()) {
            throw new RestException(HttpStatus.BAD_REQUEST, "File is not provided or is empty.");
        }

        // 2. Validate the file type (e.g., only images)
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/png") || contentType.equals("image/jpeg"))) {
            throw new RestException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Invalid file format. Only PNG and JPEG are supported.");
        }

        // 3. Validate the file size (e.g., no more than 10 MB)
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RestException(HttpStatus.PAYLOAD_TOO_LARGE, "File size exceeds the allowed limit (10 MB).");
        }

        Bed bed = bedService.getBedOrThrow(bedId);
        return imageService.uploadImageForBed(file, bed);
    }

    /**
     * Загрузка изображения для комнаты (Room)
     */
    @PostMapping(value = "/upload/room/{roomId}", consumes = "multipart/form-data", produces = "text/plain")
    @Operation(
            summary = "Upload an image for a specific bed",
            description = "Allows the upload of an image for a specified room. Only PNG and JPEG formats are supported, and the maximum file size is 10 MB.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "File to upload",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data"
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image uploaded successfully. Returns a URL to the uploaded image.",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(value = "https://example.com/images/bed/12345.png")
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "File is not provided or is empty"),
                    @ApiResponse(responseCode = "415", description = "Invalid file format. Only PNG and JPEG are supported"),
                    @ApiResponse(responseCode = "413", description = "File size exceeds the allowed limit (10 MB)")
            }
    )
    public String uploadImageForRoom(@Parameter(description = "ID of the bed to which the image will be uploaded", required = true)
                                     @PathVariable Long roomId,

                                     @Parameter(description = "Image file to upload. Only PNG and JPEG are accepted.", required = true)
                                     @RequestParam("file")
                                     MultipartFile file) {
        // 1. Check if the file is provided
        if (file == null || file.isEmpty()) {
            throw new RestException(HttpStatus.BAD_REQUEST, "File is not provided or is empty.");
        }

        // 2. Validate the file type (e.g., only images)
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/png") || contentType.equals("image/jpeg"))) {
            throw new RestException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Invalid file format. Only PNG and JPEG are supported.");
        }

        // 3. Validate the file size (e.g., no more than 10 MB)
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RestException(HttpStatus.PAYLOAD_TOO_LARGE, "File size exceeds the allowed limit (10 MB).");
        }

        Room room = roomService.findByIdOrThrow(roomId);
        return imageService.uploadImageForRoom(file, room);
    }

    /**
     * Получение изображений для кровати
     */
    @GetMapping("/bed/{bedId}")
    public List<ImageInfoDto> getImagesByBed(@PathVariable Long bedId) {
        return imageService.getImagesByBed(bedId);
    }

    /**
     * Получение изображений для комнаты
     */
    @GetMapping("/room/{roomId}")
    public List<ImageInfoDto> getImagesByRoom(@PathVariable Long roomId) {
        return imageService.getImagesByRoom(roomId);
    }

    /**
     * Удаление изображения по ID
     */
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }

    /**
     * Получение Presigned URL для изображения по ID
     */
    @GetMapping("/presigned/{imageId}")
    public String getImageUrl(@PathVariable Long imageId) {
        return imageService.getImageUrl(imageId);
    }
}
