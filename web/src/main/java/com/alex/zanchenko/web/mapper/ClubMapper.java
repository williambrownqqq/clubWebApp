package com.alex.zanchenko.web.mapper;

import com.alex.zanchenko.web.dto.ClubDTO;
import com.alex.zanchenko.web.model.Club;

import java.util.stream.Collectors;

import static com.alex.zanchenko.web.mapper.EventMapper.mapToEventDTO;

public class ClubMapper {

    public static Club mapToClub(ClubDTO clubDTO) {
        Club club = Club.builder()
                .id(clubDTO.getId())
                .title(clubDTO.getTitle())
                .content(clubDTO.getContent())
                .photoURL(clubDTO.getPhotoURL())
                .createdBy(clubDTO.getCreatedBy())
                .createdOn(clubDTO.getCreatedOn())
                .updatedOn(clubDTO.getUpdatedOn())
                .build();
        return club;
    }

    public static ClubDTO mapToClubDTO(Club club){ // mapper
        ClubDTO clubDTO = ClubDTO.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoURL(club.getPhotoURL())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map(event ->  mapToEventDTO(event)).collect(Collectors.toList()))
                .build();
        return clubDTO;
    }
    //  the method mapToClubDTO can be considered a mapper.
    //  It takes an instance of the Club class and maps its properties to a ClubDTO (Data Transfer Object) instance.
    // In this mapper method, the properties of the Club object are copied or assigned to the corresponding properties of the ClubDTO object.
    // The ClubDTO object is constructed using a builder pattern, where the values from the Club object are set for each property.
    // By using a mapper like this, you can convert an entity object (such as Club) to a DTO object (ClubDTO) that is specifically designed for transferring data between different layers or components of an application.
    // This helps to separate concerns and maintain a clear distinction between the domain model and the data transfer objects used for communication.
    // The mapToClubDTO method takes care of the mapping logic, ensuring that the relevant properties are copied over correctly. It provides a convenient way to convert a Club object to its corresponding ClubDTO representation.

}
