package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.brand.BrandCreateDto;
import com.example.mobilele.domain.dtos.brand.BrandEditDto;
import com.example.mobilele.domain.dtos.brand.BrandModelView;
import com.example.mobilele.domain.dtos.brand.BrandView;
import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.exceptions.AlreadyExistException;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.BrandRepository;
import com.example.mobilele.utils.EntityHelper;
import com.example.mobilele.utils.constants.ModelCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImpTest {

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private EntityHelper entityHelper;

    @InjectMocks
    private BrandServiceImp brandServiceImp;
    @Captor
    private ArgumentCaptor<Brand> brandArgumentCaptor;

    private final List<BrandView> brandViews = new ArrayList<>();
    private final List<Brand> brands = new ArrayList<>();

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        brands.addAll(List.of(
                Brand.builder()
                        .id(id1)
                        .name("BMWW")
                        .created(LocalDate.parse("1916-03-07", formatter))
                        .modified(LocalDate.parse("1924-07-11", formatter))
                        .modelList(List.of(
                                        Model.builder()
                                                .id(id3)
                                                .name("s 25 test")
                                                .category(ModelCategory.Car)
                                                .imageUrl("testImageUrl")
                                                .build()
                                )
                        )
                        .build(),
                Brand.builder()
                        .id(id2)
                        .name("Mercedes-Benz")
                        .created(LocalDate.parse("1926-02-18", formatter))
                        .modified(LocalDate.parse("1956-02-13", formatter))
                        .modelList(List.of())
                        .build()));
        brandViews.addAll(List.of(
                new BrandView(id1.toString(), "BMWW", "1916-03-07", "1924-07-11", List.of(
                        new BrandModelView(id3.toString(), "s 25 test", "Car", "testImageUrl")
                )),
                new BrandView(id2.toString(), "Mercedes-Benz", "1926-02-18", "1956-02-13", List.of())
        ));
    }

    @Test
    void getAllBrandsView_ConvertToView_Successfuly() {
        when(brandRepository.findAll()).thenReturn(brands);

        List<BrandView> result = brandServiceImp.getAllBrandsView();

        assertEquals(brandViews, result);
    }

    @Test
    void getViewBrandById_ConvertToView_Successful() throws NotFoundException, WrongCredentialsException {
        Brand brand = brands.get(0);
        BrandView expected = brandViews.get(0);
        String validId = brand.getId().toString();

        when(entityHelper.findBrandById(validId)).thenReturn(brand);

        BrandView result = brandServiceImp.getViewBrandById(validId);

        assertEquals(expected, result);
    }

    @Test
    void getViewBrandByName_ConvertToView_Successful() throws NotFoundException {
        Brand brand = brands.get(0);
        String validName = brand.getName();
        BrandView expected = brandViews.get(0);

        when(entityHelper.findBrandByName(validName)).thenReturn(brand);

        BrandView result = brandServiceImp.getViewBrandByName(validName);

        assertEquals(expected, result);
    }

    @Test
    void save_ValidName_Successful() throws AlreadyExistException {
        LocalDate date = LocalDate.now();
        String name = "Something Valid";
        BrandCreateDto createDto = new BrandCreateDto(name, date.toString());
        Brand expected = Brand.builder().name(name).created(date).build();

        brandServiceImp.save(createDto);

        verify(brandRepository, times(1)).save(brandArgumentCaptor.capture());
        Brand result = brandArgumentCaptor.getValue();

        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getCreated(), result.getCreated());

    }

    @Test
    void save_AlreadyExistingName_throwsAlreadyExistException() {

        Brand brand = brands.get(0);
        String busyName = brand.getName();
        BrandCreateDto invalidNameDto = new BrandCreateDto(busyName, LocalDate.now().toString());

        when(brandRepository.findByName(busyName)).thenReturn(Optional.of(brand));

        assertThrows(AlreadyExistException.class, () -> brandServiceImp.save(invalidNameDto));
    }

    @Test
    void edit_WithEmptyBody_SuccessfulChangeToView() throws NotFoundException, WrongCredentialsException {
        BrandEditDto brandEditDto = new BrandEditDto();
        Brand expected = brands.get(0);
        BrandView expected2 = brandViews.get(0);

        when(entityHelper.findBrandById(expected.getId().toString())).thenReturn(expected);
        BrandView result2 = brandServiceImp.edit(brandEditDto, expected.getId().toString());

        verify(brandRepository, times(1)).save(brandArgumentCaptor.capture());
        Brand result = brandArgumentCaptor.getValue();

        assertEquals(expected, result);
        assertNotEquals(expected2.getModified(), result2.getModified());
        assertEquals(expected2.getName(), result2.getName());
        assertEquals(expected2.getCreated(), result2.getCreated());
    }

    @Test
    void edit_WithNonEmptyBody_SuccessfulChangeToView() throws NotFoundException, WrongCredentialsException {
        BrandEditDto brandEditDto = new BrandEditDto("Mercedes", LocalDate.parse("1900-02-23"));
        Brand brand = brands.get(0);
        BrandView expected2 = brandViews.get(0);

        when(entityHelper.findBrandById(brand.getId().toString())).thenReturn(brand);
        BrandView result2 = brandServiceImp.edit(brandEditDto, brand.getId().toString());

        assertNotEquals(expected2.getModified(), result2.getModified());
        assertNotEquals(expected2.getName(), result2.getName());
        assertNotEquals(expected2.getCreated(), result2.getCreated());
    }

    @Test
    void edit_WithWrongName_ThrowsWrongCredentialsException() throws NotFoundException, WrongCredentialsException {
        BrandEditDto brandEditDto = new BrandEditDto("Mer", LocalDate.parse("1900-02-23"));
        Brand brand = brands.get(0);

        when(entityHelper.findBrandById(brand.getId().toString())).thenReturn(brand);

        assertThrows(WrongCredentialsException.class,
                () -> brandServiceImp.edit(brandEditDto, brand.getId().toString()));
    }

    @Test
    void deleteBrand() throws NotFoundException, WrongCredentialsException {
        Brand brand = brands.get(0);
        String validBrandId = brand.getId().toString();

        when(entityHelper.findBrandById(validBrandId)).thenReturn(brand);

        brandServiceImp.deleteBrand(validBrandId);

        verify(brandRepository).delete(brand);
    }
}