package com.accounts.web;

import com.accounts.api.model.FileRec;
import com.accounts.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * Created by pasha on 15.02.18.
 */
@Controller
@RequestMapping(value = "files")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("")
    public String uploadForm(Model model) {
        model.addAttribute("fileList", fileService.findAll());
        return "upload";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            return "redirect:/files";
        }

        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        FileRec fileRec = new FileRec();
        fileRec.setData(bytes);
        fileRec.setName(file.getOriginalFilename());
        fileService.save(fileRec);

        return "redirect:/files";
    }


}
