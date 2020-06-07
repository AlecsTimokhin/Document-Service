package com.myservice.mainpack.controller;

import com.myservice.mainpack.dto.RestResponce;
import com.myservice.mainpack.exception.BadValidateException;
import com.myservice.mainpack.exception.ConflictException;
import com.myservice.mainpack.model.Document;
import com.myservice.mainpack.repository.DocumentRepository;
import com.myservice.mainpack.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class DocumentController {

    @Value("${OneDocumentTitle}")
    private String OneDocumentTitle;

    @Value("${DocumentListTitle}")
    private String DocumentListTitle;

    @Value("${AddDocumentTitle}")
    private String AddDocumentTitle;

    private DocumentService documentService;
    public DocumentService getDocumentService() { return documentService; }
    @Autowired
    public void setDocumentService(DocumentService documentService) { this.documentService = documentService; }



    @GetMapping("/documentsAll")
    public String getAllDocumentsUI(Model model){
        model.addAttribute("documentList", documentService.getAll());
        model.addAttribute("countDocuments", documentService.getAllCount());
        model.addAttribute("title", DocumentListTitle);
        return "documents/documentList";
    }


    @GetMapping("/documents/{id}")
    public String getOneDocument(@PathVariable("id") Long id, Model model){
        Document document = documentService.getOne(id);
        model.addAttribute("document", document);
        model.addAttribute("title", OneDocumentTitle + " (" + id + ")");
        return "documents/showOneDocument";
    }


    @GetMapping("/documents/add")
    public String addDocumentsGet(Model model, HttpServletRequest request){

        boolean canDoActions = (boolean) request.getSession().getAttribute("canDoActions");

        if( !canDoActions ) {
            return "redirect:/documents";
        }

        model.addAttribute("title", AddDocumentTitle);

        return "documents/addDocumentAdmin";

    }


}
