package com.example.mobilele.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class SeedService {
    protected abstract Boolean isEmpty();
    protected abstract void seed();

    public void initData(){
        if(isEmpty()){
            seed();
        }
    }
}
