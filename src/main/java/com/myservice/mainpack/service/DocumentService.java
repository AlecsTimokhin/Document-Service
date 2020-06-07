package com.myservice.mainpack.service;

import com.myservice.mainpack.dto.RestResponce;
import com.myservice.mainpack.exception.ConflictException;
import com.myservice.mainpack.exception.ProcessException;
import com.myservice.mainpack.model.Document;
import com.myservice.mainpack.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class DocumentService {

    @Value("${upload.path_Load}")
    private String uploadPathLoad;

    @Value("${conflict}")
    private String conflict;

    @Value("${badDeleteDocument}")
    private String badDeleteDocument;

    @Value("${badAddDocument}")
    private String badAddDocument;

    private DocumentRepository documentRepository;
    public DocumentRepository getDocumentRepository() { return documentRepository; }
    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) { this.documentRepository = documentRepository; }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDocument(Document document, MultipartFile file) {

        String uuidFile = UUID.randomUUID().toString();

        String resultFilename = uuidFile + "_" + file.getOriginalFilename();
        document.setSrc( resultFilename );

        try{
            documentRepository.save(document);
        }
        catch(Exception ex){
            throw new ConflictException( new RestResponce(conflict, "error") );
        }

        File uploadDir = new File(uploadPathLoad);
        if ( !uploadDir.exists() ) {
            uploadDir.mkdir();
        }

        try{
            //file.transferTo( new File(uploadDir + "/" + resultFilename) );
            file.transferTo( new File(uploadDir + "/" + resultFilename) );
        }
        catch(Exception ex){
            throw new ProcessException( new RestResponce(badAddDocument, "error") );
        }

    }


    public Document getOne(Long id){
        return documentRepository.findById(id).get();
    }


    public int getAllCount(){
        return documentRepository.getAllCount();
    }


    public List<Document> getAll(){
        return documentRepository.getAll();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteDocument(Long id) {

        Document document = documentRepository.findById(id).get();

        String filename = uploadPathLoad + "/" + document.getSrc();
        File file = new File(filename);
        if( !file.delete() ){
            throw new ProcessException( new RestResponce(badDeleteDocument, "error") );
        }

        try{
            documentRepository.deleteById(id);
            return true;
        }
        catch(Exception ex){
            throw new ProcessException( new RestResponce(badDeleteDocument, "error") );
        }

    }


}
