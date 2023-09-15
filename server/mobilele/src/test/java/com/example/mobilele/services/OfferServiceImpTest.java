package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.offer.*;
import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.utils.EntityHelper;
import com.example.mobilele.utils.constants.EngineType;
import com.example.mobilele.utils.constants.TransmissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceImpTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private EntityHelper entityHelper;
    @InjectMocks
    private OfferServiceImp offerServiceImp;

    @Captor
    private ArgumentCaptor<Offer> argumentCaptor;
    private final List<Offer> offers = new ArrayList<>();
    private final List<OfferView> offerViews = new ArrayList<>();
    private final OfferCreateDto offerCreateDto = new OfferCreateDto();
    private final OfferEditDto offerEditDto = new OfferEditDto();
    private final User seller1 = new User();
    private final User seller2 = new User();
    private final Model model1 = new Model();
    private final Model model2 = new Model();


    @BeforeEach
    void setUp() {
        UUID sellerId1 = UUID.randomUUID();
        UUID sellerId2 = UUID.randomUUID();

        UUID modelId1 = UUID.randomUUID();
        UUID modelId2 = UUID.randomUUID();

        UUID offerId1 = UUID.randomUUID();
        UUID offerId2 = UUID.randomUUID();

        UUID brandId1 = UUID.randomUUID();
        UUID brandId2 = UUID.randomUUID();

        Brand brand1 = Brand.builder()
                .id(brandId1)
                .name("testName1")
                .build();
        Brand brand2 = Brand.builder()
                .id(brandId2)
                .name("testName2")
                .build();

        seller1.setId(sellerId1);
        seller1.setUsername("testUsername1");
        seller1.setFirstName("testFirstName1");
        seller1.setLastName("testLastName1");

        seller2.setId(sellerId2);
        seller2.setUsername("testUsername2");
        seller2.setFirstName("testFirstName2");
        seller2.setLastName("testLastName2");

        model1.setId(modelId1);
        model1.setName("testModel1");
        model1.setImageUrl("somePictureHere");
        model1.setCreated(2000);
        model1.setBrand(brand1);

        model2.setId(modelId2);
        model2.setName("testModel2");
        model2.setImageUrl("somePictureHere");
        model2.setCreated(2000);
        model2.setBrand(brand2);

        offers.addAll(
                List.of(
                        Offer.builder()
                                .id(offerId1)
                                .created(LocalDate.now())
                                .description("This car is perfect")
                                .engine(EngineType.DIESEL)
                                .imageUrl(model1.getImageUrl())
                                .mileage(0)
                                .modified(LocalDate.now())
                                .price(new BigDecimal("10900"))
                                .transmission(TransmissionType.AUTOMATIC)
                                .year(model1.getCreated())
                                .model(model1)
                                .seller(seller1)
                                .build(),
                        Offer.builder()
                                .id(offerId2)
                                .created(LocalDate.now())
                                .description("This car is perfect")
                                .engine(EngineType.DIESEL)
                                .imageUrl(model2.getImageUrl())
                                .mileage(0)
                                .modified(LocalDate.now())
                                .price(new BigDecimal("10900"))
                                .transmission(TransmissionType.AUTOMATIC)
                                .year(model2.getCreated())
                                .model(model2)
                                .seller(seller2)
                                .build()
                ));
        seller1.setOfferList(List.of(offers.get(0)));
        seller2.setOfferList(List.of(offers.get(1)));

        OfferModelView offerModelView1 = new OfferModelView();
        offerModelView1.setId(modelId1.toString());
        offerModelView1.setName("testModel1");

        OfferModelView offerModelView2 = new OfferModelView();
        offerModelView2.setId(modelId2.toString());
        offerModelView2.setName("testModel2");

        OfferUserView offerUserView1 = new OfferUserView();
        offerUserView1.setId(sellerId1.toString());
        offerUserView1.setUsername("testUsername1");
        offerUserView1.setFirstName("testFirstName1");
        offerUserView1.setLastName("testLastName1");

        OfferUserView offerUserView2 = new OfferUserView();
        offerUserView2.setId(sellerId2.toString());
        offerUserView2.setUsername("testUsername2");
        offerUserView2.setFirstName("testFirstName2");
        offerUserView2.setLastName("testLastName2");

        OfferView offerView1 = new OfferView();
        offerView1.setId(offerId1.toString());
        offerView1.setDescription("This car is perfect");
        offerView1.setEngine("DIESEL");
        offerView1.setImageUrl(model1.getImageUrl());
        offerView1.setMileage(0);
        offerView1.setPrice("10900");
        offerView1.setTransmission("AUTOMATIC");
        offerView1.setYear(model1.getCreated());
        offerView1.setCreated(LocalDate.now().toString());
        offerView1.setModified("");
        offerView1.setModel(offerModelView1);
        offerView1.setModified(LocalDate.now().toString());
        offerView1.setSeller(offerUserView1);
        offerView1.setBrandName(brand1.getName());

        OfferView offerView2 = new OfferView();
        offerView2.setId(offerId2.toString());
        offerView2.setDescription("This car is perfect");
        offerView2.setEngine("DIESEL");
        offerView2.setImageUrl(model2.getImageUrl());
        offerView2.setMileage(0);
        offerView2.setPrice("10900");
        offerView2.setTransmission("AUTOMATIC");
        offerView2.setYear(model2.getCreated());
        offerView2.setModified(LocalDate.now().toString());
        offerView2.setCreated(LocalDate.now().toString());
        offerView2.setModel(offerModelView2);
        offerView2.setSeller(offerUserView2);
        offerView2.setBrandName(brand2.getName());

        offerViews.addAll(List.of(offerView1, offerView2));

        offerCreateDto.setDescription("descriptionTest");
        offerCreateDto.setEngine("GASOLINE");
        offerCreateDto.setMileage(120);
        offerCreateDto.setPrice(new BigDecimal("500000"));
        offerCreateDto.setTransmission("MANUAL");
        offerCreateDto.setYear(2023);
        offerCreateDto.setModelName(model1.getName());

        offerEditDto.setDescription("descriptionTest");
        offerEditDto.setEngine("GASOLINE");
        offerEditDto.setMileage(120);
        offerEditDto.setPrice("500000");
        offerEditDto.setTransmission("MANUAL");
        offerEditDto.setYear(2023);
        offerEditDto.setModelName(model1.getName());
    }

    @Test
    void getOfferViewById_ConvertToView_Correctly() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(1);
        OfferView expected = offerViews.get(1);

        when(entityHelper.findOfferById(offer.getId().toString())).thenReturn(offer);

        OfferView result = offerServiceImp.getOfferViewById(offer.getId().toString());

        assertEquals(expected, result);
    }

    @Test
    void getAllOfferViews_ConvertToView_Correctly() {
        when(offerRepository.findAll()).thenReturn(offers);

        List<OfferView> result = offerServiceImp.getAllOfferViews();

        assertEquals(offerViews, result);
    }

    @Test
    void testGetAllOfferViews_ConvertToViewGetCorrectOfferByUserId_Correctly() throws NotFoundException, WrongCredentialsException {
        String userId = seller1.getId().toString();
        List<OfferView> expected = new ArrayList<>(List.of(offerViews.get(0)));

        when(entityHelper.findUserById(userId)).thenReturn(seller1);

        List<OfferView> result = offerServiceImp.getAllOfferViews(userId);

        assertEquals(expected, result);
    }

    @Test
    void save_CreateOfferWithValidBody_Successful() throws NotFoundException, WrongCredentialsException {
        String userId = seller1.getId().toString();
        String modelName = model1.getName();

        when(entityHelper.findUserById(userId)).thenReturn(seller1);
        when(entityHelper.findModelByName(modelName)).thenReturn(model1);

        offerServiceImp.save(offerCreateDto , userId);

        verify(offerRepository , times(1)).save(argumentCaptor.capture());
        Offer result = argumentCaptor.getValue();

        assertEquals(offerCreateDto.getDescription() , result.getDescription());
        assertEquals(offerCreateDto.getEngine() , result.getEngine().name());
        assertEquals(offerCreateDto.getMileage() , result.getMileage());
        assertEquals(offerCreateDto.getPrice() , result.getPrice());
        assertEquals(offerCreateDto.getTransmission() , result.getTransmission().name());
        assertEquals(offerCreateDto.getYear() , result.getYear());
        assertEquals(offerCreateDto.getModelName() , result.getModel().getName());
    }

    @Test
    void save_CreateOfferWithInvalidBody_throwsWrongCredentialsException() throws NotFoundException, WrongCredentialsException {
        String userId = seller1.getId().toString();
        String modelName = model1.getName();
        OfferCreateDto invalidBody1 = new OfferCreateDto();
        invalidBody1.setModelName(modelName);
        invalidBody1.setTransmission("something wrong");

        when(entityHelper.findUserById(userId)).thenReturn(seller1);
        when(entityHelper.findModelByName(modelName)).thenReturn(model1);

        assertThrows(WrongCredentialsException.class ,
                () ->offerServiceImp.save(invalidBody1 , userId));

        OfferCreateDto invalidBody2 = new OfferCreateDto();
        invalidBody2.setEngine("something wrong");
        invalidBody2.setModelName(modelName);

        assertThrows(WrongCredentialsException.class ,
                () ->offerServiceImp.save(invalidBody2 , userId));

    }
    @Test
    void edit_ValidBody_Successful() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(1);
        String modelName = offerEditDto.getModelName();
        String offerId = offer.getId().toString();

        when(entityHelper.findOfferById(offerId)).thenReturn(offer);
        when(entityHelper.findModelByName(modelName)).thenReturn(model1);

        OfferView result = offerServiceImp.edit(offerEditDto, offerId);

        assertEquals(offerEditDto.getDescription() , result.getDescription());
        assertEquals(offerEditDto.getEngine() , result.getEngine());
        assertEquals(offerEditDto.getMileage() , result.getMileage());
        assertEquals(offerEditDto.getPrice() , result.getPrice());
        assertEquals(offerEditDto.getTransmission() , result.getTransmission());
        assertEquals(offerEditDto.getYear() , result.getYear());
        assertEquals(offerEditDto.getModelName() , result.getModel().getName());
    }

    @Test
    void edit_EmptyBody_Successful() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(1);
        String offerId = offer.getId().toString();
        OfferEditDto editDto = new OfferEditDto();

        when(entityHelper.findOfferById(offerId)).thenReturn(offer);
        assertDoesNotThrow(() -> offerServiceImp.edit(editDto, offerId));
    }

    @Test
    void edit_InvalidBody_throwsWrongCredentialsException() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(1);
        String offerId = offer.getId().toString();
        OfferEditDto invalidDescriptionSize = new OfferEditDto();
        invalidDescriptionSize.setDescription("invalid");

        when(entityHelper.findOfferById(offerId)).thenReturn(offer);

        assertThrows(WrongCredentialsException.class,() -> offerServiceImp.edit(invalidDescriptionSize, offerId));

        OfferEditDto invalidEngineType = new OfferEditDto();
        invalidEngineType.setEngine("invalid");

        assertThrows(WrongCredentialsException.class,() -> offerServiceImp.edit(invalidEngineType, offerId));

        OfferEditDto negativeMileage = new OfferEditDto();
        negativeMileage.setMileage(-1);

        assertThrows(WrongCredentialsException.class,() -> offerServiceImp.edit(negativeMileage, offerId));

        OfferEditDto negativePrice = new OfferEditDto();
        negativePrice.setEngine("-12");

        assertThrows(WrongCredentialsException.class,() -> offerServiceImp.edit(negativePrice, offerId));

        OfferEditDto invalidTransmission = new OfferEditDto();
        invalidTransmission.setTransmission("-12");

        assertThrows(WrongCredentialsException.class,() -> offerServiceImp.edit(invalidTransmission, offerId));

        OfferEditDto yearInTheFuture = new OfferEditDto();
        yearInTheFuture.setYear(999999999);

        assertThrows(WrongCredentialsException.class,() -> offerServiceImp.edit(yearInTheFuture, offerId));


    }

    @Test
    void deleteOffer_deleteTheCorrectEntity_Successful() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(0);
        String offerId = offer.getId().toString();

        when(entityHelper.findOfferById(offerId)).thenReturn(offer);

        offerServiceImp.deleteOffer(offerId);

        verify(offerRepository , times(1)).delete(argumentCaptor.capture());
        Offer result = argumentCaptor.getValue();

        assertEquals(offer , result);
    }

    @Test
    void userContainOffer_ContainsTheOffer_True() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(0);
        String offerId = offer.getId().toString();
        String userId = seller1.getId().toString();

        when(entityHelper.findUserById(userId)).thenReturn(seller1);

        assertTrue(offerServiceImp.userContainOffer(userId , offerId));
    }
    @Test
    void userContainOffer_DoesNotContainsTheOffer_False() throws NotFoundException, WrongCredentialsException {
        Offer offer = offers.get(1);
        String offerId = offer.getId().toString();
        String userId = seller1.getId().toString();

        when(entityHelper.findUserById(userId)).thenReturn(seller1);

        assertFalse(offerServiceImp.userContainOffer(userId , offerId));
    }
}