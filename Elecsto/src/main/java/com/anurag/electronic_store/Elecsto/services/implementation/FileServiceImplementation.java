package com.anurag.electronic_store.Elecsto.services.implementation;

import com.anurag.electronic_store.Elecsto.exceptions.BadApiRequestException;
import com.anurag.electronic_store.Elecsto.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImplementation.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        String orignalFileName = file.getOriginalFilename();
        logger.info("Filename : {}",orignalFileName);
        String filename = UUID.randomUUID().toString();

        //Doing this part to name something with sense i.e adding orignal file name without its extension (eg .png) in the randomly generated file name.
        String extension =  orignalFileName.substring(orignalFileName.lastIndexOf("."));
        String filenameWithExtension = filename + extension;

        String fullPathWithFilenameWithExtension = path + filenameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            File folder = new File(path);
            if(!folder.exists()){

                //create folder
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFilenameWithExtension));
            return filenameWithExtension;

        }
        else {
             throw  new BadApiRequestException("File with "+extension+" extension is not allowed");
        }
    }
}
