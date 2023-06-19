package com.masterswork.organization.model.projection;

import java.util.HashSet;
import java.util.Set;

public interface OrganizationUnitParticipantsProjection {

    Long getHeadId();

    Set<Long> getParticipants();

    default Set<Long> getAllParticipants() {
        Set<Long> participants = new HashSet<>(getParticipants());
        participants.add(getHeadId());
        return participants;
    }

}
