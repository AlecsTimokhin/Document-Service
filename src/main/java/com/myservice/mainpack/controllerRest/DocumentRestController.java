package com.myservice.mainpack.controllerRest;

import com.myservice.mainpack.dto.RestResponce;
import com.myservice.mainpack.exception.BadValidateException;
import com.myservice.mainpack.exception.ConflictException;
import com.myservice.mainpack.model.Document;
import com.myservice.mainpack.repository.DocumentRepository;
import com.myservice.mainpack.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/documents")
public class DocumentRestController {

    @Value("${goodDeleteDocument}")
    private String goodDeleteDocument;

    @Value("${badDeleteDocument}")
    private String badDeleteDocument;

    @Value("${badValidateDocument}")
    private String badValidateDocument;

    @Value("${goodAddDocument}")
    private String goodAddDocument;

    private DocumentService documentService;
    public DocumentService getDocumentService() { return documentService; }
    @Autowired
    public void setDocumentService(DocumentService documentService) { this.documentService = documentService; }




    @GetMapping
    public ResponseEntity<List<Document>> getDocuments(){
        return ResponseEntity.status(200).body( documentService.getAll() );
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestResponce addDocument(@Valid Document document,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "file", required = false) MultipartFile file) {

        if( bindingResult.hasErrors() || file == null || "".equals( file.getOriginalFilename() ) ){
            throw new BadValidateException( new RestResponce(badValidateDocument, "error") );
        }

        documentService.saveDocument(document, file);

        return new RestResponce(goodAddDocument, "good");

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestResponce deleteDocument(@PathVariable("id") Long id) {

        if( documentService.deleteDocument(id) ){
            return new RestResponce(goodDeleteDocument, "good");
        }
        else{
            return new RestResponce(badDeleteDocument, "error");
        }

    }


}
