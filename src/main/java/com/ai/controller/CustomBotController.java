package com.ai.controller;


import com.ai.dto.ChatGPTRequest;
import com.ai.dto.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> chat(@RequestParam("prompt") String prompt){
        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        Map<String,Object> map1=new LinkedHashMap<>();
        map1.put("version","1.0");
        map1.put("prompt",prompt);
        map1.put("result",chatGptResponse.getChoices().get(0).getMessage().getContent());
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("company","HCLTech");
        map.put("team","tecresearch");
        map.put("name","Brijesh Nishad");
        map.put("role","FullStack Java Developer specialization with Iot and ai");
        map.put("email","bnlv1212@gmail.com");
        map.put("website","https://tecresearch.in");
        map1.put("developer",map);
        return ResponseEntity.status(HttpStatus.OK).body(map1);
    }
}
