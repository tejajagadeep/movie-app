package com.cts.moviebooking.serviceimpl;

import com.cts.moviebooking.dto.UserDetailsRequestDto;
import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.entity.UserEntity;
import com.cts.moviebooking.exception.InvalidPasswordException;
import com.cts.moviebooking.exception.UserNotFoundException;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.service.UserRegistrationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImp implements UserRegistrationService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;




    @Override
    public UserDetailsResponseDto createUser(UserDetailsRequestDto userDetails) {

        ModelMapper modelMapper = new ModelMapper();
        //it will map only when both field name matches
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails,UserEntity.class);

        //setting the role
        userEntity.setRole("User");


        String enctryptedPassword1 = bCryptPasswordEncoder.encode(userDetails.getPassword());
        userEntity.setPassword(enctryptedPassword1);
        String enctryptedPassword2 = bCryptPasswordEncoder.encode(userDetails.getConfirmPassword());
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
    public UserDetailsResponseDto getByUserNameAndPassword(String userName, String password) {

        UserEntity user = userRepo.findByUserName(userName);

        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + userName);
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password for user: " + userName);
        }

        UserDetailsResponseDto response = mapUserToResponseDto(user);
        response.setRole(user.getRole());
        System.out.println("in service "+response.getRole());
        return response;
    }



    private UserDetailsResponseDto mapUserToResponseDto(UserEntity user) {
        UserDetailsResponseDto responseDto = new UserDetailsResponseDto();

        responseDto.setUserName(user.getUserName());
        responseDto.setEmail(user.getEmail());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setPassword(user.getPassword());
        responseDto.setRole(user.getRole());

        return responseDto;
    }

}
