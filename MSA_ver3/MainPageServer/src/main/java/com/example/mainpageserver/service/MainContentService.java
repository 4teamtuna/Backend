package com.example.mainpageserver.service;

import com.example.mainpageserver.entity.DetailsEntity;
import com.example.mainpageserver.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainContentService {

    private final DetailsRepository detailsRepository;

    @Autowired
    public MainContentService(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @GetMapping("/images/{interest}")
    public List<String> getImagesByInterest(@PathVariable String interest) {
        List<DetailsEntity> details = detailsRepository.findByTagsContaining(interest);
        return details.stream().map(DetailsEntity::getImageUrl).collect(Collectors.toList());
    }
}