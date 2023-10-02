package com.alex.zanchenko.web.service;

import com.alex.zanchenko.web.dto.ClubDTO;
import com.alex.zanchenko.web.model.Club;

import java.util.List;

public interface ClubService {
    List<ClubDTO> findAllClubs();
    Club saveClub(ClubDTO clubDTO);

    ClubDTO findClubById(Long clubID);

    void updateClub(ClubDTO club);

    void delete(Long clubID);

    List<ClubDTO> searchClubs(String query);
}
