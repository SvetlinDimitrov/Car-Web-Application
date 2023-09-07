package com.example.mobilele.web;

import com.example.mobilele.services.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class InitData implements CommandLineRunner {
    private UserRoleService userRoleService;
    private UserServiceImp userServiceImp;
    private BrandServiceImp brandServiceImp;
    private ModelServiceImp modelServiceImp;
    private OfferServiceImp offerServiceImp;

    @Override
    public void run(String... args) throws Exception {
        userRoleService.initData();
        userServiceImp.initData();
        brandServiceImp.initData();
        modelServiceImp.initData();
        offerServiceImp.initData();

    }
}
