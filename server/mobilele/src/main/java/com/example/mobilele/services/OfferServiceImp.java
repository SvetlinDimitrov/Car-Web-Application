package com.example.mobilele.services;

import com.example.mobilele.utils.EntityHelper;
import com.example.mobilele.utils.constants.EngineType;
import com.example.mobilele.utils.constants.TransmissionType;
import com.example.mobilele.domain.dtos.offer.OfferCreateDto;
import com.example.mobilele.domain.dtos.offer.OfferView;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.ModelRepository;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferServiceImp extends SeedService{

    private OfferRepository offerRepository;
    private ModelRepository modelRepository;
    private UserRepository userRepository;
    private EntityHelper entityHelper;

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

    @Transactional
    public OfferView getOfferViewById(String id) throws WrongCredentialsException, NotFoundException {
        return new OfferView(entityHelper.findOfferById(id));

    }
    @Transactional
    public List<OfferView> getAllOfferViews() {
        return offerRepository.findAll()
                .stream()
                .map(OfferView::new)
                .toList();
    }
    public void save(OfferCreateDto offerCreateDto , String userId) throws WrongCredentialsException, NotFoundException {
        Offer offer = offerCreateDto.toOffer();

        User user = entityHelper.findUserById(userId);
        Model model = entityHelper.findModelByName(offerCreateDto.getModelName());

        offer.setEngine(getEngineByName(offerCreateDto.getEngine()));
        offer.setTransmission(getTransmissionByName(offerCreateDto.getTransmission()));
        offer.setSeller(user);
        offer.setModel(model);

        offerRepository.save(offer);
    }



    private TransmissionType getTransmissionByName(String name) throws WrongCredentialsException {
        return Arrays.stream(TransmissionType.values())
                .filter(e->e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongCredentialsException("Does not have that type of transmission.\nAvailable types:"+
                        Arrays.stream(TransmissionType.values())
                                .map(Enum::name)
                                .collect(Collectors.joining(","))));
    }
    private EngineType getEngineByName(String name) throws WrongCredentialsException {
        return Arrays.stream(EngineType.values())
                .filter(e->e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongCredentialsException("Does not have that type of engine.\nAvailable types:"+
                        Arrays.stream(EngineType.values())
                                .map(Enum::name)
                                .collect(Collectors.joining(","))));
    }

}
