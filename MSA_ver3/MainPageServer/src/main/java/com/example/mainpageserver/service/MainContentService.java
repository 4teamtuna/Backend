package com.example.mainpageserver.service;

import com.example.mainpageserver.entity.DetailsEntity;
import java.util.List;
import java.util.stream.Collectors;

import com.example.mainpageserver.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class MainContentService {

    @GetMapping("/images/{interest}")
    public List<String> getImagesByInterest(@PathVariable String interest) {
        List<DetailsEntity> details = DetailsRepository.findByTagsContaining(interest);
        return details.stream().map(DetailsEntity::getImageUrl).collect(Collectors.toList());
    }
}
