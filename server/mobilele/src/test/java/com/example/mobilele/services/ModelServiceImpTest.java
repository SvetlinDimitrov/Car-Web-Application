package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.model.*;
import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.ModelRepository;
import com.example.mobilele.utils.constants.ModelCategory;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelServiceImpTest {
    @Mock
    private ModelRepository modelRepository;
    @Mock
    private EntityHelper entityHelper;
    @InjectMocks
    private ModelServiceImp modelService;

    @Captor
    private ArgumentCaptor<Model> argumentCaptor;

    private final List<Model> modelList = new ArrayList<>();
    private final List<ModelView> modelViewList = new ArrayList<>();
    private final ModelCreateDto modelCreateDto = new ModelCreateDto();
    private final ModelEditDto modelEditDto = new ModelEditDto();
    private final Brand brand1 = new Brand();
    private final Brand brand2 = new Brand();




    @BeforeEach
    void setUp() {
        Long model1 = 1L;
        Long model2 = 2L;
        Long userId1 = 1L;
        Long userId2 = 2L;
        Long offerId1 = 1L;
        Long offerId2 = 2L;
        Long brandId1 = 1L;
        Long brandId2 = 2L;

        User user1 = User.builder()
                .id(userId1)
                .username("UserTest1")
                .build();
        User user2 = User.builder()
                .id(userId2)
                .username("UserTest2")
                .build();

        brand1.setId(brandId1);
        brand1.setName("test1");
        brand1.setCreated(LocalDate.now());

        brand2.setId(brandId2);
        brand2.setName("test2");
        brand2.setCreated(LocalDate.now());

        Offer offer1 = Offer.builder()
                .id(offerId1)
                .description("Very Good to buy")
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .price(new BigDecimal("10000"))
                .seller(user1)
                .build();
        Offer offer2 = Offer.builder()
                .id(offerId2)
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .price(new BigDecimal("10000"))
                .description("Very Good to buy")
                .seller(user2)
                .build();

        modelList.addAll(
                List.of(
                        Model.builder()
                                .id(model1)
                                .name("test1")
                                .created(2008)
                                .brand(brand1)
                                .category(ModelCategory.Car)
                                .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_X6-2023_main.jpg")
                                .generation(1)
                                .offerList(List.of(offer1))
                                .userList(List.of())
                                .build(),
                        Model.builder()
                                .id(model2)
                                .name("test2")
                                .created(2014)
                                .brand(brand2)
                                .category(ModelCategory.Car)
                                .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_M4-Competition-Convertible-M-xDrive-2021_main.jpg")
                                .generation(2)
                                .offerList(List.of(offer2))
                                .userList(List.of())
                                .build()
        ));
        ModelUserView modelUserView1 = new ModelUserView(userId1.toString() , "UserTest1");
        ModelUserView modelUserView2 = new ModelUserView(userId2.toString() , "UserTest2");

        ModelBrandView modelBrandView1 = new ModelBrandView(brandId1.toString() , "test1" , LocalDate.now().toString());
        ModelBrandView modelBrandView2 = new ModelBrandView(brandId2.toString() , "test2" , LocalDate.now().toString());

        ModelOfferView modelOfferView1 = new ModelOfferView(offerId1.toString(),"Very Good to buy" , "10000" , LocalDate.now().toString() ,modelUserView1 );
        ModelOfferView modelOfferView2 = new ModelOfferView(offerId2.toString(),"Very Good to buy" , "10000" , LocalDate.now().toString() ,modelUserView2 );

        ModelView modelView1 =
                new ModelView(model1.toString() ,
                        "test1",
                        "Car",
                        "https://s1.cdn.autoevolution.com/images/models/BMW_X6-2023_main.jpg",
                        "2008",
                        "1",
                        modelBrandView1,
                        List.of(modelOfferView1),
                        List.of());

        ModelView modelView2 =
                new ModelView(model2.toString() ,
                        "test2",
                        "Car",
                        "https://s1.cdn.autoevolution.com/images/models/BMW_M4-Competition-Convertible-M-xDrive-2021_main.jpg",
                        "2014",
                        "2",
                        modelBrandView2,
                        List.of(modelOfferView2),
                        List.of());

        modelViewList.addAll(List.of(modelView1 , modelView2));

        modelCreateDto.setName("createDtoTest");
        modelCreateDto.setCategory("Truck");
        modelCreateDto.setCreated(2000);
        modelCreateDto.setGeneration(1);
        modelCreateDto.setBrandName(brand1.getName());

        modelEditDto.setName("editDtoTest");
        modelEditDto.setCategory("Truck");
        modelEditDto.setCreated(2010);
        modelEditDto.setGeneration(2);
        modelEditDto.setBrandName(brand2.getName());

    }

    @Test
    void getModelViewById_ConversionToView_Successful() throws NotFoundException, WrongCredentialsException {
        Model model = modelList.get(0);
        ModelView expected = modelViewList.get(0);

        when(entityHelper.findModelById(model.getId().toString())).thenReturn(model);

        ModelView result = modelService.getModelViewById(model.getId().toString());

        assertEquals(expected , result);
    }

    @Test
    void getModelViewByName_ConversionToView_Successful() throws NotFoundException {
        Model model = modelList.get(0);
        ModelView expected = modelViewList.get(0);

        when(entityHelper.findModelByName(model.getName())).thenReturn(model);

        ModelView result = modelService.getModelViewByName(model.getName());

        assertEquals(expected , result);
    }

    @Test
    void getAllModelViews_ConversionToView_Successful() {
        when(modelRepository.findAll()).thenReturn(modelList);

        List<ModelView> result = modelService.getAllModelViews();

        assertEquals(modelViewList , result);
    }

    @Test
    void save_NoChangingData_Successful() throws NotFoundException, WrongCredentialsException {
        when(entityHelper.findBrandByName(modelCreateDto.getBrandName())).thenReturn(brand1);

        modelService.save(modelCreateDto);
        verify(modelRepository , times(1)).save(argumentCaptor.capture());

        Model result = argumentCaptor.getValue();

        assertEquals(modelCreateDto.getName() , result.getName());
        assertEquals(modelCreateDto.getCategory() , result.getCategory().name());
        assertEquals(modelCreateDto.getCreated() , result.getCreated());
        assertEquals(modelCreateDto.getBrandName() , result.getBrand().getName());
        assertEquals(modelCreateDto.getGeneration() , result.getGeneration());
        assertNotNull(result.getImageUrl());
    }

    @Test
    void findByName_IfNameExists_True() {
        Model model = modelList.get(0);
        when(modelRepository.findByName(model.getName())).thenReturn(Optional.of(model));

        assertTrue(modelService.findByName(model.getName()));
    }
    @Test
    void findByName_IfNameDoesNotExists_False() {
        String notExistingName = "Not persist";

        when(modelRepository.findByName(notExistingName)).thenReturn(Optional.empty());

        assertFalse(modelService.findByName(notExistingName));
    }

    @Test
    void edit_WithCorrectData_Successful() throws NotFoundException, WrongCredentialsException {
        Model modelToEdit = modelList.get(0);

        when(entityHelper.findModelById(modelToEdit.getId().toString())).thenReturn(modelToEdit);
        when(entityHelper.findBrandByName(modelEditDto.getBrandName())).thenReturn(brand2);

        ModelView result = modelService.edit(modelEditDto, modelToEdit.getId().toString());

        assertEquals(modelEditDto.getBrandName() , result.getBrand().getName());
        assertEquals(modelEditDto.getCategory() , result.getCategory());
        assertEquals(modelEditDto.getCreated().toString() , result.getCreated());
        assertEquals(modelEditDto.getGeneration().toString() , result.getGeneration());
        assertEquals(modelEditDto.getName() , result.getName());
    }
    @Test
    void edit_WithIncorrectData_NothingHappen() throws NotFoundException, WrongCredentialsException {
        ModelEditDto modelEditDto1 = new ModelEditDto();
        Model modelToEdit = modelList.get(0);

        when(entityHelper.findModelById(modelToEdit.getId().toString())).thenReturn(modelToEdit);

        assertDoesNotThrow(() -> modelService.edit(modelEditDto1 , modelToEdit.getId().toString()));
    }
    @Test
    void edit_WithIncorrectData_throwsWrongCredentialsException() throws NotFoundException, WrongCredentialsException {
        ModelEditDto modelEditDto1 = new ModelEditDto();
        modelEditDto1.setCategory("Invalid Data");
        Model modelToEdit = modelList.get(0);

        when(entityHelper.findModelById(modelToEdit.getId().toString())).thenReturn(modelToEdit);

        assertThrows( WrongCredentialsException.class,
                () -> modelService.edit(modelEditDto1 , modelToEdit.getId().toString()));
    }

    @Test
    void deleteModel() throws NotFoundException, WrongCredentialsException {
        Model model = modelList.get(0);
        when(entityHelper.findModelById(model.getId().toString())).thenReturn(model);

        modelService.deleteModel(model.getId().toString());

        verify(modelRepository , times(1)).delete(argumentCaptor.capture());

        Model result = argumentCaptor.getValue();

        assertEquals(model , result);
    }
}