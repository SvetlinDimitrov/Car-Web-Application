package com.example.mobilele.services;

import com.example.mobilele.domain.constants.EngineType;
import com.example.mobilele.domain.constants.TransmissionType;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.repos.ModelRepository;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OfferServiceImp extends SeedService{

    private OfferRepository offerRepository;
    private ModelRepository modelRepository;
    private UserRepository userRepository;

    @Override
    protected Boolean isEmpty() {
        return offerRepository.count() ==0;
    }

    @Override
    protected void seed() {
        List<Model> models = modelRepository.findAll();
        List<User> users = userRepository.findAll();
        offerRepository.saveAll(List.of(
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(0).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("109890.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(0).getCreated())
                        .model(models.get(0))
                        .seller(users.get(0))
                        .build(),
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(1).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("209890.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(1).getCreated())
                        .model(models.get(1))
                        .seller(users.get(0))
                        .build(),
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(2).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("219890.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(2).getCreated())
                        .model(models.get(2))
                        .seller(users.get(0))
                        .build(),
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(3).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("9890.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(3).getCreated())
                        .model(models.get(3))
                        .seller(users.get(1))
                        .build(),
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(4).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("102290.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(4).getCreated())
                        .model(models.get(4))
                        .seller(users.get(1))
                        .build(),
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(4).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("312890.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(4).getCreated())
                        .model(models.get(4))
                        .seller(users.get(0))
                        .build(),
                Offer.builder()
                        .created(LocalDate.now())
                        .description("This car is perfect")
                        .engine(EngineType.DIESEL)
                        .imageUrl(models.get(5).getImageUrl())
                        .mileage(0)
                        .modified(LocalDate.now())
                        .price(new BigDecimal("409890.230"))
                        .transmission(TransmissionType.AUTOMATIC)
                        .year(models.get(5).getCreated())
                        .model(models.get(5))
                        .seller(users.get(1))
                        .build()
        ));
    }
}
