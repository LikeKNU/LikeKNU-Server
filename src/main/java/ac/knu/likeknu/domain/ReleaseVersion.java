package ac.knu.likeknu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Table(name = "release_version")
@Entity
public class ReleaseVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String version;

    private LocalDate releaseDate;

    protected ReleaseVersion() {
    }

    public ReleaseVersion(String version, LocalDate releaseDate) {
        this.version = version;
        this.releaseDate = releaseDate;
    }
}
