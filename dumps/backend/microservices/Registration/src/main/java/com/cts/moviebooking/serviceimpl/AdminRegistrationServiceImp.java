package com.cts.moviebooking.serviceimpl;

import com.cts.moviebooking.dto.BasicUserDetails;
import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.entity.UserEntity;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.service.AdminRegistrationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRegistrationServiceImp implements AdminRegistrationService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetailsResponseDto createAdmin(UserDetailsRequestDto adminDetails) {
        ModelMapper modelMapper = new ModelMapper();
        //it will map only when both field name matches
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(adminDetails,UserEntity.class);

        //setting the role
        userEntity.setRole("Admin");


        String enctryptedPassword1 = bCryptPasswordEncoder.encode(adminDetails.getPassword());
        userEntity.setPassword(enctryptedPassword1);
        String enctryptedPassword2 = bCryptPasswordEncoder.encode(adminDetails.getConfirmPassword());
        userEntity.setConfirmPassword(enctryptedPassword2);


        userRepo.save(userEntity);

        UserDetailsResponseDto userDetailsResponseDto = new UserDetailsResponseDto();

        userDetailsResponseDto.setFirstName(userEntity.getFirstName());
        userDetailsResponseDto.setUserName(userEntity.getUserName());
        userDetailsResponseDto.setEmail(userEntity.getEmail());
        userDetailsResponseDto.setPassword(userEntity.getPassword());
        userDetailsResponseDto.setRole(userEntity.getRole());

        return userDetailsResponseDto;
    }

    @Override
    public List<BasicUserDetails> getAllUsers() {
        List<UserEntity> allUserDetails = userRepo.findAll();


        List<BasicUserDetails> basicUserDetailsList = allUserDetails.stream()
                .map(userEntity -> mapUserEntityToBasicUserDetails(userEntity))
                .collect(Collectors.toList());

        return basicUserDetailsList;
    }

    private BasicUserDetails mapUserEntityToBasicUserDetails(UserEntity userEntity) {
        BasicUserDetails basicUserDetails = new BasicUserDetails();
        basicUserDetails.setFirstName(userEntity.getFirstName());
        basicUserDetails.setUserName(userEntity.getUserName());
        basicUserDetails.setEmail(userEntity.getEmail());
        basicUserDetails.setRole(userEntity.getRole());


        return basicUserDetails;
    }
}
