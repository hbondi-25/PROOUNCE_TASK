package com.example.PROOUNCE_TASK.Controller;

import com.example.PROOUNCE_TASK.DTO.UniqueCharRequest;
import com.example.PROOUNCE_TASK.DTO.UniqueCharResponse;
import com.example.PROOUNCE_TASK.RespoceStucture;
import com.example.PROOUNCE_TASK.Service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    @Autowired
    private UtilsService utilsService; // Wires to the Service package

    @Autowired
    private RespoceStucture<UniqueCharResponse> structure;

    // Part D: POST /utils/first-unique-char [cite: 72]
    @PostMapping("/first-unique-char")
    public ResponseEntity<RespoceStucture<UniqueCharResponse>> getFirstUniqueChar(
            @RequestBody UniqueCharRequest request) {
        
    	/*
    	 http://localhost:8080/utils/first-unique-char
    	 i/p    - 1
    	 
    	 {
    "text": "swiss"
}
    	 
    	 o/p
    	 {
    "satuscode": 200,
    "message": "Calculation Complete",
    "data": {
        "firstUnique": "w"
    }
}

	i/p    - 2
	{
    "text": "aabbs"
}

o/p   - 2
{
    "satuscode": 200,
    "message": "Calculation Complete",
    "data": {
        "firstUnique": "s"
    }
}
    	 */
    	
        Character uniqueChar = utilsService.findFirstUniqueChar(request.getText());
        UniqueCharResponse responseData = new UniqueCharResponse(uniqueChar);
        
        structure.setSatuscode(HttpStatus.OK.value());
        structure.setMessage("Calculation Complete");
        structure.setData(responseData);
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
}