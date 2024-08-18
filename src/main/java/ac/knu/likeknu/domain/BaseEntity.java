package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.constants.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false, length = 60)
    private String id;

    protected BaseEntity() {
    }

    protected BaseEntity(Domain domain) {
        id = String.join("", domain.toString().toLowerCase(), "_",
                UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
