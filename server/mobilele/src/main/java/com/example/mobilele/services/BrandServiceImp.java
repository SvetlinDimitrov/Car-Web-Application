package com.example.mobilele.services;

import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.repos.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImp extends SeedService {

    private BrandRepository brandRepository;

    @Override
    protected Boolean isEmpty() {
        return brandRepository.count() == 0;
    }

    @Override
    protected void seed() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        brandRepository.saveAll(List.of(
                Brand.builder()
                        .name("BMW")
                        .created(LocalDate.parse("1916-03-07", formatter))
                        .modified(LocalDate.parse("1924-07-11", formatter))
                        .build(),
                Brand.builder()
                        .name("Mercedes-Benz")
                        .created(LocalDate.parse("1926-02-18", formatter))
                        .modified(LocalDate.parse("1956-02-13", formatter))
                        .build(),
                Brand.builder()
                        .name("Audi")
                        .created(LocalDate.parse("1909-06-16", formatter))
                        .modified(null)
                        .build()
        ));
    }
}
