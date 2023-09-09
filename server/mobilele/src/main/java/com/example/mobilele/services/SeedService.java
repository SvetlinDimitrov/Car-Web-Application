package com.example.mobilele.services;

import com.example.mobilele.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class SeedService {
    protected abstract Boolean isEmpty();
    protected abstract void seed() throws NotFoundException;

    public void initData(){
        if(isEmpty()){
            seed();
        }
    }
}
