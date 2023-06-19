package com.masterswork.organization.model;

import com.masterswork.organization.model.base.AuditedEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization")
public class OrganizationUnit extends AuditedEntity {

    @Id
    @Column(name = "organization_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "head_id", nullable = false)
    private Long headId;

    @ElementCollection
    @CollectionTable(name = "organization_participants", joinColumns = @JoinColumn(name="organization_id"))
    @Column(name="participant_id")
    private Set<Long> participants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OrganizationUnit owner;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<OrganizationUnit> subs = new HashSet<>();

    public OrganizationUnit setOwner(OrganizationUnit owner) {
        if (Objects.equals(this.getId(), owner.getId())) {
            throw new IllegalArgumentException("Organization can not bet it's own owner");
        }
        if (this.owner != null) {
            this.owner.getSubs().remove(this);
        }
        this.owner = owner;
        owner.getSubs().add(this);
        return this;
    }

    public OrganizationUnit removeOwner() {
        if (this.owner != null) {
            this.owner.getSubs().remove(this);
            this.owner = null;
        }
        return this;
    }

    public OrganizationUnit setHeadId(Long headId) {
        this.headId = headId;
        this.participants.add(headId);
        return this;
    }
}
