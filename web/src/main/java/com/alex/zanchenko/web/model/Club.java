package com.alex.zanchenko.web.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GeneratedValue is what's going to increment this ID right here so that we have a unique ID
    private Long id;
    private String title;
    private String photoURL;
    private String content;
    @CreationTimestamp // so we do have the creation timestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    // both timestamps automatically add these dates and whenever it's updated

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;
    @JsonManagedReference
    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE) // cascade of remove
    private List<Event> events = new ArrayList<>();
    // key point - whenever the child is removed it's going to actually remove or whenever the parent is removed is going to remove the child
    // so that's what this Cascade means
    // if there is a entity that is removed from the relationship then that's when you want to perform the same operation to the child
}
