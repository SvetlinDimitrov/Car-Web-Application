package com.example.mobilele.utils;

import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.BrandRepository;
import com.example.mobilele.repos.ModelRepository;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityHelperTest {

    @Mock
    private  ModelRepository modelRepository;
    @Mock
    private  OfferRepository offerRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  BrandRepository brandRepository;
    @InjectMocks
    private EntityHelper entityHelper;

    private final User user = new User();
    private final Brand brand = new Brand();
    private final Offer offer = new Offer();
    private final Model model = new Model();


    @BeforeEach
    void setUp() {
        user.setId(UUID.randomUUID());
        user.setUsername("Test");

        brand.setId(UUID.randomUUID());
        brand.setName("Mercedes");

        offer.setId(UUID.randomUUID());

        model.setId(UUID.randomUUID());
        model.setName("s 25 test");
    }

    @Test
    void findUserById_WithTheCorrectId_Successful() throws NotFoundException, WrongCredentialsException {
        UUID correctId = user.getId();

        when(userRepository.findById(correctId)).thenReturn(Optional.of(user));

        User result = entityHelper.findUserById(correctId.toString());

        assertEquals(user , result);
    }
    @Test
    void findUserById_WithWrongId_ThrowNotFoundExceptionException() {
        UUID wrongId = UUID.randomUUID();
        String expectedMessage = "User with id :"+ wrongId +" does not exists";

        when(userRepository.findById(wrongId)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> entityHelper.findUserById(wrongId.toString()));

        assertEquals(expectedMessage , result.getMessage());


    }

    @Test
    void findOfferById_WithTheCorrectId_Successful() throws NotFoundException, WrongCredentialsException {
        UUID correctId = offer.getId();

        when(offerRepository.findById(correctId)).thenReturn(Optional.of(offer));

        Offer result = entityHelper.findOfferById(correctId.toString());

        assertEquals(offer , result);
    }

    @Test
    void findOfferById_WithWrongId_ThrowNotFoundExceptionException(){
        UUID wrongId = UUID.randomUUID();
        String expectedMessage = "Offer with id :"+ wrongId +" does not exists";

        when(offerRepository.findById(wrongId)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> entityHelper.findOfferById(wrongId.toString()));

        assertEquals(expectedMessage , result.getMessage());
    }

    @Test
    void findBrandById_WithTheCorrectId_Successful() throws NotFoundException, WrongCredentialsException {
        UUID correctId = brand.getId();

        when(brandRepository.findById(correctId)).thenReturn(Optional.of(brand));

        Brand result = entityHelper.findBrandById(correctId.toString());

        assertEquals(brand , result);
    }
    @Test
    void findBrandById_WithWrongId_ThrowNotFoundExceptionException(){
        UUID wrongId = UUID.randomUUID();
        String expectedMessage = "Brand with id :"+ wrongId +" does not exists";

        when(brandRepository.findById(wrongId)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> entityHelper.findBrandById(wrongId.toString()));

        assertEquals(expectedMessage , result.getMessage());
    }

    @Test
    void findModelById_WithTheCorrectId_Successful() throws NotFoundException, WrongCredentialsException {
        UUID correctId = model.getId();

        when(modelRepository.findById(correctId)).thenReturn(Optional.of(model));

        Model result = entityHelper.findModelById(correctId.toString());

        assertEquals(model , result);
    }
    @Test
    void findModelById_WithWrongId_ThrowNotFoundExceptionException()  {
        UUID wrongId = UUID.randomUUID();
        String expectedMessage = "Model with id :"+ wrongId +" does not exists";

        when(modelRepository.findById(wrongId)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> entityHelper.findModelById(wrongId.toString()));

        assertEquals(expectedMessage , result.getMessage());
    }

    @Test
    void findModelByName_WithCorrectName_Successful() throws NotFoundException {
        String correctName = model.getName();

        when(modelRepository.findByName(correctName)).thenReturn(Optional.of(model));

        Model result = entityHelper.findModelByName(correctName);

        assertEquals(model , result);
    }

    @Test
    void findModelByName_WithWrongName_throwNotFoundException(){
        String wrongName = "Wrong";
        String expectedMessage = "Model "+ wrongName + " does not exists";

        when(modelRepository.findByName(wrongName)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> entityHelper.findModelByName(wrongName));

        assertEquals(expectedMessage , result.getMessage());
    }

    @Test
    void findBrandByName_WithCorrectName_Successful() throws NotFoundException {
        String correctName = brand.getName();

        when(brandRepository.findByName(correctName)).thenReturn(Optional.of(brand));

        Brand result = entityHelper.findBrandByName(correctName);

        assertEquals(brand , result);
    }
    @Test
    void findBrandByName_WithWrongName_throwNotFoundException(){
        String wrongName = "Wrong";
        String expectedMessage = "Brand "+ wrongName + " does not exists";

        when(brandRepository.findByName(wrongName)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> entityHelper.findBrandByName(wrongName));

        assertEquals(expectedMessage , result.getMessage());
    }
    @Test
    void convertToUUID_WithWrongCredentials_throwsWrongCredentialsException(){
        String wrongUuidFormat = "dasdasdasdadasd";
        assertThrows(WrongCredentialsException.class , () -> entityHelper.findBrandById(wrongUuidFormat));
    }
}