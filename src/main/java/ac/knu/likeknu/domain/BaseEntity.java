package ac.knu.likeknu.domain;

import ac.knu.likeknu.domain.value.Domain;
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
    @Column(name = "id", nullable = false, length = 60)
    private String id;

    private BaseEntity() {
    }

    protected BaseEntity(Domain domain) {
        id = String.join("", domain.toString().toLowerCase(), "_",
                UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        BaseEntity that = (BaseEntity) object;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
