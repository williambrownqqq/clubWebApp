package com.alex.zanchenko.web.service.impl;

import com.alex.zanchenko.web.dto.ClubDTO;
import com.alex.zanchenko.web.mapper.ClubMapper;
import com.alex.zanchenko.web.model.Club;
import com.alex.zanchenko.web.model.UserEntity;
import com.alex.zanchenko.web.repository.ClubRepository;
import com.alex.zanchenko.web.repository.UserRepository;
import com.alex.zanchenko.web.security.SecurityUtil;
import com.alex.zanchenko.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alex.zanchenko.web.mapper.ClubMapper.mapToClub;
import static com.alex.zanchenko.web.mapper.ClubMapper.mapToClubDTO;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<ClubDTO> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club) -> mapToClubDTO(club)).collect(Collectors.toList());
        // we are going to map to ClubDto because we
        //need to return a clubDTO but we're just
        //getting clubs back from our database
        //why do we need to actually map this and or
        //why do we need to turn this into a club
        //DTO because it's not the same type and
        //in the future when our code becomes more
        //complicated we're going to want that dto
        //so we're trading a minor amount of
        //inconvenience for the ability to be able
        //to turn this into a dto and be able to
        //shape this data
        // However we want to so that we can display it in more secure
        // and more convenient ways on our actual
        // webpage and the way that we do this is
        // we create this thing called a mapper
    }

    @Override
    public Club saveClub(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDTO);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDTO findClubById(Long clubID) {
        Club club = clubRepository.findById(clubID).get();
        return mapToClubDTO(club);
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = mapToClub(clubDTO);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(Long clubID) {
        clubRepository.deleteById(clubID);
    }

    @Override
    public List<ClubDTO> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(ClubMapper::mapToClubDTO).collect(Collectors.toList());
    }


}
