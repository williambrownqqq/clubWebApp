package com.masterswork.account.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum PersonType {
    TEACHER("Teacher"),
    STUDENT("Student"),
    ADMIN("Admin");

    private final String displayName;

    public static Set<String> getAllTypesNames() {
        return Arrays.stream(PersonType.values())
                .map(PersonType::getDisplayName)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
