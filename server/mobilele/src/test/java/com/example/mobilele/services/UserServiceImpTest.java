package com.example.mobilele.services;

import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.dtos.user.*;
import com.example.mobilele.domain.entity.*;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.UserRepository;
import com.example.mobilele.repos.UserRoleRepository;
import com.example.mobilele.utils.constants.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EntityHelper entityHelper;
    @InjectMocks
    private UserServiceImp userService;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;
    private final UserRole user = new UserRole();
    private final UserRole admin = new UserRole();
    private final List<User> userList = new ArrayList<>();
    private final List<UserView> userViews = new ArrayList<>();
    private final UserEditDto userEditDto = new UserEditDto();
    private final UserLoginDto userLoginDto = new UserLoginDto();
    private final UserRegisterDto userRegisterDto = new UserRegisterDto();


    @BeforeEach
    void setUp() {
        Long user1 = 1L;
        Long user2 = 2L;
        Long userRole = 1L;
        Long adminRole = 2L;
        Long offerId1 = 1L;
        Long offerId2 = 2L;
        Long modelId1 = 1L;
        Long modelId2 = 2L;
        Long brandId1 = 1L;
        Long brandId2 = 2L;


        user.setId(userRole);
        user.setRole(Role.USER);
        user.setUserList(List.of());
        admin.setId(adminRole);
        admin.setRole(Role.ADMIN);
        admin.setUserList(List.of());

        Brand brand1 = Brand.builder()
                .id(brandId1)
                .name("test1")
                .created(LocalDate.now())
                .build();
        Brand brand2 = Brand.builder()
                .id(brandId2)
                .name("test2")
                .created(LocalDate.now())
                .build();

        Model model1 = Model.builder()
                .id(modelId1)
                .name("Micobishi")
                .created(2023)
                .brand(brand1)
                .build();
        Model model2 = Model.builder()
                .id(modelId2)
                .name("BWm h 25")
                .created(2023)
                .brand(brand2)
                .build();

        Offer offer1 = Offer.builder()
                .id(offerId1)
                .description("Very Good to buy")
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .model(model1)
                .build();
        Offer offer2 = Offer.builder()
                .id(offerId2)
                .created(LocalDate.now())
                .modified(LocalDate.now())
                .description("Very Good to buy")
                .model(model2)
                .build();

        userList.addAll(List.of(
                User.builder()
                        .id(user1)
                        .isActive(Boolean.TRUE)
                        .created(LocalDate.now())
                        .firstName("Test")
                        .lastName("Testov")
                        .imageUrl("https://yt3.googleusercontent.com/ytc/AOPolaROJsray4rabTdqph4zpBJAt_01EwS5FbVlNfus=s900-c-k-c0x00ffffff-no-rj")
                        .modified(null)
                        .password("12345")
                        .username("Test")
                        .userRoles(List.of(user, admin))
                        .offerList(List.of(offer1, offer2))
                        .modelList(List.of(model1))
                        .build(),
                User.builder()
                        .id(user2)
                        .isActive(Boolean.TRUE)
                        .created(LocalDate.now())
                        .firstName("Test2")
                        .lastName("Test2ov")
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/2/27/Ivan_Abadjiev.jpg")
                        .modified(null)
                        .password("12345")
                        .username("Test2")
                        .userRoles(List.of(user))
                        .offerList(List.of())
                        .modelList(List.of(model2))
                        .build()));

        UserBrandView userBrandView1 = new UserBrandView();
        userBrandView1.setName("test1");
        userBrandView1.setCreated(LocalDate.now().toString());
        userBrandView1.setId(brandId1);
        userBrandView1.setModified("");
        UserBrandView userBrandView2 = new UserBrandView();
        userBrandView2.setId(brandId2);
        userBrandView2.setCreated(LocalDate.now().toString());
        userBrandView2.setName("test2");
        userBrandView2.setModified("");

        UserModelView userModelView1 = new UserModelView();
        userModelView1.setId(modelId1);
        userModelView1.setName("Micobishi");
        userModelView1.setCreated(2023);
        userModelView1.setBrand(userBrandView1);
        UserModelView userModelView2 = new UserModelView();
        userModelView2.setId(modelId2);
        userModelView2.setName("BWm h 25");
        userModelView2.setCreated(2023);
        userModelView2.setBrand(userBrandView2);

        UserOfferView userOfferView1 = new UserOfferView();
        userOfferView1.setId(offerId1);
        userOfferView1.setDescription("Very Good to buy");
        userOfferView1.setCreated(LocalDate.now().toString());
        userOfferView1.setModified(LocalDate.now().toString());
        userOfferView1.setModel(userModelView1);
        UserOfferView userOfferView2 = new UserOfferView();
        userOfferView2.setId(offerId2);
        userOfferView2.setDescription("Very Good to buy");
        userOfferView2.setCreated(LocalDate.now().toString());
        userOfferView2.setModified(LocalDate.now().toString());
        userOfferView2.setModel(userModelView2);

        userViews.addAll(
                List.of(
                        new UserView(user1,
                                "Test",
                                "Test",
                                "Testov",
                                true,
                                "https://yt3.googleusercontent.com/ytc/AOPolaROJsray4rabTdqph4zpBJAt_01EwS5FbVlNfus=s900-c-k-c0x00ffffff-no-rj",
                                LocalDate.now().toString(),
                                "",
                                List.of(userOfferView1, userOfferView2),
                                List.of(userModelView1),
                                List.of("USER", "ADMIN")),
                        new UserView(user2,
                                "Test2",
                                "Test2",
                                "Test2ov",
                                true,
                                "https://upload.wikimedia.org/wikipedia/commons/2/27/Ivan_Abadjiev.jpg",
                                LocalDate.now().toString(),
                                "",
                                List.of(),
                                List.of(userModelView2),
                                List.of("USER"))

                ));

        userEditDto.setUsername("Test2");
        userEditDto.setFirstName("Test2");
        userEditDto.setLastName("Test2ov");
        userEditDto.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/2/27/Ivan_Abadjiev.jpg");

        userRegisterDto.setUsername("Test2");
        userRegisterDto.setFirstName("Test2");
        userRegisterDto.setLastName("Test2ov");
        userRegisterDto.setPassword("12345");
        userRegisterDto.setConfirmPassword("12345");

        userLoginDto.setPassword("12345");
        userLoginDto.setUsername("Test2");
    }

    @Test
    void getById_ReturnTheCorrectUser_Successful() throws NotFoundException, WrongCredentialsException {
        User user = userList.get(0);

        when(entityHelper.findUserById(user.getId().toString())).thenReturn(user);

        User result = userService.getById(user.getId().toString());

        assertEquals(user, result);
    }

    @Test
    void getAllUsers_ConvertToViews_Successfuly() {
        when(userRepository.findAll()).thenReturn(userList);

        List<UserView> result = userService.getAllUsers();

        assertEquals(userViews, result);
    }

    @Test
    void register_ConvertRegisterDtoToUser_Successful() {

        when(passwordEncoder.encode(userRegisterDto.getPassword())).thenReturn(userRegisterDto.getPassword());
        when(userRoleRepository.findByRole(Role.USER)).thenReturn(user);

        userService.register(userRegisterDto);

        verify(userRepository , times(1)).save(argumentCaptor.capture());

        User result = argumentCaptor.getValue();

        assertEquals(userRegisterDto.getUsername() , result.getUsername());
        assertEquals(userRegisterDto.getFirstName() , result.getFirstName());
        assertEquals(userRegisterDto.getLastName() , result.getLastName());
        assertEquals(userRegisterDto.getPassword() , result.getPassword());
        assertEquals(List.of(user) , result.getUserRoles());

    }

    @Test
    void login_CovertLoginUserToPrincipal_Successfuly() throws WrongCredentialsException {
        User user = userList.get(1);
        when(userRepository.findByUsername(userLoginDto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userLoginDto.getPassword() , user.getPassword())).thenReturn(true);

        UserPrincipal result = userService.login(userLoginDto);

        assertEquals(user.getId().toString() , result.getId());
        assertEquals(user.getUsername() , result.getUsername());
    }

    @Test
    void login_WithNotExistingUser_throwsWrongCredentials(){
        String expectedErrorMessage = "Username or password does not match";
        when(userRepository.findByUsername(userLoginDto.getUsername())).thenReturn(Optional.empty());

        WrongCredentialsException result = assertThrows(WrongCredentialsException.class,
                () -> userService.login(userLoginDto));

        assertEquals(List.of(expectedErrorMessage) , result.getMessages());

    }
    @Test
    void editUser_ValidUserCredentials_Successful() throws NotFoundException, WrongCredentialsException {
        User userToEdit = userList.get(0);
        when(entityHelper.findUserById(userToEdit.getId().toString())).thenReturn(userToEdit);
        UserView result = userService.editUser(userEditDto, userToEdit.getId().toString());

        assertEquals(userEditDto.getUsername() , result.getUsername());
        assertEquals(userEditDto.getFirstName() , result.getFirstName());
        assertEquals(userEditDto.getLastName() , result.getLastName());
        assertEquals(userEditDto.getImageUrl() , result.getImageUrl());
    }

    @Test
    void editUser_InvalidUserCredentials_throwsWrongCredentials() throws NotFoundException, WrongCredentialsException {
        User userToEdit = userList.get(0);
        UserEditDto wrongUsernameEditDto = new UserEditDto();
        wrongUsernameEditDto.setUsername("INV");

        when(entityHelper.findUserById(userToEdit.getId().toString())).thenReturn(userToEdit);

        assertThrows(WrongCredentialsException.class ,
                () -> userService.editUser(wrongUsernameEditDto, userToEdit.getId().toString()));

        UserEditDto wrongFirstNameEditDto = new UserEditDto();
        wrongFirstNameEditDto.setFirstName("IN");

        assertThrows(WrongCredentialsException.class ,
                () -> userService.editUser(wrongFirstNameEditDto, userToEdit.getId().toString()));

        UserEditDto wrongPasswordEditDto = new UserEditDto();
        wrongPasswordEditDto.setPassword("IN");

        assertThrows(WrongCredentialsException.class ,
                () -> userService.editUser(wrongPasswordEditDto, userToEdit.getId().toString()));

        UserEditDto wrongLastNameEditDto = new UserEditDto();
        wrongLastNameEditDto.setLastName("IN");

        assertThrows(WrongCredentialsException.class ,
                () -> userService.editUser(wrongLastNameEditDto, userToEdit.getId().toString()));
    }

    @Test
    void deleteUser_DeleteTheCorrectUser_Successful() throws NotFoundException, WrongCredentialsException {
        User userToDelete = userList.get(0);

        when(entityHelper.findUserById(userToDelete.getId().toString())).thenReturn(userToDelete);

        userService.deleteUser(userToDelete.getId().toString());

        verify(userRepository , times(1)).delete(argumentCaptor.capture());
        User result = argumentCaptor.getValue();

        assertEquals(userToDelete , result);
    }

    @Test
    void isAdmin_ReturnTrueForAdminCredentials_Successful() {
        User admin = userList.get(0);
        assertTrue(userService.isAdmin(admin));
    }
}